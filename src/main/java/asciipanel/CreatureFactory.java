package asciipanel;
import asciiLib.AsciiPanel;
import java.util.List;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        //constructor for CreatureFactory initializes the world attribute
        this.world = world;
    }

    //The Creature constructor is used to create player creature
    public Creature newPlayer(List<String> messages){
        // initialize player in world with white '@' and its stats
        // World represents the game world to which the creature belongs
        // player character is represented by the '@' glyph and is displayed in bright white color
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);

        // adds the player character to an empty location in the game world
        world.addAtEmptyLocation(player);

        //creates a PlayerAi object, which manages the player's behavior and interactions and the messages
        new PlayerAi(player, messages);

        return player;
    }

    //The Creature constructor is used to create fungus creature
    //later change creature factory to incoporate enemy data from .txt
    public Creature newFungus(){
        // initialize fungus in world with green 'f' and its stats
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);

        // adds the fungus creature to an empty location in the game world
        world.addAtEmptyLocation(fungus);

        //FungusAi fungusAi = new FungusAi(fungus, this);
        //fungus.setCreatureAi(fungusAi);
        //return fungus;

        //creates a fungus Ai object, which manages the fungus's behavior and interactions
        new FungusAi(fungus, this);
        return fungus;
    }


}
