import asciiPanel.AsciiPanel;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AsciiArtDisplayer {
    private String name;


    // other attributes and methods...

    // Method to display ASCII art of the creature from a file using Files.readAllLines()
    public void displayAsciiArtFromFile(int x, int y, String filePath, boolean isAttacking, AsciiPanel terminal) {
        try {
            Path path = Paths.get(filePath);
            List<String> asciiArt = Files.readAllLines(path);

            boolean displayFrame = !isAttacking; // Initially set to display idle frame

            for (String line : asciiArt) {
                if (line.equals("[attack]")) {
                    displayFrame = isAttacking; // Change display to attacking frame if marker is found
                    continue; // Skip displaying the marker line
                }
                if (line.equals("[idle]")) {
                    displayFrame = !isAttacking; // Change display to idle frame if marker is found
                    continue; // Skip displaying the marker line
                }

                if (line.length() > 60) { // Limit the line length to fit the terminal width
                    line = line.substring(0, 60); // Trim the line if it's too long
                }

                if (displayFrame) {
                    terminal.write(line, x, y++);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the file reading exception as per your requirements
        }
    }

    public void displayAsciiArtFromFile(int x, int y, String filePath, AsciiPanel terminal) {
        try {
            Path path = Paths.get(filePath);
            List<String> asciiArt = Files.readAllLines(path);

            int maxWidth = terminal.getWidth(); // Get the terminal's width

            for (String line : asciiArt) {
                if (line.length() > maxWidth - 40) { // Limit the line length to fit the terminal width
                    line = line.substring(0, maxWidth - 40); // Trim the line if it's too long
                }
                terminal.write(line, x, y++);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the file reading exception as per your requirements
        }
    }

    public void displayAsciiArtFromFile(int x, int y, String filePath, Color color, AsciiPanel terminal) {
        try {
            Path path = Paths.get(filePath);
            List<String> asciiArt = Files.readAllLines(path);

            int maxWidth = terminal.getWidth(); // Get the terminal's width

            for (String line : asciiArt) {
                if (line.length() > maxWidth - 40) { // Limit the line length to fit the terminal width
                    line = line.substring(0, maxWidth - 40); // Trim the line if it's too long
                }
                terminal.write(line, x, y++,color);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the file reading exception as per your requirements
        }
    }
    /*
    public void displayAsciiArtFromFile(int x, int y, String filePath, AsciiPanel terminal) {
        try {
            Path path = Paths.get(filePath);
            List<String> asciiArt = Files.readAllLines(path);

            for (String line : asciiArt) {
                if (line.length() > 60) { // Limit the line length to fit the terminal width
                    line = line.substring(0, 60); // Trim the line if it's too long
                }
                terminal.write(line, x, y++);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the file reading exception as per your requirements
        }
    }

     */

}


