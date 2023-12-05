package asciipanel;

import asciiLib.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class BattleScreen implements Screen {
    private Creature player;
    private Creature enemy;


    private boolean isEnemyAttacking = false;//for turn based combat
    private boolean canHeal = true; //heal 1 per game

    private int defenseCooldown = 0; // Track the remaining cooldown rounds for defense
    private int round = 0;


    private List<String> logHistory; // List to store attack log history

    AsciiArtDisplayer enemyAscii = new AsciiArtDisplayer();

    public BattleScreen(Creature player, Creature enemy) {

        logHistory = new ArrayList<>();

        this.player = player;
        this.enemy = enemy;
    }

    int xCenter = 45;

    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Display information about the battle, including player and enemy details
        // You can access player and enemy attributes here to show relevant information
        // 60x80(x,y)
        // length 89(x)
        // height 50(y)

        //partition
        terminal.clear();
        for(int i=0; i<=50-1; i++){
            terminal.write("|", 0,i,Color.yellow);
        }
        for(int i=0; i<=50-1; i++){
            terminal.write("|", 89-1,i,Color.yellow);
        }
        for(int i=0; i<=100-1; i++){
            terminal.write('-',i,0,Color.yellow);
        }
        terminal.write('+',0,0,Color.yellow);
        terminal.write('+',88,0,Color.yellow);
        for(int i=0; i<=100-1; i++){
            terminal.write('-',i,2,Color.yellow);
        }
        terminal.write('+',0,2,Color.yellow);
        terminal.write('+',88,2,Color.yellow);
        for(int i=3; i<=37-1; i++){
            terminal.write("|", xCenter,i,Color.yellow);
        }
        for(int i=0; i<=89-1; i++){
            terminal.write('-',i,36,Color.yellow);
        }
        terminal.write('+',0,36,Color.yellow);
        terminal.write('+',88,36,Color.yellow);
        for(int i=0; i<=100-1; i++){
            terminal.write('-',i,50-1,Color.yellow);
        }
        terminal.write('+',0,50-1,Color.yellow);
        terminal.write('+',88,50-1,Color.yellow);


        //content profile
        int nameCenter = xCenter-(6 + enemy.name().length())/2;
        terminal.write(">> " + enemy.name() + " <<",nameCenter,1,Color.red);
        displayHealth(enemy,2,4,terminal);

        terminal.write(">> " + player.name() + " <<",46,38,AsciiPanel.green);
        terminal.write(' ',46,39);
        displayHealth(player,46,40,terminal);

        //Ascii Art
        // x=43, y=32 character
        enemyAscii.displayAsciiArtFromFile(6,6, enemy.asciiPath(), isEnemyAttacking, terminal);

        //action
        int y = 38;
        terminal.write("< ACTION >",2,y++);
        terminal.write("----------",2,y++);
        terminal.write("[1] to Attack",2,y++);
        terminal.write("[2] to Defend",2,y++);
        terminal.write("[3] to Heal",2,y++);
        terminal.write("[4] to use Item",2,y++);
        terminal.write("[5] to Run",2,y++);

        y = 38;
        int x = 20;
        terminal.write("{ SPELL }",x,y++);
        terminal.write("---------",x,y++);
        terminal.write("[Q] to use Spell 1",x,y++);
        terminal.write("[W] to use Spell 2",x,y++);
        terminal.write("[E] to use Spell 3",x,y++);

        //log
        displayLog(terminal);

    }

    //enemy x=2 y=4
    private void displayHealth(Creature creature,int x, int y,AsciiPanel terminal){
        terminal.write("HP ", x, y);
        int remainingPercentage = creature.hp() * 100 / creature.maxHp();
        int barLength = 29;

        int barStartX = x + 3;
        terminal.write("{", barStartX, y, AsciiPanel.white);

        int healthBarLength = (remainingPercentage * barLength) / 100;

        for (int i = 0; i < barLength; i++) {
            if (i < healthBarLength) {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.green);
            } else {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.red);
            }
        }

        terminal.write("}", barStartX + barLength, y, AsciiPanel.white);

        /*
        x = x+barLength+5;
        if (remainingPercentage < 10) {
            terminal.write("" + creature.hp(), x , y, AsciiPanel.red);
        }
        else if (remainingPercentage < 25) {
            terminal.write("" + creature.hp(), x, y, AsciiPanel.darkOrange);
        } else if (remainingPercentage < 50) {
            terminal.write("" + creature.hp(), x, y, AsciiPanel.yellow);
        } else {
            terminal.write("" + creature.hp(), x, y, AsciiPanel.green);
        }

        terminal.write( "/", x+3, y,AsciiPanel.white);
        terminal.write(""+creature.maxHp(), x+3+1, y,AsciiPanel.green);

         */
    }


    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_1)
            playerAttack();
        else if (key.getKeyCode() == KeyEvent.VK_2) {
            if (defenseCooldown == 0) {
                playerDefend(); // Increase defense value for a turns. cooldown 2 turn
                defenseCooldown = 3; // Set the cooldown to 3 rounds
            } else {
                log(" > You can't defend yet. (" + defenseCooldown +" more round)");
                log("");
            }
            //playerDefend(); //increase defense value for 3 turn
        }
        else if (key.getKeyCode() == KeyEvent.VK_3) {
            if(canHeal)
                playerHeal();
            else {
                log(" > You can only heal once per game ");
                log("");
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_5)
            return null;
        else
            return this;

        //isEnemyAttacking = !isEnemyAttacking;
        enemyTurn();
        round++;

        if(enemy.isDead()) {
            enemy.leaveCorpse();
            return null;
        }
        if (defenseCooldown > 0) {
            defenseCooldown--;
        }

        // For example, returning null to exit the battle screen
        return this;
    }

    public void enemyTurn(){
        isEnemyAttacking = !isEnemyAttacking;
        enemyAttack();
        player.resetDefense();
    }

    public void playerAttack(){

        //take the attackers attack value - defenders defense value
        int amount = Math.max(0, player.attackValue() - enemy.defenseValue());

        //random number from 1 to amount of possible damage
        amount = (int)(Math.random() * amount) + 1;

        enemy.modifyHp(-amount);
        log(" > You attack the " + enemy.name() + " for " + amount + " damage");
        log("");

        //call the notify method while passing the string
        //notify("You attack the '%s' for %d damage.", other.name(), amount);
        //other.notify("The '%s' attacks you for %d damage.", name, amount);

    }

    public void playerDefend(){
        //take the players defend value
        int amount = Math.max(0, player.defenseValue());

        //random number from 1 to amount of possible defense
        amount = (int)(Math.random() * amount) + 1;

        player.modifyDefense(amount);
        log(" + You increase your defense for " + amount + " defense");
        log("");
    }

    public void playerHeal(){
        //take the players lost hp
        int amount = player.maxHp() - player.hp();

        //random number from 1 to amount of possible lost hp
        amount = (int)(Math.random() * amount) + 1;

        player.modifyHp(amount);
        log(" + You increase your health for " + amount + " Hp");
        log("");
        canHeal = false;
    }

    public void enemyAttack(){
        int amount = Math.max(0, enemy.attackValue() - player.defenseValue());
        amount = (int) (Math.random() * amount) + 1;
        player.modifyHp(-amount);

        log(" > The " + enemy.name() + " attack " + player.name() + " for " + amount + " damage");
        log("");
    }

    // Method to display attack log including history
    // Method to display attack log including history
    private void displayLog(AsciiPanel terminal) {
        terminal.write("< LOG >", 67-4, 3); // Header for attack log
        terminal.write("-------",67-4,3+1);

        int logY = 6; // Starting Y position for displaying log

        // Display attack log history
        for (String log : logHistory) {
            // Wrap long messages
            List<String> wrappedLines = wrapText(log, 41); // Wrap at 96 characters (adjust as needed)
            for (String line : wrappedLines) {
                terminal.write(line, 46, logY++, AsciiPanel.white); // Display the wrapped log message
            }
        }

    }

    private void log(String log) {
        logHistory.add(log);


        // Ensure the attack log history maintains a maximum size (e.g., 10 logs)
        if (logHistory.size() > 22) {
            logHistory.remove(0); // Remove the oldest log if the list exceeds the maximum size
        }

    }

    // Method to wrap text into multiple lines
    private List<String> wrapText(String text, int wrapLength) {
        List<String> lines = new ArrayList<>();
        while (text.length() > wrapLength) {
            int spaceIndex = text.lastIndexOf(' ', wrapLength);
            if (spaceIndex <= 0) {
                spaceIndex = wrapLength;
            }
            lines.add(text.substring(0, spaceIndex));
            text = text.substring(spaceIndex);
        }
        lines.add(text); // Add the remaining or last line
        return lines;
    }

    // Method to add attack log to history


    /*
   public class BattleScreen extends Screen {
    private Creature player;
    private Creature enemy;
    // Other attributes...

    // Constructor and other methods...

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        // Player's input handling...

        if (enemy.isDead()) {
            enemy.leaveCorpse();
            return null; // Exit the battle if enemy is dead
        }

        // Enemy's turn
        enemyTurn();

        if (player.isDead()) {
            // Handle player defeat
            return null;
        }

        return this; // Return the battle screen
    }

    // Enemy's turn logic
    private void enemyTurn() {
        int amount = Math.max(0, enemy.attackValue() - player.defenseValue());
        amount = (int) (Math.random() * amount) + 1;
        player.modifyHp(-amount);

        String attackLogMessage = "The " + enemy.name() + " attacks you for " + amount + " damage!";
        addToAttackLog(attackLogMessage);
    }

    // Method to add attack log messages to history
    private void addToAttackLog(String message) {
        attackLogHistory.add(message);
        if (attackLogHistory.size() > 10) {
            attackLogHistory.remove(0); // Keep the attack log limited to 10 messages
        }
    }

    // Other methods...
}




     */


}
