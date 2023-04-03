/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.icon;

import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * It loads all the mindustry <a href="https://github.com/Anuken/Mindustry/blob/master/core/assets/icons/icons.properties">icons</a+>.<br>
 * This class is Thread-Safe.
 */
public final class ChatIcons {

    private static final String FILE_NAME = "icons.properties";
    private static final String ICONS_FILE_LINK = "https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets/icons/" + FILE_NAME;
    private static ChatIcons instance;
    private final AtomicBoolean areIconsLoaded = new AtomicBoolean(false);
    private final ObjectMap<String, String> STRING_ICONS = new ObjectMap<>();
    private final ObjectIntMap<String> UNICODE_ICONS = new ObjectIntMap<>();

    private ChatIcons() {}

    public static ChatIcons get() {
        if (instance == null) instance = new ChatIcons();
        return instance;
    }

    private void setIconsFromSource(InputStream source) {

        // I set it at the moment it is getting loaded.
        if (areIconsLoaded.getAndSet(true)) return;

        final Scanner scanner = new Scanner(source);
        while (scanner.hasNextLine()) {
            /*
            0 -> int    - unicode
            1 -> String - content name
            2 -> String - content name ui
             */
            final String nextLine = scanner.nextLine();
            // I ignore comments lines.
            if (nextLine.trim().startsWith("#")) continue;

            final String[] values = nextLine.split("[=|]");
            final int unicode = Integer.parseInt(values[0]);
            final String name = values[1];
            // For now, I don't need ui names.

            UNICODE_ICONS.put(name, unicode);
            final String unicodeString = "\\u" + Integer.toHexString(unicode);
            STRING_ICONS.put(name, unicodeString);
        }
    }

    public int getIcon(String content) {
        return UNICODE_ICONS.get(content);
    }

    public char getIconChar(String content) {
        return (char) getIcon(content);
    }

    public String getIconStr(String content) {
        return STRING_ICONS.get(content);
    }

    /**
     * Asks the icon.properties from GitHub.
     * @throws IOException In case of a problem with the connection.
     */
    public void loadFromGithub() throws IOException {

        final URL url;
        try {
            url = new URL(ICONS_FILE_LINK);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }

        final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        final InputStream input = con.getInputStream();

        // I load the unicode.
        setIconsFromSource(input);
        input.close();
        con.disconnect();
    }

    /**
     * Loads the icon.properties from a file.
     * @param iconProprieties The file path.
     * @throws IOException In case of a problem with the stream.
     */
    public void loadFromFile(String iconProprieties) throws IOException {
        final FileInputStream input = new FileInputStream(iconProprieties);
        setIconsFromSource(input);
        input.close();
    }

    /**
     * Loads the icon.properties from the internal file.
     */
    public void loadFromProprieties() {

        try (final InputStream input = ChatIcons.class.getResourceAsStream("/" + FILE_NAME)) {
            if (input == null) throw new IllegalStateException("Proprieties file is not present.");
            setIconsFromSource(input);
        } catch (IOException e) {
            // I don't think there could be problems while reading proprieties.
            throw new IllegalStateException(e);
        }
    }

    /**
     * Tries to load the icon.properties from GitHub, if it fails it loads it from the internal file.
     */
    public void loadReliable() {
        try {
            loadFromGithub();
        } catch (IOException ignored) {
            loadFromProprieties();
        }
    }
}
