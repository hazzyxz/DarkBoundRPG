package game;

public class MagicShield extends Spell {
    public void cast(Creature creature, Creature other) {
        manaCost = 20;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            uptime = 2 + 1; // not include the round spell is cast
            cooldown = 2 + 1; // not include the round spell is cast
            magDefend = (int) (0.25 * creature.magDefense());
            creature.modifyMagDefense(magDefend);
            message.add(" > You cast Magic Shield");
            message.add(" + You increase your Barrier by " + magDefend);
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature player, Creature enemy) {
        player.resetMagDefense();
    }
}
