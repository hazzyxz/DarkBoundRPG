public abstract class Spell {
    protected int damage = 0;
    public int damage() {
        return this.damage;
    }
    protected boolean defend;
    public boolean defend() {
        return this.defend;
    }
    protected String message = "";
    public String message() {
        return this.message;
    }

    void cast(Creature player, Creature enemy) {
        // @Override
    }
}
