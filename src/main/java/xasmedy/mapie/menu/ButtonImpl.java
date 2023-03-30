/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import xasmedy.mapie.menu.entity.Button;
import xasmedy.mapie.menu.entity.ButtonTriggerListener;
import java.util.Objects;
import java.util.Optional;

class ButtonImpl implements Button {

    private ButtonTriggerListener listener; // Null is allowed.
    private String label; // Always not null.
    private boolean isDisabled;

    ButtonImpl(String label, boolean disabled) {
        this.label = Objects.requireNonNull(label);
        this.isDisabled = disabled;
    }

    ButtonImpl(String label) {
        this(label, false);
    }

    ButtonImpl() {
        this("", false);
    }

    @Override
    public Optional<ButtonTriggerListener> getListener() {
        return Optional.ofNullable(listener);
    }

    @Override
    public void setListener(ButtonTriggerListener listener) {
        this.listener = listener;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.isDisabled = disabled;
    }

    @Override
    public boolean isDisabled() {
        return isDisabled;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
