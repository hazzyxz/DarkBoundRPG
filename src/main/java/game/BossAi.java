package game;

public class BossAi extends CreatureAi {

    private Spell spell;
    private int chance;

    private boolean ultiIsUsed = false;

    public BossAi(Creature creature) {
        super(creature);
    }

    public void onUpdate() {

    }

    public void onTurn(Creature other) {
        attack(other);

        // Check for silence effect, if true monster cannot be enraged
        if (!creature.statusEffect(2)) {
            //enrage();
        }
    }


    @Override
    public void attack(Creature other) {
        chance = (int) (Math.random() * 10) + 1;
        // Check for silence effect, if true monster cannot cast spells
        if (creature.statusEffect(2)) {
            chance += 2;
        }

        int amount = 0;

        switch (chance) {
            case 1:

                amount = creature.phyAttack();
                amount -= (int) (0.5 * other.phyDefense());

                creature.modifyMp(-120);

                BattleScreen.log(" > The " + creature.name() + " breath out Fire");

                if (BattleScreen.isPlayerDefending) {
                    amount = 0;
                    BattleScreen.isPlayerDefending = false;

                    BattleScreen.log(" > You manage to evade the attack! ");
                }
                else
                    BattleScreen.log(" : The Dragon's Breath cause you to burn!");

                other.modifyHp(-amount);
                break;

            case 2:

                BattleScreen.log(" > The " + creature.name() + " inflict an Ancient curse upon you!");
                BattleScreen.log(" : You feel your body weaken!");

                other.modifyMp(-70);
                if(other.mp() < 0)
                    other.setMp(0);

                break;

            case 3:
                spell = new ArtemisArrow();

                if (ultiIsUsed) {
                    attack(other);
                }

                spell.cast(creature, other);
                amount = 0;
                ultiIsUsed = true;

                BattleScreen.log(" > " + creature.name() + " scream out from its lung!");
                BattleScreen.log(" > " + creature.name() + " cast Regal Roar!" );
                BattleScreen.log(" : A bright light seem to surround the " + creature.name() + "!");

                other.modifyHp(-amount);
                break;

            default:
                //take the largest between phyatt or magatt
                amount = Math.max(creature.magAttack(), creature.phyAttack());

                //if phy att then phy def
                if (amount == creature.phyAttack())
                    amount -= other.phyDefense();
                else
                    amount -= other.magDefense();

                amount = Math.max(5, amount);

                if (BattleScreen.isPlayerDefending) {
                    amount = (int) (0.85 * amount);
                    BattleScreen.isPlayerDefending = false;
                }

                other.modifyHp(-amount);
                BattleScreen.log(" > The " + creature.name() + " deals " + amount + " damage");

                break;
        }
    }
}
