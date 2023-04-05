/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import mindustry.gen.Player;

/**
 * The class that manages the player menu-interface.
 */
public interface Panel {

    Player player();

    Template template();

    void update();

    void selectionEvent(Button onButton);

    void close();
}
