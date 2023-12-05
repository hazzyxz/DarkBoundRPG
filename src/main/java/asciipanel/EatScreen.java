package asciipanel;

import asciipanel.Creature;
import asciipanel.Item;

public class EatScreen extends InventoryBasedScreen {

    //subclass EatScreen
    public EatScreen(Creature player) {
        super(player);
    }
    //asking the use what they want to eat
    protected String getVerb() {
        return "eat";
    }
    // check if items are acceptable(can be eat)
    protected boolean isAcceptable(Item item) {
        return item.foodValue() != 0;
    }
    //selects what to eat, tell the player to do the work and return null since we are done with the EatScreen
    protected Screen use(Item item) {
        player.eat(item);
        return null;
    }
}
