/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import mindustry.gen.Call;
import mindustry.gen.Player;
import java.util.Objects;

/**
 * Implementation using {@link Call#followUpMenu}.
 * @param <T> User decided template.
 */
public class FollowUpPanel<T extends Template> implements Panel {

    private final Menus menus;
    private final Player player;
    protected T current;

    FollowUpPanel(Menus menus, Player player, T current) {
        this.menus = menus;
        this.player = player;
        this.current = current;
    }

    /**
     * Overrides the current template and displays the new one.
     * @param template The new template to display.
     */
    public void display(T template) {
        this.current = Objects.requireNonNull(template);
        update();
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
        Call.followUpMenu(player.con(), current.menuId(), current.title(), current.message(), options);
    }

    @Override
    public void selectionEvent(Button onButton) {
        // I update myself making it look nothing happened.
        if (onButton.isDisabled()) update();
        else onButton.listener().run();
    }

    @Override
    public final void close() {
        menus.closePanel(this, ClosureType.BY_PANEL);
    }
}
