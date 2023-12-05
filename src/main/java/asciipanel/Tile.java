package asciipanel;

import asciiLib.AsciiPanel;
import java.awt.Color;

public enum Tile {
    //Represents a floor tile with a yellow character (ASCII 250) and yellow color.
    FLOOR((char)250, AsciiPanel.yellow),

    //Represents a wall tile with a yellow character (ASCII 177) and yellow color.
    WALL((char)177, AsciiPanel.yellow),

    //Represents a bounds tile with an 'x' character and bright black color
    BOUNDS('X', AsciiPanel.brightBlack),

    //represent a tile that not yet been seen
    UNKNOWN(' ', AsciiPanel.white),

    //represent a stair tile with '>' and '<' character and white color
    STAIRS_DOWN('<', AsciiPanel.brightYellow),
    STAIRS_UP('>', AsciiPanel.brightYellow);

    private char glyph;

    public char glyph() { return glyph; }
    // allows other parts of the code to access the glyph value.
    // It returns the character associated with the tile.

    private Color color;
    public Color color() { return color; }

    Tile(char glyph, Color color){
        // constructor for the Tile enumeration.
        // It initializes a new Tile instance with the provided glyph and color
        this.glyph = glyph;
        this.color = color;
        // For example Tile.FLOOR is created with a glyph of '250' and a yellow color
    }

    public boolean isDiggable() {
        //allow us to dig throug wall aka wall==floor
        //if new tile, update this
        return this == Tile.WALL;
    }

    public boolean isGround() {
        return this != WALL && this != BOUNDS;
        //checks if the current Tile object is equal to WALL or BOUNDS
    }
}
