package game;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Choice {
    public int getChoice() {
        Scanner in = new Scanner(System.in);
        int choice = -1;

        do {
            try {
                choice = in.nextInt();
                break;
            } catch (InputMismatchException ignored){
            } finally {
                in.nextLine();
            }
        }while(true);

        return choice;
    }
}
