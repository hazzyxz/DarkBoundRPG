package game;

public class RegalRoar extends Spell {
    public void cast(Creature creature, Creature other) {
        manaCost = 20;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            uptime = 2 + 1; // not include the round spell is cast
            cooldown = 2 + 1; // not include the round spell is cast
            phyDefend = (int) (0.25 * creature.phyDefense());
            creature.modifyPhyDefense(phyDefend);
            message.add(" > You cast Regal Roar!");
            message.add(" + You increase your Armour by " + phyDefend);
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature player, Creature enemy) {
        player.resetPhyDefense();
    }
}
