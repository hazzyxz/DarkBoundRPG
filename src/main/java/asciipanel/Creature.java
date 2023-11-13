package asciipanel;
import java.awt.Color;

public class Creature {
    private World world;

    //represent cord x
    public int x;
    //represent cord y
    public int y;


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
    private int attackValue;
    public int attackValue() { return attackValue; }
    private int defenseValue;
    public int defenseValue() { return defenseValue; }


    // later change this and others to incorporate phyattack, magattack ...
    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense){
        //constructor injection of creature class to set values

        this.world = world; //the world
        this.glyph = glyph; //the creature's glyph
        this.color = color; //the color of glyph
        this.maxHp = maxHp; //the Max HP of the creature
        this.hp = maxHp; //the Max HP of creature after damaged
        this.attackValue = attack; //the attack value of this creature
        this.defenseValue = defense; //the defense value of this creature
    }

    private CreatureAi ai;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

    public void dig(int wx, int wy) {
        //method to dig wall
        world.dig(wx, wy);
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
        else
            //if it does then call attack method
            //here can initiate battle screen method call
            attack(other);

        /*
        //declare other as creature in the world
        Creature other = world.creature(x+mx, y+my);

        //check if tile that is entered have monster
        if(other == null)
            //x + mx and y + my represent the potential new coordinates after the movement.
            //world.tile(x + mx, y + my) returns the type of tile that exists at the new position (x + mx, y + my)
            ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
        else
            //if it does then call attack method
            //here can initiate battle screen method call
            attack(other);

         */


    }


    //attack method
    //change this to incorporate battle screen
    public void attack(Creature other){
        //kill creature
        //call remove method from world class
        //world.remove(other);

        //damage will soon be change
        //take the attackers attack value - defenders defense value
        int amount = Math.max(0, attackValue() - other.defenseValue());

        //random number from 1 to amount of possible damage
        amount = (int)(Math.random() * amount) + 1;

        //call the modify hp method and passing -amount argument
        other.modifyHp(-amount);

        //call the notify method while passing the string
        notify("You attack the '%s' for %d damage.", other.glyph(), amount);
        other.notify("The '%s' attacks you for %d damage.", glyph, amount);

    }

    public void modifyHp(int amount) {
        //hp is minus the amount of damage taken
        hp += amount;

        //if no hp left then remove this creature
        if (hp < 1) {
            doAction("die");
            world.remove(this);
        }
    }

    //update method to update creature in world
    public void update(){
        //call update method based on each creature
        ai.onUpdate();
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
                // Skip locations outside the circular area
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
                else
                    other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
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


}
