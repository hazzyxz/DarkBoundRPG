import java.awt.Color;

//for handling item
public class Item {

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String name() { return name; }

    //for food logic
    private int foodValue;
    public int foodValue() { return foodValue; }

    public Item(char glyph, Color color, String name){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }

    public void modifyFoodValue(int amount) { foodValue += amount; }
}
