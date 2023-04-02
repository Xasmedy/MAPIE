/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu;

import mindustry.gen.Call;
import mindustry.gen.Player;
import java.util.ArrayList;

/**
 * The current interface the player is seeing, this never changes.
 */
public final class MenuPanel {

    // TODO Make first the most recent?
    /**
     * Keeps track of the previously selected menus.<br>
     * The first element is always the father menu.
     */
    private final ArrayList<MenuTemplate> topMenus = new ArrayList<>(3); // I think it's hard to have more than 3 sub-menus.
    private final Menus menus;
    private final Player player;
    private MenuTemplate current;

    MenuPanel(Menus menus, Player player, MenuTemplate current) {
        this.menus = menus;
        this.player = player;
        this.current = current;
    }

    public void update() {
        final String[][] options = current.parser().asString();
        Call.followUpMenu(player.con(), current.menuId(), current.title(), current.message(), options);
    }

    public void displayFromTopMenus(int index) {

        // I remove all the elements after the index.
        final var iterator = topMenus.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            if (i <= index) continue;
            iterator.remove();
        }
        current = topMenus.remove(index);
        update();
    }

    public void displayTopMenu() {
        current = topMenus.remove(topMenus.size() - 1);
        update();
    }

    public void displaySubMenu(MenuTemplate template) {
        topMenus.add(current);
        current = template;
        update();
    }

    public Player getPlayer() {
        return player;
    }

    public MenuTemplate getTemplate() {
        return current;
    }

    public MenuTemplate getTemplateFromTop(int index) {
        return topMenus.get(index);
    }

    public void close() {
        // TODO Player choose? (not the best)
        menus.closeActiveMenu(this, ClosureType.BY_BUTTON);
    }

    public boolean isFather() {
        return topMenus.size() == 0;
    }

    public boolean isChild() {
        return !isFather();
    }

    public int getTopMenusSize() {
        return topMenus.size();
    }
}
