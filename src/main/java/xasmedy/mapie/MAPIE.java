/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie;

import mindustry.mod.Plugin;
import xasmedy.mapie.icon.ChatIcons;
import xasmedy.mapie.menu.Menu;

public final class MAPIE {

    private static MAPIE singleton = null;

    public final ChatIcons chatIcons;
    public final Menu menu;

    private MAPIE() {
        this.chatIcons = new ChatIcons();
        this.menu = new Menu();
    }

    /**
     * Only call this inside {@link Plugin#init()} or after it ran.<br>
     * The value is cached, so this method can be called multiple times without generating new instances.
     */
    public static MAPIE init() {
        if (singleton == null) singleton = new MAPIE();
        return singleton;
    }
}
