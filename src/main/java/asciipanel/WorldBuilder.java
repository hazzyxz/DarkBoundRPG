
package asciipanel;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class WorldBuilder {

    //stores width, height and depth of world
    private int width;
    private int height;
    private int depth;

    //represents the tiles of [x][y] cord
    //holds reference to Tile objects
    private Tile[][] tiles;

    //private int[][][] regions;

    public WorldBuilder(int width, int height) {
        //constructor for WorldBuilder class

        this.width = width;
        this.height = height;
        //takes the desired width and height of the world as parameters

        this.tiles = new Tile[width][height];
        // 2D array of Tile objects with the specified dimensions
    }

    public World build() {
        return new World(tiles);
        // build and return a World object based on the parameters
        // and configuration set in the WorldBuilder.
        // does this by creating a new World object and
        // passing the tiles array to its constructor
    }

    private WorldBuilder randomizeTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
                //randomizes the tiles in the game world
                // < 0.6 = more floor
            }
        }
        return this;
    }

    //smoothing method for procedural world generation
    private WorldBuilder smooth(int times) {
        Tile[][] tiles2 = new Tile[width][height];
        for (int time = 0; time < times; time++) {
            //counts the number of adjacent Tile.FLOOR and Tile.WALL tiles
            //determines whether the tile should become Tile.FLOOR
            //or Tile.WALL in the next iteration

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int floors = 0;
                    int rocks = 0;

                    for (int ox = -1; ox < 2; ox++) {
                        for (int oy = -1; oy < 2; oy++) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0
                                    || y + oy >= height)
                                continue;

                            if (tiles[x + ox][y + oy] == Tile.FLOOR)
                                floors++;
                            else
                                rocks++;
                        }
                    }
                    tiles2[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                }
            }
            tiles = tiles2;
        }
        return this;
        //procedural terrain generation using randomize and smoothing
        //adjusting parameters and smoothing create a diff type terrains
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles()
                .smooth(8)
                //exit stairs for winning conditions(temp)
                .addExitStairs();

    }
    /*
    private WorldBuilder addExitStairs() {
        int x = -1;
        int y = -1;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (tiles[x][y] != Tile.FLOOR);

        tiles[x][y] = Tile.STAIRS_UP;
        return this;
    }

     */

    public WorldBuilder addExitStairs(){
        for (int y = 0; y<height; y++){
            if(tiles[width-1][y] != Tile.WALL) {
                tiles[width-1][y] = Tile.STAIRS_UP;

            }
        }
        return this;
    }
}
