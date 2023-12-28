import asciiPanel.AsciiPanel;


import java.awt.event.KeyEvent;

public class StartScreen implements Screen {
//implements(kinda like inherited) access the Screen group and give them function
    private World world;
    // private boolean haveClass = false;
    public static boolean cont;

    //Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("DarkBoundRPG", 1);
        terminal.writeCenter("-- press any key to start --", 16);

    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            // Press 1 to create new game
            case KeyEvent.VK_1:
                cont = false;
                return new ChooseClassScreen();

            // Press 2 to continue from Save File
            case KeyEvent.VK_2:
                cont = true;
                return new PlayScreen();
        }

        //initiate class screen
        return this;//enter on any key
    }
}
