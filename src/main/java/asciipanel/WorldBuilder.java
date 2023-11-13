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
                .smooth(8);


    }

    /*

    //check every space in the world
    //If it is not a wall, and it does not have a region assigned
    // then that empty space, and all empty spaces it's connected to, will be given a new region number
    private WorldBuilder createRegions(){
        //3d array that keep track of regions in the world
        int depth;
        regions = new int[width][height][depth];

        //iterate over each cordinate x, y, z in the world
        for (int z = 0; z < depth; z++){
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    //check if the tile is not a wall and  if the region at that position is not already assigned
                    if (tiles[x][y][z] != Tile.WALL && regions[x][y][z] == 0){
                        //call the fill region methods and add a unique identifier
                        int size = fillRegion(nextRegion++, x, y, z);

                        //if the size of region below 25 then it will remove the region
                        if (size < 25)
                            removeRegion(nextRegion - 1, z);
                    }
                }
            }
        }
        return this;
    }

    //replace the region size of <25 with walls
    private void removeRegion(int region, int z){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if (regions[x][y][z] == region){
                    regions[x][y][z] = 0;
                    tiles[x][y][z] = Tile.WALL;
                }
            }
        }
    }

    //The fillRegion method does a flood-fill starting with an open tile
    //It, and any open tile it's connected to, gets assigned the same region number
    // This is repeated until there are no unassigned empty neighboring tiles
    //do not understand
    private int fillRegion(int region, int x, int y, int z) {
        int size = 1;
        ArrayList<Point> open = new ArrayList<Point>();
        open.add(new Point(x,y,z));
        regions[x][y][z] = region;

        while (!open.isEmpty()){
            Point p = open.remove(0);

            for (Point neighbor : p.neighbors8()){
                if (regions[neighbor.x][neighbor.y][neighbor.z] > 0
                        || tiles[neighbor.x][neighbor.y][neighbor.z] == Tile.WALL)
                    continue;

                size++;
                regions[neighbor.x][neighbor.y][neighbor.z] = region;
                open.add(neighbor);
            }
        }
        return size;
    }

    //To connect all the regions with stairs
    // we start at the top and connect them one layer at a time
    public WorldBuilder connectRegions(){
        for (int z = 0; z < depth-1; z++){
            connectRegionsDown(z);
        }
        return this;
    }

    //to establish connections between regions on different levels (z and z+1)
    private void connectRegionsDown(int z){
        List<String> connected = new ArrayList<String>();

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                String region = regions[x][y][z] + "," + regions[x][y][z+1];
                if (tiles[x][y][z] == Tile.FLOOR
                        && tiles[x][y][z+1] == Tile.FLOOR
                        && !connected.contains(region)){
                    connected.add(region);
                    connectRegionsDown(z, regions[x][y][z], regions[x][y][z+1]);
                }
            }
        }
    }

    //take tiles where its region are adjacent and place stairs that lead to them
    private void connectRegionsDown(int z, int r1, int r2){
        List<Point> candidates = findRegionOverlaps(z, r1, r2);

        int stairs = 0;
        do{
            Point p = candidates.remove(0);
            tiles[p.x][p.y][z] = Tile.STAIRS_DOWN;
            tiles[p.x][p.y][z+1] = Tile.STAIRS_UP;
            stairs++;
        }
        while (candidates.size() / stairs > 250);
    }

    //method to find where each location overlaps
    public List<Point> findRegionOverlaps(int z, int r1, int r2) {
        ArrayList<Point> candidates = new ArrayList<Point>();

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if (tiles[x][y][z] == Tile.FLOOR
                        && tiles[x][y][z+1] == Tile.FLOOR
                        && regions[x][y][z] == r1
                        && regions[x][y][z+1] == r2){
                    candidates.add(new Point(x,y,z));
                }
            }
        }

        Collections.shuffle(candidates);
        return candidates;
    }
    */

    //maybe add txtFileReader here to read map.txt
    //and add mapTxtFileBuilder here to build map.txt

}
