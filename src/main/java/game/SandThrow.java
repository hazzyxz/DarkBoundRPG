package game;

public class SandThrow extends Spell {
    public void cast(Creature creature, Creature other) {
        manaCost = 20;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 2 + 1;
            uptime = 1 + 1;
            other.setBlind();
            message.add(" > You cast Sand Throw!");
            message.add(" > The "+other.name()+"'s accuracy is reduced for 1 round");
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature creature, Creature other) {
        other.removeBlind();
    }
}
