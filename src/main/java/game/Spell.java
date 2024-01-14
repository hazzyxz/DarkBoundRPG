package game;

import java.util.ArrayList;

public abstract class Spell {
    protected int manaCost = 0;
    public int manaCost() {
        return this.manaCost;
    }
    protected int cooldown = 0;
    public int cooldown() {
        return this.cooldown;
    }
    protected int uptime = -1;
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
    protected int phyDefend = 0;
    public int phyDefend() {
        return this.phyDefend;
    }
    protected int magDefend = 0;
    public int magDefend() {
        return this.magDefend;
    }
    protected ArrayList<String> message = new ArrayList<>();
    public ArrayList<String> message() {
        return this.message;
    }

    void cast(Creature player, Creature enemy) {
        // @Override
    }

    void removeEffect(Creature player, Creature enemy) {
        // @Override
    }

    int getManaCost(Spell spell) {
        return this.manaCost;
    }
    int getCooldown(Spell spell) {
        return this.cooldown;
    }
}
