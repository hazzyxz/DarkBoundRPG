package game;

public class GreatPhalanx extends Spell {
    void cast(Creature creature, Creature other) {
        manaCost = 50;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            uptime = 5;
            cooldown = 6 + 1;
            phyDefend = (int) (0.75 * creature.phyDefense());
            magDefend = (int) (0.75 * creature.magDefense());
            message.add(" > You cast Great Phalanx!");
            message.add(" + You increase your Armour by " + phyDefend);
            message.add(" + You increase your Barrier by " + magDefend);
        }
        else
            message.add(" > Not enough mana!");
    }
}
