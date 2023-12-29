import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class PlayScreen3 extends PlayScreen {
    private Screen subscreen;

    public PlayScreen3(){
        super();

    }

    @Override
    public Screen nextPlayScreen() {
        return new PlayScreen();
    }

    public void createNewWorld(){
        int width = 40;
        int height = 40;
        float initialChance = 0.45f; // higher = more wall in initial map
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

    @Override
    public void displayHotkey(AsciiPanel terminal) {
        super.displayHotkey(terminal);
        terminal.write("World 3 but "+ChooseClassScreen.worldCount,42,21);
    }
}
