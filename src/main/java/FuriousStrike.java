public class FuriousStrike extends Spell{
    public void cast(Creature player, Creature enemy) {
        this.cooldown = 4 + 1;
        this.phyDamage = (int) (0.5*player.phyAttack()+0.25*player.phyDefense()+0.25*player.magDefense());
        this.phyDamage -= enemy.phyDefense();
        this.message = " > You cast Furious Strike on " + enemy.name();
    }
}
