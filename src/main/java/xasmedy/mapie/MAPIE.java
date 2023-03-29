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
import xasmedy.mapie.menu.MenusRegister;

/**
 * Allows the access to the services.<br>
 * Call {@link MAPIE#init()} only inside {@link Plugin#init()}.
 */
public interface MAPIE {

    static MAPIE init() {
        return new MAPIEImpl();
    }

    ChatIcons getChatIcons();

    MenusRegister getMenusRegister();
}
