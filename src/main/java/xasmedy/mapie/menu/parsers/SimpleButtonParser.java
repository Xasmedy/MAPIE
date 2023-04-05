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
import java.util.NoSuchElementException;
import java.util.function.Function;

@SuppressWarnings("unused")
public class SimpleButtonParser<T extends Button> implements ButtonParser {

    public final ArrayList<ArrayList<T>> buttons = new ArrayList<>();

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
