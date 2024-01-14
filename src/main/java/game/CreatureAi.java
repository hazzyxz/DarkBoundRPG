package game;

public class CreatureAi {
    protected Creature creature;
    private World world;
    //stores a reference to the Creature object

    public CreatureAi(Creature creature){
        //constructor for CreatureAi
        this.creature = creature;
        this.creature.setCreatureAi(this);
        //associates the AI (this) with the Creature by calling a method setCreatureAi()
    }

    public void onEnter(int x, int y, Tile tile)
    //method is intended to be overridden by subclasses to define behavior when a creature enters a new position defined by the parameters
    {
        if (tile.isGround()){
            creature.x = x;
            creature.y = y;
        } else {
            //creature.doAction("bump into a wall");
        }
    }
    //provides a framework for custom AI logic for different creatures in a game
    // Subclasses of CreatureAi can implement specific behavior for different types of creatures
    // by overriding the onEnter method to react to changes

    //empty onUpdate method
    public void onUpdate(){

    }

    //empty onNotify method
    //only used by playerAi
    public void onNotify(String message){

    }

    //method that decide if player can see or not
    public boolean canSee(int wx, int wy) {

        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
            return false;

        for (Point p : new Line(creature.x, creature.y, wx, wy)){
            if (creature.tile(p.x, p.y).isGround() || p.x == wx && p.y == wy)
                continue;

            return false;
        }

        return true;
    }

    //method for creature moving randomly
    public void wander(){
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;
        Creature other = creature.creature(creature.x + mx, creature.y + my);
        if (other != null && other.glyph() == creature.glyph())
            return;
        else
            creature.moveBy(mx, my);
    }

    public void onGainLevel(){

    }

    public void onTurn(Creature other){
        attack(other);
    }

    public void attack(Creature other){
        int amount;

        //take the largest between phyatt or magatt
        amount = Math.max(creature.magAttack(),creature.phyAttack());

        //if phy att then phy def
        if(amount == creature.phyAttack())
            amount -= other.phyDefense();
        else
            amount -= other.magDefense();

        amount = Math.max(5,amount);

        if (BattleScreen.isPlayerDefending) {
            amount = (int) (0.5 * amount);
            BattleScreen.isPlayerDefending = false;
        }

        other.modifyHp(-amount);

        BattleScreen.log(" > The " + creature.name() + " deals "  + amount + " damage");

    }


}
