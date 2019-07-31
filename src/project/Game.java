/**
 * The class that models your game. You should create a more specific
 * child of this class and instantiate the methods given.
 *
 * @author megha,2019
 * @modifier Sehyun, 2019
 * @modifier Kowsiya, 2019
 * @modifier Yui, 2019
 * 
 */
package project;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class that models your game. You should create a more specific child of
 * this class and instantiate the methods given.
 *
 * @author megha,2019
 */
public class Game {

    private final String gameName;//the title of the game
    private ArrayList<Player> players;// the players of the game

    public Game(String givenName) {
        gameName = givenName;
    }

    /**
     * @return the gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @return the players of this game
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players of this game
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Play the game.
     */
    public void play() {

        //Ask user for name input to play the game
        System.out.println("============== War Game ==============");
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the name of the first player. : ");
        String user1 = input.nextLine();
        
        String user2 = null; 
        while(checkExistedName(user1, user2) == false){   
            System.out.print("Please enter the name of the second player. : ");
            user2 = input.nextLine();
            if(user1.equals(user2)){
                System.out.println("The name already existed. "
                        + "Please enter different name.");
            }else {
                break;
            }   
        }
        
        String answer= "";
        int limitNum = 0;
        
        while (true) {
            System.out.print("Do you want to place a maximum turn limit? (Y/N) : ");
            answer = input.nextLine().toLowerCase();

            if (answer.equals("y") || answer.equals("n")) {
                if (answer.equals("y")) {
                    System.out.print("How maximum turn do you want? : ");
                    limitNum = input.nextInt();
                    input.nextLine();
                    break;
                }break;
            }else{
                System.out.println("only (Y/N) is accepted.");
            }
        }
        // Sets the players
        Player p1 = new Player(user1);
        Player p2 = new Player(user2);
        ArrayList<Player> users = new ArrayList<>();
        users.add(p1);
        users.add(p2);
        setPlayers(users);

        // Card creation and shuffle
        GroupOfCards cards = new GroupOfCards();
        cards.shuffle();

        // Assign 26 cards to two users
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = (ArrayList<Card>) cards.getCards().clone();

        for (int i = 0; i < 26; i++) {
            cards1.add(cards2.get(i));
            cards2.remove(i);
        }

        p1.setCards(cards1);
        p2.setCards(cards2);

        // Start Game 
        int count = 1;
        while (true) {

            if (p1.getCardInfo().isEmpty() || p2.getCardInfo().isEmpty()) {
                System.out.printf("The final winner is %s!%n", checkFinalWinner(p1, p2));
                break;
            }

            System.out.println("-----------------------------------------");
            System.out.printf("%d   [ %s\'s card : %d / %s\'s card : %d ]%n",
                    count, user1, p1.getCardInfo().size(), user2, p2.getCardInfo().size());

            if (answer.equals("y") && limitNum <= count) {
                String winner = "";

                if (p1.getCardInfo().size() > p2.getCardInfo().size()) {
                    winner = user1;
                } else if (p1.getCardInfo().size() < p2.getCardInfo().size()) {
                    winner = user2;
                } else {
                    winner = "Both";
                }

                System.out.printf("The final winner is %s!\n", winner);
                break;
            }

            ArrayList<Card> tmp = new ArrayList<>();

            boolean repeat = true;
            char thisTimeWinner = '1';
            do {
                Card c1 = p1.getOneCard();
                Card c2 = p2.getOneCard();
                tmp.add(c1);
                tmp.add(c2);
                String format = "    %s\'s card %s / %s\'s card %s%n";
                System.out.printf(format, user1, c1, user2, c2);
                if (c1.getNum() == c2.getNum() && p1.getCardInfo().size() > 2 && p2.getCardInfo().size() > 2) {
                    System.out.println("    next card overlay");
                    c1 = p1.getOneCard();
                    c2 = p2.getOneCard();
                    tmp.add(c1);
                    tmp.add(c2);
                } else {
                    repeat = false;
                    thisTimeWinner = (c1.getNum() > c2.getNum()) ? '1' : '2';
                }
            } while (repeat);

            String turnWinner = "";
            if (thisTimeWinner == '1') {
                turnWinner = user1;
                p1.addCards(tmp);
            } else {
                turnWinner = user2;
                p2.addCards(tmp);
            }

            System.out.printf("    In this turn, %s won. (get %d cards)%n", turnWinner, tmp.size());
            System.out.println("Next Turn? Press Enter!");
            input.nextLine();
            count++;
        }

    }

    /**
     * Check final winner.
     */
    public String checkFinalWinner(Player p1, Player p2) {
        return p1.getCardInfo().isEmpty() ? p2.getPlayerName() : p1.getPlayerName();
    }
    
    public static boolean checkExistedName(String user1, String user2){
        if(user1.equalsIgnoreCase(user2)){
            return true;
        }else {
            return false;
        }
    }
}
