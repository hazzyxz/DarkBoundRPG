package game;

import asciiPanel.AsciiPanel;

import java.awt.*;

public class PlayScreen5 extends PlayScreen{
    private Screen subscreen;
    private final int worldID = 5;
    public PlayScreen5(){
        super();
        player.doAction("enter Station KLCC");
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
    }


    public void displayLegends(AsciiPanel terminal) {
        int x = 67;
        int y = 5;

        terminal.write("Legends",x,y++);
        terminal.write("-------",x,y++);
        terminal.write(" ",x,y++);

        terminal.write(player.glyph() + " --" + " Player",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("B --" + " ?",x,y++);

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

        terminal.write("LRT Kampung Baru",x-17,34, AsciiPanel.brightWhite);
        for(int i =0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                terminal.write("-", x + j, 33+i, AsciiPanel.brightWhite);
            }
        }

        terminal.write("LRT KLCC",x-17,38,AsciiPanel.red);
        for(int i =0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                terminal.write("-", x + j, 37+i, AsciiPanel.red);
            }
        }
    }

    protected void saveGame () {
        save.saveToFileSave(player);
        save.saveToWorldSave(worldID);
    }
}
