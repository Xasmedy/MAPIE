/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import xasmedy.mapie.menu.entity.Button;
import xasmedy.mapie.menu.entity.ClosureType;
import xasmedy.mapie.menu.entity.MenuTemplate;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public final class Menus {

    private static final AtomicBoolean INSTANCIED = new AtomicBoolean(false);
    private static final int FORCE_CLOSE_ID = -2;
    private static final Function<Integer, MenuTemplate> TEMPLATE_CONSTRUCTOR = MenuTemplateImpl::new;

    /**
     * Only one menu per player. (I don't see a reason to have more than one)
     */
    private final HashMap<Player, MenuPanel> playersMenu = new HashMap<>();

    public Menus() {

        if (INSTANCIED.getAndSet(true)) throw new IllegalStateException("Cannot instantiate singleton.");

        Events.on(EventType.PlayerLeave.class, e -> {
            final MenuPanel menu = playersMenu.get(e.player);
            if (menu == null) return;
            closeActiveMenu(menu, ClosureType.PLAYER_LEAVE);
        });
    }

    private void handleMenuAction(Player player, int option) {

        final MenuPanel menu = playersMenu.get(player);
        // The menu has been closed badly by the player.
        if (menu == null) {
            Log.debug("Bad menu closure for player \"" + player.uuid() + "\"");
            return;
        }

        // When the player exits the menu by using other means.
        // e.g. clicking outside the menu, pressing esc.
        if (option == -1) {
            closeActiveMenu(menu, ClosureType.LOST_FOCUS);
            return;
        }

        // Custom closure type that indicates that the menu has been closed by force.
        if (option == FORCE_CLOSE_ID) {
            closeActiveMenu(menu, ClosureType.FORCED);
            return;
        }

        final Button button = menu.getCurrentTemplate()
                .getButtonList()
                .getMindustryOption(option);

        // I reload myself, making it look like nothing happen.
        if (button.isDisabled()) menu.update();
        else button.getListener().ifPresent(listener -> listener.action(menu));
    }

    void closeActiveMenu(MenuPanel menu, ClosureType type) {
        playersMenu.remove(menu.getPlayer());
        menu.getCurrentTemplate()
                .getCloseListener()
                .ifPresent(listener -> listener.action(menu, type));
    }

    public MenuTemplate registerNewMenu(Function<Integer, MenuTemplate> constructor) {
        final int id = mindustry.ui.Menus.registerMenu(this::handleMenuAction);
        return constructor.apply(id);
    }

    public void forceMenuClose(Player player) {
        final MenuPanel menu = playersMenu.get(player);
        if (menu == null) return;
        Call.menuChoose(player, menu.getCurrentTemplate().getId(), FORCE_CLOSE_ID);
    }

    public void displayMenu(Player player, MenuTemplate template) {
        // I get a copy to avoid unwanted changes during the menu life.
        final MenuPanel menuPanel = new MenuPanel(this, player, Objects.requireNonNull(template.copy()));
        playersMenu.put(player, menuPanel);
        menuPanel.update(); // I display the menu.
    }

    public void displayMenu(MenuTemplate template) {
        for (Player player : Groups.player) displayMenu(player, template);
    }

    public Function<Integer, MenuTemplate> getDefaultTemplateConstructor() {
        return TEMPLATE_CONSTRUCTOR;
    }

    public Button newDefaultButton(String label, boolean disabled) {
        return new ButtonImpl(label, disabled);
    }

    public Button newDefaultButton(String label) {
        return newDefaultButton(label, false);
    }

    public Button newDefaultButton() {
        return newDefaultButton("", false);
    }
}
