package asciipanel;

import java.util.ArrayList;

public abstract class Archetype {
    protected String archetypeName;
    protected int[] stats = new int[6];
    protected boolean[] statusEffect;
    protected int level;
    protected int experiencePoints;
    protected ArrayList<String> spells;
    protected ArrayList<String> inventory;

    public Archetype(String[] baseStats) {
        this.archetypeName = baseStats[0];
        this.stats[0] = Integer.parseInt(baseStats[1]);//health
        this.stats[1] = Integer.parseInt(baseStats[2]);//mana
        this.stats[2] = Integer.parseInt(baseStats[3]);//phy att
        this.stats[3] = Integer.parseInt(baseStats[4]);//mag att
        this.stats[4] = Integer.parseInt(baseStats[5]);//phy def
        this.stats[5] = Integer.parseInt(baseStats[6]);//mag def
        this.level = 1;
        this.experiencePoints = 0;
        this.statusEffect = new boolean[]{false, false, false, false};
    }



    //  Getters
    public String getArchetypeName() {
        return this.archetypeName;
    }

    public int getHealthPoints() {
        return this.stats[0];
    }

    public int getManaPoints() {
        return this.stats[1];
    }

    public int getPhysicalAttack() {
        return this.stats[2];
    }

    public int getMagicalAttack() {
        return this.stats[3];
    }

    public int getPhysicalDefense() {
        return this.stats[4];
    }

    public int getMagicalDefense() {
        return this.stats[5];
    }


    // Setters


    //  Levelling Up Algorithm
    public void gainExperience(int xp) {
        this.experiencePoints += xp;

        while (experiencePoints >= calculateExperiencePointsRequiredForNextLevel()) {
            this.experiencePoints -= calculateExperiencePointsRequiredForNextLevel();
            levelUp();
        }
    }

    private void levelUp() {
        this.level++;

        //  Stat increase based on archetype chosen
        increaseStatsOnLevelUp();
    }

    private int calculateExperiencePointsRequiredForNextLevel() {
        return (int)(1000*Math.pow(1.1,this.level-1));
    }

    protected void increaseStatsOnLevelUp() {
        //  This method is overridden by archetype subclass
        //  Default method does nothing
    }



    //  Printing Stuff
    public void printInfo() {
        System.out.println("Class: "+archetypeName);
        System.out.println("Level: "+level+" (XP: "+experiencePoints+" / "+calculateExperiencePointsRequiredForNextLevel()+")");
        System.out.println("Health: "+stats[0]);
        System.out.println("Mana: "+stats[1]);
        System.out.println("Physical Attack: "+stats[2]);
        System.out.println("Magical Attack: "+stats[3]);
        System.out.println("Physical Defense: "+stats[4]);
        System.out.println("Magical Defense: "+stats[5]);
    }


    //  Class loader after player select
    //  Can be anywhere not necessary to be here
    //  So it's at the bottom :/
    public static Archetype createCharacter(int choice) {
        return switch (choice) {
            case 2 -> new Mage(ArchetypeLoader.loadArchetype(choice));
            case 3 -> new Rogue(ArchetypeLoader.loadArchetype(choice));
            case 4 -> new Paladin(ArchetypeLoader.loadArchetype(choice));
            case 5 -> new Archer(ArchetypeLoader.loadArchetype(choice));
            default -> new Warrior(ArchetypeLoader.loadArchetype(choice)); // Default class is Warrior classID = 1
        };
    }
}