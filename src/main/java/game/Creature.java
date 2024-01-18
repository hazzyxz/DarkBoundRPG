package game;

import asciiPanel.AsciiPanel;

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
    private int maxPhyAttack;
    public int maxPhyAttack() { return maxPhyAttack; }
    private int maxMagAttack;
    public int maxMagAttack() { return maxMagAttack; }

    private int maxPhyDefense;
    public int maxPhyDefense() { return maxPhyDefense; }
    private int maxMagDefense;
    public int maxMagDefense() { return maxMagDefense; }

    private int phyDefense;
    public int phyDefense() { return phyDefense; }
    private int magDefense;
    public int magDefense() { return magDefense; }

    private boolean[] statusEffect;
    public boolean statusEffect(int effect) {
        return statusEffect[effect];
    }
    public void setPoison() {
        statusEffect[0] = true;
    }
    public void removePoison() {
        statusEffect[0] = false;
    }

    public void setStun() {
        statusEffect[1] = true;
    }
    public void removeStun() {
        statusEffect[1] = false;
    }

    public void setSilence() {
        statusEffect[2] = true;
    }
    public void removeSilence() {
        statusEffect[2] = false;
    }

    public void setBlind() {
        statusEffect[3] = true;
    }
    public void removeBlind() {
        statusEffect[3] = false;
    }

    private Inventory inventory;
    public Inventory inventory() { return inventory; }
    private int hpFlask;
    public int hpFlask() {
        return hpFlask;
    }
    public void setHpFlask(int value) {
        hpFlask = value;
    }
    public void modifyHpFlask(int value) {
        hpFlask += value;
    }
    public int maxHpFlask = 2;
    public int maxHpFlask() {
        return maxHpFlask;
    }
    private int mpFlask;
    public int mpFlask() {
        return mpFlask;
    }
    public void setMpFlask(int value) {
        mpFlask = value;
    }
    public void modifyMpFlask(int value) {
        mpFlask += value;
    }
    public int maxMpFlask = 2;
    public int maxMpFlask() {
        return maxMpFlask;
    }

    private int spell1Cost;
    public int spell1Cost() {
        return spell1Cost;
    }

    private int spell2Cost;
    public int spell2Cost() {
        return spell2Cost;
    }

    private int spell3Cost;
    public int spell3Cost() {
        return spell3Cost;
    }

    private int maxFood;
    public int maxFood() { return maxFood; }

    private int food;
    public int food() { return food; }

    private int maxXp;
    public int maxXp() { return maxXp; }
    private int xp;
    public int xp() { return xp; }
    private int level;
    public int level() { return level; }

    private ArrayList<String> spellList;
    public ArrayList<String> spellList() {
        return spellList;
    }

    private int spell1Cooldown;
    public int spell1Cooldown() {
        return spell1Cooldown;
    }
    public void setSpell1Cooldown(int value) {
        spell1Cooldown = value;
    }
    public void modifySpell1Cooldown (int value) {
        spell1Cooldown += value;
    }

    private int spell2Cooldown;
    public int spell2Cooldown() {
        return spell2Cooldown;
    }
    public void setSpell2Cooldown(int value) {
        spell2Cooldown = value;
    }
    public void modifySpell2Cooldown (int value) {
        spell2Cooldown += value;
    }

    private int spell3Cooldown;
    public int spell3Cooldown() {
        return spell3Cooldown;
    }
    public void setSpell3Cooldown(int value) {
        spell3Cooldown = value;
    }
    public void modifySpell3Cooldown (int value) {
        spell3Cooldown += value;
    }

    private int spell1Uptime = -1;
    public int spell1Uptime() {
        return spell1Uptime;
    }
    public void setSpell1Uptime(int value) {
        spell1Uptime = value;
    }
    public void modifySpell1Uptime(int value) {
        spell1Uptime += value;
    }

    private int spell2Uptime = -1;
    public int spell2Uptime() {
        return spell2Uptime;
    }
    public void setSpell2Uptime(int value) {
        spell2Uptime = value;
    }
    public void modifySpell2Uptime(int value) {
        spell2Uptime += value;
    }

    private int spell3Uptime = -1;
    public int spell3Uptime() {
        return spell3Uptime;
    }
    public void setSpell3Uptime(int value) {
        spell3Uptime = value;
    }
    public void modifySpell3Uptime(int value) {
        spell3Uptime += value;
    }


    private String asciiPath;
    public String asciiPath() { return asciiPath; }

    private Screen subscreen;
    private Creature player;



    // later change this and others to incorporate phyattack, magattack ...
    public Creature(World world, String name, char glyph, Color color, int maxHp, int hp, int maxMp, int mp, int maxPhyAttack, int maxMagAttack, int maxPhyDefense, int maxMagDefense, boolean[] statusEffect, int[] lvl, ArrayList<String> spells, String asciiPath){
        //constructor injection of creature class to set values

        this.world = world; //the world

        this.name = name; //the creature name
        this.glyph = glyph; //the creature's glyph
        this.color = color; //the color of glyph

        this.maxHp = maxHp; //the Max HP of the creature
        this.hp = hp; //the Max HP of creature after damaged
        this.maxMp = maxMp;
        this.mp = mp;

        this.phyAttack = maxPhyAttack; //the attack value of this creature
        this.maxPhyAttack = maxPhyAttack; //the attack value of this creature
        this.magAttack = maxMagAttack;
        this.maxMagAttack = maxMagAttack; //the attack value of this creature


        this.maxPhyDefense = maxPhyDefense; //the defense value of this creature
        this.phyDefense = maxPhyDefense;
        this.maxMagDefense = maxMagDefense; //the defense value of this creature
        this.magDefense = maxMagDefense;

        this.statusEffect = statusEffect;

        this.inventory = new Inventory(20); //the inventory slot of this creature
        this.maxFood = 2000; //the max food creature can eat
        this.food = maxFood / 3 * 2; //the amount of food creature is on start

        this.level = lvl[0]; // creature's level
        this.maxXp = (int)(100*Math.pow(1.1,lvl[0]-1)); //max xp before level up
        this.xp = lvl[1]; //the amount of xp of creature

        if (!spells.isEmpty()) {
            this.spellList = new ArrayList<>();
            this.spellList.add(spells.get(0));
            this.spellList.add(spells.get(1));
            this.spellList.add(spells.get(2));
        }

        this.spell1Cooldown = 0;
        this.spell2Cooldown = 0;
        this.spell3Cooldown = 0;

        this.asciiPath = asciiPath; //the ascii filepath for creature
    }

    private CreatureAi ai;
    public void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    public void creatureTurn(Creature other){
        ai.onTurn(other);
    }

    /*
    public int attack(Creature other){
        int amount = ai.attack(other);
        //either 5 0r other

        amount = Math.max(5,amount);

        return amount;
    }
     */

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
        else if(other != null){

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

    public void modifyMaxHp(int amount) {
        //hp is minus the amount of damage taken
        maxHp += amount;

        //if no hp left then remove this creature
        /*
        doAction("die");
        leaveCorpse();
        world.remove(this);
         */
    }

    public void modifyMp(int amount) {
        mp += amount;
    }

    public void setMp(int amount) {
        mp = amount;
    }

    public void modifyMaxMp(int amount) {
        maxMp += amount;
    }

    public void modifyPhyDefense(int amount) {
        //defense value
        phyDefense += amount;
    }

    public void modifyMaxPhyDefense(int amount) {
        //defense value
        maxPhyDefense += amount;
    }

    public void modifyMagDefense(int amount) {
        //defense value
        magDefense += amount;
    }

    public void modifyMaxMagDefense(int amount) {
        //defense value
        maxMagDefense += amount;
    }

    public void modifyPhyAttack(int amount) {
        //defense value
        phyAttack += amount;
    }

    public void modifyMagAttack(int amount) {
        magAttack += amount;
    }

    public void resetPhyDefense(){
        //hp is minus the amount of damage taken
        phyDefense = maxPhyDefense;
    }

    public void resetMagDefense(){
        //hp is minus the amount of damage taken
        magDefense = maxMagDefense;
    }

    public void resetAll(){
        phyDefense = maxPhyDefense;
        magDefense = maxMagDefense;
        phyAttack = maxPhyAttack;
        magAttack = maxMagAttack;
    }

    public void modifyFood(int amount) {
        food += amount;
        if (food > maxFood) {
            food = maxFood;
            //kill if hunger = 0
        } else if (food < 1 && isPlayer()) {
            // modifyHp(-1000);
        }
    }

    public void modifyXp(int amount) {
        xp += amount;

        //notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);
        //(int)(Math.pow(level, 1.5) * 20)
        while (xp > (int)(100*Math.pow(1.1,this.level-1)) && level < 35) {
            xp -= (int)(100*Math.pow(1.1,this.level-1));
            level++;
            maxXp = (int)(100*Math.pow(1.1,this.level-1));
            //doAction("advance to level %d", level);
            //ai.onGainLevel();
            //this.hp = maxHp;
            //this.mp = maxMp;
            increaseStats();
            //this.hp = this.maxHp;
            //this.mp = this.maxMp;
            doAction("gain a level!");
        }
    }

    public void increaseStats(){
        if (this.level >= 5)
            spellList.set(0, getClassSpell1());
        if (this.level >= 15)
            spellList.set(1, getClassSpell2());
        if (this.level >= 30)
            spellList.set(2, getClassSpell3());
        doAction("feel a power surging through");

        switch (name) {
            case "Warrior":
                modifyMaxHp(level/3);
                modifyPhyDefense(level/3);
                modifyMaxPhyDefense(level/3);
                modifyMagDefense(level/3);
                modifyMaxMagDefense(level/3);
                break;
            case "Mage":
                modifyMagAttack(level/3);
                modifyMaxMp(level/3);
                break;
            case "Rogue":
                modifyPhyAttack(level/3);
                modifyPhyDefense(level/3);
                modifyMaxPhyDefense(level/3);
                break;
            case "Paladin":
                modifyPhyAttack(level/3);
                modifyMagAttack(level/3);
                break;
            case "Archer":
                modifyPhyAttack(level/2);
        }
        modifyMaxHp(level);
        modifyMaxMp(level);
        modifyPhyDefense(level);
        modifyMaxPhyDefense(level);
        modifyMagDefense(level);
        modifyMaxMagDefense(level);
        modifyPhyAttack(level);
        modifyMagAttack(level);
    }

    private String getClassSpell1() {
        switch(name) {
            case "Mage":
                return "Magic Shield";
            case "Rogue":
                return "Silent Domain";
            case "Paladin":
                return "Divine Shield";
            case "Archer":
                return "Sand Throw";
            default:
                return "Regal Roar";
        }
    }

    private String getClassSpell2() {
        switch(name) {
            case "Mage":
                return "Fireball";
            case "Rogue":
                return "Backstab";
            case "Paladin":
                return "Dual Smite";
            case "Archer":
                return "Coated Arrows";
            default:
                return "Furious Strike";
        }
    }

    private String getClassSpell3() {
        switch(name) {
            case "Mage":
                return "Flow of Void";
            case "Rogue":
                return "Dark Daggers";
            case "Paladin":
                return "Ancient Blessing";
            case "Archer":
                return "Artemis Arrow";
            default:
                return "Great Phalanx";
        }
    }

    public void gainXp(Creature creature){
        int amount = creature.maxHp
                + creature.phyAttack()
                + creature.magAttack()
                + creature.phyDefense()
                + creature.magDefense()
                - level * 3;

        if (amount > 0) {
            modifyXp(amount);
            doAction("gain %d xp from killing a %s",amount,creature.name);
        }
    }

    public boolean isDead(){
        if(hp<1) {
            world.remove(this);
            return true;
        }
        return false;
    }

    public void removeCreature(){
        world.remove(this);
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
