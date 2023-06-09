/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.templates;

import xasmedy.mapie.menu.ButtonParser;
import xasmedy.mapie.menu.Template;
import xasmedy.mapie.menu.function.CloseListener;

@SuppressWarnings("unused")
public record UnmodifiableTemplate(String title, String message, ButtonParser parser,
                                   CloseListener closeListener) implements Template {

    public UnmodifiableTemplate(String title, String message, ButtonParser parser) {
        this(title, message, parser, Template.DEFAULT_LISTENER);
    }

    @Override
    public Template title(String title) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Template message(String message) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Template closeListener(CloseListener closeListener) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
