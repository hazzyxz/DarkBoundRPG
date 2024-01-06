package game;

public class SkeletonAi extends CreatureAi{

    public SkeletonAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        wander();
    }

    public void onTurn(Creature other){
        attack(other);
    }

    public void attack(Creature other){
        int amount;
        int chance = (int) (Math.random() * 7)+1;

        switch (chance){
            case 1:
                amount = (int) (0.0025 * creature.phyAttack() * other.hp());
                BattleScreen.log(" > The " + creature.name() + " lunge towards you");

                if (BattleScreen.isPlayerDefending) {
                    amount = 0;
                    BattleScreen.isPlayerDefending = false;

                    BattleScreen.log(" > You manage to block the attack! ");
                }
                else
                    BattleScreen.log(" : The piercing attack manage to hit you! ");

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
                break;
        }

        other.modifyHp(-amount);

        BattleScreen.log(" > The " + creature.name() + " deals " + amount + " damage");
    }
}
