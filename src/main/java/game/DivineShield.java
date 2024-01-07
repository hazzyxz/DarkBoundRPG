package game;

public class DivineShield extends Spell{
    public void cast(Creature creature, Creature other) {
        manaCost = 20;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            uptime = 2 + 1; // not include the round spell is cast
            cooldown = 2 + 1; // not include the round spell is cast
            phyDefend = (int) (0.15 * creature.phyDefense());
            magDefend = (int) (0.15 * creature.magDefense());
            creature.modifyPhyDefense(phyDefend);
            creature.modifyMagDefense(magDefend);
            message.add(" > You cast Divine Shield!");
            message.add(" + You increase your Armour by " + phyDefend);
            message.add(" + You increase your Barrier by " + magDefend);
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature player, Creature enemy) {
        player.resetPhyDefense();
        player.resetMagDefense();
    }
}
