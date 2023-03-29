package xasmedy.mapie.menu;

import mindustry.gen.Call;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    private final ArrayList<ArrayList<Button>> allButtons = new ArrayList<>();
    private final int id;
    private String title;
    private String message;

    private Menu(int id) {
        this.id = id;
        // TODO Register the menu externally.
    }

    private Menu(Menu menu) {
        this.id = menu.id;
        this.title = menu.title;
        this.message = menu.message;
        menu.allButtons.forEach(buttons -> allButtons.add(new ArrayList<>(buttons)));
    }

    public int getId() {
        return id;
    }

    public void display() {

        final String[][] options = new String[allButtons.size()][];

        for (int i = 0; i < allButtons.size(); i++) {
            options[i] = allButtons.get(i)
                    .stream()
                    .map(Button::getLabel)
                    .toArray(String[]::new);
        }

        // TODO Get the player?
        Call.menu(getId(), title, message, options);
    }

    public Menu copy() {
        return new Menu(this);
    }

    public void addButtons(int column, Button... buttons) {
        if (allButtons.size() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + allButtons.size());
        allButtons.add(column, new ArrayList<>(List.of(buttons)));
    }

    public void addButtons(Button... buttons) {
        allButtons.add(new ArrayList<>(List.of(buttons))); // Adds at the last position.
    }

    public void removeButtons(int column) {
        if (allButtons.size() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + allButtons.size());
        allButtons.remove(column);
    }

    public Button removeButton(int column, int row) {

        if (allButtons.size() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + allButtons.size());

        final ArrayList<Button> buttons = allButtons.get(column);
        if (buttons.size() <= row) throw new IndexOutOfBoundsException("Row: " + row + ", Size: " + buttons.size());

        return buttons.remove(row);
    }

    public boolean removeButton(int column, Button button) {

        if (allButtons.size() <= column) throw new IndexOutOfBoundsException("Column: " + column + ", Size: " + allButtons.size());
        return allButtons.get(column).remove(button);
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
