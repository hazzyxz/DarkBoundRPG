package game;

import asciiPanel.AsciiPanel;

public class PlayScreen5 extends PlayScreen{
    private Screen subscreen;

    public PlayScreen5(){
        super();
        player.doAction("enter Playscreen 5");
    }

    @Override
    public Screen nextPlayScreen() {
        return new PlayScreen5();
    }

    public void createNewWorld(){
        int width = 40;
        int height = 40;
        float initialChance = 0.1f; // higher = more wall in initial map
        int deathLimit = 3; //num of adjacent tile to decide if the tile is removed
        int birthLimit = 4;  //num of adjacent tile to decide if the tile is added
        int repeat = 10; // lower = more jagged
        int desiredSize = 750; //desired size of the largest region

        world = new WorldBuilder(width, height)
                //generate game world with specific dimensions
                //class that provide generating and customizing world
                .makeCaves2(initialChance,deathLimit,birthLimit,repeat,desiredSize)
                .build();
        ChooseClassScreen.worldCount++;
    }

    protected void createEnemies(StuffFactory creatureFactory){
        //1 boss
        for (int i = 0; i < 1; i++){
            creatureFactory.newBoss();
        }
    }

    @Override
    public void displayHotkey(AsciiPanel terminal) {
        super.displayHotkey(terminal);
        terminal.write("World 5 but "+ChooseClassScreen.worldCount,42,21);
    }
}
