package asciipanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiLib.AsciiPanel;

public class PlayScreen implements Screen {
    //doing also the same by accessing the screen group and giving different function

    private World world;

    // initialize a variable named player
    private Creature player;

    //initialize string array list
    private List<String> messages;

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You are having fun.", 1, 1);
        terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);

        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());

        //tell world to update all creature in world after user input
        world.update();

        //display stats on the play screen
        String stats = String.format(" %3d/%-3d hp att: %-3d def: %-3d", player.hp(), player.maxHp(), player.attackValue(), player.defenseValue());
        terminal.write(stats, 1, 24);


        //call the displayMessages method
        displayMessages(terminal, messages);
    }

    public Screen respondToUserInput(KeyEvent key) {

        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                return new LoseScreen();
            case KeyEvent.VK_ENTER:
                return new WinScreen();
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.moveBy(-1, 0); break;
            //go left

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.moveBy( 1, 0); break;
            //go right

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                player.moveBy( 0,-1); break;
            //go up

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.moveBy( 0, 1); break;
            //go down

            /*
            case KeyEvent.VK_Q:
                scrollBy(-1,-1); break;
                //go top left
            case KeyEvent.VK_E:
                scrollBy( 1,-1); break;
                //go top right
            case KeyEvent.VK_Z:
                scrollBy(-1, 1); break;
                //go bot left
            case KeyEvent.VK_X:
                scrollBy( 1, 1); break;
                //go bot right

             */
        }
        if( key.getKeyCode()==KeyEvent.VK_W && key.getKeyCode()==KeyEvent.VK_A){
            player.moveBy(-1,-1);
            //go top left
        }
        else if( key.getKeyCode()==KeyEvent.VK_W && key.getKeyCode()==KeyEvent.VK_D){
            player.moveBy(1,-1);
            //go top right
        }
        else if( key.getKeyCode()==KeyEvent.VK_S && key.getKeyCode()==KeyEvent.VK_A){
            player.moveBy(-1,1);
            //go bot left
        }
        else if( key.getKeyCode()==KeyEvent.VK_S && key.getKeyCode()==KeyEvent.VK_D){
            player.moveBy(1,1);
            //go bot right
        }
        //press both at the same time to go diagonally


        return this;
    }

    private int screenWidth;
    private int screenHeight;

    public PlayScreen(){
        //initialize dimension of play screen
        //change size in AsciiPanel.jar file
        screenWidth = 80;
        screenHeight = 24;
        //declare messages as an array list of string
        messages = new ArrayList<String>();

        //calls the create world method
        createWorld();

        CreatureFactory creatureFactory = new CreatureFactory(world);
        // calls the createCreature method and pass creatureFactory variable
        createCreatures(creatureFactory);

    }

    //create creatures method
    private void createCreatures(CreatureFactory creatureFactory){
        //create player and player messages
        player = creatureFactory.newPlayer(messages);

        //create fungus
        for (int i = 0; i < 8; i++){
            creatureFactory.newFungus();
        }
    }

    //generate game world with specific dimensions
    //class that provide generating and customizing world
    private void createWorld(){
        world = new WorldBuilder(90, 31)
                //call world make caves method
                .makeCaves()
                //call world build method
                .build();
    }

    public int getScrollX() {
        //method to tell us how far along the X axis we should scroll

        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
        //makes sure we never try to scroll too far to the left or right.
    }

    public int getScrollY() {
        // method to tell us how far along the Y axis we should scroll

        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
        //makes sure we never try to scroll too far to the top or bottom.
    }


    private void displayTiles(AsciiPanel terminal, int left, int top) {

        //initialise creature array list
        List<Creature> creatures = world.getCreatures();


        //to render the tiles of the world
        for(int x = 0; x < screenWidth ; x++) {
            for(int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }

        // loop through creatures, check if in bounds, and display
        for(Creature c : creatures) {
            //check if creatures is within the screen boundary area
            if((c.x >= left && c.x < left + screenWidth) && (c.y >= top && c.y < top + screenHeight)) {
                terminal.write(c.glyph(), c.x - left, c.y - top, c.color());
            }
        }
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        // Iterate over each message in the list
        for (int i = 0; i < messages.size(); i++){
            // top + i : Calculate the vertical position for each message based on the top and the index
            // Display the message at the center of the screen
            terminal.writeCenter(messages.get(i), top + i + 2);
        }
        // Clear the messages list after displaying them
        messages.clear();
    }


    /*
    private void displayTiles(AsciiPanel terminal, int left, int top) {
        //left and top determining the starting column and row for rendering tiles

        //iterate to screen width and height to render tiles
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;
                //used to determine the corresponding world coordinates
                //based on the current screen position and offset

                //to show any creature in a world
                Creature creature = world.creature(wx, wy);
                if (creature != null)
                    terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
                else
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
            //method used to render a single tile at the screen position (x, y)
            // with the glyph and color information obtained from the game world
            // at the corresponding world coordinates (wx, wy)


        }


    }*/

}
