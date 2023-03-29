/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import arc.Events;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.ui.Menus;
import xasmedy.mapie.menu.builder.MenuTemplate;
import xasmedy.mapie.menu.button.Button;
import xasmedy.mapie.menu.close.ClosureType;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenusRegister {

    private static final AtomicBoolean INSTANCIED = new AtomicBoolean(false);

    /**
     * Only one menu per player. (I don't see a reason to have more than one)
     */
    private final HashMap<Player, Menu> playerMenus = new HashMap<>();

    public MenusRegister() {

        if (INSTANCIED.getAndSet(true)) throw new IllegalStateException("Cannot instantiate singleton.");

        Events.on(EventType.PlayerLeave.class, e -> {
            if (!playerMenus.containsKey(e.player)) return;
            close(e.player, ClosureType.PLAYER_LEAVE);
        });
    }

    private void handleMenuAction(Player player, int option) {

        // When the player exits the menu by using other means.
        // e.g. clicking outside the menu, pressing esc.
        if (option == -1) {
            close(player, ClosureType.LOST_FOCUS);
            return;
        }

        final Menu menu = playerMenus.get(player);
        final Button button = menu.getCurrentTemplate()
                .getButtons()
                .get(option);

        // I reload myself, making it look like nothing happen.
        if (button.isDisabled()) menu.displayMyself();
        // I trigger the listeners.
        else button.getListeners().forEach(listener -> listener.action(menu, button));
    }

    void displayTemplate(Player player, MenuTemplate template) {
        Call.menu(player.con(), template.getId(), template.getTitle(), template.getMessage(), template.getButtons().asString());
    }

    void close(Player player, ClosureType type) {
        final Menu menu = playerMenus.remove(player);
        menu.getCurrentTemplate()
                .getCloseAction()
                .ifPresent(listener -> listener.action(menu, type));
    }

    public MenuTemplate registerNewMenu() {
        final int id = Menus.registerMenu(this::handleMenuAction);
        return new MenuTemplate(id);
    }

    public MenuTemplate newChildMenu(int fatherId) {
        return new MenuTemplate(fatherId);
    }

    public void displayMenu(Player player, MenuTemplate template) {
        playerMenus.put(player, new Menu(this, player, template));
    }

    public void displayMenu(MenuTemplate template) {
        for (Player player : Groups.player) displayMenu(player, template);
    }
}
