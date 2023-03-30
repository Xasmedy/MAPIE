# Mindustry API Extension.
[![](https://jitpack.io/v/Xasmedy/MAPIE.svg)](https://jitpack.io/#Xasmedy/MAPIE)

A library made to extend mindustry basic api functionalities.

### Warning! The Library is still under development and could contain bugs.

## Features

### Server-Side Icons
Unfortunately on the server side icons are not available, so I decided to make a class that contains all available unicodes.<br>
The unicodes can be loaded from the mindustry GitHub, from a file, or from a local proprieties file.

### Better Menus
I felt like mindustry menus where too "raw", so I thought it would have been nice to make a small menus library with functions like:<br>
- Menu - The main place where actions take place. (Like a JPanel)
- Menus Templates - A way to save the menu information.
- Buttons - A way to indicate what action a button should do, and what effect it has on the Menu.<br>

In this way you don't need to manage menus manually by using `Call.menu()` or `MenuMenus.registerMenu()`.