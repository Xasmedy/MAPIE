/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.parsers;

import xasmedy.mapie.menu.Button;
import xasmedy.mapie.menu.ButtonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@SuppressWarnings("unused")
public class ButtonsLayout<T extends Button> implements ButtonParser {

    protected final ArrayList<ArrayList<T>> buttons;

    protected ButtonsLayout(ArrayList<ArrayList<T>> buttons) {
        this.buttons = buttons;
    }

    public ButtonsLayout() {
        this.buttons = new ArrayList<>();
    }

    public ButtonsLayout(ButtonsLayout<T> buttons) {
        this.buttons = new ArrayList<>(buttons.buttons);
    }

    public int columnSize() {
        return buttons.size();
    }

    public int rowSize(int column) {
        return buttons.get(column).size();
    }

    public void add(int column, int row, T button) {
        buttons.get(column).add(row, button);
    }

    public T get(int column, int row) {
        return getColumn(column).get(row);
    }

    public T remove(int column, int row) {

        final ArrayList<T> rowButtons = buttons.get(column);
        final T button = rowButtons.remove(row);
        rowButtons.trimToSize();

        if (!rowButtons.isEmpty()) return button;

        buttons.remove(column);
        buttons.trimToSize();
        return button;
    }

    public void addColumn(int column, List<T> buttons) {
        this.buttons.add(column, new ArrayList<>(buttons));
    }

    public ArrayList<T> getColumn(int column) {
        return new ArrayList<>(buttons.get(column));
    }

    public ArrayList<T> removeColumn(int column) {
        final ArrayList<T> removedColumn = new ArrayList<>(buttons.remove(column));
        buttons.trimToSize();
        return removedColumn;
    }

    public boolean removeFromColumn(int column, T button) {

        final ArrayList<T> rowButtons = buttons.get(column);
        final boolean removed = rowButtons.remove(button);
        rowButtons.trimToSize();
        if (!rowButtons.isEmpty()) return removed;

        buttons.remove(column);
        buttons.trimToSize();
        return removed;
    }

    public void addFirstColumn(List<T> buttons) {
        addColumn(0, buttons);
    }

    public ArrayList<T> getFirstColumn() {
        return getColumn(0);
    }

    public ArrayList<T> removeFirstColumn() {
        return removeColumn(0);
    }

    public void addLastColumn(List<T> buttons) {
        addColumn(columnSize(), buttons);
    }

    public ArrayList<T> getLastColumn() {
        return getColumn(columnSize() - 1);
    }

    public ArrayList<T> removeLastColumn() {
        return removeColumn(columnSize() - 1);
    }

    @Override
    public T get(int option) throws NoSuchElementException {

        for (int column = 0, i = 0; column < buttons.size(); column++) {
            final ArrayList<T> rowButtons = buttons.get(column);
            for (final T button : rowButtons) {
                if (button.isHidden()) continue; // I ignore hidden buttons since the client has no idea of those.
                if (i == option) return button; // In case I reached the wanted button.
                i++;
            }
        }
        throw new NoSuchElementException("Option not found: " + option); // This should never happen.
    }

    @Override
    public String[][] asString() {
        final Function<ArrayList<T>, String[]> rowMap = row -> row.stream()
                .filter(Button::isShown)
                .map(Button::label)
                .toArray(String[]::new);

        return buttons.stream()
                .map(rowMap)
                .toArray(String[][]::new);
    }
}
