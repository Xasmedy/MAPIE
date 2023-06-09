/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.function;

import xasmedy.mapie.menu.ClosureType;

@FunctionalInterface
public interface CloseListener {
    void action(ClosureType type);
}
