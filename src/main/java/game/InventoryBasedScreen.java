package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import asciiPanel.AsciiPanel;

//a key that gets pressed -> verb (drop, quaff, read)
// -> check against the items (droppable, quaffable, readable),
// -> action
//class for common behaviour for inventory
public abstract class InventoryBasedScreen implements Screen {
    //reference to the player because that's the one who's going to do the work
    //protected so that the subclasses can use it
    protected Creature player;
    //assign each letter to inventory slot
    private String letters;
    //abstract methods so subclasses
    //can specify the verb,
    protected abstract String getVerb();
    //what items are acceptable for the action
    protected abstract boolean isAcceptable(Item item);
    //method to actually perform the action
    //Using an item returns a Screen since it may lead to a different screen
    protected abstract Screen use(Item item);

    public InventoryBasedScreen(Creature player){
        this.player = player;
        //assign to what key
        //this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.letters = "qwertyasdfghzxcvbniopjklm";
    }

    //write the list in the lower left hand corner and ask the user what to do
    public void displayOutput(AsciiPanel terminal) {
        ArrayList<String> lines = getList();

        int y = 23 - lines.size();
        int x = 4;

        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());

        for (String line : lines){
            terminal.write(line, x, y++);
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write("What would you like to " + getVerb() + "?", 2, 23);

        terminal.repaint();
    }

    //make a list of all the acceptable items and the letter for each corresponding inventory slot
    private ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<String>();
        Item[] inventory = player.inventory().getItems();

        for (int i = 0; i < inventory.length; i++){
            Item item = inventory[i];

            if (item == null || !isAcceptable(item))
                continue;

            String line = letters.charAt(i) + " - " + item.glyph() + " " + item.name();

            lines.add(line);
        }
        return lines;
    }

    //respond to user input
    /*
    Esc - return
    others - no response
    */
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();

        Item[] items = player.inventory().getItems();

        if (letters.indexOf(c) > -1
                && items.length > letters.indexOf(c)
                && items[letters.indexOf(c)] != null
                && isAcceptable(items[letters.indexOf(c)]))
            return use(items[letters.indexOf(c)]);
        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return null;
        else
            return this;
    }
}