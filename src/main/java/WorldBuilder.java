import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;


public class WorldBuilder {

    //stores width, height of world
    private int width;
    private int height;

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
        return randomizeTiles().smooth(8);

    }

    //false = floor , true = wall
    public WorldBuilder initialiseMap(float chanceToStartAlive){
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                if(Math.random()  < chanceToStartAlive){
                    tiles[x][y] = Tile.WALL;
                }
                else
                    tiles[x][y] = Tile.FLOOR;
            }
        }
        return this;
    }

    public WorldBuilder doSimulationStep(int deathLimit, int birthLimit, Tile[][] oldMap) {
        Tile[][] newMap = new Tile[width][height];

        // Loop over each row and column of the map
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int nbs = 0; // Initialize neighbor count

                // Count alive neighbors
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int neighbourX = x + i;
                        int neighbourY = y + j;

                        // Skip the center point
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        // Check if neighbor is within bounds
                        if (neighbourX < 0 || neighbourX >= width || neighbourY < 0 || neighbourY >= height) {
                            continue;
                        }

                        // If neighbor is alive, increment count
                        if (oldMap[neighbourX][neighbourY] == Tile.WALL) {
                            nbs++;
                        }
                    }
                }

                // Apply simulation rules based on neighbor count
                if (oldMap[x][y] == Tile.WALL) { // Tile.WALL = alive
                    if (nbs < deathLimit) {
                        newMap[x][y] = Tile.FLOOR;
                    } else {
                        newMap[x][y] = Tile.WALL;
                    }
                } else { // Tile.FLOOR = dead
                    if (nbs > birthLimit) {
                        newMap[x][y] = Tile.WALL;
                    } else {
                        newMap[x][y] = Tile.FLOOR;
                    }
                }
            }
        }

        tiles = newMap;
        return this;
    }

    private int floodFill(int x, int y, Tile[][] tiles, boolean[][] visited) {
        // Base cases to check if the current cell is within bounds or already visited
        if (x < 0 || x >= width || y < 0 || y >= height || visited[x][y] || tiles[x][y] != Tile.FLOOR) {
            return 0;
        }

        visited[x][y] = true;

        // Recursively visit neighboring cells
        int size = 1;
        size += floodFill(x + 1, y, tiles, visited);
        size += floodFill(x - 1, y, tiles, visited);
        size += floodFill(x, y + 1, tiles, visited);
        size += floodFill(x, y - 1, tiles, visited);

        return size;
    }

    public WorldBuilder makeCaves2(float initialChance, int deathLimit, int birthLimit, int times, int desiredSize) {
        tiles = new Tile[width][height];
        initialiseMap(initialChance);//set map with random tiles

        for (int i = 0; i < times; i++) {
            doSimulationStep(deathLimit, birthLimit, tiles);//cellular automata method
        }

        convertToFloor(); //convert bot and top row to floor

        int x = (int) (Math.random() * height);
        // Perform flood fill method
        boolean[][] visited = new boolean[width][height];
        int filledRegionSize = floodFill(x, 0, tiles, visited);

        // Rebuild the world if the filled region is below the desired size
        if (filledRegionSize < desiredSize) {
            makeCaves2( initialChance, deathLimit, birthLimit, times, desiredSize);
        }

        addExitStairs(); //add exit stairs to next level

        return this;
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

    public void convertToFloor() {
        // Convert bottom row to floor tiles
        for (int x = 0; x < width; x++) {
            tiles[x][height - 1] = Tile.FLOOR;
        }

        // Convert upper row to floor tiles
        for (int x = 0; x < width; x++) {
            tiles[x][0] = Tile.FLOOR;
        }
    }

    public WorldBuilder addExitStairs(){
        for (int x = 0; x<width; x++){
            if(tiles[x][height-1] != Tile.WALL) {
                tiles[x][height-1] = Tile.STAIRS_DOWN;

            }
        }
        return this;
    }
}
