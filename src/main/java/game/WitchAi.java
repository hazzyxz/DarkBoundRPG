package game;

public class WitchAi extends CreatureAi{

    public WitchAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        wander();
    }

}
