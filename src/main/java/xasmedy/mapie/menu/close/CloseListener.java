package xasmedy.mapie.menu.close;

import xasmedy.mapie.menu.Menu;

@FunctionalInterface
public interface CloseListener {

    void action(Menu menu, ClosureType type);
}
