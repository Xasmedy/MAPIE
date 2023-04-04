/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.command;

import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.gen.Player;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * Small Thread-Safe class where you can save commands.<br>
 * The retrieving of commands is concurrent, while the modifying is not.
 */
@SuppressWarnings("unused")
public class CommandRepository {

    protected final HashMap<String, AbstractCommand> commands = new HashMap<>();
    protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    protected CommandHandler handler;

    public CommandRepository(CommandHandler handler) {
        this.handler = Objects.requireNonNull(handler);
    }

    public CommandRepository() {}

    public void handler(CommandHandler handler) {
        Objects.requireNonNull(handler);
        if (this.handler != null) throw new IllegalStateException("Handler already defined.");
        this.handler = handler;
    }

    public CommandHandler handler() {
        return handler;
    }

    protected void commandCaller(AbstractCommand command, String[] args, Player player) {

        try {

            // Null only when called by the server.
            if (player == null) {

                // If already initialised it does nothing, else it initialises.
                if (!command.isInit.getAndSet(true)) command.init(true);

                command.serverAction(args);
                command.serverDispose(args);
                return;
            }

            if (!command.hasRequiredRoles(player, args)) {
                command.noPermissionsAction(player, args);
                return;
            }

            // If already initialised it does nothing, else it initialises.
            if (!command.isInit.getAndSet(true)) command.init(false);

            command.clientAction(player, args);
            command.clientDispose(player, args);

        } catch(Exception e) {
            Log.err("Exception inside the command: " + command.name(), e);
        }
    }

    private void registerToHandler(AbstractCommand command) {
        Objects.requireNonNull(handler); // I throw an error in case the user is trying to register without handler.
        final CommandHandler.CommandRunner<Player> runner = (String[] args, Player player) -> commandCaller(command, args, player);
        handler.register(command.name(), command.params(), command.description(), runner);
    }

    private void removeFromHandler(String name) {
        Objects.requireNonNull(handler); // I throw an error in case the user is trying to remove without a handler.
        handler.removeCommand(name);
    }

    public void add(AbstractCommand command, boolean registerToHandler) {
        final var writeLock = lock.writeLock();
        try {
            writeLock.lock();
            if (registerToHandler) registerToHandler(command);
            commands.put(command.name(), command);
        } finally {
            writeLock.unlock();
        }
    }

    public void add(AbstractCommand command) {
        add(command, true);
    }

    public AbstractCommand remove(String name, boolean removeFromHandler) {
        final var writeLock = lock.writeLock();
        try {
            writeLock.lock();
            if (removeFromHandler) removeFromHandler(name);
            return commands.remove(name);
        } finally {
            writeLock.unlock();
        }
    }

    public AbstractCommand remove(String name) {
        return remove(name, true);
    }

    public AbstractCommand get(String name) {
        final var readLock = lock.readLock();
        try {
            readLock.lock();
            return commands.get(name);
        } finally {
            readLock.unlock();
        }
    }

    public void forEach(Consumer<AbstractCommand> consumer) {
        final var readLock = lock.readLock();
        try {
            readLock.lock();
            commands.values().forEach(consumer);
        } finally {
            readLock.unlock();
        }
    }

    public void reload() {
        final var writeLock = lock.writeLock();
        try {
            writeLock.lock();
            // I remove previous commands.
            commands.values()
                    .stream()
                    .peek(command -> removeFromHandler(command.name()))
                    .filter(AbstractCommand::shown)
                    .forEach(this::registerToHandler);
        } finally {
            writeLock.unlock();
        }
    }
}
