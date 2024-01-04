package game;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class PlayScreen2 extends PlayScreen {
    private Screen subscreen;

    public PlayScreen2(){
        super();
        player.doAction("enter Playscreen 2");
    }

    @Override
    public Screen nextPlayScreen() {
        return new PlayScreen3();
    }

    protected void createNewWorld(){
        int width = 40;
        int height = 40;
        float initialChance = 0.45f; // higher = more wall in initial map
        int deathLimit = 3; //num of adjacent tile to decide if the tile is removed
        int birthLimit = 4;  //num of adjacent tile to decide if the tile is added
        int repeat = 5; // lower = more jagged
        int desiredSize = 750; //desired size of the largest region

        world = new WorldBuilder(width, height)
                //generate game world with specific dimensions
                //class that provide generating and customizing world
                .makeCaves2(initialChance,deathLimit,birthLimit,repeat,desiredSize)
                .build();
        ChooseClassScreen.worldCount++;
    }

    protected void createEnemies(StuffFactory creatureFactory){
        //20 creature
        for (int i = 0; i < 7; i++){
            creatureFactory.newWitch();
        }
        for (int i = 0; i < 9; i++){
            creatureFactory.newOrc();
        }
        for (int i = 0; i < 9; i++){
            creatureFactory.newHarpy();
        }
    }

    @Override
    public void displayHotkey(AsciiPanel terminal) {
        super.displayHotkey(terminal);
        terminal.write("World 2 but "+ChooseClassScreen.worldCount,42,21);
    }
}
