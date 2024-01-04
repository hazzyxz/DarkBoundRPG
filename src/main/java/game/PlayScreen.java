package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {
    //doing also the same by accessing the screen group and giving different function

    public World world;

    // initialize a variable named player
    protected Creature player;
    protected Creature creature;
    protected SaveFunction save = new SaveFunction();

    //initialize string array list
    private List<String> messages;
    private FieldOfView fov;
    //let PlayScreen know what the sub-screen is
    private Screen subscreen;
    //determine if player have chosen class

    Archetype playerCharacter;

    private int screenWidth;
    private int screenHeight;
    private Connection connection = null;

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

        //display stats on the play screen
        //, player.defenseValue(), player.attackValue()
        String stats = String.format(" Level %d [%d/%d]xp " , player.level(), player.xp(), player.maxXp());
        terminal.write(stats, 41, 1);

        displayInfo(terminal);
        displayLegends(terminal);
        displayHotkey(terminal);

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
            if (isCreatureAdjacent(player)) {
                Creature creature = getAdjacentCreature(player);
                if (creature != null) {
                    if (isEnemyAdjacent(player)) {
                        subscreen = new BattleScreen(player, creature);
                    }
                }
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
            switch (key.getKeyCode()) {
                case KeyEvent.VK_F:
                    if (userIsTryingToExit()){
                        save.saveToQuickSave(player);
                        return nextPlayScreen();
                        //return new PlayScreen2();
                    }
                    else if (isCreatureAdjacent(player)) {
                        Creature creature = getAdjacentCreature(player);
                        if (creature != null ) {
                            if(isEnemyAdjacent(player)) {
                                //subscreen = new BattleScreen(player, creature);
                            }
                            else if(!isEnemyAdjacent(player)) {
                                subscreen = new DialogScreen(player, creature);
                            }
                        }
                    }
                    else
                        player.pickup();
                    break;
                case KeyEvent.VK_G: subscreen = new DropScreen(player); break;
                case KeyEvent.VK_E: subscreen = new EatScreen(player); break;
                case KeyEvent.VK_H: subscreen = new HelpScreen(); break;
                case KeyEvent.VK_L: player.modifyXp(1000); break;

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

                case KeyEvent.VK_P:
                    player.doAction("quick-save");
                    save.saveToFileSave(player);
                    break;

                case KeyEvent.VK_ESCAPE:
                    save.saveToFileSave(player);
                    ChooseClassScreen.worldCount = 0;
                    return new StartScreen();

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

        }

        //update world if no sub-screen
        if (subscreen == null) {
            world.update();
        }


        //if hp<0 enter lose screen
        if (player.hp() < 1)
            return new LoseScreen();

        return this;
    }

    public Screen nextPlayScreen(){
        return new PlayScreen2();
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

        terminal.write("Physical: "+player.phyAttack(),x,y++);
        terminal.write("",x,y++);
        terminal.write("Magical: "+player.magAttack(),x,y++);
        terminal.write("",x,y++);
        terminal.write("Armour: "+player.phyDefense(),x,y++);
        terminal.write("",x,y++);
        terminal.write("Barrier: "+player.magDefense(),x,y++);
    }

    public void displayLegends(AsciiPanel terminal) {
        int x = 67;
        int y = 5;

        terminal.write("Legends",x,y++);
        terminal.write("-------",x,y++);
        terminal.write(" ",x,y++);

        terminal.write(player.glyph() + " - " + " Player",x,y++);
        terminal.write(" ",x,y++);
        terminal.write("F - " + " Fungus",x,y++);
    }

    public void displayHotkey(AsciiPanel terminal) {
        int x = 67;
        int y = 21;

        terminal.write("Hotkey",x,y++);
        terminal.write("-------",x,y++);
        terminal.write(" ",x,y++);

        terminal.write("WASD -- Move",x,y++);
        terminal.write("",x,y++);
        terminal.write("F -- Interact",x,y++);
        terminal.write("",x,y++);
        terminal.write("P -- Quick-save",x,y++);
        terminal.write("",x,y++);
        terminal.write("ESC -- Save & Exit",x,y++);

        displayWorlds(terminal);
    }

    public void displayWorlds(AsciiPanel terminal) {
        terminal.write("World 1 but "+ChooseClassScreen.worldCount,42,21);
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
        int remainingPercentage = creature.mp() * 100 / creature.maxMp();
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

    protected void createNewWorld(){
        int width = 40;
        int height = 40;
        float initialChance = 0.4f; // higher = more wall in initial map
        int deathLimit = 3; //num of adjacent tile to decide if the tile is removed
        int birthLimit = 4;  //num of adjacent tile to decide if the tile is added
        int repeat = 7; // lower = more jagged
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

        // if world 1 and cont false = new game | create character from base stats
        if(ChooseClassScreen.worldCount==1 && !StartScreen.cont){
            // create the archetype class
            playerCharacter = Archetype.createCharacter(ChooseClassScreen.classChoice);

            // Set the base stats to the archetype
            playerCharacter.setStats(ArchetypeLoader.loadArchetype(ChooseClassScreen.classChoice));
            // Create a player object with stats from archetype
            player = creatureFactory.newPlayer(messages,playerCharacter);

            creatureFactory.newNpc();
        }

        // if world 1 and cont true = continue game from FileSave
        else if (ChooseClassScreen.worldCount == 1 && StartScreen.cont) {
            // Create the archetype class
            playerCharacter = Archetype.createCharacter(ChooseClassScreen.classChoice);

            // Load stats from database
            playerCharacter.setStats(save.loadFromFileSave());

            // Create a player creature from stats
            player = creatureFactory.newPlayer(messages,playerCharacter);
            StartScreen.cont = false;
        }
        // if not world 1 = continue game from QuickSave
        else if (ChooseClassScreen.worldCount > 1){
            playerCharacter = Archetype.createCharacter(ChooseClassScreen.classChoice);
            
            playerCharacter.setStats(save.loadFromQuickSave());

            player = creatureFactory.newPlayer(messages,playerCharacter);
            StartScreen.cont = false;
        }

        /*
        //create bat
        for (int i = 0; i < 7; i++){
            creatureFactory.newBat();
        }
         */

        createEnemies(creatureFactory);
    }

    protected void createEnemies(StuffFactory creatureFactory){
        //20 creature
        for (int i = 0; i < 6; i++){
            creatureFactory.newFungus();
        }
        for (int i = 0; i < 7; i++){
            creatureFactory.newGoblin();
        }
        for (int i = 0; i < 7; i++){
            creatureFactory.newSkeleton();
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

        fov.update(player.x, player.y, visionRadius());


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

    protected int visionRadius() {
        return 9;
    }

    /*
    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = 41; // Set the initial top position
        // Iterate over each message in the list in reverse order
        for (int i = messages.size() - 1; i >= 0; i--) {
            // top + (messages.size() - 1 - i) : Calculate the vertical position for each message based on the top and the index
            // Display the message at the center of the screen
            terminal.write(messages.get(i), 2, top + (messages.size() - 1 - i));
        }
        // Clear the messages list after displaying them
        messages.clear();
    }
     */

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = 42; // Set the initial top position
        boolean isFirstMessage = true; // Flag to track the first message

        for (int i = messages.size() - 1; i >= 0; i--) {
            String currentMessage = messages.get(i);

            if (isFirstMessage) {
                terminal.write("> "+currentMessage+" <", 2, top + (messages.size() - 1 - i));
                isFirstMessage = false; // Update the flag after displaying the first message
            } else {
                terminal.write(currentMessage, 4, top + (messages.size() - 1 - i),AsciiPanel.brightBlack);
            }

            if (messages.size() >= 8) {
                messages.remove((messages.size() - 1 - i));
            }
        }
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
        int count=0;

        for (Point point : adjacentPoints) {
            Creature adjacentCreature = world.creature(point.x, point.y);
            if (adjacentCreature != null && adjacentCreature != player) {
                count++;

            }
        }

        if(count > 1)
            return false;
        else {
            return true;
        }
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
