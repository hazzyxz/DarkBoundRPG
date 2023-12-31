import java.util.ArrayList;

public class Archetype {
    protected String archetypeName;
    protected int[] stats = new int[8];
    protected boolean[] statusEffect;  // Poison, Silence, Stun, Blind
    protected int[] lvl = new int[2]; // 1. Level 2. XP
    protected int experiencePoints;
    protected ArrayList<String> spells;
    protected ArrayList<String> inventory;

    public Archetype() {

    }

    // setters
    public void setStats(String[] baseStats) {
        this.archetypeName = baseStats[0];
        this.stats[0] = Integer.parseInt(baseStats[1]);//max health
        this.stats[1] = Integer.parseInt(baseStats[2]);//health
        this.stats[2] = Integer.parseInt(baseStats[3]);//max mana
        this.stats[3] = Integer.parseInt(baseStats[4]);//mana
        this.stats[4] = Integer.parseInt(baseStats[5]);//phy attack
        this.stats[5] = Integer.parseInt(baseStats[6]);//mag attack
        this.stats[6] = Integer.parseInt(baseStats[7]);//phy defense
        this.stats[7] = Integer.parseInt(baseStats[8]);//mag defense
        this.lvl[0] = Integer.parseInt(baseStats[9]);
        this.lvl[1] = Integer.parseInt(baseStats[10]);
        this.statusEffect = new boolean[]{false, false, false, false};
    }

    public String[] baseStats(Archetype playerCharacter) {
        String[] baseStats;
        baseStats = ArchetypeLoader.loadArchetype(playerCharacter.getClassID(0));
        return baseStats;
    }


    //  Getters
    public String getArchetypeName() {
        return this.archetypeName;
    }

    public int getMaxHealthPoints() {
        return this.stats[0];
    }
    public int getHealthPoints() {
        return this.stats[1];
    }
    public int getMaxManaPoints() {
        return this.stats[2];
    }
    public int getManaPoints() {
        return this.stats[3];
    }

    public int getPhysicalAttack() {
        return this.stats[4];
    }

    public int getMagicalAttack() {
        return this.stats[5];
    }

    public int getPhysicalDefense() {
        return this.stats[6];
    }

    public int getMagicalDefense() {
        return this.stats[7];
    }

    public boolean[] getStatusEffect() {
        return statusEffect;
    }
    public int[] getLevel() {
        return this.lvl;
    }

    public int getClassID(int ID) {
        // Override
        return ID;
    }


    //  Printing Stuff
    public void printInfo() {
        System.out.println("Class: "+archetypeName);
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
            case 2 -> new Mage();
            case 3 -> new Rogue();
            case 4 -> new Paladin();
            case 5 -> new Archer();
            default -> new Warrior(); // Default class is Warrior classID = 1
        };
    }
}