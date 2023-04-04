/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.command;

import mindustry.gen.Player;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public interface Command {

    // No roles required.
    Set<Integer> DEFAULT_PERMISSIONS = Collections.unmodifiableSet(new HashSet<>());

    String name();

    default String params() {
        return "";
    }

    String description();

    default boolean hidden() {
        return false;
    }

    default boolean shown() {
        return !hidden();
    }

    /**
     * @return The roles required to execute this command if any.
     */
    default Set<Integer> rolesRequired() {
        return DEFAULT_PERMISSIONS;
    }

    /**
     * @return true if the player has the roles required to execute this command.
     */
    boolean hasRequiredRoles(Player player, String[] args);

    default void action(Player player, String[] args) {}

    default void noPermissionsAction(Player player, String[] args) {}

    default void serverAction(String[] args) {}

    default void hide(boolean hidden) {
        throw new UnsupportedOperationException();
    }
}
