import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static int turn = 1;
    private static int numOfPlayersIn;
    private static boolean handEnd = true;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {

        System.out.print(" Welcome to pass and play poker! How may players will be playing today?");
        String strNumPlayers = sc.nextLine();
        int numPlayers = Integer.parseInt(strNumPlayers);
        Player[] playerList = new Player[numPlayers];
        for (int i = 0; i < playerList.length; i++) {

            System.out.println("What is the name of player" + (i + 1) + "?");
            String theName = sc.nextLine();

            playerList[i] = new Player(theName);
            numOfPlayersIn++;
        }
        while (playerList.length > 1) {

            System.out.println("Each player has 100 dollars in chips (There will be no blinds just an ante)");
            System.out.println("Your hand is now going to be dealed to you and you will pay the ante");
            numOfPlayersIn = playerList.length;
            while (numOfPlayersIn > 1) {
                handEnd = true;
                while (handEnd) {
                    for (int i = 0; i < playerList.length; i++) {
                        playerList[i].changeIsInHand();
                    }
                    Player.minBet = 0;
                    Deck.shuffleCards();
                    for (int i = 0; i < playerList.length; i++) {

                        playerList[i].dealHand();
                        playerList[i].ante();
                    }

                    for (int i = 0; i < playerList.length; i++) {
                        System.out
                                .println(playerList[i].getName() + ", are you ready to see your cards (press any key)");
                        sc.nextLine();
                        System.out.println(playerList[i].getName() + " your hand is: " + playerList[i].showHand());
                        playerList[i].playerBet();
                        System.out.print("press any key to clear");
                        sc.nextLine();
                        clearScreen();
                        if(!playerList[i].getIsInHand()){
                            numOfPlayersIn--;
                        }
                    }
                    if (numOfPlayersIn < 2) {
                        for (int i = 0; i < playerList.length; i++) {
                            if (playerList[i].getIsInHand()) {
                                playerList[i].collectPot();
                            }
                        }

                    }
                    
                }
            }
        }
    }

}