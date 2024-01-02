package game;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;


public class DialogScreen implements Screen {
    private Creature player;
    private Creature npc;


    private boolean isNpcTurn = false;//for turn based combat
    private int dialogCount = 0;


    private List<String> logHistory; // List to store log history
    //private Map<String, String> dialogOptions;
    //private String currentDialogResponse; // Field to store the current dialog response


    AsciiArtDisplayer npcAscii = new AsciiArtDisplayer();
    Random rand = new Random();

    public DialogScreen(Creature player, Creature npc) {

        logHistory = new ArrayList<>();

        this.player = player;
        this.npc = npc;

        //dialogOptions = new HashMap<>();
        //initializeDialogOptions();
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
        for (int i = 0; i <= 50 - 1; i++) {
            terminal.write("|", 0, i, Color.yellow);
        }
        for (int i = 0; i <= 50 - 1; i++) {
            terminal.write("|", 89 - 1, i, Color.yellow);
        }
        for (int i = 0; i <= 89 - 1; i++) {
            terminal.write('-', i, 0, Color.yellow);
        }
        terminal.write('+', 0, 0, Color.yellow);
        terminal.write('+', 88, 0, Color.yellow);
        for (int i = 0; i <= 89 - 1; i++) {
            terminal.write('-', i, 2, Color.yellow);
        }
        terminal.write('+', 0, 2, Color.yellow);
        terminal.write('+', 88, 2, Color.yellow);
        for (int i = 3; i <= 37 - 1; i++) {
            terminal.write("|", xCenter, i, Color.yellow);
        }
        for (int i = 0; i <= 89 - 1; i++) {
            terminal.write('-', i, 36, Color.yellow);
        }
        terminal.write('+', 0, 36, Color.yellow);
        terminal.write('+', 88, 36, Color.yellow);
        for (int i = 0; i <= 89 - 1; i++) {
            terminal.write('-', i, 50 - 1, Color.yellow);
        }
        terminal.write('+', 0, 50 - 1, Color.yellow);
        terminal.write('+', 88, 50 - 1, Color.yellow);


        //content profile
        int nameCenter = xCenter - (6 + npc.name().length()) / 2;
        terminal.write(">> " + npc.name() + " <<", xCenter - npc.name().length() / 2 - 2, 1, AsciiPanel.green);
        boolean isPlayer = false;
        //displayHealth(enemy,2,6, isPlayer,terminal);
        //displayMana(enemy,2,8,isPlayer,terminal);

        /*
        terminal.write(">> " + player.name() + " <<", 2, 38, AsciiPanel.brightGreen);
        isPlayer = true;
        displayHealth(player, 2, 40, isPlayer, terminal);
        displayMana(player, 2, 42, isPlayer, terminal);
         */

        //Ascii Art
        // x=43, y=32 character
        npcAscii.displayAsciiArtFromFile(1, 10, npc.asciiPath(), isNpcTurn, terminal);

        terminal.write("Press [1] to continue ..." , 2,38);

        /*
        //action
        int y = 38;
        int x = 46;
        terminal.write("< ACTION >", x, y++, AsciiPanel.brightBlack);
        terminal.write("----------", x, y++, AsciiPanel.brightBlack);
        terminal.write("", x, y++);
        terminal.write("[1] to Attack", x, y++);
        terminal.write("[2] to Defend", x, y++);
        terminal.write("[3] to Heal", x, y++);
        terminal.write("[4] to use Item", x, y++);
        terminal.write("[5] to Run", x, y++);

        y = 38;
        x = 46 + 20;

        terminal.write("{ SPELL }", x, y++, AsciiPanel.brightBlack);
        terminal.write("---------", x, y++, AsciiPanel.brightBlack);
        terminal.write("", x, y++);
        terminal.write("[Q] to use Spell 1", x, y++);
        terminal.write("[W] to use Spell 2", x, y++);
        terminal.write("[E] to use Spell 3", x, y++);

         */

        //log
        displayLog(terminal);

    }

