package asciipanel;

import asciiLib.AsciiPanel;

import java.awt.event.KeyEvent;

//interface is way to group a method together
public interface Screen {
    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);
}
