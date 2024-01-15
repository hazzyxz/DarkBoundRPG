package game;

import asciiPanel.AsciiPanel;

import java.awt.*;

public class PlayScreen4 extends PlayScreen{
    private Screen subscreen;
    private final int worldID = 4;
    public PlayScreen4(){
        super();
        player.doAction("enter Station Kampung Baru");
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

    }

    public void displayLegends(AsciiPanel terminal) {
        int x = 67;
        int y = 5;

        terminal.write("Legends",x,y++);
        terminal.write("-------",x,y++);
        terminal.write(" ",x,y++);

        terminal.write(player.glyph() + " --" + " Player",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("G --" + " Hobgoblin",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("0 --" + " Ogre",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("W --" + " Coven Witch",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("S --" + " Skeleton Warrior",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("H --" + " Stygian Harpy",x,y++);

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