    //enemy x=2 y=4
    private void displayHealth(Creature creature, int x, int y, boolean isPlayer, AsciiPanel terminal) {
        terminal.write("HP ", x, y);
        int remainingPercentage = creature.hp() * 100 / creature.maxHp();
        int barLength = 25;

        int barStartX = x + 3;
        terminal.write("{", barStartX, y, AsciiPanel.white);

        int healthBarLength = (remainingPercentage * barLength) / 100;

        for (int i = 0; i < barLength; i++) {
            if (i < healthBarLength) {
                terminal.write('/', barStartX + i + 1, y, AsciiPanel.brightRed);
            } else {
                terminal.write('/', barStartX + i + 1, y, AsciiPanel.red);
            }
        }

        terminal.write("}", barStartX + barLength, y, AsciiPanel.white);

        if (isPlayer) {
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
            x = x + barLength + 5;

            terminal.write("" + creature.hp(), x, y, AsciiPanel.brightRed);
            terminal.write("/", x + 3, y, AsciiPanel.white);
            terminal.write("" + creature.maxHp(), x + 3 + 1, y, AsciiPanel.brightRed);
        }
    }

    private void displayMana(Creature creature, int x, int y, boolean isPlayer, AsciiPanel terminal) {
        terminal.write("MP ", x, y);
        int remainingPercentage = creature.mp() * 100 / creature.maxMp();
        int barLength = 25;

        int barStartX = x + 3;
        terminal.write("{", barStartX, y, AsciiPanel.white);

        int manaBarLength = (remainingPercentage * barLength) / 100;

        for (int i = 0; i < barLength; i++) {
            if (i < manaBarLength) {
                terminal.write('/', barStartX + i + 1, y, AsciiPanel.brightCyan);
            } else {
                terminal.write('/', barStartX + i + 1, y, AsciiPanel.cyan);
            }
        }

        terminal.write("}", barStartX + barLength, y, AsciiPanel.white);

        if (isPlayer) {
            x = x + barLength + 5;
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
            terminal.write("" + creature.mp(), x, y, AsciiPanel.brightCyan);
            terminal.write("/", x + 3, y, AsciiPanel.white);
            terminal.write("" + creature.maxMp(), x + 3 + 1, y, AsciiPanel.brightCyan);
        }
    }


    @Override
    public Screen respondToUserInput(KeyEvent key) {
        //log(" You have encounter an npc ", false);

        /*
        if (key.getKeyCode() == KeyEvent.VK_1){
            if (dialogCount < dialog.length) {
                npcDialog();
                this.dialogCount++;
                System.out.println(dialogCount);
            } else {
                npc.removeCreature();
                return null;
            }
        }
        else if(key.getKeyCode() == KeyEvent.VK_5)
            return null;
        else
            return this;

         */
        /*
        switch (key.getKeyCode()) {
            case KeyEvent.VK_1:
                npcDialog();
                System.out.println(dialogCount);
                respondToUserInput(key);
                break;
                //selectAndDisplayDialogResponse("1");
                //log(" > Who are you?",true);
            case KeyEvent.VK_5:
                return null;
            default:
                return this;
        }

         */

        switch (key.getKeyCode()) {
            case KeyEvent.VK_1:
                if (dialogCount < dialog.length) {
                    npcDialog();
                    break;
                } else {
                    npc.removeCreature();
                    return null;
                }
                //selectAndDisplayDialogResponse("1");
                //log(" > Who are you?",true);

            case KeyEvent.VK_5:
                return null;
            default:
                return this;
        }


        //isEnemyAttacking = !isEnemyAttacking;
        //enemyTurn();
        //round++;

        /*
        if(enemy.isDead()) {
            enemy.leaveCorpse();
            player.gainXp(enemy);

            return null;
        }
         */

        // For example, returning null to exit the battle screen
        return this;
    }

    private String[] dialog =
            {
                    " A silhouette don in brown hood can be seen sitting near a fireplace ",
                    " > ...",
                    " > Greetings... Scholar from beyond the dark.",
                    " > I am <penasihat_name> ",
                    " She open her hood",
                    " > ... and I wish to offer you an accord.",
                    " > Have you heard of Academic Advisor?",
                    " > They serve the Academy, offering guidance, and aid to the Scholars",
                    " > but you, I'm afraid, are advisorless.",
                    " > I can play the role of advisor.",
                    " > Turning merit into strength.",
                    " > To aid you in the search of GPA 4.0.",
                    " > You need only to take me with you.",
                    " > To the foot of the KLCC.",
                    " > Summon me by grace to turn merits into strength.",
                    " > Till we meet again, Scholar."
            };


    //dialog linear and branching
    public void npcDialog() {
        log(dialog[dialogCount++], false);

    }

