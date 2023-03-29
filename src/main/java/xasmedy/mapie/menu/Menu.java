package xasmedy.mapie.menu;

import mindustry.gen.Player;
import xasmedy.mapie.menu.builder.MenuTemplate;
import java.util.ArrayList;

/**
 * The current interface the player is seeing, this never changes.
 */
public class Menu {

    private final ArrayList<MenuTemplate> previous = new ArrayList<>(3); // I think it's hard to have more than 3 sub-menus.
    private final Player player;
    private MenuTemplate current;

    Menu(Player owner, MenuTemplate current) {
        this.player = owner;
        this.current = current;
    }

    public Player getPlayer() {
        return player;
    }

    public MenuTemplate getCurrentTemplate() {
        return current;
    }

    public boolean isFather() {
        return previous.size() == 0;
    }

    public MenuTemplate getPrevTemplate() {
        if (isFather()) throw new IndexOutOfBoundsException("Cannot go previously the first Menu.");
        return previous.get(previous.size() - 1);
    }

    public void displayMyself() {
        // TODO Low level call.
    }

    public void displayPrev() {
        current = previous.remove(previous.size() - 1);
        // TODO Low level call.
    }

    public void displayMenu(MenuTemplate template) {
        previous.add(current);
        current = template;
        // TODO Low level call.
    }

    public void close() {
        // TODO Low level call.
    }
}
