package game;

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
    // list for items
    private Item[][] items;

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
        //initialize item list
        this.items = new Item[width][height];

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
        Creature creature = creature(x, y);
        if (creature != null)
            return creature.glyph();
        if (item(x,y) != null)
            return item(x,y).glyph();
        return tile(x, y).glyph();
        // uses the tile(int x, int y) method to get the tile at those coordinates and
        // then retrieves the character glyph associated with that tile using the glyph() method of the Tile class
    }

    public Color color(int x, int y){
        Creature creature = creature(x, y);
        if (creature != null)
            return creature.color();

        if (item(x,y) != null)
            return item(x,y).color();

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

    //add creature in an empty tile
    public void addAtEmptyLocation(Creature creature){
        int x;
        int y;
        // initializes x and y variables to store the coordinates of the location where the Creature will be added

        //add player at empty location in left screen side
        if(creature.isPlayer()){
            y = 0; // Fixed y coordinate at the first column

            do {
                x = (int) (Math.random() * height);
                // Randomly generate x coordinate within the height of the screen
            } while (!tile(x, y).isGround() || creature(x, y) != null);
            // Keep generating new x coordinates until an empty location is found

            creature.x = x;
            creature.y = y;
            creatures.add(creature);
        }
        else if(creature.glyph()=='N'){
            y = 0; // Fixed y coordinate at the first column

            do {
                x = (int) (Math.random() * height);
                // Randomly generate x coordinate within the height of the screen
            } while (!tile(x, y).isGround() || creature(x, y) != null);
            // Keep generating new x coordinates until an empty location is found

            creature.x = x;
            creature.y = y;
            creatures.add(creature);
        }
        //add any creature in any
        else {
            do {
                x = (int) (Math.random() * width);
                y = (int) (Math.random() * height);
            }
            while (!tile(x, y).isGround() || creature(x, y) != null);
            // enters a do-while loop, which will keep generating random coordinates (x,y)
            // until it finds an empty location in the game world and not fill with creature

            creature.x = x;
            creature.y = y;
            // assigns the x and y coordinates to the creature object.
            creatures.add(creature);
        }
    }

    //add item in an empty tile
    public void addAtEmptyLocation(Item item) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (!tile(x,y).isGround() || item(x,y) != null);

        items[x][y] = item;
    }

    // way to determine what item is in a location
    public Item item(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return items[x][y];
    }

    //method that remove the killed creature
    public void remove(Creature other) {
        //remove creature from list of creatures
        creatures.remove(other);
    }

    //method that remove picked item
    public void remove(int x, int y) {
        items[x][y] = null;
    }

    //method will check adjacent tiles for an open space
    // and repeat until it find one or run out of open spaces
    public void addAtEmptySpace(Item item, int x, int y){
        if (item == null)
            return;

        List<Point> points = new ArrayList<Point>();
        List<Point> checked = new ArrayList<Point>();

        points.add(new Point(x, y));

        while (!points.isEmpty()){
            Point p = points.remove(0);
            checked.add(p);

            if (!tile(p.x, p.y).isGround())
                continue;

            if (items[p.x][p.y] == null){
                items[p.x][p.y] = item;
                Creature c = this.creature(p.x, p.y);
                if (c != null)
                    c.notify("A %s lands between your feet.", item.name());
                return;
            } else {
                List<Point> neighbors = p.neighbors8();
                neighbors.removeAll(checked);
                points.addAll(neighbors);
            }
        }
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
