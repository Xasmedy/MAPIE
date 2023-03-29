/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.button;

import xasmedy.mapie.menu.Menu;
import java.util.ArrayList;

public class Button {

    private final ArrayList<ButtonTriggerListener> listeners = new ArrayList<>();
    private String label;
    private boolean isDisabled;

    public Button(String label, boolean disabled) {
        this.label = label;
        this.isDisabled = disabled;
    }

    public Button(String label) {
        this(label, false);
    }

    public Button() {
        this(null, false);
    }

    public ArrayList<ButtonTriggerListener> getListeners() {
        return listeners;
    }

    public void addListener(ButtonTriggerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ButtonTriggerListener listener) {
        listeners.remove(listener);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public boolean isDisabled() {
        return isDisabled;
    }
}
