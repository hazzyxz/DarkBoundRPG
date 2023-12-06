package asciipanel;
import asciiLib.AsciiPanel;
import java.util.List;

public class StuffFactory {
    private World world;
    private FieldOfView fov;
    Archetype playerCharacter = Archetype.createCharacter(ChooseClassScreen.classChoice);


    public StuffFactory(World world, FieldOfView fov){
        //constructor for CreatureFactory initializes the world attribute
        this.world = world;
        this.fov = fov;
    }

    //The Creature constructor is used to create player creature
    public Creature newPlayer(List<String> messages, Archetype playerCharacter){
        // initialize player in world with white '@' and its stats
        // World represents the game world to which the creature belongs
        // player character is represented by the '@' glyph and is displayed in bright white color
        Creature player = new Creature(world, "Sissyphus",'@', AsciiPanel.brightWhite, playerCharacter.getHealthPoints(), playerCharacter.getManaPoints(), playerCharacter.getPhysicalAttack(), playerCharacter.getMagicalAttack(), playerCharacter.getPhysicalDefense(), playerCharacter.getMagicalDefense(), "C:\\Users\\Ahmad Taufiq\\Desktop\\test ascii.txt");

        // adds the player character to an empty location in the game world
        world.addAtEmptyLocation(player);

        //creates a PlayerAi object, which manages the player's behavior and interactions and the messages
        new PlayerAi(player, messages, fov);

        return player;
    }

    public Creature newNpc(){
        // initialize npc in world with green 'N' and its stats
        Creature npc = new Creature(world, "Npc",'N', AsciiPanel.brightMagenta, 0, 0, 0, 0,0,0,"C:\\Users\\Ahmad Taufiq\\Desktop\\test ascii.txt");

        // adds the npc creature to an empty location in the game world
        world.addAtEmptyLocation(npc);

        //creates a npc Ai object, which manages the fungus's behavior and interactions
        new NpcAi(npc, this);
        return npc;
    }

    //The Creature constructor is used to create fungus creature
    //later change creature factory to incoporate enemy data from .txt
    public Creature newFungus(){
        // initialize fungus in world with green 'f' and its stats
        Creature fungus = new Creature(world, "fungus",'f', AsciiPanel.brightRed, 100, 10, 10, 10,10,10,"C:\\Users\\Ahmad Taufiq\\Desktop\\test ascii.txt");

        // adds the fungus creature to an empty location in the game world
        world.addAtEmptyLocation(fungus);

        //FungusAi fungusAi = new FungusAi(fungus, this);
        //fungus.setCreatureAi(fungusAi);
        //return fungus;

        //creates a fungus Ai object, which manages the fungus's behavior and interactions
        new FungusAi(fungus, this);
        return fungus;
    }

    //The Creature constructor is used to create bat creature
    public Creature newBat(){
        Creature bat = new Creature(world, "bat",'b', AsciiPanel.brightRed, 150, 15, 10, 10, 10, 10,"C:\\Users\\Ahmad Taufiq\\Desktop\\test ascii 2.txt");
        world.addAtEmptyLocation(bat);
        new BatAi(bat);
        return bat;
    }

    // a rock item
    public Item newRock(){
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock);
        return rock;
    }

    public Item newVictoryItem(){
        Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
        world.addAtEmptyLocation(item);
        return item;
    }




}
