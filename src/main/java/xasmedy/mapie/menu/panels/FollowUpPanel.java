/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.panels;

import mindustry.gen.Call;
import mindustry.gen.Player;
import xasmedy.mapie.menu.*;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Implementation using {@link Call#followUpMenu}.
 * @param <T> User decided template.
 */
@SuppressWarnings("unused")
public class FollowUpPanel<T extends Template> implements Panel {

    private static final Consumer<Button> DEFAULT = button -> {};
    private final Menu menu;
    private final Player player;
    private Consumer<Button> endAction = DEFAULT;
    protected T current;

    public FollowUpPanel(Player player, T current) {
        this.menu = Menu.init();
        this.player = Objects.requireNonNull(player);
        this.current = Objects.requireNonNull(current);
        menu.registerPanel(player, this);
    }

    /**
     * Overrides the current template and displays the new one.
     * @param template The new template to display.
     */
    public void display(T template) {
        this.current = Objects.requireNonNull(template);
        update();
    }

    /**
     * Sets an action to execute after a button is triggered.<br>
     * This can be useful when some buttons share the same code.
     */
    public void endAction(Consumer<Button> listener) {
       endAction = Objects.requireNonNull(listener);
    }

    @Override
    public Player player() {
        return player;
    }

    @Override
    public T template() {
        return current;
    }

    @Override
    public void update() {
        final String[][] options = current.parser().asString();
        Call.followUpMenu(player.con(), menu.menuId, current.title(), current.message(), options);
    }

    @Override
    public void selectionEvent(Button onButton) {
        // I update myself making it look nothing happened.
        if (onButton.isDisabled()) {
            update();
            return;
        }
        onButton.listener().run();
        endAction.accept(onButton);
    }

    @Override
    public final void close() {
        Call.hideFollowUpMenu(menu.menuId);
        menu.removePanel(this, ClosureType.BY_PANEL);
    }
}
