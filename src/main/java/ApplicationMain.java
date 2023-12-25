//without adding z-depth but trying to add experimental stuff, ie region, combat screen, etc.
//incoporating advance battlescreen
/*
    to add new creature:-
    1. create new ai for creature
    2. create creature constructor in creatureFactory
    3. update createCreature in playscreen
    4. change CreatureAi placeholder to fit need for creature behaviour using override
*/

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ApplicationMain extends JFrame implements KeyListener {
    //class that extends jframe to create window for the game
    private static final long serialVersionUID = 1060623638149583738L;

    private AsciiPanel terminal;
    private Screen screen;

    public ApplicationMain(){
        super();
        terminal = new AsciiPanel(90,50, AsciiFont.CP437_9x16);
        add(terminal);
        pack();
        // automatically set the size of the window to accommodate the terminal/contents

        screen = new StartScreen();
        addKeyListener(this);
        // allows it to listen for keyboard input

        repaint();
        //method is overridden to update the display
    }

    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
        //clears the terminal, displays the content of the current screen,
        //and then calls super.repaint() to repaint the frame
    }


    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
        //When a key is pressed, it updates the current screen in response to user input
        //and then triggers a repaint (update)
    }

    public void keyReleased(KeyEvent e) {
        //null
    }

    public void keyTyped(KeyEvent e) {
        //null
    }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

}
