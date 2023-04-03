/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.command;

import mindustry.gen.Player;
import java.util.HashSet;

public interface Command {

    String name();

    String params();

    String description();

    boolean hidden();

    default boolean shown() {
        return !hidden();
    }

    /**
     * @return The roles required to execute this command if any.
     */
    HashSet<Integer> rolesRequired();

    /**
     * @return true if the player has the roles required to execute this command.
     */
    boolean hasRequiredRoles(Player player);

    void action(Player player, String[] args);

    void noPermissionsAction(Player player, String[] args);

    void serverAction(String[] args);

    boolean hide(boolean hidden);
}
