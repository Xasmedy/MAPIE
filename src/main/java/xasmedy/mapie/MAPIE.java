/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie;

import arc.util.CommandHandler;
import mindustry.mod.Plugin;
import xasmedy.mapie.command.CommandRepository;
import xasmedy.mapie.icon.ChatIcons;
import xasmedy.mapie.menu.Menu;
import java.util.Objects;

public final class MAPIE {

    private static MAPIE singleton;

    private volatile CommandRepository clientCommands;
    private volatile CommandRepository serverCommands;
    public final ChatIcons chatIcons;
    public final Menu menu;

    private MAPIE() {
        this.chatIcons = new ChatIcons();
        this.menu = new Menu();
    }

    /**
     * Only call this inside {@link Plugin#init()} or after it ran.<br>
     * The value is cached, so this method can be called multiple times without generating new instances.
     */
    public synchronized static MAPIE init() {
        if (singleton == null) singleton = new MAPIE();
        return singleton;
    }

    public CommandRepository createClientCommands(CommandHandler clientHandler) {
        Objects.requireNonNull(clientHandler);
        synchronized (this) {
            if (clientCommands == null) clientCommands = new CommandRepository(clientHandler);
        }
        return clientCommands;
    }

    public CommandRepository clientCommands() {
        return clientCommands;
    }

    public CommandRepository createServerCommands(CommandHandler serverHandler) {
        Objects.requireNonNull(serverHandler);
        synchronized (this) {
            if (serverCommands == null) serverCommands = new CommandRepository(serverHandler);
        }
        return serverCommands;
    }

    public CommandRepository serverCommands() {
        return serverCommands;
    }
}
