package game;

public class Backstab extends Spell{
    public void cast(Creature creature, Creature other) {
        manaCost = 70;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 4 + 1;
            uptime = 2 + 1;
            other.setStun();
            phyDamage = (int) (1.00 * creature.phyAttack());
            phyDamage -= other.phyDefense();
            other.modifyHp(-phyDamage);
            message.add(" > You cast Backstab!");
            message.add(" > The "+other.name()+" fell onto the ground");
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature creature, Creature other) {
        other.removeStun();
    }
}
