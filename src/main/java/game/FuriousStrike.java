package game;

public class FuriousStrike extends Spell {
    public void cast(Creature creature, Creature other) {
        manaCost = 25;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 4 + 1;
            phyDamage = (int) (0.40 * creature.phyAttack() + 0.40 * creature.phyDefense() + 0.40 * creature.magDefense());
            phyDamage -= other.phyDefense();
            other.modifyHp(-phyDamage);
            System.out.println(phyDamage);
            message.add(" > You cast Furious Strike on " + other.name());
            message.add(" > You hit the " + other.name() + " for " + phyDamage() + " damage");
        }
        else
            message.add(" > Not enough mana!");
    }
}
