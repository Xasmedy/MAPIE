/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import xasmedy.mapie.menu.function.CloseListener;

public record UnmodifiableTemplate(int menuId, String title, String message, ButtonParser parser,
                                   CloseListener closeListener) implements Template {

    @Override
    public void title(String title) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void message(String message) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void closeListener(CloseListener listener) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
