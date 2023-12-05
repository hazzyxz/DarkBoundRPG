package asciipanel;

import asciipanel.Creature;
import asciipanel.Item;

//let PlayScreen know we're working a sub-screen and delegate input and output to that screen
//once done, sub-screen gets set to null and the PlayScreen works as normal

public class DropScreen extends InventoryBasedScreen {



    //subclass DropScreen
    public DropScreen(Creature player) {
        super(player);
    }

    //asking the use what they want to drop
    protected String getVerb() {
        return "drop";
    }

    // all items are acceptable(can be drop)
    protected boolean isAcceptable(Item item) {
        return true;
    }

    //selects what to drop, tell the player to do the work and return null since we are done with the DropScreen
    protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
}
