package game;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WinScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        AsciiArtDisplayer asciiDisplay = new AsciiArtDisplayer();

        int x = 23;
        int y = 5;
        asciiDisplay.displayAsciiArtFromFile(0,0,"/asciiArt/youWin.txt", Color.orange,terminal);
        terminal.writeCenter("                                                                   ",y);
        terminal.write("Director               Ahmad Taufiq"   , x, y++, AsciiPanel.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.writeCenter("                                                                   ",y);
        terminal.write("Co-Director            Muhamad Hazim"   , x, y++, AsciiPanel.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.writeCenter("                                                                   ",y);
        terminal.write("Database               Muhamad Hazim"   , x, y++, AsciiPanel.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.writeCenter("                                                                   ",y);
        terminal.write("Character Design       Shahir Izzat"   , x, y++, AsciiPanel.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.writeCenter("                                                                   ",y);
        terminal.write("UI Designer            Ahmad Taufiq"   , x, y++, AsciiPanel.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.write(""   , x, y++, Color.white);
        terminal.writeCenter("                                                                   ",y);
        terminal.write("Game Design            Sidiq Musleh"   , x, y++, AsciiPanel.white);
        //asciiDisplay.displayAsciiArtFromFile(13,30,"/asciiArt/rose.txt",AsciiPanel.brightBlack,terminal);

        terminal.writeCenter("                                                                   ",y+15);
        terminal.write("-- press [Space] to start anew --",x,y+15,AsciiPanel.brightWhite);

    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_SPACE ? new StartScreen() : this;
    }
}
