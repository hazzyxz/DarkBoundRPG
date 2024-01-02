package game;

public class Paladin extends Archetype {
    private final int classID = 4;
    public Paladin() {
        super();
    }

    public int getClassID(int ID) {
        return this.classID;
    }


    //  Level up stat increase (tentative values)
    //  @Override
    public void increaseStatsOnLevelUp() {
        // 5 = no boost, 10 = secondary class boost, 15 = primary class boost
        stats[0] += 5;
        stats[1] += 5;
        stats[2] += 10;
        stats[3] += 10;
        stats[4] += 5;
        stats[5] += 5;
    }
}
