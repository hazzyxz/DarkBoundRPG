package game;

public class OrcAi extends CreatureAi {
    private boolean isEnraged = false;
    private int chance;

    public OrcAi(Creature creature) {
        super(creature);
    }

    public void onUpdate() {
        wander();
    }

    public void onTurn(Creature other) {
        attack(other);
        enrage();
    }


    @Override
    public void attack(Creature other) {
        chance = (int) (Math.random() * 7)+1;
        int amount;
        System.out.println(chance);

        switch (chance){
            case 1:
                amount = (int) (0.2 * creature.maxHp());
                BattleScreen.log(" > The " + creature.name() + " smash the ground with his axe! ");

                if (BattleScreen.isPlayerDefending) {
                    amount = 0;
                    BattleScreen.isPlayerDefending = false;

                    BattleScreen.log(" > You manage to evade the attack! ");
                }
                else
                    BattleScreen.log(" : The shockwave cause you to tumble! ");
                break;

            case 2:
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
                break;
        }

        other.modifyHp(-amount);

        BattleScreen.log(" > The " + creature.name() + " deals " + amount + " damage");
    }

    public void enrage(){
        if (chance == 2 && !isEnraged) {
            creature.modifyAttack(creature.phyAttack());
            creature.modifyPhyDefense(-(creature.phyDefense() / 2));
            BattleScreen.log(" : The " + creature.name() + " is Enraged!! ");
            BattleScreen.log(" : The " + creature.name() + " now deals damage with extra passion ");

            isEnraged = true;
        }
    }
}
