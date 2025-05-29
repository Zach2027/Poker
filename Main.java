import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        
        System.out.print(" Welcome to pass and play poker! How may players will be playing today?");
        String numPlayers = sc.nextLine();
            
        for (int i = 0; i < numPlayers; i++){




        }   
            
    }
}