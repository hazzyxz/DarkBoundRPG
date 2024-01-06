package game;

public class Fireball extends Spell {
    public void cast(Creature creature, Creature other) {
        manaCost = 30;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 4 + 1;
            magDamage = (int) (1.05* creature.magAttack());
            magDamage -= other.magDefense();
            other.modifyHp(-magDamage);
            message.add(" > You cast Fireball on " + other.name());
            message.add(" > You hit the " + other.name() + " for " + magDamage() + " damage");
        }
        else
            message.add(" > Not enough mana!");
    }
}
