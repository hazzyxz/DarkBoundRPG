package asciipanel;

import asciiLib.AsciiPanel;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class ChooseClassScreen implements Screen {
    private Screen returnScreen;
    private Creature player;
    private int xCenter = 45;

    public ChooseClassScreen() {
        //this.player = player;
    }

    // length 89(x)
    // height 50(y)
    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Display the class selection options and instructions
        // Example:
        terminal.clear();

        //15,20,20,20,15
        String caption = "Choose Your Starting Class O' wise Hero:";

        terminal.write(caption,xCenter-caption.length()/2 ,2);
        displayBox(15,5,terminal);
        terminal.write("WARRIOR",15 ,6);
        displayBox(55,5,terminal);
        terminal.write("MAGE",15 ,6);
        displayBox(15,20,terminal);
        terminal.write("ROGUE",15 ,6);
        displayBox(55,20,terminal);
        terminal.write("PALADIN",15 ,6);
        displayBox(xCenter-20/2,35,terminal);

        /*
        int y = 1;
        terminal.write("Choose Your Class:",xCenter, y++);
        terminal.write("[1] Warrior",xCenter, y++);
        terminal.write("[2] Mage",xCenter, y++);
        terminal.write("[3] Rogue",xCenter, y++);
        terminal.write("[4] Paladin",xCenter, y++);
        terminal.write("[5] Archer",xCenter, y++);
        terminal.write("",xCenter, y++);
        terminal.write("Select a class",xCenter, y++);
         */


    }

    private void displayBox(int x, int y,AsciiPanel terminal){
        int width = 20;
        int height = 10;
        for(int i=0; i<height; i++)
            terminal.write('|',x,y+i);
        for(int i=1; i<=width; i++)
            terminal.write('_',x+i,y-1+height);
        for(int i=1; i<width; i++)
            terminal.write('_',x+i,y-1);
        for(int i=0; i<height; i++)
            terminal.write('|',x+width,y+i);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        //Scanner in = new Scanner(System.in);
        //Choice choice = new Choice();

        int keyCode = key.getKeyCode();
        int choice;
        if (keyCode == KeyEvent.VK_1) {
            // Set the player class to Warrior and return to the previous screen
            choice = 1;
        } else if (keyCode == KeyEvent.VK_2) {
            // Set the player class to Mage and return to the previous screen
            choice = 2;
        } else if (keyCode == KeyEvent.VK_3) {
            // Set the player class to rogue and return to the previous screen
            choice = 3;
        } else if (keyCode == KeyEvent.VK_4) {
            // Set the player class to archer and return to the previous screen
            choice = 4;
        } else if (keyCode == KeyEvent.VK_5) {
            // Set the player class to paladin and return to the previous screen
            choice = 5;
        }
        else{
            return this;
        }

        Archetype playerCharacter = Archetype.createCharacter(choice);
        playerCharacter.printInfo();

        //inititate playscreen (world)
        return new PlayScreen();


    }

}

