package game;

public class GoblinAi extends CreatureAi {

    public GoblinAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        //call method 2x so move 2x every turn
        wander();
    }
}