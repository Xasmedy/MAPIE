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
import mindustry.ui.Menus;
import xasmedy.mapie.menu.entity.Button;
import xasmedy.mapie.menu.entity.ClosureType;
import xasmedy.mapie.menu.entity.MenuTemplate;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class MenusExecutor {

    private static final AtomicBoolean INSTANCIED = new AtomicBoolean(false);

    /**
     * Only one menu per player. (I don't see a reason to have more than one)
     */
    private final HashMap<Player, MenuPanel> playersMenu = new HashMap<>();

    public MenusExecutor() {

        if (INSTANCIED.getAndSet(true)) throw new IllegalStateException("Cannot instantiate singleton.");

        Events.on(EventType.PlayerLeave.class, e -> {
            if (!playersMenu.containsKey(e.player)) return;
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

        // Custom closure type that indicates the player opened a new menu.
        if (option == -2) {
            close(player, ClosureType.OVERWRITE);
            return;
        }

        final MenuPanel menu = playersMenu.get(player);
        final Button button = menu.getCurrentTemplate()
                .getButtonList()
                .getMindustryOption(option);

        // I reload myself, making it look like nothing happen.
        if (button.isDisabled()) {
            Log.info("Displaying myself");
            menu.update();
        }
        // I trigger the listeners.
        else button.getListener().ifPresent(listener -> listener.action(menu));
    }

    void close(Player player, ClosureType type) {
        final MenuPanel menu = playersMenu.remove(player);
        menu.getCurrentTemplate()
                .getCloseListener()
                .ifPresent(listener -> listener.action(menu, type));
    }

    public MenuTemplate registerNewMenu(Function<Integer, MenuTemplate> constructor) {
        final int id = Menus.registerMenu(this::handleMenuAction);
        return constructor.apply(id);
    }

    public void displayMenu(Player player, MenuTemplate template) {

        // TODO Figure things out. Separate method?
        // I close the player old menu.
        final MenuPanel oldMenu = playersMenu.get(player);
        if (oldMenu != null) Call.menuChoose(player, oldMenu.getCurrentTemplate().getId(), -2);
        // Network delay will fuck things up.

        // I get a copy to avoid unwanted changes during execution.
        template = Objects.requireNonNull(template.copy());
        final MenuPanel menuPanel = new MenuPanel(this, player, template);
        playersMenu.put(player, menuPanel);
        menuPanel.update(); // I display the new panel.
    }

    public void displayMenu(MenuTemplate template) {
        for (Player player : Groups.player) displayMenu(player, template);
    }
}