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
import mindustry.mod.Plugin;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public final class Menu {

    /**
     * Mindustry default closure when a player selects no option.
     */
    private static final int DEFAULT_CLOSURE = -1;
    /**
     * Used when closed via {@link Call#menuChoose} by a {@link Panel}.
     */
    private static final int REMOTE_CLOSURE = -2;

    private static Menu instance;

    /**
     * Only one menu per player. (I don't see a reason to have more than one)
     */
    private final HashMap<Player, Panel> playersMenu = new HashMap<>(); // I don't save the menuId since it is variable.

    private Menu() {}

    /**
     * Must be called during {@link Plugin#init()}.
     */
    public static Menu init() {

        if (instance != null) return instance;
        else instance = new Menu();

        Events.on(EventType.PlayerLeave.class, e -> {
            final Panel panel = instance.playersMenu.get(e.player);
            if (panel == null) return;
            instance.removePanel(panel, ClosureType.PLAYER_LEAVE);
        });
        return instance;
    }

    private void handleMenuAction(Player player, int option) {

        final Panel panel = playersMenu.get(player);
        // Modified Mindustry version. (Call.menuChoose without having a dialog)
        // This can also happen when a panel is used after being closed.
        if (panel == null) {
            Log.debug("Modified client for player \"" + player.plainName() + "\" / \"" + player.ip() + "\" or bad panel usage.");
            return;
        }

        switch (option) {
            case DEFAULT_CLOSURE -> removePanel(panel, ClosureType.LOST_FOCUS);
            case REMOTE_CLOSURE -> removePanel(panel, ClosureType.BY_PANEL);
            default -> {
                final Button button;
                try {
                    // I avoid modified clients picking an option that does not exist.
                    button = panel.template().parser().get(option);
                } catch (NoSuchElementException ignored) {
                    // This might also happen in case the player spam clicks buttons.
                    Log.debug("Modified client by: \"" + player.plainName() + "\" / \"" + player.ip() + "\" or button spamming.");
                    return;
                }
                panel.selectionEvent(button);
            }
        }
    }

    @SuppressWarnings("unused")
    public <T extends Template> T register(Function<Integer, T> constructor) {
        // I check before registering, else the user would have no way to access the menuId, wasting memory.
        Objects.requireNonNull(constructor);
        final int id = mindustry.ui.Menus.registerMenu(this::handleMenuAction);
        // I create the user-defined MenuTemplate.
        return constructor.apply(id);
    }

    /**
     * Removes the panel but <strong>does NOT hide it</strong>  to the player!<br>
     * ALWAYS use {@link Panel#close()} unless making a custom implementation.
     * @param panel The panel to remove.
     * @param type How this panel has been removed.
     */
    public void removePanel(Panel panel, ClosureType type) {
        playersMenu.remove(panel.player());
        panel.template().closeListener().action(type);
    }
}
