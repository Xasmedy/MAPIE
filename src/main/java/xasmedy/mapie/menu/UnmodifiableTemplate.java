/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import xasmedy.mapie.menu.function.CloseListener;

@SuppressWarnings("unused")
public record UnmodifiableTemplate(int menuId, String title, String message, ButtonParser parser,
                                   CloseListener closeListener) implements Template {

    @Override
    public Template title(String title) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Template message(String message) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Template closeListener(CloseListener listener) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
