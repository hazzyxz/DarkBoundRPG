package asciipanel;

public class FungusAi extends CreatureAi {

    private CreatureFactory factory;
    private int spreadcount;

    // constructor for the FungusAi class
    public FungusAi(Creature creature, CreatureFactory factory) {

        //calls the constructor of the superclass (CreatureAi) and passes the creature parameter to it
        super(creature);

        //initialize factory field with the provided CreatureFactory
        this.factory = factory;
    }

    //update method for fungus Ai
    public void onUpdate(){
        //check the condition for spreading
        if (spreadcount < 5 && Math.random() < 0.01)
            spread();
    }

    //spread method for fungus
    private void spread(){
        //generate new cord around the current fungus
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;

        //check if the cord can be entered or not
        if (!creature.canEnter(x, y))
            return;

        //if not then create a new fungus
        Creature child = factory.newFungus();
        //set its cord from the new cord
        child.x = x;
        child.y = y;
        spreadcount++;
        creature.doAction("spawn a child");
    }

}