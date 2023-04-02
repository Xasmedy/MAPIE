/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import java.util.NoSuchElementException;

/**
 * Parses {@link Button} into mindustry readable format or viceversa.
 */
public interface ButtonParser {

    /**
     * Gets the Button by using the mindustry option.
     * @throws NoSuchElementException When the button could not be found.
     */
    Button get(int option) throws NoSuchElementException;

    /**
     * Convert Buttons to mindustry standard.
     */
    String[][] asString();
}
