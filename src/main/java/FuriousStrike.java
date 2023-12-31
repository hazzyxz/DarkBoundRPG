public class FuriousStrike extends Spell{
    public void cast(Creature player, Creature enemy) {
        this.cooldown = 4 + 1;
        this.phyDamage = (int) (0.75*(player.phyAttack()+player.maxPhyDefense()/2));
        this.phyDamage -= enemy.phyDefense();
        this.message = " > You cast Furious Strike on " + enemy.name();
    }
}
