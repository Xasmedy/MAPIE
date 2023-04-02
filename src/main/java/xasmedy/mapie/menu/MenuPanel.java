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
import java.util.Objects;

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
    /**
     * A copy of the template in case {@link MenuTemplateImpl#shouldResetOnContextChange()} is true.<br>
     * If the option is set to false, this must be null.
     */
    private MenuTemplate currentCopy;

    MenuPanel(Menus menus, Player player, MenuTemplate current) {
        this.menus = menus;
        this.player = player;
        this.current = current;
        copyIfEnabled();
    }

    private void copyIfEnabled() {
        // User defined, so it could be null.
        // Checking it here is better than having it be called at some random point in the program.
        this.currentCopy = current.shouldResetOnContextChange() ? Objects.requireNonNull(current.copy()) : null;
    }

    private void resetTemplateWithCopy() {
        // If the user still wants to reset.
        if (!current.shouldResetOnContextChange()) return;
        current = currentCopy;
    }

    public void update() {
        copyIfEnabled();
        final String[][] options = current.buttons().asMindustryOptions();
        Call.followUpMenu(player.con(), current.getId(), current.getTitle(), current.getMessage(), options);
    }

    public void displayFromTopMenus(int index) {

        // I remove all the elements after the index.
        final var iterator = topMenus.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            if (i <= index) continue;
            iterator.remove();
        }
        resetTemplateWithCopy();
        current = topMenus.remove(index);
        update();
    }

    public void displayTopMenu() {
        resetTemplateWithCopy();
        current = topMenus.remove(topMenus.size() - 1);
        update();
    }

    public void displaySubMenu(MenuTemplate template) {
        resetTemplateWithCopy();
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
