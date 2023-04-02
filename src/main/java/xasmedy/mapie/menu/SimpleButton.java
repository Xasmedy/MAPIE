/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import java.util.Objects;

public final class SimpleButton implements Button {

    private static final ButtonTriggerListener DEFAULT_LISTENER = (menu, myself) -> {};
    private ButtonTriggerListener listener = DEFAULT_LISTENER;
    private String label;
    private boolean disabled;
    private boolean hidden;

    public SimpleButton(String label, boolean disabled) {
        this.label = Objects.requireNonNull(label);
        this.disabled = disabled;
    }

    public SimpleButton(String label) {
        this(label, false);
    }

    public SimpleButton() {
        this("", false);
    }

    @Override
    public String label() {
        return label;
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
    public ButtonTriggerListener listener() {
        return listener;
    }

    @Override
    public void label(String label) {
        this.label = Objects.requireNonNull(label);
    }

    @Override
    public void disable(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public void hide(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public void listener(ButtonTriggerListener listener) {
        this.listener = Objects.requireNonNull(listener);
    }
}
