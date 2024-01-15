package game;

public class AncientBlessing extends Spell{
    public void cast(Creature creature, Creature other) {
        manaCost = 120;
        if (manaCost() <= creature.mp()) {
            creature.modifyMp(-manaCost());
            cooldown = 6 + 1;

            int healUses = 0;
            if (creature.hpFlask()==0)
                healUses = 2;
            else if (creature.hpFlask()==1)
                healUses = 1;

            creature.modifyHpFlask(healUses);

            int heal = (int)(0.25*creature.maxHp());
            if (creature.hp() + heal > creature.maxHp())
                heal = creature.maxHp() - creature.hp();
            creature.modifyHp(heal);

            message.add(" > You cast Ancient Blessing!");
            message.add(" + You recovered "+ healUses +" healing uses");
            message.add(" + You increase your health for " + heal + " Hp");
        }
    }
}
