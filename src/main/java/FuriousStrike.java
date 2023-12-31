public class FuriousStrike extends Spell{
    public void cast(Creature player, Creature enemy) {
        this.damage = (int) (0.75*player.phyAttack());
        this.damage -= enemy.phyDefense();
        this.message = " > You cast Furious Strike on " + enemy.name();
    }
}
