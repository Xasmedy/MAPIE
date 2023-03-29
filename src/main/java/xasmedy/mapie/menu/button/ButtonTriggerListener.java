package xasmedy.mapie.menu.button;

import xasmedy.mapie.menu.Menu;

@FunctionalInterface
public interface ButtonTriggerListener {

    void action(Menu menu, Button myself);
}
