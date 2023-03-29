/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import mindustry.gen.Player;
import xasmedy.mapie.menu.builder.MenuTemplate;
import xasmedy.mapie.menu.close.ClosureType;
import java.util.ArrayList;

/**
 * The current interface the player is seeing, this never changes.
 */
public class Menu {

    private final ArrayList<MenuTemplate> previous = new ArrayList<>(3); // I think it's hard to have more than 3 sub-menus.
    private final MenusRegister menusRegister;
    private final Player player;
    private MenuTemplate current;

    Menu(MenusRegister register, Player player, MenuTemplate current) {
        this.menusRegister = register;
        this.player = player;
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
        menusRegister.displayTemplate(getPlayer(), current);
    }

    public void displayPrev() {
        current = previous.remove(previous.size() - 1);
        menusRegister.displayTemplate(getPlayer(), current);
    }

    public void displayMenu(MenuTemplate template) {
        previous.add(current);
        current = template;
        menusRegister.displayTemplate(getPlayer(), template);
    }

    public void close() {
        menusRegister.close(getPlayer(), ClosureType.BY_BUTTON);
    }
}
