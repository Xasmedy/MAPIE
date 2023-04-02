/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import java.util.Objects;

public record UnmodifiableButton(String label, Runnable listener) implements Button {

    public UnmodifiableButton {
        Objects.requireNonNull(label);
        Objects.requireNonNull(listener);
    }

    @Override
    public boolean isDisabled() {
        return false; // Always false, why making a button with no functionalities.
    }

    @Override
    public boolean isHidden() {
        return false; // Always false, do not add the button if it will never be shown.
    }

    @Override
    public void label(String label) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disable(boolean disabled) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void hide(boolean hidden) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void listener(Runnable listener) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
