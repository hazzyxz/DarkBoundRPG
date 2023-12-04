package player;

public class Warrior extends Archetype{
    private final int classID = 1;
    /*
    public Warrior() {
        super(new String[]{"Warrior","300","50","80","20","70","50"});
    }

     */

    //  This requires the ArchetypeLoader class to load
    //  all the data from archetypes.txt
    //  However, it's easier to hard code in the stats from
    //  individual classes from each archetype
    //  so the super() method can be used without long chains of method call
    //  Hence, this code is omitted but left as proof
    //  File I/O is better showcased in other parts of the code
    //  e.g. Login & Saving feature
    public Warrior(String[] stats) {
        super(stats);
    }


    //  Getters
    public int getClassID() {
        return this.classID;
    }


    //  Level up stat increase (tentative values)
    //  @Override
    public void increaseStatsOnLevelUp() {
        // 5 = no boost, 10 = secondary class boost, 15 = primary class boost
        stats[0] += 15;
        stats[1] += 5;
        stats[2] += 5;
        stats[3] += 5;
        stats[4] += 10;
        stats[5] += 10;
    }
}
