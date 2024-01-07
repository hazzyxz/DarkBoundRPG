package game;

public class DualSmite extends Spell{
    public void cast(Creature creature, Creature other) {
        manaCost = 40;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 4 + 1;

            phyDamage = (int) (0.5 * creature.phyAttack());
            phyDamage -= other.phyDefense();
            magDamage = (int) (0.5 * creature.magAttack());
            magDamage -= other.magDefense();

            other.modifyHp(-phyDamage);
            other.modifyHp(-magDamage);

            int damage = phyDamage + magDamage;
            message.add(" > You cast Dual Smite!");
            message.add(" > You hit the " + other.name() + " for " + damage + " damage");
        }
        else
            message.add(" > Not enough mana!");
    }
}
