package asciipanel;

public class NpcAi extends CreatureAi {

    private StuffFactory factory;
    private int spreadcount;

    // constructor for the FungusAi class
    public NpcAi(Creature creature, StuffFactory factory) {

        //calls the constructor of the superclass (CreatureAi) and passes the creature parameter to it
        super(creature);

        //initialize factory field with the provided CreatureFactory
        this.factory = factory;
    }

    //update method for fungus Ai
    public void onUpdate(){
        //check the condition for spreading

    }

    //spread method for fungus
    private void spread(){
        //generate new cord around the current fungus

    }

}