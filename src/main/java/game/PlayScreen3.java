package game;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class PlayScreen3 extends PlayScreen {
    private Screen subscreen;

    public PlayScreen3(){
        super();
        player.doAction("enter Playscreen 3");
    }

    @Override
    public Screen nextPlayScreen() {
        return new PlayScreen4();
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
                .build();
        ChooseClassScreen.worldCount++;
    }

    //createEnemies
    protected void createEnemies(StuffFactory creatureFactory){
        //20 creature
        for (int i = 0; i < 7; i++){
            creatureFactory.newGoblin2();
        }
        for (int i = 0; i < 9; i++){
            creatureFactory.newOrc2();
        }
        for (int i = 0; i < 9; i++){
            creatureFactory.newWitch2();
        }
    }

    @Override
    public void displayHotkey(AsciiPanel terminal) {
        super.displayHotkey(terminal);
        terminal.write("World 3 but "+ChooseClassScreen.worldCount,42,21);
    }
}
