/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie;

import xasmedy.mapie.icon.ChatIcons;
import xasmedy.mapie.menu.MenusRegister;

final class MAPIEImpl implements MAPIE {

    private final ChatIcons chatIcons = new ChatIcons();
    private final MenusRegister menusRegister = new MenusRegister();

    @Override
    public ChatIcons getChatIcons() {
        return chatIcons;
    }

    @Override
    public MenusRegister getMenusRegister() {
        return menusRegister;
    }
}
