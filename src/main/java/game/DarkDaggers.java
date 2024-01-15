package game;

public class DarkDaggers extends Spell {
    public void cast(Creature creature, Creature other) {
        manaCost = 120;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 6 + 1;

            int hitCount = 0, hitChance, damage;
            for (int i = 1; i <= 5; i++) {
                hitChance = 100 - (i-1)*10;

                if ((Math.random()*100)+1 <= hitChance) {
                    damage = (int) (0.05*creature.phyAttack());
                    damage -= other.phyDefense();
                    phyDamage += damage;
                    hitCount++;
                }
            }

            other.modifyHp(-phyDamage);
            uptime = hitCount * 2;
            other.setPoison();

            message.add(" > You cast Dark Daggers!");
            message.add(" > "+hitCount+" of 5 daggers hit!");
        }
        else
            message.add(" > Not enough mana!");
    }

    public void removeEffect(Creature creature, Creature other) {
        other.removePoison();
    }
}
