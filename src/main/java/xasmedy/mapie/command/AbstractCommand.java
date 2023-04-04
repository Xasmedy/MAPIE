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
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractCommand {

    // No roles required.
    private static final Set<Integer> DEFAULT_PERMISSIONS = Collections.unmodifiableSet(new HashSet<>());
    protected final AtomicBoolean isInit = new AtomicBoolean(false);
    
    public abstract String name();

    public String params() {
        return "";
    }

    public abstract String description();

    /**
     * @return The roles required to execute this command, if any.
     */
    public Set<Integer> rolesRequired() {
        return DEFAULT_PERMISSIONS;
    }

    /**
     * @return true if the player has the roles required to execute this command.<br>
     * Arguments are provided in case more specific control is wanted.
     */
    public abstract boolean hasRequiredRoles(Player player, String[] args);

    public boolean hidden() {
        return false;
    }

    public boolean shown() {
        return !hidden();
    }

    public void hide(boolean hidden) {
        throw new UnsupportedOperationException();
    }

    protected void clientAction(Player player, String[] args) {}

    /**
     * Always called, even if {@link AbstractCommand#hasRequiredRoles} returns false.
     */
    protected void serverAction(String[] args) {}

    /**
     * Called when {@link AbstractCommand#hasRequiredRoles} returns false.
     */
    protected void noPermissionsAction(Player player, String[] args) {}

    /**
     * The init is called when the user uses the command for the first time;<br>
     * <ul>
     *     <li>for clientCommands called after {@link AbstractCommand#hasRequiredRoles} but before the {@link AbstractCommand#clientAction} is executed.</li>
     *     <li>for serverCommands called before {@link AbstractCommand#serverAction} is executed.</li>
     * </ul>
     * <strong>Warning!</strong> {@link AbstractCommand#isInit} is set to true the moment before calling this method.
     * @param isServer true if the command is used by the server.
     */
    protected void init(boolean isServer) {}

    protected boolean isInit() {
        return isInit.get();
    }

    /**
     * Called after executing {@link AbstractCommand#clientAction}.<br>
     * <strong>Warning!</strong> This does <strong>not</strong> set {@link AbstractCommand#isInit} to false.
     */
    protected void clientDispose(Player player, String[] args) {}

    /**
     * Called after executing {@link AbstractCommand#serverAction}.<br>
     * <strong>Warning!</strong> This does <strong>not</strong> set {@link AbstractCommand#isInit} to false.
     */
    protected void serverDispose(String[] args) {}
}
