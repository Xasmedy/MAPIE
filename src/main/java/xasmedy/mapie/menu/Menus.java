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
import mindustry.gen.Player;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public final class Menus {

    private static final AtomicBoolean INSTANCIED = new AtomicBoolean(false);
    @Deprecated
    private static final int FORCE_CLOSE_ID = -2;

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

    private void handleMenuAction(int menuId, Player player, int option) {

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

        // TODO Remove.
        // Custom closure type that indicates that the menu has been closed by force.
        if (option == FORCE_CLOSE_ID) {
            closeActiveMenu(menu, ClosureType.FORCED);
            return;
        }

        final Button button = menu.getTemplate()
                .parser()
                .get(option);

        // I reload myself, making it look like nothing happen.
        if (button.isDisabled()) menu.update();
        else button.listener().action(menu, button);
    }

    void closeActiveMenu(MenuPanel menu, ClosureType type) {
        playersMenu.remove(menu.getPlayer());
        menu.getTemplate()
                .closeListener()
                .action(menu, type);
    }

    public <T extends MenuTemplate> T registerNewMenu(Function<Integer, T> constructor) {

        Objects.requireNonNull(constructor);

        // set() will always be called before get().
        final AtomicInteger menuId = new AtomicInteger(-1);
        final int registeredId = mindustry.ui.Menus.registerMenu((Player player, int option) -> handleMenuAction(menuId.get(), player, option));
        menuId.set(registeredId);
        // I create the user-defined MenuTemplate.
        return constructor.apply(registeredId);
    }

    public void forceMenuClose(Player player) {
        final MenuPanel menu = playersMenu.get(player);
        if (menu == null) return;
        Call.menuChoose(player, menu.getTemplate().menuId(), FORCE_CLOSE_ID);
    }

    public void displayMenu(Player player, MenuTemplate template) {

        Objects.requireNonNull(player);
        Objects.requireNonNull(template);

        // I get a copy to avoid unwanted changes during the menu life.
        final MenuPanel menuPanel = new MenuPanel(this, player, template);
        playersMenu.put(player, menuPanel);
        menuPanel.update(); // I display the menu.
    }
}
