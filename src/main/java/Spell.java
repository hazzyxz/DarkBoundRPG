public abstract class Spell {
    protected int cooldown = 0;
    public int cooldown() {
        return this.cooldown;
    }
    protected int uptime;
    public int uptime() {
        return this.uptime;
    }
    protected int phyDamage = 0;
    public int phyDamage() {
        return this.phyDamage;
    }
    protected int magDamage = 0;
    public int magDamage() {
        return this.magDamage;
    }
    protected int phyDefend;
    public int phyDefend() {
        return this.phyDefend;
    }
    protected int magDefend;
    public int magDefend() {
        return this.magDefend;
    }
    protected String message = "";
    public String message() {
        return this.message;
    }

    void cast(Creature player, Creature enemy) {
        // @Override
    }
}
