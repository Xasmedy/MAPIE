/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.buttons;

import xasmedy.mapie.menu.Button;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * An implementation that uses a supplier to get the label.
 */
@SuppressWarnings("unused")
public class SupplierButton implements Button {

    private final Supplier<String> label;
    private Runnable listener = DEFAULT_LISTENER;
    private boolean disabled;
    private boolean hidden;

    public SupplierButton(Supplier<String> labelSupplier, boolean disabled, boolean hidden) {
        this.label = Objects.requireNonNull(labelSupplier);
        this.disabled = disabled;
        this.hidden = hidden;
    }

    public SupplierButton(Supplier<String> labelSupplier, boolean disabled) {
        this(labelSupplier, disabled, false);
    }

    public SupplierButton(Supplier<String> labelSupplier) {
        this(labelSupplier, false, false);
    }

    @Override
    public String label() {
        return Objects.requireNonNull(label.get());
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public Runnable listener() {
        return listener;
    }

    @Override
    public SupplierButton label(String label) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(); // The label is defined through supplier.
    }

    @Override
    public SupplierButton disable(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    @Override
    public SupplierButton hide(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    @Override
    public SupplierButton listener(Runnable listener) throws NullPointerException {
        this.listener = listener;
        return this;
    }
}
