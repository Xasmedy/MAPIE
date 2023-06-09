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
import java.util.Objects;

@SuppressWarnings("unused")
public class SimpleTemplate implements Template {

    private final ButtonParser parser;
    private String title = "";
    private String message = "";
    private CloseListener closeListener = DEFAULT_LISTENER;

    public SimpleTemplate(ButtonParser parser) {
        this.parser = Objects.requireNonNull(parser);
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public CloseListener closeListener() {
        return closeListener;
    }

    @Override
    public SimpleTemplate title(String title) {
        this.title = Objects.requireNonNull(title);
        return this;
    }

    @Override
    public SimpleTemplate message(String message) {
        this.message = Objects.requireNonNull(message);
        return this;
    }

    @Override
    public SimpleTemplate closeListener(CloseListener closeListener) {
        this.closeListener = Objects.requireNonNull(closeListener);
        return this;
    }

    @Override
    public ButtonParser parser() {
        return parser;
    }
}
