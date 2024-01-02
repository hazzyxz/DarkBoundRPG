package game;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class StartScreen implements Screen {
//implements(kinda like inherited) access the Screen group and give them function
    private World world;
    // private boolean haveClass = false;
    public static boolean cont;


    //Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
    public void displayOutput(AsciiPanel terminal) {
        AsciiArtDisplayer asciiDisplay = new AsciiArtDisplayer();
        //terminal.write("DarkBoundRPG",1, 3);

        int y =20;
        terminal.writeCenter("[1] Start", y++);
        terminal.writeCenter(" ", y++);
        terminal.writeCenter("    [2] Continue ", y++);
        terminal.writeCenter(" ", y++);
        terminal.writeCenter("[3] Quit ", y++);
        asciiDisplay.displayAsciiArtFromFile(7,5,"/asciiArt/title.txt",AsciiPanel.brightWhite,terminal);
        asciiDisplay.displayAsciiArtFromFile(0,33,"/asciiArt/klcc.txt",AsciiPanel.brightBlack, terminal);
        terminal.write("DarkBoundRPGtm/c2023 FromHardware,Inc.",1,49,AsciiPanel.white);//28 center
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

            case KeyEvent.VK_3:
                System.exit(0);

        }

        //initiate class screen
        return this;//enter on any key
    }


}
