package game;

public class FlowOfVoid extends Spell{
    public void cast(Creature creature, Creature other) {
        manaCost = 120;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 6 + 1;
            uptime = 4 + 1;
            other.setBlind();
            message.add(" > You cast Flow of Void!");
            message.add(" > The "+other.name()+"'s accuracy is reduced for 4 rounds");
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature creature, Creature other) {
        other.removeBlind();
    }
}
