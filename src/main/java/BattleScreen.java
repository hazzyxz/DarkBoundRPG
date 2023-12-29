import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BattleScreen implements Screen {
    private Creature player;
    private Creature enemy;


    private boolean isEnemyAttacking = false;//for turn based combat
    private boolean canHeal = true; //heal 1 per game

    private int defenseCooldown = 0; // Track the remaining cooldown rounds for defense
    private int round = 0;


    private List<String> logHistory; // List to store log history

    AsciiArtDisplayer enemyAscii = new AsciiArtDisplayer();
    Random rand = new Random();

    public BattleScreen(Creature player, Creature enemy) {

        logHistory = new ArrayList<>();

        this.player = player;
        this.enemy = enemy;
    }

    private int xCenter = 45;

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
        for(int i=0; i<=89-1; i++){
            terminal.write('-',i,0,Color.yellow);
        }
        terminal.write('+',0,0,Color.yellow);
        terminal.write('+',88,0,Color.yellow);
        for(int i=0; i<=89-1; i++){
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
        for(int i=0; i<=89-1; i++){
            terminal.write('-',i,50-1,Color.yellow);
        }
        terminal.write('+',0,50-1,Color.yellow);
        terminal.write('+',88,50-1,Color.yellow);


        //content profile
        int nameCenter = xCenter-(6 + enemy.name().length())/2;
        terminal.write(">> " + enemy.name() + " <<",2,4,Color.red);
        boolean isPlayer = false;
        displayHealth(enemy,2,6, isPlayer,terminal);
        displayMana(enemy,2,8,isPlayer,terminal);

        terminal.write(">> " + player.name() + " <<",2,38,AsciiPanel.brightGreen);
        isPlayer = true;
        displayHealth(player,2,40, isPlayer, terminal);
        displayMana(player,2,42,isPlayer,terminal);

        //Ascii Art
        // x=43, y=24 character
        enemyAscii.displayAsciiArtFromFile(1,10, enemy.asciiPath(), isEnemyAttacking, terminal);

        //action
        int y = 38;
        int x = 46;
        terminal.write("< ACTION >",x,y++,AsciiPanel.brightBlack);
        terminal.write("----------",x,y++,AsciiPanel.brightBlack);
        terminal.write("",x,y++);
        terminal.write("[1] Attack",x,y++);
        terminal.write("[2] Defend",x,y++);
        terminal.write("[3] Heal",x,y++);
        terminal.write("[4] Escape",x,y++);

        y = 38;
        x = 46+20;
        terminal.write("{ SPELL }",x,y++,AsciiPanel.brightBlack);
        terminal.write("---------",x,y++,AsciiPanel.brightBlack);
        terminal.write("",x,y++);
        terminal.write("[Q] use Spell 1",x,y++);
        terminal.write("[W] use Spell 2",x,y++);
        terminal.write("[E] use Spell 3",x,y++);

        //log
        displayLog(terminal);

    }

    //enemy x=2 y=4
    private void displayHealth(Creature creature,int x, int y, boolean isPlayer, AsciiPanel terminal){
        terminal.write("HP ", x, y);
        int remainingPercentage = creature.hp() * 100 / creature.maxHp();
        int barLength = 25;

        int barStartX = x + 3;
        terminal.write("{", barStartX, y, AsciiPanel.white);

        int healthBarLength = (remainingPercentage * barLength) / 100;

        for (int i = 0; i < barLength; i++) {
            if (i < healthBarLength) {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.brightRed);
            } else {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.red);
            }
        }

        terminal.write("}", barStartX + barLength, y, AsciiPanel.white);

        if(isPlayer){
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

             */
            x = x+barLength+5;

            terminal.write("" + creature.hp(), x , y, AsciiPanel.brightRed);
            terminal.write( "/", x+3, y,AsciiPanel.white);
            terminal.write(""+creature.maxHp(), x+3+1, y,AsciiPanel.brightRed);
        }
    }

    private void displayMana(Creature creature,int x, int y, boolean isPlayer, AsciiPanel terminal){
        terminal.write("MP ", x, y);
        int remainingPercentage = creature.mp() * 100 / creature.maxMp();
        int barLength = 25;

        int barStartX = x + 3;
        terminal.write("{", barStartX, y, AsciiPanel.white);

        int manaBarLength = (remainingPercentage * barLength) / 100;

        for (int i = 0; i < barLength; i++) {
            if (i < manaBarLength) {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.brightCyan);
            } else {
                terminal.write('/', barStartX + i +1, y, AsciiPanel.cyan);
            }
        }

        terminal.write("}", barStartX + barLength, y, AsciiPanel.white);

        if(isPlayer){
            x = x+barLength+5;
            /*
            if (remainingPercentage < 10) {
                terminal.write("" + creature.mp(), x , y, AsciiPanel.red);
            }
            else if (remainingPercentage < 25) {
                terminal.write("" + creature.mp(), x, y, AsciiPanel.darkOrange);
            } else if (remainingPercentage < 50) {
                terminal.write("" + creature.mp(), x, y, AsciiPanel.yellow);
            } else {
                terminal.write("" + creature.mp(), x, y, AsciiPanel.cyan);
            }

             */
            terminal.write("" + creature.mp(), x , y, AsciiPanel.brightCyan);
            terminal.write( "/", x+3, y,AsciiPanel.white);
            terminal.write(""+creature.maxMp(), x+3+1, y,AsciiPanel.brightCyan);
        }
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
                log(" > You can't defend yet. ( " + defenseCooldown +" more round )");
            }
            //playerDefend(); //increase defense value for 3 turn
        }
        else if (key.getKeyCode() == KeyEvent.VK_3) {
            if(canHeal)
                playerHeal();
            else {
                log(" > You can only heal once per game ");
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_4) {
            if(canPlayerRun())
                return null;
            else {
                log(" : Fail to escape");
                enemyTurn();
                return this;
            }
        }
        else
            return this;

        //isEnemyAttacking = !isEnemyAttacking;
        enemyTurn();
        round++;

        if(enemy.isDead()) {
            enemy.leaveCorpse();
            player.gainXp(enemy);

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

    public boolean canPlayerRun(){
        int player =(int) (Math.random() * 6)+1;
        log(" : Player roll a "+player+ " to escape");
        if(player == 6){
            return true;
        }
        else{
            return false;
        }

    }

    public void playerAttack(){
        int amount;

        switch (player.name()){
            case "Warrior":
            case "Rogue":
            case "Archer":
                amount = (int) (0.25*player.phyAttack() );
                amount -= enemy.phyDefense();
                break;
            case "Mage":
                amount = (int) (0.25 * player.magAttack());
                amount -= enemy.magDefense();
                break;
            default:
                amount = (int) (0.25 * (player.phyAttack()+ player.magAttack())/2);
                amount -= (enemy.phyDefense() + enemy.magDefense())/2;
        }

        enemy.modifyHp(-amount);
        log(" > You attack the " + enemy.name() + " for " + amount + " damage");

        //call the notify method while passing the string
        //notify("You attack the '%s' for %d damage.", other.name(), amount);
        //other.notify("The '%s' attacks you for %d damage.", name, amount);

    }

    public void playerDefend(){
        //take the players 35% of defend value
        int amount =(int) (Math.max(0, (player.magDefense()+player.phyDefense())*0.35/2));

        //random number from 1 to amount of possible value
        amount = (int)(Math.random() * amount) + 1;

        player.modifyDefense(amount);
        log(" + You increase your Armour and Barrier by " + amount);
    }

    public void playerHeal(){
        //take the players remaining hp
        int amount = (int) (0.15 * player.hp());

        //random number from 1 to amount of possible lost hp
        //amount = (int)(Math.random() * amount) + 1;
        if(player.hp()+amount>player.maxHp())
            amount = player.maxHp()- player.hp();

        player.modifyHp(amount);
        log(" + You increase your health for " + amount + " Hp");
        canHeal = false;
    }

    public void enemyAttack(){
        int amount;

        //take the largest between phyatt or magatt
        amount = Math.max(enemy.magAttack(),enemy.phyAttack());

        //if phy att then phy def
        if(amount == enemy.phyAttack())
            amount -= player.phyDefense();
        else
            amount -= player.magDefense();

        //either 5 0r other
        amount = Math.max(5,amount);
        player.modifyHp(-amount);

        log(" > The " + enemy.name() + " attack " + player.name() + " for " + amount + " damage");
    }



    private int lines;

    // Method to display attack log including history
    // Method to display attack log including history
    private void displayLog(AsciiPanel terminal) {
        terminal.write("[ LOG ]", 67 - 4, 3, AsciiPanel.brightBlack); // Header for attack log
        terminal.write("-------", 67 - 4, 3 + 1, AsciiPanel.brightBlack);

        int logY = 6; // Starting Y position for displaying log
        lines = logY;

        // Display attack log history
        for (String log : logHistory) {
            int xPosition = 46; // Initial X position

            // Wrap long messages
            List<String> wrappedLines = wrapText(log, 41); // Wrap at x characters (adjust as needed)

            for (String line : wrappedLines) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    if (xPosition + word.length() >= terminal.getWidth()) {
                        // Move to the next line if the word exceeds the terminal width
                        xPosition = 46; // Reset X position
                        logY++; // Move to the next line
                    }

                    // Write the word at the calculated position
                    if (word.equalsIgnoreCase(player.name()) || word.equalsIgnoreCase("you")) {
                        terminal.write(word, xPosition, logY, AsciiPanel.brightGreen);
                    } else if (word.equalsIgnoreCase(enemy.name())) {
                        terminal.write(word, xPosition, logY, AsciiPanel.brightRed);
                    } else if (word.equalsIgnoreCase("health")) {
                        terminal.write(word, xPosition, logY, AsciiPanel.red);
                    } else if (word.equalsIgnoreCase("armour")) {
                        terminal.write(word, xPosition, logY, Color.orange);
                    } else if (word.matches("\\d+")) {
                        terminal.write(word, xPosition, logY, AsciiPanel.brightWhite);
                    } else if (word.equalsIgnoreCase(">") || word.equalsIgnoreCase("+")) {
                        terminal.write(word, xPosition, logY, AsciiPanel.brightBlack);
                    } else {
                        terminal.write(word, xPosition, logY, AsciiPanel.white);
                    }

                    xPosition += word.length() + 1; // Move X position for the next word
                }

                logY++; // Move to the next line for the next log entry
                lines++;
                xPosition = 49; // Reset X position for a new line
            }

            logY++; // Extra space between logs
            lines++;
        }
    }


    private void log(String log) {
        //logHistory.add(log);
        logHistory.add(log);

        // Ensure the log history maintains a maximum size
        if (logHistory.size() > 0 && lines > 30+3 ) {//22
            logHistory.remove(0); // Remove the oldest log until it reaches the maximum size
        }

    }

    // Method to wrap text into multiple lines
    private List<String> wrapText(String text, int wrapLength) {
        List<String> lines = new ArrayList<>();

        while (!text.isEmpty()) {
            if (text.length() <= wrapLength) {
                lines.add(text);
                break;
            }

            int spaceIndex = text.lastIndexOf(" ", wrapLength);
            if (spaceIndex <= 0) {
                spaceIndex = wrapLength;
            }
            lines.add(text.substring(0, spaceIndex));
            text = text.substring(spaceIndex).trim(); // Resetting text for the next line
        }

        return lines;
    }


}
