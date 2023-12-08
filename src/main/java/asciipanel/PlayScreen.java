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
    private Creature creature;

    //initialize string array list
    private List<String> messages;
    private FieldOfView fov;
    //let PlayScreen know what the sub-screen is
    private Screen subscreen;
    //determine if player have chosen class

    Archetype playerCharacter = Archetype.createCharacter(ChooseClassScreen.classChoice);



    public void displayOutput(AsciiPanel terminal) {

        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());

        //tell world to update all creature in world after user input
        world.update();

        //display stats on the play screen
        //, player.defenseValue(), player.attackValue()
        String stats = String.format(" %3d/%-3dhp", player.hp(), player.maxHp());
        terminal.write(stats, 41, 2);

        //call the displayMessages method
        displayMessages(terminal, messages);

        //display the sub-screen
        //playScreen will be background screen
        if (subscreen != null)
            subscreen.displayOutput(terminal);
    }

    public Screen respondToUserInput(KeyEvent key) {
        /*
        movement - WASD/arrowKeys
        Diagonal movement - press movement keys simultaneously
        interact/pickup - f
        drop screen - g
        eat screen - e
        */
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        }
        else {
            /*
            if(!haveClass) {
                haveClass = true;
                subscreen = new ChooseClassScreen(player);
            }

             */

            switch (key.getKeyCode()) {
                case KeyEvent.VK_F:
                    if (userIsTryingToExit())
                        return new PlayScreen();
                    else if (isCreatureAdjacent(player)) {
                        Creature creature = getAdjacentCreature(player);
                        if (creature != null ) {
                            if(isEnemyAdjacent(player))
                                subscreen = new BattleScreen(player, creature);
                            else if(!isEnemyAdjacent(player))
                                subscreen = new DialogScreen(player, creature);
                        }
                    }
                    else
                        player.pickup();
                    break;
                case KeyEvent.VK_G: subscreen = new DropScreen(player); break;
                case KeyEvent.VK_E: subscreen = new EatScreen(player); break;
                case KeyEvent.VK_H: subscreen = new HelpScreen(); break;

                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    player.moveBy(-1, 0);
                    break;
                //go left
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    player.moveBy(1, 0);
                    break;
                //go right
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    player.moveBy(0, -1);
                    break;
                //go up
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    player.moveBy(0, 1);
                    break;
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
            if (key.getKeyCode() == KeyEvent.VK_W && key.getKeyCode() == KeyEvent.VK_A) {
                player.moveBy(-1, -1);
                //go top left
            } else if (key.getKeyCode() == KeyEvent.VK_W && key.getKeyCode() == KeyEvent.VK_D) {
                player.moveBy(1, -1);
                //go top right
            } else if (key.getKeyCode() == KeyEvent.VK_S && key.getKeyCode() == KeyEvent.VK_A) {
                player.moveBy(-1, 1);
                //go bot left
            } else if (key.getKeyCode() == KeyEvent.VK_S && key.getKeyCode() == KeyEvent.VK_D) {
                player.moveBy(1, 1);
                //go bot right
            }
            //press both at the same time to go diagonally
        }

        //update world if no sub-screen
        if (subscreen == null)
            world.update();

        //if hp<0 enter lose screen
        if (player.hp() < 1)
            return new LoseScreen();

        return this;
    }

    private boolean isCreatureAdjacent(Creature player) {
        List<Point> adjacentPoints = player.neighbors8();

        for (Point point : adjacentPoints) {
            Creature adjacentCreature = world.creature(point.x, point.y);
            if (adjacentCreature != null && adjacentCreature != player) {
                return true;
            }
        }
        return false;
    }

    private boolean isEnemyAdjacent(Creature player) {
        List<Point> adjacentPoints = player.neighbors8();
        for (Point point : adjacentPoints) {
            if ( world.glyph(point.x, point.y) =='N') {
                return false;
            }
        }
        return true;
    }

    // Get the adjacent enemy if it exists
    private Creature getAdjacentCreature(Creature player) {
        List<Point> adjacentPoints = player.neighbors8();

        for (Point point : adjacentPoints) {
            Creature adjacentCreature = world.creature(point.x, point.y);
            if (adjacentCreature != null && adjacentCreature != player) {
                return adjacentCreature;
            }
        }
        return null;
    }

    private int screenWidth;
    private int screenHeight;

    public PlayScreen(){
        //initialize dimension of play screen
        //change size in AsciiPanel.jar file
        screenWidth = 40;
        screenHeight = 40;
        //declare messages as an array list of string
        messages = new ArrayList<String>();

        int width = 40;
        int height = 40;
        float initialChance = 0.45f; // higher = more wall in initial map
        int deathLimit = 3; //num of adjacent tile to decide if the tile is removed
        int birthLimit = 4;  //num of adjacent tile to decide if the tile is added
        int repeat = 6; // lower = more jagged
        int desiredSize = 750; //desired size of the largest region

        world = new WorldBuilder(width, height)
                //generate game world with specific dimensions
                //class that provide generating and customizing world
                .makeCaves2(initialChance,deathLimit,birthLimit,repeat,desiredSize)
                .build();

        //create FieldOfView instance
        fov = new FieldOfView(world);
        StuffFactory stuffFactory = new StuffFactory(world, fov);
        // calls the createCreature and createItems method and pass stuffFactory variable
        createCreatures(stuffFactory);
        createItems(stuffFactory);

    }

    //create creatures method
    private void createCreatures(StuffFactory creatureFactory){
        //create player and player messages
        playerCharacter.setBaseStats(ArchetypeLoader.loadArchetype(playerCharacter.getClassID(0)));
        player = creatureFactory.newPlayer(messages,playerCharacter);

        creatureFactory.newNpc();

        //create fungus
        for (int i = 0; i < 8; i++){
            creatureFactory.newFungus();
        }

        //create bat
        for (int i = 0; i < 10; i++){
            creatureFactory.newBat();
        }
    }
    //create items method
    private void createItems(StuffFactory factory) {

        for (int i = 0; i < world.width() * world.height() / 20; i++){
            factory.newRock();
        }
        //create a victory item
        factory.newVictoryItem();
    }


    //generate game world with specific dimensions
    //class that provide generating and customizing world
    private void createWorld(){

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

        fov.update(player.x, player.y, player.visionRadius());


        //to render the tiles of the world
        for(int x = 0; x < screenWidth ; x++) {
            for(int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                //check if player can see and render the tile and creature if true
                if (player.canSee(wx, wy))
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                else
                    terminal.write(fov.tile(wx, wy).glyph(), x, y, AsciiPanel.darkGray);
            }
        }


        /*
        // loop through creatures, check if in bounds, and display
        for(Creature c : creatures) {
            //check if creatures is within the screen boundary area
            if((c.x >= left && c.x < left + screenWidth) && (c.y >= top && c.y < top + screenHeight)) {
                terminal.write(c.glyph(), c.x - left, c.y - top, c.color());
            }
        }
        */

    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        // Iterate over each message in the list
        for (int i = 0; i < messages.size(); i++){
            // top + i : Calculate the vertical position for each message based on the top and the index
            // Display the message at the center of the screen
            terminal.write(messages.get(i), 2 ,top + i + 5);
        }
        // Clear the messages list after displaying them
        messages.clear();
    }

    //empty code(will be remove)
    private boolean userIsTryingToExit(){
        return world.tile(player.x, player.y) == Tile.STAIRS_DOWN;
    }
    //victory condition(temporary)
    /*
    private Screen userExits(){
        for (Item item : player.inventory().getItems()){
            if (item != null && item.name().equals("teddy bear"))
                return new WinScreen();
        }
        //how to diplay text in terminal?
        return this;
    }
     */

    //tell player his hunger bar
    private String hunger(){
        if (player.food() < player.maxFood() * 0.1)
            return "Starving";
        else if (player.food() < player.maxFood() * 0.2)
            return "Hungry";
        else if (player.food() > player.maxFood() * 0.9)
            return "Stuffed";
        else if (player.food() > player.maxFood() * 0.8)
            return "Full";
        else
            return "Contempt";
    }



    /*
    //check if got adjacent enemy
    public boolean isEnemyAdjacent(Creature player) {
        List<Point> adjacentPoints = player.neighbors8();

        for (Point point : adjacentPoints) {
            Creature adjacentCreature = world.creature(point.x, point.y);

            if (adjacentCreature != null && adjacentCreature != player) {
                return true;
            }
        }
        return false;
    }

     */







}
