/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import xasmedy.mapie.menu.function.CloseListener;

public interface Template {

    int menuId();

    String title();

    String message();

    CloseListener closeListener();

    void title(String title) throws NullPointerException;

    void message(String message) throws NullPointerException;

    void closeListener(CloseListener listener) throws NullPointerException;

    ButtonParser parser();
}