package game;

import java.io.*;

public class ArchetypeLoader {
    public static String[] loadArchetype(int choice) {
        String[] statsBuffer = new String[14];
        InputStream file = ArchetypeLoader.class.getResourceAsStream("/archetype");

        if (file != null) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(file))) {
                for(int i=0;i<choice;i++)
                    in.readLine();

                String str = in.readLine();
                statsBuffer = str.split(",");
            }
            catch (IOException e) {
                System.err.println("File not found");
            }
        }
        /*
        //Try to read "ID"th line of Archetype.txt and set it to statsBuffer
        try {
            FileReader file = new FileReader("src/main/resources/archetype");
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

         */
        return statsBuffer;
    }
}
