package game;

public class WitchAi extends CreatureAi{

    private int chance;
    private Spell spell;

    public WitchAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
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
        chance = (int) (Math.random() * 7)+1;
        // Check for silence effect, if true monster cannot cast spells
        if (creature.statusEffect(2)) {
            chance += 2;
        }

        int amount = 0;

        switch (chance){
            case 1:
                spell = new Fireball();

                if(spell.manaCost > creature.mp()){
                    attack(other);
                }

                spell.cast(creature, other);
                amount = Math.max(5, spell.magDamage);

                BattleScreen.log(" > " + creature.name() + " cast Fireball!" );
                BattleScreen.log(" : The Fireball burns you!");
                BattleScreen.log(" > The " + creature.name() + " deals " + amount + " damage");

                break;

            case 2:
                spell = new MagicShield();

                if(spell.manaCost > creature.mp()){
                    attack(other);
                }

                spell.cast(creature, other);
                amount = 0;

                BattleScreen.log(" > " + creature.name() + " mutter an incomprehensible mantra" );
                BattleScreen.log(" > " + creature.name() + " cast Magic Shield!" );
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
