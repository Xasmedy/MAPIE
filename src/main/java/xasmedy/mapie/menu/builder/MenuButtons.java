/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.builder;

import xasmedy.mapie.menu.button.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class MenuButtons {

    private final ArrayList<ArrayList<Button>> buttons = new ArrayList<>();

    MenuButtons() {}

    private MenuButtons(ArrayList<ArrayList<Button>> toCopy) {
        toCopy.forEach(buttons -> this.buttons.add(new ArrayList<>(buttons)));
    }

    /**
     * Copies the buttons on a new collection.
     */
    public MenuButtons copy() {
        return new MenuButtons(this.buttons);
    }

    public int howManyColumns() {
        return buttons.size();
    }

    public int howManyRows(int column) {
        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + this.buttons.size());
        return buttons.get(column).size();
    }

    public void add(int column, int row, Button button) {

        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + this.buttons.size());

        final ArrayList<Button> buttons = this.buttons.get(column);
        if (buttons.size() <= row) throw new IndexOutOfBoundsException("Row: " + row + ", Size: " + buttons.size());

        buttons.add(row, button);
    }

    public void addColumn(int column, Button... buttons) {
        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + this.buttons.size());
        this.buttons.add(column, new ArrayList<>(List.of(buttons)));
    }

    public void addLastColumn(Button... buttons) {
        this.buttons.add(new ArrayList<>(List.of(buttons))); // Adds at the last position.
    }

    public Button get(int index) throws NoSuchElementException {

        for (int x = 0, y = 0; x < this.buttons.size(); x++) {

            final ArrayList<Button> buttons = this.buttons.get(x);

            if ((y += (buttons.size() - 1)) < index) continue;
            return buttons.get(index - y);
        }
        throw new NoSuchElementException(); // This should be avoided (you must know what buttons are there), if it's thrown, the caller is at fault.
    }

    public Button get(int column, int row) {

        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + buttons.size());

        final ArrayList<Button> buttons = this.buttons.get(column);
        if (buttons.size() <= row) throw new IndexOutOfBoundsException("Row: " + row + ", Size: " + buttons.size());

        return buttons.get(row);
    }

    public Button[] getColumn(int column) {
        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + buttons.size());
        return this.buttons.get(column).toArray(Button[]::new);
    }

    public Button remove(int column, int row) {

        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + buttons.size());

        final ArrayList<Button> buttons = this.buttons.get(column);
        if (buttons.size() <= row) throw new IndexOutOfBoundsException("Row: " + row + ", Size: " + buttons.size());

        return buttons.remove(row);
    }

    public boolean removeFromColumn(int column, Button button) {
        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + buttons.size());
        return this.buttons.get(column).remove(button);
    }

    public Button[] removeColumn(int column) {
        if (howManyColumns() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + buttons.size());
        return this.buttons.remove(column).toArray(Button[]::new);
    }

    public String[][] asString() {
        final String[][] options = new String[buttons.size()][];
        for (int i = 0; i < buttons.size(); i++) {
            options[i] = buttons.get(i)
                    .stream()
                    .map(Button::getLabel)
                    .toArray(String[]::new);
        }
        return options;
    }
}
