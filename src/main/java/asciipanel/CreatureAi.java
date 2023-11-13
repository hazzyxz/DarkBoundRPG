package asciipanel;
import asciiLib.AsciiPanel;
public class CreatureAi {
    protected Creature creature;
    //stores a reference to the Creature object

    public CreatureAi(Creature creature){
        //constructor for CreatureAi
        this.creature = creature;
        this.creature.setCreatureAi(this);
        //associates the AI (this) with the Creature by calling a method setCreatureAi()
    }

    public void onEnter(int x, int y, Tile tile)
    //method is intended to be overridden by subclasses to define behavior when a creature enters a new position defined by the parameters
    {
    }
    //provides a framework for custom AI logic for different creatures in a game
    // Subclasses of CreatureAi can implement specific behavior for different types of creatures
    // by overriding the onEnter method to react to changes

    //empty onUpdate method
    public void onUpdate(){

    }

    //empty onNotify method
    //only used by playerAi
    public void onNotify(String message){

    }

}
