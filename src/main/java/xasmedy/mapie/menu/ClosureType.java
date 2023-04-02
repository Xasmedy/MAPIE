/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

public enum ClosureType {
    /**
     * When a player leaves the server.
     */
    PLAYER_LEAVE,
    /**
     * When the player closes the menu via Esc (on PC) or by taping outside the menu (Mobile)
     */
    LOST_FOCUS,
    /**
     * When the menu has been closed by a {@link Panel} implementation.
     */
    BY_PANEL,
    /**
     * A closure that can be used in custom {@link Panel} implementations.
     */
    @SuppressWarnings("unused")
    CUSTOM
}
