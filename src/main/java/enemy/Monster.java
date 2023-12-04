package enemy;

public abstract class Monster {
    protected String monsterName;
    protected int healthPoints;
    protected int manaPoints;
    protected int physicalAttack;
    protected int magicalAttack;
    protected int physicalDefense;
    protected int magicalDefense;
    protected boolean[] status;


    public Monster(String name, int health, int mana, int phyAtk, int magAtk, int phyDef, int magDef) {
        this.monsterName = name;
        this.healthPoints = health;
        this.manaPoints = mana;
        this.physicalAttack = phyAtk;
        this.magicalAttack = magAtk;
        this.physicalDefense = phyDef;
        this.magicalDefense = magDef;
        this.status = new boolean[]{false, false, false, false}; // Poison, Silence, Bleed, Blind
    }

    public String getMonsterName() {
        return monsterName;
    }

}
