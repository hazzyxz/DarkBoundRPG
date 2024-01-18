package game;

public class CoatedArrows extends Spell {
    public void cast(Creature creature, Creature other) {
        manaCost = 70;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 4 + 1;
            uptime = 2;

            phyDamage = (int)(1.10*creature.phyAttack());
            phyDamage -= other.phyDefense();
            other.modifyHp(-phyDamage);
            other.setPoison();

            message.add(" > You cast Coated Arrows!");
            message.add(" > Your arrows poison the enemy!");
            message.add(" > You hit "+other.name()+" for "+phyDamage+" damage");
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature creature, Creature other) {
        other.removePoison();
    }
}
