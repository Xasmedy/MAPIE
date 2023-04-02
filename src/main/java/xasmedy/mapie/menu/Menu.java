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

public final class Menu {

    private static final AtomicBoolean INSTANCIED = new AtomicBoolean(false);
    /**
     * Mindustry default closure when a player selects no option.
     */
    private static final int DEFAULT_CLOSURE = -1;
    /**
     * Used when closed via {@link Call#menuChoose} by a {@link Panel}.
     */
    private static final int REMOTE_CLOSURE = -2;

    /**
     * Only one menu per player. (I don't see a reason to have more than one)
     */
    private final HashMap<Player, Panel> playersMenu = new HashMap<>();

    public Menu() {

        if (INSTANCIED.getAndSet(true)) throw new IllegalStateException("Cannot instantiate singleton.");

        Events.on(EventType.PlayerLeave.class, e -> {
            final Panel panel = playersMenu.get(e.player);
            if (panel == null) return;
            removePanel(panel, ClosureType.PLAYER_LEAVE);
        });
    }

    private void handleMenuAction(int menuId, Player player, int option) {

        final Panel panel = playersMenu.get(player);
        // Modified Mindustry version. (Call.menuChoose without having a dialog)
        // This can also happen when a panel is used after being closed.
        if (panel == null) {
            Log.debug("Modified client for player \"" + player.ip() + "\" or bad panel usage.");
            return;
        }

        switch (option) {
            case DEFAULT_CLOSURE -> removePanel(panel, ClosureType.LOST_FOCUS);
            case REMOTE_CLOSURE -> removePanel(panel, ClosureType.BY_PANEL);
            default -> panel.selectionEvent(panel.template().parser().get(option));
        }
    }

    public <T extends Template> T registerNewMenu(Function<Integer, T> constructor) {

        Objects.requireNonNull(constructor);

        // set() will always be called before get().
        final AtomicInteger menuId = new AtomicInteger(-1);
        final int registeredId = mindustry.ui.Menus.registerMenu((Player player, int option) -> handleMenuAction(menuId.get(), player, option));
        menuId.set(registeredId);
        // I create the user-defined MenuTemplate.
        return constructor.apply(registeredId);
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