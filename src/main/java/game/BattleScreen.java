package game;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class BattleScreen implements Screen {
    private Creature player;
    private Creature enemy;


    private boolean isEnemyAttacking = false;//for turn based combat
    private int canHeal = 1; //heal 1 per game, 1 = true

    private int defenseCooldown = 0; // Track the remaining cooldown rounds for defense
    private boolean isPlayerDefending = false;
    private int spell1Cooldown = 0;
    private int spell2Cooldown = 0; // Track cooldown of spells
    private int spell3Cooldown = 0;
    private int spell1Uptime = -1;
    private int spell2Uptime = -1; // Track the uptime of special effects of spells
    private int spell3Uptime = -1;
    private int round = 0;
    private int randEncounter = (int) (Math.random()*4);
    private int randType = (int) (Math.random()*7);


    private List<String> logHistory; // List to store log history

    AsciiArtDisplayer enemyAscii = new AsciiArtDisplayer();

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
        String str = String.format("You"+ encounter[randEncounter] + "a" + type[randType] + enemy.name());
        terminal.write(str,xCenter - str.length()/2,1,AsciiPanel.white);
        terminal.write(">> " + enemy.name() + " <<",2,4,AsciiPanel.brightRed);
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
        str = "[2] Defend";
        terminal.write(str,x,y);
        terminal.write("<"+defenseCooldown+"/2>cd",58,y++,AsciiPanel.brightBlack);
        str = "[3] Heal";
        terminal.write(str,x,y);
        terminal.write("<"+canHeal+"/1>",58,y++,AsciiPanel.brightBlack);
        terminal.write("[4] Escape",x,y++);

        y = 38;
        x = 46+20;
        terminal.write("{ SPELL }",x,y++,AsciiPanel.brightBlack);
        terminal.write("---------",x,y++,AsciiPanel.brightBlack);
        terminal.write("",x,y++);
        terminal.write("[Q] "+player.spellList().get(0),x,y++);
        terminal.write("[W] "+player.spellList().get(1),x,y++);
        terminal.write("[E] "+player.spellList().get(2),x,y++);

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

            String str = String.format("%d",creature.hp());
            terminal.write(str,x,y,AsciiPanel.brightRed);
            terminal.write( "/", x+str.length(), y,AsciiPanel.white);
            terminal.write(""+creature.maxHp(), x+str.length()+1, y,AsciiPanel.brightRed);
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
            String str = String.format("%d",creature.mp());
            terminal.write(str,x,y,AsciiPanel.brightCyan);
            terminal.write( "/", x+str.length(), y,AsciiPanel.white);
            terminal.write(""+creature.maxMp(), x+str.length()+1, y,AsciiPanel.brightCyan);
        }
    }

    private String[] encounter =
            {
                    " encounter ",
                    " ambush ",
                    " engage with ",
                    " attack ",
                    " clashing with"
            };

    private String[] type =
            {
                    " wild ",
                    " feral ",
                    " mad ",
                    " dangerous ",
                    " big ",
                    " hostile ",
                    " rabid ",
                    " agile "
            };

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_1)
            playerAttack();
        else if (key.getKeyCode() == KeyEvent.VK_2) {
            if (defenseCooldown == 0) {
                isPlayerDefending = true;
                defenseCooldown = 3; // Set the cooldown to 3 rounds
                log(" > You defend! Damage received is reduced");
            } else {
                log(" > You can't defend yet. ( " + defenseCooldown +" more round )");
            }
            //playerDefend(); //increase defense value for 3 turn
        }
        else if (key.getKeyCode() == KeyEvent.VK_3) {
            if(canHeal==1)
                playerHeal();
            else {
                log(" > You can only heal once per game ");
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_4) {
            if(canPlayerRun()) {
                player.doAction("manage to run away..");
                return null;
            }
            else {
                log(" : Fail a saving throw!! Back is turn away from the enemy");
                enemyTurn();
                return this;
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_4)
            return null;
        else if (key.getKeyCode() == KeyEvent.VK_Q) {
            if (spell1Cooldown == 0) {
                castSpell(player.spellList().get(0));
            }
            else {
                log(" > You can't cast that yet. ( " + spell1Cooldown + " more round )");
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_W) {
            if (spell2Cooldown == 0)
                castSpell(player.spellList().get(1));
            else {
                log(" > You can't cast that yet. ( " + spell2Cooldown + " more round )");
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_E) {
            if (spell3Cooldown == 0)
                castSpell(player.spellList().get(2));
            else {
                log(" > You can't cast that yet. ( " + spell3Cooldown + " more round )");
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
            removeSpell1Effect(player,enemy);
            removeSpell2Effect(player,enemy);
            removeSpell3Effect(player,enemy);

            return null;
        }

        // Check for spell uptime, remove any at zero
        if (spell1Uptime == 0)
            removeSpell1Effect(player, enemy);
        if (spell2Uptime == 0)
            removeSpell2Effect(player, enemy);
        if (spell3Uptime == 0)
            removeSpell3Effect(player, enemy);

        if (defenseCooldown > 0) {
            defenseCooldown--;
        }

        if (spell1Cooldown > 0) {
            spell1Cooldown--;
        }
        if (spell2Cooldown > 0) {
            spell2Cooldown--;
        }
        if (spell3Cooldown > 0) {
            spell3Cooldown--;
        }

        if (spell1Uptime > 0) {
            spell1Uptime--;
        }
        if (spell2Uptime > 0) {
            spell2Uptime--;
        }
        if (spell3Uptime > 0) {
            spell3Uptime--;
        }


        // For example, returning null to exit the battle screen
        return this;
    }

    public void enemyTurn(){
        isEnemyAttacking = !isEnemyAttacking;
        enemyAttack();
    }

    public boolean canPlayerRun(){
        int player =(int) (Math.random() * 6)+1;
        log(" : Player roll a "+player+ " to escape");
        if(player == 4){
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
                amount = (int) (0.25*player.phyAttack());
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

        player.modifyPhyDefense(amount);
        player.modifyMagDefense(amount);
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
        canHeal = 0;
    }

    private void castSpell(String spellName) {
        switch(spellName) {
            case "Regal Roar":
                useSpell(new RegalRoar(), 1);
                break;
            case "Furious Strike":
                useSpell(new FuriousStrike() ,2);
                break;
            case "Great Phalanx":
                useSpell(new GreatPhalanx(),3);
                break;
        }
    }

    private void useSpell(Spell spell, int spellSlot) {
        // Cast the spell
        spell.cast(player,enemy);
        switch (spellSlot) {
            case 1:
                spell1Uptime = spell.uptime();
                spell1Cooldown = spell.cooldown();
                break;
            case 2:
                spell2Uptime = spell.uptime();
                spell2Cooldown = spell.cooldown();
                break;
            case 3:
                spell3Uptime = spell.uptime();
                spell3Cooldown = spell.cooldown();
                break;
        }
        log(spell.message);

        // Do the effects of spell
        if (spell.phyDamage >0) {
            enemy.modifyHp(-spell.phyDamage());
            System.out.println(spell.phyDamage());
            log(" > You hit the " + enemy.name() + " for " + spell.phyDamage() + " damage");
        }
        if (spell.phyDefend()>0) {
            player.modifyPhyDefense(spell.phyDefend());
            log(" + You increase your Armour by " + spell.phyDefend());
        }
        if (spell.magDefend()>0) {
            player.modifyMagDefense(spell.magDefend);
            log(" + You increase your Barrier by " + spell.magDefend());
        }

    }

    private void removeSpell1Effect(Creature player, Creature enemy) {
        switch(player.spellList().get(0)) {
            case "Regal Roar":
                player.resetPhyDefense();
                spell1Uptime = -1;
                break;
        }
    }

    private void removeSpell2Effect(Creature player, Creature enemy) {
        switch(player.spellList().get(1)) {

        }
    }

    private void removeSpell3Effect(Creature player, Creature enemy) {
        switch(player.spellList().get(2)) {
            case "Great Phalanx":
                player.resetPhyDefense();
                player.resetMagDefense();
                spell3Uptime = -1;
                break;
        }
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
        if (isPlayerDefending) {
            amount = (int) (0.85 * amount);
            isPlayerDefending = false;
        }
        player.modifyHp(-amount);

        log(" > The " + enemy.name() + " attack " + player.name() + " for " + amount + " damage");
    }



    private int lines;

    // Method to display attack log including history
    // Method to display attack log including history
    private void displayLog(AsciiPanel terminal) {
        terminal.write("[ LOG ]", 67 - 4, 3,AsciiPanel.brightBlack); // Header for attack log
        terminal.write("-------", 67 - 4, 3 + 1,AsciiPanel.brightBlack);

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
                    } else if (word.equalsIgnoreCase("barrier")) {
                        terminal.write(word, xPosition, logY, AsciiPanel.cyan);
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
        if (logHistory.size() > 0 && lines > 30+2 ) {//22
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