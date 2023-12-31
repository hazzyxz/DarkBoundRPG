public class RegalRoar extends Spell{
    public void cast(Creature player, Creature enemy) {
        this.defend = true;
        this.message = " > You cast Regal Roar!";
    }
}
