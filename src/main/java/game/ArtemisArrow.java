package game;

public class ArtemisArrow extends Spell{
    public void cast(Creature creature, Creature other) {
        manaCost = 120;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 6 + 1;
            phyDamage = (int)(1.50 * creature.phyAttack());
            other.modifyHp(-phyDamage);
            message.add(" > You cast Artemis Arrow!");
            message.add(" > Your arrow pierces the enemies' armor");
            message.add(" > You hit the " + other.name() + " for " + phyDamage() + " damage");
        }
        else
            message.add(" > Not enough mana!");
    }
}
