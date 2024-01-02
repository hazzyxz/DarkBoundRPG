package game;

public class RegalRoar extends Spell{
    public void cast(Creature player, Creature enemy) {
        this.uptime = 2 + 1;
        this.cooldown = 2 + 1;
        this.phyDefend = (int) (0.25*player.phyDefense());
        this.message = " > You cast Regal Roar!";
    }
}
