package game;

import asciiPanel.AsciiPanel;

import java.util.ArrayList;
import java.util.List;

public class StuffFactory {
    private World world;
    private FieldOfView fov;
    private ArrayList<String> empty = new ArrayList<>();

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
        Creature player = new Creature(world, playerCharacter.archetypeName, '@', AsciiPanel.brightWhite,playerCharacter.getMaxHealthPoints(), playerCharacter.getHealthPoints(),playerCharacter.getMaxManaPoints(), playerCharacter.getManaPoints(), playerCharacter.getPhysicalAttack(), playerCharacter.getMagicalAttack(), playerCharacter.getPhysicalDefense(), playerCharacter.getMagicalDefense(), playerCharacter.getStatusEffect(),playerCharacter.getLevel(), playerCharacter.spells, "C:\\Users\\Ahmad Taufiq\\Desktop\\test ascii.txt");

        // adds the player character to an empty location in the game world
        world.addAtEmptyLocation(player);

        //creates a PlayerAi object, which manages the player's behavior and interactions and the messages
        new PlayerAi(player, messages, fov);

        return player;
    }

    public Creature newNpc(){
        // initialize npc in world with green 'N' and its stats
        Creature npc = new Creature(world, "Damsel",'N', AsciiPanel.brightMagenta, 1, 1, 1, 1, 1, 1,1,1, new boolean[]{false, false, false, false}, new int[]{1, 0}, empty,"/asciiArt/npc.txt");

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
        Creature fungus = new Creature(world, "fungus",'f', AsciiPanel.brightRed, 100, 100, 10, 10, 10,10,10, 10, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/test ascii.txt");

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
        Creature bat = new Creature(world, "bat",'b', AsciiPanel.brightRed, 150, 150, 15, 15, 10, 10, 10, 10, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/test ascii 2.txt");
        world.addAtEmptyLocation(bat);
        new BatAi(bat);
        return bat;
    }

    public Creature newGoblin(){
        Creature goblin = new Creature(world, "Goblin",'g',AsciiPanel.brightRed,40, 40, 1,1,10,1,5,2, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/goblinIdle.txt");
        world.addAtEmptyLocation(goblin);
        new GoblinAi(goblin);
        return goblin;
    }

    public Creature newSkeleton() {
        Creature skeleton = new Creature(world, "Skeleton",'s',AsciiPanel.brightRed,60, 60, 1,1,12,1,7,3, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/skeletonIdle.txt");
        world.addAtEmptyLocation(skeleton);
        new SkeletonAi(skeleton);
        return skeleton;
    }

    public Creature newWitch(){
        Creature witch = new Creature(world, "Witch",'w',AsciiPanel.brightRed,50,50, 80,80,8,20,6,12, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/witch.txt");
        world.addAtEmptyLocation(witch);
        new WitchAi(witch);
        return witch;
    }

    public Creature newOrc(){
        Creature orc = new Creature(world, "Orc",'O',AsciiPanel.brightRed,70, 70, 1,1,15,1,9,4, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/orcIdle.txt");
        world.addAtEmptyLocation(orc);
        new OrcAi(orc);
        return orc;
    }

    public Creature newHarpy(){

        Creature harpy = new Creature(world, "Harpy",'h',AsciiPanel.brightRed,60, 60, 20,20,14,10,8,8, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/harpyIdle.txt");
        world.addAtEmptyLocation(harpy);
        new HarpyAi(harpy);
        return harpy;
    }

    public Creature newGoblin2(){
        Creature goblin = new Creature(world, "Hobgoblin",'G',AsciiPanel.brightRed,80, 80, 1,1,20,2,10,4, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/test ascii 2.txt");
        world.addAtEmptyLocation(goblin);
        new GoblinAi(goblin);
        return goblin;
    }

    public Creature newSkeleton2() {
        Creature skeleton = new Creature(world, "Skeleton Warrior",'S',AsciiPanel.brightRed,120, 120, 1,1,24,1,14,6, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/test ascii 2.txt");
        world.addAtEmptyLocation(skeleton);
        new SkeletonAi(skeleton);
        return skeleton;
    }

    public Creature newWitch2(){
        Creature witch = new Creature(world, "Coven Witch",'W',AsciiPanel.brightRed,100,100, 160,160,16,40,12,24, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/witch.txt");
        world.addAtEmptyLocation(witch);
        new WitchAi(witch);
        return witch;
    }

    public Creature newOrc2(){
        Creature orc = new Creature(world, "Ogre",'0',AsciiPanel.brightRed,140, 140, 1,1,30,1,18,8, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/test ascii 2.txt");
        world.addAtEmptyLocation(orc);
        new OrcAi(orc);
        return orc;
    }

    public Creature newHarpy2(){
        Creature harpy = new Creature(world, "Stygian Harpy",'H',AsciiPanel.brightRed,120, 120, 40,40,28,20,16,16, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/test ascii 2.txt");
        world.addAtEmptyLocation(harpy);
        new HarpyAi(harpy);
        return harpy;
    }


    //create boss character
    public Creature newBoss(){
        Creature boss = new Creature(world, "Margit, Omen Jatuh",'B',AsciiPanel.brightRed,300, 300, 50,50,80,20,70,50, new boolean[]{false, false, false, false}, new int[]{1, 0},empty,"/asciiArt/boss1.txt");
        world.addAtEmptyLocation(boss);
        new BossAi(boss);
        return boss;
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
