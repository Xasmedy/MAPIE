/*
 * Copyright (c) 2023 - Xasmedy.
 * This file is part of the Mindustry API Extension Project licensed under GNU-GPLv3.
 *
 * The Project source-code can be found at https://github.com/Xasmedy/MAPIE
 * Contributors of this file may put their name into the copyright notice.
 */

package xasmedy.mapie.menu.entity;

import java.util.Optional;

public interface MenuTemplate {

    int getId();

    MenuTemplate copy();

    MenuTemplate makeSubMenu(MenuTemplate father);

    ButtonList getButtonList();

    void setTitle(String title);

    void setMessage(String message);

    void setResetOnContextChange(boolean shouldReset);

    void setCloseListener(CloseListener listener);

    String getTitle();

    String getMessage();

    boolean shouldResetOnContextChange();

    Optional<CloseListener> getCloseListener();
}
