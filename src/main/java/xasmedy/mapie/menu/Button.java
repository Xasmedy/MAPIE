package xasmedy.mapie.menu;

import java.util.Objects;

// TODO AddButton from menu?
public abstract class Button {

    private final Menu menu;

    public Button(Menu menu) {
        this.menu = Objects.requireNonNull(menu);
    }

    public final Menu getMenu() {
        return menu;
    }

    String getComponentName();

    void action();

    public String getLabel() {
    }
}
