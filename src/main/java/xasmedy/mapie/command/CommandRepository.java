/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.command;

import arc.util.CommandHandler;
import mindustry.gen.Player;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * Small Thread-Safe class where you can save commands.<br>
 * The retrieving of commands is concurrent, while the modifying is not.
 */
public class CommandRepository {

    protected final HashMap<String, Command> commands = new HashMap<>();
    protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    protected final CommandHandler handler;

    public CommandRepository(CommandHandler handler) {
        this.handler = Objects.requireNonNull(handler);
    }

    protected void commandCaller(Command command, String[] args, Player player) {

        if (player == null) {
            command.serverAction(args);
            return;
        }
        if (command.hasRequiredRoles(player)) command.action(player, args);
        else command.noPermissionsAction(player, args);
    }

    public void addCommand(Command command) {
        final var writeLock = lock.writeLock();
        try {
            writeLock.lock();
            commands.put(command.name(), command);
        } finally {
            writeLock.unlock();
        }
    }

    public Command removeCommand(String name) {
        final var writeLock = lock.writeLock();
        try {
            writeLock.lock();
            return commands.remove(name);
        } finally {
            writeLock.unlock();
        }
    }

    public Command getCommand(String name) {
        final var readLock = lock.readLock();
        try {
            readLock.lock();
            return commands.get(name);
        } finally {
            readLock.unlock();
        }
    }

    public void forEach(Consumer<Command> consumer) {
        final var readLock = lock.readLock();
        try {
            readLock.lock();
            commands.values().forEach(consumer);
        } finally {
            readLock.unlock();
        }
    }

    public void reloadCommands() {
        final var writeLock = lock.writeLock();
        try {
            writeLock.lock();
            // I remove previous commands.
            handler.getCommandList().clear();
            commands.values()
                    .stream()
                    .filter(Command::shown)
                    .forEach(command -> handler.register(
                            command.name(), command.params(), command.description(),
                            (String[] args, Player player) -> commandCaller(command, args, player)));
        } finally {
            writeLock.unlock();
        }
    }
}