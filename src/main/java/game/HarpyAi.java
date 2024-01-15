package game;

public class HarpyAi extends CreatureAi{

    private Spell spell;
    private int chance;

    public HarpyAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        wander();
        wander();
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
        chance = (int) (Math.random() * 7) + 1;
        // Check for silence effect, if true monster cannot cast spells
        if (creature.statusEffect(2)) {
            chance += 2;
        }

        int amount = 0;

        switch (chance) {
            case 1:

                amount = creature.phyAttack();
                BattleScreen.log(" > The " + creature.name() + " does a Swooping Slash! ");

                if (BattleScreen.isPlayerDefending) {
                    amount = 0;
                    BattleScreen.isPlayerDefending = false;

                    BattleScreen.log(" > You manage to evade the attack! ");
                }
                else
                    BattleScreen.log(" : The Slash cause you several lacerations!");

                other.modifyHp(-amount);
                break;

            case 2:
                spell = new RegalRoar();

                if (spell.manaCost > creature.mp()) {
                    attack(other);
                }

                spell.cast(creature, other);
                amount = 0;

                BattleScreen.log(" > " + creature.name() + " scream out from its lung!");
                BattleScreen.log(" > " + creature.name() + " cast Regal Roar!" );
                BattleScreen.log(" : A bright light seem to surround the " + creature.name() + "!");

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
