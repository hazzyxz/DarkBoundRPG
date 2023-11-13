package asciipanel;
import java.util.List;

public class PlayerAi extends CreatureAi {
    //extends the CreatureAi class

    //initialize array list of string call messages
    private List<String> messages;

    // constructor for the PlayerAi class
    public PlayerAi(Creature creature, List<String> messages) {
        //calls the constructor of the superclass (CreatureAi) and passes the creature parameter to it
        super(creature);
        //the constructor injection method
        this.messages = messages;
    }

    public void onEnter(int x, int y, Tile tile)
    //method for player movement
    {
        if (tile.isGround()){
            //can also be applied for doors,etc...
            creature.x = x;
            creature.y = y;

            //take the (x,y) of the tile that player is entering
            //update the (x,y) of player to new cord
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
            //call the dig method while passing new cord
        }
    }

    //constructor injection for message
    //list created in PlayScreen -> CreatureFactory -> PlayerAi
    public void onNotify(String message){
        messages.add(message);
    }



}
