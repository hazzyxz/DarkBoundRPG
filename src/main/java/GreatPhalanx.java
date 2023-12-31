public class GreatPhalanx extends Spell{
    void cast(Creature player, Creature enemy) {
        this.cooldown = 6 + 1;
        this.phyDefend = (int) (0.25*player.phyDefense());
        this.magDamage = (int) (0.25*player.magDefense());
        this.message = " > You cast Great Phalanx";
    }
}
