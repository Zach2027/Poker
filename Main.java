import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        
        System.out.print(" Welcome to pass and play poker! How may players will be playing today?");
        String strNumPlayers =  sc.nextLine();
        int numPlayers = strNumPlayers.length();
        Player [] playerList = new Player[numPlayers];
        for (int i = 1; i <= numPlayers; i++){
            
            System.out.println("What is the name of player" + i + "?");
            String theName = sc.nextLine();

            playerList[i] = new Player(theName);

        } 

        for (int i = 0; i < playerList.length; i++){




        }
            
    }
}