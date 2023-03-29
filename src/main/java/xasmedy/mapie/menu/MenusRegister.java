package xasmedy.mapie.menu;

import arc.Events;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.ui.Menus;
import xasmedy.mapie.menu.builder.MenuTemplate;
import xasmedy.mapie.menu.button.Button;
import xasmedy.mapie.menu.close.ClosureType;
import java.util.HashMap;

public class MenusRegister {

    /**
     * Only one menu per player. (I don't see a reason to have more than one)
     */
    private final HashMap<Player, Menu> playerMenus = new HashMap<>();

    public MenusRegister() {
        Events.on(EventType.PlayerLeave.class, e -> {
            if (!playerMenus.containsKey(e.player)) return;
            close(e.player, ClosureType.PLAYER_LEAVE);
        });
    }

    private void handleMenuAction(Player player, int option) {

        // When the player exits the menu by using other means.
        // e.g. clicking outside the menu, pressing esc.
        if (option == -1) {
            close(player, ClosureType.LOST_FOCUS);
            return;
        }

        final Menu menu = playerMenus.get(player);
        final Button button = menu.getCurrentTemplate()
                .getButtons()
                .get(option);

        // I reload myself, making it look like nothing happen.
        if (button.isDisabled()) menu.displayMyself();
        // I trigger the listeners.
        else button.getListeners().forEach(listener -> listener.action(menu, button));
    }

    void close(Player player, ClosureType type) {
        final Menu menu = playerMenus.remove(player);
        menu.getCurrentTemplate()
                .getCloseAction()
                .ifPresent(listener -> listener.action(menu, type));
    }

    public MenuTemplate registerNewMenu() {
        final int id = Menus.registerMenu(this::handleMenuAction);
        return new MenuTemplate(id);
    }

    public MenuTemplate newChildMenu(int fatherId) {
        return new MenuTemplate(fatherId);
    }
}
