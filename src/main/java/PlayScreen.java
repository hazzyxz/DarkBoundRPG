import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;

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

    private int screenWidth;
    private int screenHeight;

    public PlayScreen(){
        //initialize dimension of play screen
        //change size in AsciiPanel.jar file
        screenWidth = 40;
        screenHeight = 40;
        //declare messages as an array list of string
        messages = new ArrayList<String>();

        createNewWorld();

        //create FieldOfView instance
        fov = new FieldOfView(world);
        StuffFactory stuffFactory = new StuffFactory(world, fov);
        // calls the createCreature and createItems method and pass stuffFactory variable
        createCreatures(stuffFactory);
        createItems(stuffFactory);

    }

    public void displayOutput(AsciiPanel terminal) {

        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());

        //tell world to update all creature in world after user input
        world.update();

        //display stats on the play screen
        //, player.defenseValue(), player.attackValue()
        String stats = String.format(" Level %d [%d/%d]xp " , player.level(), player.xp(), player.maxXp());
        terminal.write(stats, 41, 1);

        displayInfo(terminal);

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
                    if (userIsTryingToExit()){
                        return new PlayScreen();
                    }
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

    public void displayInfo(AsciiPanel terminal){
        for (int i = 40; i <= 89 - 1; i++) {
            terminal.write('-', i, 0, Color.yellow);
        }
        for (int i = 0; i <= 40; i++) {
            terminal.write("/", 40, i, Color.yellow);
        }
        for (int i = 40; i <= 89 - 1; i++) {
            terminal.write('-', i, 4, Color.yellow);
        }
        for (int i = 5; i <= 40; i++) {
            terminal.write("|", 40 + 50/2, i, Color.yellow);
        }
        for (int i = 0; i <= 40; i++) {
            terminal.write("\\", 88, i, Color.yellow);
        }
        for (int i = 40; i <= 89 - 1; i++) {
            terminal.write('-', i, 20, Color.yellow);
        }
        for (int i = 0; i <= 89 - 1; i++) {
            terminal.write('-', i, 40, Color.yellow);
        }
        terminal.write('+', 40, 0, Color.yellow);
        terminal.write('+', 88, 0, Color.yellow);
        terminal.write('+', 40, 4, Color.yellow);
        terminal.write('+', 88, 4, Color.yellow);
        terminal.write('+', 40, 20, Color.yellow);
        terminal.write('+', 88, 20, Color.yellow);
        terminal.write('+', 0, 40, Color.yellow);
        terminal.write('+', 40, 40, Color.yellow);
        terminal.write('+', 40 + 50/2, 40, Color.yellow);
        terminal.write('+', 88, 40, Color.yellow);
        terminal.write('+', 40 + 50/2, 20 , Color.yellow);
        terminal.write('+', 40 + 50/2, 4, Color.yellow);

        String string = String.format(" Level %d [%d/%d]xp " , player.level(), player.xp(), player.maxXp());
        terminal.write(string,41, 1);
        terminal.write(" "+player.name(),41,3);

        displayHealth(player, 42,6,terminal);
        displayMana(player, 42,9,terminal);

        int x = 42;
        int y = 12;

        terminal.write("Phy. Attack: "+player.phyAttack(),x,y++);
        terminal.write("",x,y++);
        terminal.write("Mag. Attack: "+player.magAttack(),x,y++);
        terminal.write("",x,y++);
        terminal.write("Phy. Defense: "+player.phyDefense(),x,y++);
        terminal.write("",x,y++);
        terminal.write("Mag. Defense: "+player.magDefense(),x,y++);
    }

    private void displayHealth(Creature creature,int x, int y, AsciiPanel terminal){
        terminal.write("[HP]", x, y++);
        int remainingPercentage = creature.hp() * 100 / creature.maxHp();
        int barLength = 22;

        int barStartX = x-1;
        int healthBarLength = (remainingPercentage * barLength) / 100;

        for (int i = 0; i < barLength; i++) {
            if (i < healthBarLength) {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.brightRed);
            } else {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.red);
            }
        }

        /*
        y--;
        x = x+5;
        if (remainingPercentage < 10) {
            terminal.write("" + creature.hp(), x , y, AsciiPanel.red);
        }
        else if (remainingPercentage < 25) {
            terminal.write("" + creature.hp(), x, y, Color.ORANGE);
        } else if (remainingPercentage < 50) {
            terminal.write("" + creature.hp(), x, y, AsciiPanel.yellow);
        } else {
            terminal.write("" + creature.hp(), x, y, AsciiPanel.green);
        }

        terminal.write( "/", x+3, y,AsciiPanel.white);
        terminal.write(""+creature.maxHp(), x+3+1, y,AsciiPanel.green);
         */
    }

    private void displayMana(Creature creature,int x, int y, AsciiPanel terminal){
        terminal.write("[MP]", x, y++);
        int remainingPercentage = creature.hp() * 100 / creature.maxHp();
        int barLength = 22;

        int barStartX = x-1;
        int healthBarLength = (remainingPercentage * barLength) / 100;

        for (int i = 0; i < barLength; i++) {
            if (i < healthBarLength) {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.brightCyan);
            } else {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.cyan);
            }
        }

        /*
        y--;
        x = x+5;
        if (remainingPercentage < 10) {
            terminal.write("" + creature.hp(), x , y, AsciiPanel.red);
        }
        else if (remainingPercentage < 25) {
            terminal.write("" + creature.hp(), x, y, Color.ORANGE);
        } else if (remainingPercentage < 50) {
            terminal.write("" + creature.hp(), x, y, AsciiPanel.yellow);
        } else {
            terminal.write("" + creature.hp(), x, y, AsciiPanel.green);
        }

        terminal.write( "/", x+3, y,AsciiPanel.white);
        terminal.write(""+creature.maxHp(), x+3+1, y,AsciiPanel.green);
         */
    }

    public void createNewWorld(){
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
        ChooseClassScreen.worldCount++;
    }

    //create creatures method
    private void createCreatures(StuffFactory creatureFactory){
        //create player and player messages
        System.out.println(ChooseClassScreen.worldCount);

        if(ChooseClassScreen.worldCount==1){

            //playerCharacter.setStats(ArchetypeLoader.loadArchetype(playerCharacter.getClassID(0)));

            playerCharacter.setStats(playerCharacter.baseStats(playerCharacter));
            player = creatureFactory.newPlayer(messages,playerCharacter);

        }
        else {
            playerCharacter.setStats(playerCharacter.baseStats(playerCharacter));
            player = creatureFactory.newPlayer(messages,playerCharacter);
        }

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
                    terminal.write(fov.tile(wx, wy).glyph(), x, y, Color.DARK_GRAY);
            }
        }

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

    /*
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

     */


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
