package asciipanel;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class World {
    private Tile[][] tiles;
    //This instance variable represents a two-dimensional array of Tile objects
    // The first dimension represents the horizontal position (x-coordinate), and
    // the second dimension represents the vertical position (y-coordinate) of each tile

    // list for creature
    private List<Creature> creatures;

    //getter for array list creatures
    public List<Creature> getCreatures() {
        return creatures;
    }

    private int width;
    //stores the width by representing how many columns there are horizontally

    public int width() { return width; }
    //getter method to allow access to width method

    private int height;
    //stores the height by representing how many rows there are vertically

    public int height() { return height; }
    //getter method to allow access to height method

    public World(Tile[][] tiles){

        //constructor of the World class
        //takes a two-dimensional array of Tile objects as a parameter
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        //tiles[0] set 1st array as nil and calculate 2nd array
        //calculates the width and length based on tiles[width][height]

        // initialize creature list as an ArrayList
        this.creatures = new ArrayList<Creature>();

    }
    //define and stores the tile in game world

    // way to get creature at a specific location
    public Creature creature(int x, int y){
        for (Creature c : creatures){
            if (c.x == x && c.y == y)
                return c;
        }
        return null;
    }

    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height) {
            //checks if the provided coordinates are outside the boundaries of the world

            return Tile.BOUNDS;
            //out of bounds
        }
        else{
            return tiles[x][y];
            //return tile located at (x, y)
        }
    }

    public char glyph(int x, int y){
        //takes the same x and y coordinates

        return tile(x, y).glyph();
        // uses the tile(int x, int y) method to get the tile at those coordinates and
        // then retrieves the character glyph associated with that tile using the glyph() method of the Tile class
    }

    public Color color(int x, int y){
        return tile(x, y).color();
        //same as glyph() method
        //retrieve the color of those coordinates
    }
    //both method to retrieve tile info

    public void dig(int x, int y) {
        //check if tile(x,y) is diggable() = true, then tile(x,y) = floor
        if (tile(x,y).isDiggable())
            tiles[x][y] = Tile.FLOOR;
    }

    public void addAtEmptyLocation(Creature creature){
        int x;
        int y;
        // initializes x and y variables to store the coordinates of the location where the Creature will be added

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (!tile(x,y).isGround() || creature(x,y) != null);
        // enters a do-while loop, which will keep generating random coordinates (x,y)
        // until it finds an empty location in the game world and not fill with creature

        creature.x = x;
        creature.y = y;
        // assigns the x and y coordinates to the creature object.
        creatures.add(creature);
    }

    //method that instant remove the killed creature
    public void remove(Creature other) {
        //remove creature from list of creatures
        creatures.remove(other);
    }

    //let creature knows to update itself and start their turn
    public void update(){
        //'creature' represents every Creature in the creatures list
        //iterate through every Creature in array list
        List<Creature> toUpdate = new ArrayList<Creature>(creatures);
        for (Creature creature : toUpdate){
            creature.update();
        }
    }


}
