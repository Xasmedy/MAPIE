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

    // TODO Make this a singleton.
    private static final String FILE_NAME = "icons.properties";
    private static final String ICONS_FILE_LINK = "https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets/icons/" + FILE_NAME;
    private static final AtomicBoolean IS_LOADED = new AtomicBoolean(false);
    private static final ObjectMap<String, String> STRING_ICONS = new ObjectMap<>();
    private static final ObjectIntMap<String> UNICODE_ICONS = new ObjectIntMap<>();

    private static void setIconsFromSource(InputStream source) {

        // I set it at the moment it is getting loaded.
        if (IS_LOADED.getAndSet(true)) return;

        final Scanner scanner = new Scanner(source);
        while (scanner.hasNextLine()) {
            /*
            0 -> int    - unicode
            1 -> String - content name
            2 -> String - content name ui
             */
            final String[] values = scanner.nextLine().split("[=|]");
            final int unicode = Integer.parseInt(values[0]);
            final String name = values[1];
            // For now, I don't need ui names.

            UNICODE_ICONS.put(name, unicode);
            final String unicodeString = "\\u" + Integer.toHexString(unicode);
            STRING_ICONS.put(name, unicodeString);
        }
    }

    public static int getIcon(String content) {
        return UNICODE_ICONS.get(content);
    }

    public static char getIconChar(String content) {
        return (char) getIcon(content);
    }

    public static String getIconStr(String content) {
        return STRING_ICONS.get(content);
    }

    /**
     * Asks the icon.properties from GitHub.
     * @throws IOException In case of a problem with the connection.
     */
    public static void loadFromGithub() throws IOException {

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
    public static void loadFromFile(String iconProprieties) throws IOException {
        final FileInputStream input = new FileInputStream(iconProprieties);
        setIconsFromSource(input);
        input.close();
    }

    /**
     * Loads the icon.properties from the internal file.
     */
    public static void loadFromProprieties() {

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
    public static void loadReliable() {
        try {
            loadFromGithub();
        } catch (IOException ignored) {
            loadFromProprieties();
        }
    }
}
