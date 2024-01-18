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

    private int defenseCooldown = 0; // Track the remaining cooldown rounds for defense
    protected static boolean isPlayerDefending = false;
    private int round = 1;
    private int randEncounter = (int) (Math.random()*4);
    private int randType = (int) (Math.random()*7);


    private static List<String> logHistory; // List to store log history

    AsciiArtDisplayer enemyAscii = new AsciiArtDisplayer();

    public BattleScreen(Creature player, Creature enemy) {

        logHistory = new ArrayList<>();

        this.player = player;
        this.enemy = enemy;

        log(" [ROUND " + round + "]");
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

        //Ascii Art
        // x=43, y=24 character
        enemyAscii.displayAsciiArtFromFile(1,10, enemy.asciiPath(), isEnemyAttacking, terminal);

        //content profile
        String str = String.format("You"+ encounter[randEncounter] + "a" + type[randType] + enemy.name());
        terminal.write(str,xCenter - str.length()/2,1,AsciiPanel.white);
        terminal.write(">> " + enemy.name() + " <<",2,4,AsciiPanel.brightRed);
        boolean isPlayer = false;
        displayHealth(enemy,2,6, isPlayer,terminal);
        displayMana(enemy,2,8,isPlayer,terminal);

        // Status effects for enemy
        if (enemy.statusEffect(0)) {
            terminal.write("[PSN]",2,7,AsciiPanel.green);
        }
        if (enemy.statusEffect(1)) {
            terminal.write("[STN]",8,7,AsciiPanel.brightWhite);
        }
        if (enemy.statusEffect(2)) {
            terminal.write("[SIL]",14,7,AsciiPanel.brightBlack);
        }
        if (enemy.statusEffect(3)) {
            terminal.write("[BLN]",20,7,AsciiPanel.blue);
        }

        terminal.write(">> " + player.name() + " <<",2,38,AsciiPanel.brightGreen);
        isPlayer = true;
        displayHealth(player,2,40, isPlayer, terminal);
        displayMana(player,2,42,isPlayer,terminal);


        //action
        int y = 38;
        int x = 46;
        terminal.write("< ACTION >",x,y++,AsciiPanel.brightBlack);
        terminal.write("----------",x,y++,AsciiPanel.brightBlack);
        terminal.write("",x,y++);
        terminal.write("[1] Attack",x,y++);
        str = "[2] Defend";
        terminal.write(str,x,y);
        terminal.write("<"+defenseCooldown+"/2>cd",x+11,y++,AsciiPanel.brightBlack);
        //str = "[3] Heal";
        terminal.write("[3] Heal",x,y);
        terminal.write("<"+player.hpFlask()+"/"+player.maxHpFlask()+">use",x+11,y++,AsciiPanel.brightBlack);
        terminal.write("[4] Focus",x,y);
        terminal.write("<"+player.mpFlask()+"/"+player.maxMpFlask()+">use",x+11,y++,AsciiPanel.brightBlack);
        terminal.write("",x,y++);
        terminal.write("[5] Escape",x,y++);

        y = 38;
        x = 46+21;
        terminal.write("{ SPELL }",x,y++,AsciiPanel.brightBlack);
        terminal.write("---------",x,y++,AsciiPanel.brightBlack);
        terminal.write("",x,y++);
        terminal.write("[Q] "+player.spellList().get(0),x,y++);
        terminal.write("<"+player.spell1Cooldown()+"/2>cd <20>mp",x,y++,AsciiPanel.brightBlack);
        terminal.write("[W] "+player.spellList().get(1),x,y++);
        terminal.write("<"+player.spell2Cooldown()+"/4>cd <70>mp",x,y++,AsciiPanel.brightBlack);
        terminal.write("[E] "+player.spellList().get(2),x,y++);
        terminal.write("<"+player.spell3Cooldown()+"/6>cd <120>mp",x,y++,AsciiPanel.brightBlack);

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
        // Check for poison effect
        if (enemy.statusEffect(0)) {
            enemy.modifyHp(-(int)(0.065*enemy.maxHp()));
            log(" > The "+enemy.name()+" took poison damage!");
        }

        // Check for spell uptime, remove any at zero
        checkSpellUptime(player,enemy);

        if (key.getKeyCode() == KeyEvent.VK_1)
            playerAttack();
        else if (key.getKeyCode() == KeyEvent.VK_2) {
            playerDefend(); //increase defense value for 3 turn
        }
        else if (key.getKeyCode() == KeyEvent.VK_3) {
            playerHeal();
        }
        else if (key.getKeyCode() == KeyEvent.VK_4) {
            playerRecoverMp();
        }
        else if (key.getKeyCode() == KeyEvent.VK_5) {
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

        else if (key.getKeyCode() == KeyEvent.VK_Q) {
            if (player.spellList().get(0).equals("Locked")){
                log(" > You can't cast that yet.");
            }
            if (player.spell1Cooldown() == 0) {
                castSpell(player.spellList().get(0), player, enemy);
            }
            else {
                log(" > You can't cast that yet. ( " + player.spell1Cooldown() + " more round )");
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_W) {
            if (player.spellList().get(1).equals("Locked")){
                log(" > You can't cast that yet.");
            }
            if (player.spell2Cooldown() == 0  )
                castSpell(player.spellList().get(1),player,enemy);
            else {
                log(" > You can't cast that yet. ( " + player.spell2Cooldown() + " more round )");
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_E) {
            if (player.spellList().get(2).equals("Locked")){
                log(" > You can't cast that yet.");
            }
            if (player.spell3Cooldown() == 0)
                castSpell(player.spellList().get(2),player,enemy);
            else {
                log(" > You can't cast that yet. ( " + player.spell3Cooldown() + " more round )");
            }
        }
        else
            return this;


        if(enemy.isDead()) {
            enemy.doAction("fell to the ground");
            enemy.leaveCorpse();
            enemy.resetAll();
            player.gainXp(enemy);
            round = 1;

            // Remove all effects on the player
            removeSpellEffect(player.spellList().get(0), player, enemy);
            removeSpellEffect(player.spellList().get(1), player, enemy);
            removeSpellEffect(player.spellList().get(2), player, enemy);

            // Reset all spell cooldown
            player.setSpell1Cooldown(0);
            player.setSpell2Cooldown(0);
            player.setSpell3Cooldown(0);

            if (enemy.glyph()=='B'){
                return new WinScreen();
            }

            return null;
        }

        // Check for stun & blind
        if (enemy.statusEffect(3)) {
            // check for blind - only 10% chance to hit
            int chance = (int) (Math.random()*10) + 1;
            if (chance == 1) {
                enemyTurn();
            } else {
                log(" > The " + enemy.name() + " cannot see anything!");
            }
        }
        else if (enemy.statusEffect(1)) {
            log(" > The " + enemy.name() + " is stunned!");
        }
        else
            enemyTurn();

        if (defenseCooldown > 0) {
            defenseCooldown--;
        }

        if (player.spell1Cooldown() > 0) {
            player.modifySpell1Cooldown(-1);
        }
        if (player.spell2Cooldown() > 0) {
            player.modifySpell2Cooldown(-1);
        }
        if (player.spell3Cooldown() > 0) {
            player.modifySpell3Cooldown(-1);
        }

        if (player.spell1Uptime() > 0) {
            player.modifySpell1Uptime(-1);
        }
        if (player.spell2Uptime() > 0) {
            player.modifySpell2Uptime(-1);
        }
        if (player.spell3Uptime() > 0) {
            player.modifySpell3Uptime(-1);
        }

        round++;
        log(" [ROUND "+round+"]");
        // For example, returning null to exit the battle screen
        return this;
    }

    public void enemyTurn(){
        isEnemyAttacking = !isEnemyAttacking;
        enemy.creatureTurn(player);
        //enemyAttack();
    }

    public void playerAttack(){
        int amount = 0;

        switch (player.name()){
            case "Warrior":
            case "Rogue":
            case "Archer":
                amount = (int) (0.25*player.phyAttack());
                amount -= enemy.phyDefense();
                break;
            case "Paladin":
            case "Mage":
                amount = (int) (0.25 * player.magAttack());
                amount -= enemy.magDefense();
                break;
        }

        amount = Math.max(amount, 5);
        enemy.modifyHp(-amount);


        log(" > You attack the " + enemy.name() + " for " + amount + " damage");

        //call the notify method while passing the string
        //notify("You attack the '%s' for %d damage.", other.name(), amount);
        //other.notify("The '%s' attacks you for %d damage.", name, amount);



    }

    public void playerDefend(){
        if (defenseCooldown == 0) {
            isPlayerDefending = true;
            defenseCooldown = 3; // Set the cooldown to 3 rounds
            log(" > You defend! Damage received is reduced");
        } else {
            log(" > You can't defend yet. ( " + defenseCooldown +" more round )");
        }

        /*
        //take the players 35% of defend value
        int amount =(int) (Math.max(0, (player.magDefense()+player.phyDefense())*0.35/2));

        //random number from 1 to amount of possible value
        amount = (int)(Math.random() * amount) + 1;

        player.modifyPhyDefense(amount);
        player.modifyMagDefense(amount);
        log(" + You increase your Armour and Barrier by " + amount);

         */
    }

    public void playerHeal(){
        if(player.hpFlask()>0) {
            //take the players remaining hp
            int amount = (int) (0.25 * player.maxHp());

            if (player.hp() + amount > player.maxHp())
                amount = player.maxHp() - player.hp();

            player.modifyHp(amount);
            log(" + You increase your health for " + amount + " Hp");
            player.modifyHpFlask(-1);
        }
        else {
            log(" > Out of charges ");
        }

        /*
        //take the players remaining hp
        int amount = (int) (0.15 * player.hp());

        //random number from 1 to amount of possible lost hp
        //amount = (int)(Math.random() * amount) + 1;
        if(player.hp()+amount>player.maxHp())
            amount = player.maxHp()- player.hp();

        player.modifyHp(amount);
        log(" + You increase your health for " + amount + " Hp");
        canHeal = 0;
         */
    }
    public void playerRecoverMp() {
        if (player.mpFlask() > 0) {
            //take the players remaining hp
            int amount = (int) (0.25 * player.maxMp());

            if (player.mp() + amount > player.maxMp())
                amount = player.maxMp() - player.mp();

            player.modifyMp(amount);
            log(" + You increase your mana for " + amount + " mp");
            player.modifyMpFlask(-1);
        } else {
            log(" > Out of charges ");
        }
    }

    public boolean canPlayerRun(){
        int player =(int) (Math.random() * 6)+1;
        log(" : Player roll a "+player+ " to escape");
        if(player >= 5){
            return true;
        }
        else{
            return false;
        }

    }

    private void castSpell(String spellName, Creature creature, Creature other) {
        switch(spellName) {
            case "Regal Roar":
                useSpell(new RegalRoar(), 1, creature, other);
                break;
            case "Furious Strike":
                useSpell(new FuriousStrike() ,2, creature, other);
                break;
            case "Great Phalanx":
                useSpell(new GreatPhalanx(),3, creature, other);
                break;

            case "Magic Shield":
                useSpell(new MagicShield(),1, creature, other);
                break;
            case "Fireball":
                useSpell(new Fireball(), 2, creature, other);
                break;
            case "Flow of Void":
                useSpell(new FlowOfVoid(),3, creature, other);
                break;

            case "Silent Domain":
                useSpell(new SilentDomain(),1, creature, other);
                break;
            case "Backstab":
                useSpell(new Backstab(),2, creature, other);
                break;
            case "Dark Daggers":
                useSpell(new DarkDaggers(),3, creature, other);
                break;

            case "Divine Shield":
                useSpell(new DivineShield(),1, creature, other);
                break;
            case "Dual Smite":
                useSpell(new DualSmite(),2, creature, other);
                break;
            case "Ancient Blessing":
                useSpell(new AncientBlessing(), 3, creature, other);
                break;

            case "Sand Throw":
                useSpell(new SandThrow(),1,creature,other);
                break;
            case "Coated Arrows":
                useSpell(new CoatedArrows(),2,creature,other);
                break;
            case "Artemis Arrow":
                useSpell(new ArtemisArrow(),3,creature,other);
                break;
        }
    }

    public void useSpell(Spell spell, int spellSlot, Creature creature, Creature other) {
        // Cast the spell
        spell.cast(creature,other);

        switch (spellSlot) {
            case 1:
                creature.setSpell1Uptime(spell.uptime);
                creature.setSpell1Cooldown(spell.cooldown());
                break;
            case 2:
                creature.setSpell2Uptime(spell.uptime);
                creature.setSpell2Cooldown(spell.cooldown());
                break;
            case 3:
                creature.setSpell3Uptime(spell.uptime);
                creature.setSpell3Cooldown(spell.cooldown());
                break;
        }


        for (String line : spell.message()) {
            log(line);
        }
    }

    private void checkSpellUptime(Creature creature, Creature other) {
        if (!creature.spellList().isEmpty()) {
            if (creature.spell1Uptime() == 0) {
                removeSpellEffect(creature.spellList().get(0), creature, other);
            }
            if (creature.spell2Uptime() == 0) {
                removeSpellEffect(creature.spellList().get(1), creature, other);
            }
            if (creature.spell3Uptime() == 0) {
                removeSpellEffect(creature.spellList().get(2), creature, other);
            }
        }
    }

    private void removeSpellEffect(String spellName, Creature creature, Creature other) {
        switch(spellName) {
            case "Regal Roar":
                removeEffect(new RegalRoar(), creature, other);
                creature.setSpell1Uptime(-1);
                break;
            case "Great Phalanx":
                removeEffect(new GreatPhalanx(), creature, other);
                creature.setSpell3Uptime(-1);
                break;

            case "Magic Shield":
                removeEffect(new MagicShield(), creature, other);
                creature.setSpell1Uptime(-1);
                break;
            case "Flow of Void":
                removeEffect(new FlowOfVoid(), creature, other);
                creature.setSpell3Uptime(-1);
                break;

            case "Silent Domain":
                removeEffect(new SilentDomain(), creature, other);
                creature.setSpell1Uptime(-1);
                break;
            case "Backstab":
                removeEffect(new Backstab(), creature, other);
                creature.setSpell2Uptime(-1);
                break;
            case "Dark Daggers":
                removeEffect(new DarkDaggers(), creature, other);
                creature.setSpell3Uptime(-1);
                break;

            case "Divine Shield":
                removeEffect(new DivineShield(), creature, other);
                creature.setSpell1Uptime(-1);
                break;

            case "Sand Throw":
                removeEffect(new SandThrow(), creature, other);
                creature.setSpell1Uptime(-1);
                break;
            case "Coated Arrows":
                removeEffect(new CoatedArrows(), creature, other);
                creature.setSpell1Uptime(-1);
                break;
        }
    }


    private void removeEffect(Spell spell, Creature creature, Creature other) {
        spell.removeEffect(creature,other);
    }

    public void enemyAttack(){

        /*
        int amount = enemy.attack(player);

        if (isPlayerDefending) {
            amount = (int) (0.8 * amount);
            isPlayerDefending = false;
        }

        player.modifyHp(-amount);

        log(" > The " + enemy.name() + " attack " + player.name() + " for " + amount + " damage");
         */
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



    private static int lines;

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
                        //logY++; // Move to the next line
                        lines++;
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


    protected static void log(String log) {
        //logHistory.add(log);
        logHistory.add(log);

        // Ensure the log history maintains a maximum size
        if (logHistory.size() > 0 && lines > 29 ) {//22

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
