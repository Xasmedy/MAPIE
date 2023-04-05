/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

@SuppressWarnings("unused")
public interface Button {

    String label();

    boolean isDisabled();

    boolean isHidden();

    default boolean isShown() {
        return !isHidden();
    }

    Runnable listener();

    /**
     * Modifies the current label with a new one.
     * @throws NullPointerException when a null label is provided.
     */
    Button label(String label) throws NullPointerException;

    /**
     * Disabled buttons will not have their listener called when clicked by the user.
     */
    Button disable(boolean disabled);

    /**
     * Hidden buttons will not be visible by the user.
     */
    Button hide(boolean hidden);

    /**
     * Changes the button action when clicked.
     * @throws NullPointerException when a null listener is provided.
     */
    Button listener(Runnable listener) throws NullPointerException;
}
