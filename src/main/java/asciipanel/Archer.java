package asciipanel;

public class Archer extends Archetype {
    private final int classID = 5;
    public Archer() {
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
        stats[2] += 20;
        stats[3] += 5;
        stats[4] += 5;
        stats[5] += 5;
    }
}
