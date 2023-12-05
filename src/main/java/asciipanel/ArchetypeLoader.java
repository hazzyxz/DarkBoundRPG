package asciipanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArchetypeLoader {
    public static String[] loadArchetype(int choice) {
        String[] statsBuffer = new String[0];

        //Try to read "ID"th line of Archetype.txt and set it to statsBuffer
        try {
            FileReader file = new FileReader("C:/Users/Ahmad Taufiq/Desktop/archetypes.txt");
            BufferedReader in = new BufferedReader(file);

            //  Number of lines skipped depends on classID
            //  i.e. Warrior ID is 1, so 1 line is skipped
            for(int i=0;i<choice;i++)
                in.readLine();

            String str = in.readLine();
            statsBuffer = str.split(",");
            in.close();
        }catch(IOException e) {
            //  System.out.println("File Read Error");
        }
        return statsBuffer;
    }
}
