public class GreatPhalanx extends Spell{
    void cast(Creature player, Creature enemy) {
        this.uptime = 5 + 1;
        this.cooldown = 6 + 1;
        this.phyDefend = (int) (0.75*player.phyDefense());
        this.magDefend = (int) (0.75*player.magDefense());
        this.message = " > You cast Great Phalanx!";
    }
}
