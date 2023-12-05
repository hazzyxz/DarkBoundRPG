package asciipanel;
import asciiLib.AsciiPanel;


import java.awt.event.KeyEvent;

public class StartScreen implements Screen {
//implements(kinda like inherited) access the Screen group and give them function
    private World world;
    private boolean haveClass = false;

    //Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("DarkBoundRPG", 1);
        terminal.writeCenter("-- press any key to start --", 16);

    }

    public Screen respondToUserInput(KeyEvent key) {
        //initiate class screen
        return new ChooseClassScreen();//enter on any key
    }
}
