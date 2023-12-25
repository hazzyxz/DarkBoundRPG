//ai for bat
public class BatAi extends CreatureAi {

    public BatAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        //call method 2x so move 2x every turn
        wander();
        wander();
    }
}
