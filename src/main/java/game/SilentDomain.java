package game;

public class SilentDomain extends Spell{
    public void cast(Creature creature, Creature other) {
        manaCost = 10;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 3 + 1;
            uptime = 2;
            other.setSilence();
            message.add(" > You cast Silent Domain!");
            message.add(" > The surroundings became dead quiet... ");
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature creature, Creature other) {
        other.removeSilence();
    }
}