    //if dialog is not linear and branching
    /*
    private void initializeDialogOptions() {
        // Set up dialog options and their responses
        dialogOptions.put("1", " > Hello!");
        dialogOptions.put("2", " > How can I help you?");
        dialogOptions.put("3", " > Goodbye!");
        // Add more options as needed
    }

    // Method to handle user input and select dialog response
    public String selectDialogResponse(String userInput) {
        // Retrieve the response based on the user's selected dialog option
        return dialogOptions.getOrDefault(userInput, "Invalid option");
    }

    // Method to handle user input and select dialog response
    public void selectAndDisplayDialogResponse(String userInput) {
        // Retrieve the response based on the user's selected dialog option
        currentDialogResponse = selectDialogResponse(userInput);

        // Display the selected response to the user
        log(currentDialogResponse,false);
    }
     */

    /*
    public void enemyTurn(){
        isEnemyAttacking = !isEnemyAttacking;
        enemyAttack();
        player.resetDefense();
    }

     */

    /*
    public void playerAttack(){

        //take the attackers attack value - defenders defense value
        int maxAmount = Math.max(2, player.phyAttack() - enemy.phyDefense());
        int minAmount = maxAmount/2;

        //random number from min amount to max amount of possible damage
        int amount = rand.nextInt(maxAmount-minAmount)+minAmount;
        //amount = (int)(Math.random() * amount);

        enemy.modifyHp(-amount);
        log(" > You attack the " + enemy.name() + " for " + amount + " damage");
        log("");

        //call the notify method while passing the string
        //notify("You attack the '%s' for %d damage.", other.name(), amount);
        //other.notify("The '%s' attacks you for %d damage.", name, amount);

    }

     */

    /*
    public void playerDefend(){
        //take the players defend value
        int amount = Math.max(0, player.magDefense()/2);

        //random number from 1 to amount of possible defense
        amount = (int)(Math.random() * amount) + 1;

        player.modifyDefense(amount);
        log(" + You increase your armour by " + amount);
        log("");
    }

     */

    /*
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

     */

    /*
    public void enemyAttack(){
        int maxAmount = Math.max(2, enemy.phyAttack() - player.phyDefense());
        int minAmount = maxAmount/2;

        //random number from min amount to max amount of possible damage
        int amount = rand.nextInt(maxAmount-minAmount )+minAmount;
        player.modifyHp(-amount);

        log(" > The " + enemy.name() + " attack " + player.name() + " for " + amount + " damage");
        log("");
    }

     */

    private int lines;

    // Method to display attack log including history
    // Method to display attack log including history
    private void displayLog(AsciiPanel terminal) {
        terminal.write("[ LOG ]", 67 - 4, 3, AsciiPanel.yellow); // Header for the log
        terminal.write("-------", 67 - 4, 3 + 1, AsciiPanel.yellow);

        int logY = 6; // Starting Y position for displaying logs
        lines = logY;

        // Display log history
        for (String log : logHistory) {
            String[] parts = log.split("\\|");
            String message = parts[0];
            String speaker = parts[1];

            int xPosition = 46; // Initial X position

            // Wrap the message text
            List<String> wrappedLines = wrapText(message, 41); // Adjust the wrap length as needed

            // Display each wrapped line with the appropriate color
            for (String line : wrappedLines) {
                if (speaker.equals("player")) {
                    terminal.write(line, xPosition, logY, AsciiPanel.brightGreen); // Player's dialogue in green
                } else if (speaker.equals("npc")) {
                    terminal.write(line, xPosition, logY, AsciiPanel.white); // NPC's dialogue in white
                } else {
                    terminal.write(line, xPosition, logY, AsciiPanel.yellow); // Default color for other logs
                }
                logY++; // Move to the next line for the next log entry
                lines++;
            }
            logY++; // Extra space between logs
            lines++;
        }
    }

    private void log(String log, boolean isPlayerSpeaking) {
        //logHistory.add(log);
        logHistory.add(log + "|" + (isPlayerSpeaking ? "player" : "npc"));

        // Ensure the log history maintains a maximum size
        if (logHistory.size() > 0 && lines > 30+4 ) {//22
            logHistory.remove(0); // Remove the oldest log until it reaches the maximum size
        }

    }

    // Method to wrap text into multiple lines
    private List<String> wrapText(String text, int wrapLength) {
        List<String> lines = new ArrayList<>();
        while (text.length() > wrapLength) {
            int spaceIndex = text.lastIndexOf(" ", wrapLength);
            if (spaceIndex <= 0) {
                spaceIndex = wrapLength;
            }
            lines.add(text.substring(0, spaceIndex));
            text = text.substring(spaceIndex);
        }

        lines.add(text); // Add the remaining or last line
        return lines;
    }

}
