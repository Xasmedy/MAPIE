/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.templates;

import xasmedy.mapie.menu.ButtonParser;
import xasmedy.mapie.menu.Template;
import xasmedy.mapie.menu.function.CloseListener;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * An implementation that uses a supplier to get the title and message.
 */
@SuppressWarnings("unused")
public class SupplierTemplate implements Template {

    private final int menuId;
    private final ButtonParser parser;
    private final Supplier<String> title;
    private final Supplier<String> message;
    private CloseListener closeListener = DEFAULT_LISTENER;

    public SupplierTemplate(int menuId, ButtonParser parser, Supplier<String> titleSupplier, Supplier<String> messageSupplier) {
        this.menuId = menuId;
        this.parser = Objects.requireNonNull(parser);
        this.title = Objects.requireNonNull(titleSupplier);
        this.message = Objects.requireNonNull(messageSupplier);
    }

    @Override
    public int menuId() {
        return menuId;
    }

    @Override
    public String title() {
        return Objects.requireNonNull(title.get());
    }

    @Override
    public String message() {
        return Objects.requireNonNull(message.get());
    }

    @Override
    public CloseListener closeListener() {
        return closeListener;
    }

    @Override
    public SupplierTemplate title(String title) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(); // The title is defined through supplier.
    }

    @Override
    public SupplierTemplate message(String message) throws NullPointerException {
        throw new UnsupportedOperationException(); // The message is defined through supplier.
    }

    @Override
    public SupplierTemplate closeListener(CloseListener closeListener) throws NullPointerException {
        this.closeListener = Objects.requireNonNull(closeListener);
        return this;
    }

    @Override
    public ButtonParser parser() {
        return parser;
    }
}
