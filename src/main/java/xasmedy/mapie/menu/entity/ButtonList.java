/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.entity;

import java.util.NoSuchElementException;

public interface ButtonList {

    /**
     * Copies the buttons over a new list.
     */
    ButtonList copy();

    int columnSize();

    int rowSize(int column);

    void add(int column, int row, Button button);

    void addColumn(int column, Button... buttons);

    default void addFirstColumn(Button... buttons) {
        addColumn(0, buttons);
    }

    default void addLastColumn(Button... buttons) {
        addColumn(columnSize() - 1, buttons);
    }

    Button[] getColumn(int column);

    default Button get(int column, int row) {
        return getColumn(column)[row];
    }

    Button remove(int column, int row);

    Button[] removeColumn(int column);

    boolean removeFromColumn(int column, Button button);

    default Button[] removeFirstColumn() {
        return removeColumn(0);
    }

    default Button[] removeLastColumn() {
        return removeColumn(columnSize() - 1);
    }

    Button getMindustryOption(int option) throws NoSuchElementException;

    String[][] asMindustryOptions();
}
