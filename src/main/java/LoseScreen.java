import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {
    public void displayOutput(AsciiPanel terminal) {
        AsciiArtDisplayer asciiDisplay = new AsciiArtDisplayer();

        asciiDisplay.displayAsciiArtFromFile(11,3,"src/main/java/asciiArt/youDied.txt",AsciiPanel.brightRed,terminal);
        asciiDisplay.displayAsciiArtFromFile(13,30,"src/main/java/asciiArt/rose.txt",AsciiPanel.brightBlack,terminal);

        terminal.writeCenter("-- press [Space] to return --", 22);

    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_SPACE ? new StartScreen() : this;
    }


}
