package asciipanel;

public class Mage extends Archetype{
    private final int classID = 2;
    public Mage(String[] stats) {
        super(stats);
    }

    public int getClassID() {
        return this.classID;
    }

    //  Level up stat increase (tentative values)
    //  @Override
    public void increaseStatsOnLevelUp() {
        // 5 = no boost, 10 = secondary class boost, 15 = primary class boost
        stats[0] += 5;
        stats[1] += 10; // manaPoints
        stats[2] += 5;
        stats[3] += 15; // magicalAttack
        stats[4] += 5;
        stats[5] += 5;
    }
}
