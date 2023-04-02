/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import xasmedy.mapie.menu.function.CloseListener;
import java.util.Objects;

public class SimpleTemplate implements Template {

    private static final CloseListener DEFAULT_LISTENER = (closureType) -> {};
    private final int menuId;
    private final ButtonParser parser;
    private String title = "";
    private String message = "";
    private CloseListener closeListener = DEFAULT_LISTENER;

    public SimpleTemplate(int menuId, ButtonParser parser) {
        this.menuId = menuId;
        this.parser = Objects.requireNonNull(parser);
    }

    @Override
    public int menuId() {
        return menuId;
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
    public void title(String title) {
        this.title = Objects.requireNonNull(title);
    }

    @Override
    public void message(String message) {
        this.message = Objects.requireNonNull(message);
    }

    @Override
    public void closeListener(CloseListener closeListener) {
        this.closeListener = Objects.requireNonNull(closeListener);
    }

    @Override
    public ButtonParser parser() {
        return parser;
    }
}