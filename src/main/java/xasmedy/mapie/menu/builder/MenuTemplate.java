package xasmedy.mapie.menu.builder;

import xasmedy.mapie.menu.close.CloseListener;
import java.util.Objects;
import java.util.Optional;

public class MenuTemplate {

    private final int id;
    private final MenuButtons buttons;
    private String title = ""; // Never null.
    private String message = ""; // Never null.
    private CloseListener closeAction;

    public MenuTemplate(int id) {
        if (id < 0) throw new IllegalArgumentException("The id can not be negative");
        this.id = id;
        this.buttons = new MenuButtons();
    }

    private MenuTemplate(MenuTemplate menu) {
        this.id = menu.id;
        this.title = menu.title;
        this.message = menu.message;
        this.closeAction = menu.closeAction;
        this.buttons = menu.buttons.copy();
    }

    public int getId() {
        return id;
    }

    public MenuTemplate copy() {
        return new MenuTemplate(this);
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title);
    }

    public String getTitle() {
        return title;
    }

    public void setMessage(String message) {
        this.message = Objects.requireNonNull(message);
    }

    public String getMessage() {
        return message;
    }

    public void setCloseAction(CloseListener closeAction) {
        this.closeAction = closeAction;
    }

    public Optional<CloseListener> getCloseAction() {
        return Optional.of(closeAction);
    }

    public MenuButtons getButtons() {
        return buttons;
    }
}
