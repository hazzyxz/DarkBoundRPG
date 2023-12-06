package asciipanel;

import asciiLib.AsciiPanel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Creature {
    private World world;

    //represent cord x
    public int x;
    //represent cord y
    public int y;

    private String name;
    private AsciiPanel BattleScreen;


    public String name() { return name; }
    //stores visual representation (glyph) of the creature
    private char glyph;
    //getter for glyph
    //to retrieve glyph of the creature
    public char glyph() { return glyph; }


    // stores the color used to display the creature's glyph
    private Color color;
    //getter for color
    public Color color() { return color; }

    //getter and setter
    private int maxHp;
    public int maxHp() { return maxHp; }
    private int hp;
    public int hp() { return hp; }

    private int maxMp;
    public int maxMp() { return maxMp; }

    private int mp;
    public int mp() { return mp; }

    private int phyAttack;
    public int phyAttack() { return phyAttack; }
    private int magAttack;
    public int magAttack() { return magAttack; }

    private int maxPhyDefense;
    public int maxPhyDefense() { return maxPhyDefense; }
    private int maxMagDefense;
    public int maxMagDefense() { return maxMagDefense; }

    private int phyDefense;
    public int phyDefense() { return phyDefense; }
    private int magDefense;
    public int magDefense() { return magDefense; }


    private Inventory inventory;
    public Inventory inventory() { return inventory; }

    private int maxFood;
    public int maxFood() { return maxFood; }

    private int food;
    public int food() { return food; }

    private String asciiPath;
    public String asciiPath() { return asciiPath; }

    private Screen subscreen;
    private Creature player;



    // later change this and others to incorporate phyattack, magattack ...
    public Creature(World world, String name, char glyph, Color color, int maxHp, int maxMp, int phyAttack, int magAttack, int maxPhyDefense, int maxMagDefense, String asciiPath){
        //constructor injection of creature class to set values

        this.world = world; //the world

        this.name = name; //the creature name
        this.glyph = glyph; //the creature's glyph
        this.color = color; //the color of glyph

        this.maxHp = maxHp; //the Max HP of the creature
        this.hp = maxHp; //the Max HP of creature after damaged
        this.maxMp = maxMp;
        this.mp = maxMp;

        this.phyAttack = phyAttack; //the attack value of this creature
        this.magAttack = magAttack;

        this.maxPhyDefense = maxPhyDefense; //the defense value of this creature
        this.phyDefense = maxPhyDefense;
        this.maxMagDefense = maxMagDefense; //the defense value of this creature
        this.magDefense = maxMagDefense;

        this.inventory = new Inventory(20); //the inventory slot of this creature
        this.maxFood = 2000; //the max food creature can eat
        this.food = maxFood / 3 * 2; //the amount of food creature is on start
        this.asciiPath = asciiPath; //the ascii filepath for creature
    }

    public void setPlayerClass(Creature player) {

    }

    private CreatureAi ai;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

    //method to dig wall
    public void dig(int wx, int wy) {
        world.dig(wx, wy);
        modifyFood(-5);
        doAction("dig");
    }

    //ai movement method
    public void moveBy(int mx, int my){
        Tile tile = world.tile(x+mx, y+my);

        //give message when reach world boundaries

        //declare other as creature in the world
        Creature other = world.creature(x+mx, y+my);

        //check if tile that is entered have monster
        if (other == null)
            //represent the potential new coordinates after the movement.
            //returns the type of tile that exists at the new position
            ai.onEnter(x+mx, y+my, tile);
        else {
            //attack(other);
        }

        if (mx==0 && my==0)
            return;

    }


    public void modifyHp(int amount) {
        //hp is minus the amount of damage taken
        hp += amount;
        isDead();

        //if no hp left then remove this creature
        /*
        doAction("die");
        leaveCorpse();
        world.remove(this);
         */
    }

    public void modifyDefense(int amount) {
        //defense value
        phyDefense += amount;
    }

    public void resetDefense(){
        //hp is minus the amount of damage taken
        phyDefense = maxPhyDefense;
    }

    public boolean isDead(){
        if(hp<1) {
            world.remove(this);
            return true;
        }
        return false;
    }

    //update method to update creature in world
    public void update(){
        //call update method based on each creature
        ai.onUpdate();
        //set hunger -1 every turn
        modifyFood(-1);
    }

    //check if creature can enter the tile
    public boolean canEnter(int wx, int wy) {
        return world.tile(wx, wy).isGround() && world.creature(wx, wy) == null;
    }

    //make easier for callers to build messages
    // notify is in third person
    //take the string and any parameters
    public void notify(String message, Object ... params){
        ai.onNotify(String.format(message, params));

    }

    //to notify creature of action around them
    // doAction is in second person
    //call doAction in your creature code and anyone nearby will be notified
    public void doAction(String message, Object ... params){
        int r = 9;
        // Loop through a square area centered around the creature
        for (int ox = -r; ox < r+1; ox++){
            for (int oy = -r; oy < r+1; oy++){
                if (ox*ox + oy*oy > r*r)
                    continue;

                //get the current location of creature
                Creature other = world.creature(x+ox, y+oy);

                // Skip empty locations
                if (other == null)
                    continue;

                // Notify the creature about the action
                if (other == this)
                    other.notify("You " + message + ".", params);
                else if (other.canSee(x, y))
                    other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
            }
        }
    }

    //to convert 3rd person to 2nd person
    private String makeSecondPerson(String text){
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words){
            builder.append(" ");
            builder.append(word);
        }

        return builder.toString().trim();
    }

    // give creatures a new stat to say how far they can see
    //set the vision range of 9
    private int visionRadius = 9;
    public int visionRadius() { return visionRadius; }

    public boolean canSee(int wx, int wy){
        return ai.canSee(wx, wy);
    }

    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    //Creatures able to see what other creatures are in the world so the CreatureAi can know
    public Creature creature(int wx, int wy) {
        return world.creature(wx, wy);
    }

    //method for picking up stuff

    public void pickup(){
        Item item = world.item(x, y);
        if (inventory.isFull() || item == null){
            doAction("grab at the ground");
        } else {
            doAction("pickup a %s", item.name());
            world.remove(x, y);
            inventory.add(item);
        }
    }

    //method for dropping stuff
    public void drop(Item item){
        doAction("drop a " + item.name());
        inventory.remove(item);
        world.addAtEmptySpace(item, x, y);
    }

    //leave corpse after died
    public void leaveCorpse(){
        Item corpse = new Item('%', color, name + " corpse");
        corpse.modifyFoodValue(maxHp * 3);
        world.addAtEmptySpace(corpse, x, y);
    }

    //add food logic into the game
    public void modifyFood(int amount) {
        food += amount;
        if (food > maxFood) {
            food = maxFood;
        //kill if hunger = 0
        } else if (food < 1 && isPlayer()) {
           // modifyHp(-1000);
        }
    }
    public boolean isPlayer(){
        return glyph == '@';
    }


    //method for creature to eat
    public void eat(Item item){
        modifyFood(item.foodValue());
        inventory.remove(item);
    }

    public List<Point> neighbors8() {
        List<Point> points = new ArrayList<Point>();

        for (int ox = -1; ox < 2; ox++){
            for (int oy = -1; oy < 2; oy++){
                if (ox == 0 && oy == 0)
                    continue;

                points.add(new Point(x+ox, y+oy));
            }
        }

        Collections.shuffle(points);
        return points;
    }


}
