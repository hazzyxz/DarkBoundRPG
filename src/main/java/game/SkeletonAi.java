package game;

public class SkeletonAi extends CreatureAi{

    public SkeletonAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        wander();
    }


}
