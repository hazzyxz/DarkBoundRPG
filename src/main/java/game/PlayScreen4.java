package game;

import asciiPanel.AsciiPanel;

public class PlayScreen4 extends PlayScreen{
    private Screen subscreen;

    public PlayScreen4(){
        super();
        player.doAction("enter Playscreen 4");
    }

    @Override
    public Screen nextPlayScreen() {
        return new PlayScreen5();
    }

    public void createNewWorld(){
        int width = 40;
        int height = 40;
        float initialChance = 0.47f; // higher = more wall in initial map
        int deathLimit = 3; //num of adjacent tile to decide if the tile is removed
        int birthLimit = 4;  //num of adjacent tile to decide if the tile is added
        int repeat = 3; // lower = more jagged
        int desiredSize = 750; //desired size of the largest region

        world = new WorldBuilder(width, height)
                //generate game world with specific dimensions
                //class that provide generating and customizing world
                .makeCaves2(initialChance,deathLimit,birthLimit,repeat,desiredSize)
                .addExitStairs()
                .build();
        ChooseClassScreen.worldCount++;
    }

    protected void createEnemies(StuffFactory creatureFactory){
        //30 creature
        for (int i = 0; i < 6; i++){
            creatureFactory.newGoblin2();
        }
        for (int i = 0; i < 6; i++){
            creatureFactory.newSkeleton2();
        }
        for (int i = 0; i < 6; i++){
            creatureFactory.newWitch2();
        }
        for (int i = 0; i < 6; i++){
            creatureFactory.newOrc2();
        }
        for (int i = 0; i < 6; i++){
            creatureFactory.newHarpy2();
        }
    }

    @Override
    protected int visionRadius() {
        return 5;
    }

    @Override
    public void displayHotkey(AsciiPanel terminal) {
        super.displayHotkey(terminal);
        terminal.write("World 4 but "+ChooseClassScreen.worldCount,42,21);
    }
}
