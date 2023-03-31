/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import xasmedy.mapie.menu.entity.Button;
import xasmedy.mapie.menu.entity.ButtonList;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@SuppressWarnings("unused")
public final class ButtonListImpl implements ButtonList {

    private final ArrayList<ArrayList<Button>> buttons = new ArrayList<>();

    ButtonListImpl() {}

    private ButtonListImpl(ArrayList<ArrayList<Button>> toCopy) {
        toCopy.forEach(buttons -> this.buttons.add(new ArrayList<>(buttons)));
    }

    @Override
    public ButtonListImpl copy() {
        return new ButtonListImpl(this.buttons);
    }

    @Override
    public int columnSize() {
        return buttons.size();
    }

    @Override
    public int rowSize(int column) {
        return buttons.get(column).size();
    }

    @Override
    public void add(int column, int row, Button button) {
        buttons.get(column).add(row, button);
    }

    @Override
    public void addColumn(int column, Button... buttonsArray) {
        buttons.add(column, new ArrayList<>(List.of(buttonsArray)));
    }

    @Override
    public Button[] getColumn(int column) {
        return buttons.get(column).toArray(Button[]::new);
    }

    @Override
    public Button remove(int column, int row) {

        final ArrayList<Button> rowButtons = buttons.get(column);
        final Button button = rowButtons.remove(row);
        rowButtons.trimToSize();

        if (!rowButtons.isEmpty()) return button;

        buttons.remove(column);
        buttons.trimToSize();
        return button;
    }

    @Override
    public Button[] removeColumn(int column) {
        final Button[] removedColumn = buttons.remove(column).toArray(Button[]::new);
        buttons.trimToSize();
        return removedColumn;
    }

    @Override
    public boolean removeFromColumn(int column, Button button) {

        final ArrayList<Button> rowButtons = buttons.get(column);
        final boolean removed = rowButtons.remove(button);
        rowButtons.trimToSize();
        if (!rowButtons.isEmpty()) return removed;

        buttons.remove(column);
        buttons.trimToSize();
        return removed;
    }

    @Override
    public Button getMindustryOption(int option) throws NoSuchElementException {

        for (int column = 0, i = 0; column < buttons.size(); column++) {

            final ArrayList<Button> rowButtons = buttons.get(column);

            // In case the option is not at this row I go to the next one.
            if ((i + rowButtons.size() - 1) < option) {
                // I increment this here to avoid problems in case the if statement is false.
                i = rowButtons.size() - 1;
                continue;
            }
            // I get how many rows are left aka the index.
            return rowButtons.get(option - i);
        }
        throw new NoSuchElementException("Option not found: " + option); // This should never happen.
    }

    @Override
    public String[][] asMindustryOptions() {

        final Function<ArrayList<Button>, String[]> rowMap = row -> row.stream()
                .map(Button::getLabel)
                .toArray(String[]::new);

        return buttons.stream()
                .map(rowMap)
                .toArray(String[][]::new);
    }
}
