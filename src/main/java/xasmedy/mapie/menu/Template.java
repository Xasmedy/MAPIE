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
public interface Template {

    CloseListener DEFAULT_LISTENER = (closureType) -> {};

    int menuId();

    String title();

    String message();

    CloseListener closeListener();

    Template title(String title) throws NullPointerException;

    Template message(String message) throws NullPointerException;

    Template closeListener(CloseListener closeListener) throws NullPointerException;

    ButtonParser parser();
}
