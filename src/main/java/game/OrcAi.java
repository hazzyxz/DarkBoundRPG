package game;

public class OrcAi extends CreatureAi {

    public OrcAi(Creature creature) {
        super(creature);
    }

    public void onUpdate() {
        wander();
    }


}
