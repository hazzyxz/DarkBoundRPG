package asciipanel;
import asciiLib.AsciiPanel;

import asciiLib.AsciiPanel;

import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You lost.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {

        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }


}
