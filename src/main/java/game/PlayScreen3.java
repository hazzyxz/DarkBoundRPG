package game;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayScreen3 extends PlayScreen {
    private Screen subscreen;
    private final int worldID = 3;
    public PlayScreen3(){
        super();
        player.doAction("enter Station Pasar Seni");
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
                .addExitStairs()
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
    }

    public void displayLegends(AsciiPanel terminal) {
        int x = 67;
        int y = 5;

        terminal.write("Legends",x,y++);
        terminal.write("-------",x,y++);
        terminal.write(" ",x,y++);

        terminal.write(player.glyph() + " --" + " Player",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("N --" + " Penasihat",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("G --" + " Hobgoblin",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("0 --" + " Ogre",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("W --" + " Coven Witch",x,y++);

    }

    @Override
    public void displayWorlds(AsciiPanel terminal) {
        int x = 59;

        for(int i =0; i<19; i++) {
            terminal.write("|", x+2, 21 + i,Color.DARK_GRAY);
        }

        terminal.write("LRT Universiti",x-17,22,AsciiPanel.brightWhite);
        for(int i =0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                terminal.write("-", x + j, 21+i, AsciiPanel.brightWhite);
            }
        }

        terminal.write("LRT KL Sentral",x-17,26, AsciiPanel.brightWhite);
        for(int i =0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                terminal.write("-", x + j, 25+i, AsciiPanel.brightWhite);
            }
        }

        terminal.write("LRT Pasar Seni",x-17,30, AsciiPanel.brightWhite);
        for(int i =0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                terminal.write("-", x + j, 29+i, AsciiPanel.brightWhite);
            }
        }

        terminal.write("LRT Kampung Baru",x-17,34,Color.DARK_GRAY);
        for(int i =0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                terminal.write("-", x + j, 33+i, Color.DARK_GRAY);
            }
        }

        terminal.write("LRT KLCC",x-17,38,Color.DARK_GRAY);
        for(int i =0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                terminal.write("-", x + j, 37+i, Color.DARK_GRAY);
            }
        }
    }

    protected void saveGame () {
        save.saveToFileSave(player);
        save.saveToWorldSave(worldID);
    }
}
