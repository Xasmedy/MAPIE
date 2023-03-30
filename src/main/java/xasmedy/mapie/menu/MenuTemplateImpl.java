/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import xasmedy.mapie.menu.entity.CloseListener;
import xasmedy.mapie.menu.entity.MenuTemplate;
import java.util.Objects;
import java.util.Optional;

final class MenuTemplateImpl implements MenuTemplate {

    private final ButtonListImpl buttons;
    private int id;
    private String title = ""; // Never null.
    private String message = ""; // Never null.
    private boolean shouldResetOnContextChange = true;
    private CloseListener closeListener;

    private MenuTemplateImpl() {
        this.buttons = new ButtonListImpl();
    }

    private MenuTemplateImpl(int id) {
        if (id < 0) throw new IllegalArgumentException("The id can not be negative.");
        this.id = id;
        this.buttons = new ButtonListImpl();
    }

    private MenuTemplateImpl(MenuTemplateImpl menu) {
        this.id = menu.id;
        this.title = menu.title;
        this.message = menu.message;
        this.shouldResetOnContextChange = menu.shouldResetOnContextChange;
        this.closeListener = menu.closeListener;
        this.buttons = menu.buttons.copy();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public MenuTemplateImpl copy() {
        return new MenuTemplateImpl(this);
    }

    @Override
    public MenuTemplate makeSubMenu(MenuTemplate father) {
        return new MenuTemplateImpl(father.getId());
    }

    @Override
    public ButtonListImpl getButtonList() {
        return buttons;
    }

    @Override
    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title);
    }

    @Override
    public void setMessage(String message) {
        this.message = Objects.requireNonNull(message);
    }

    @Override
    public void setResetOnContextChange(boolean shouldReset) {
        this.shouldResetOnContextChange = shouldReset;
    }

    @Override
    public void setCloseListener(CloseListener closeListener) {
        this.closeListener = closeListener;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean shouldResetOnContextChange() {
        return shouldResetOnContextChange;
    }

    @Override
    public Optional<CloseListener> getCloseListener() {
        return Optional.ofNullable(closeListener);
    }
}
