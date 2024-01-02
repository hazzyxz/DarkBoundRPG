package game;

public class HarpyAi extends CreatureAi{

    public HarpyAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        wander();
        wander();
    }

}
