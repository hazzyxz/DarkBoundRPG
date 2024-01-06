package game;

class GoblinAi extends CreatureAi {

    public GoblinAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        //call method 2x so move 2x every turn
        wander();
    }

    public void onTurn(Creature other){
        super.onTurn(other);
    }
}

