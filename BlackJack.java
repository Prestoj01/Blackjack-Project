/*
Names: Preston Johnson and Luke Schultz
Lab 8: Blackjack!
Section 005
*/
import java.util.*;

public class BlackJack {
    //deals 2 random cards
    public static ArrayList<String> deal(Random random, ArrayList<String> deck) {
        ArrayList<String> hand = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(deck.size());
            String card = deck.get(index);
            hand.add(card);
            deck.remove(index);
        }
        return hand;
    }

    //deals 1 random card
    public static void hit(Random random, ArrayList<String> deck, ArrayList<String> hand) {
        int index = random.nextInt(deck.size());
        String card = deck.get(index);
        hand.add(card);
        deck.remove(index);
    }


    public static boolean checkBusted(ArrayList<String> hand) {
        //checks if the total is over 21
        return (getTotal(hand) > 21);
    }

    //Gets total value of hand
    //Checks for number of aces and lowers total accordingly
    public static int getTotal(ArrayList<String> hand) {
        int total = 0;
        int numAces = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).charAt(0) == 'A') {
                numAces++;
            }
        }
        for (int i = 0; i < hand.size(); i++) {
            total = checkForNumbers(hand.get(i), hand, total);
        }
        while (total > 21 && numAces > 0) {
            total -= 10;
            numAces--;
        }
        return total;
    }

    //Gets the value of each card
    public static int checkForNumbers(String card, ArrayList<String> hand, int handTotal) {
        if (Character.isDigit(card.charAt(0))) {
            if (card.charAt(0) == '1') {
                handTotal += 10;
            } 
            else {
                handTotal += Integer.parseInt(card.charAt(0) + "");
            }
        } 
        else if (card.charAt(0) == 'K' || card.charAt(0) == 'Q' || card.charAt(0) == 'J') {
            handTotal += 10;
        } 
        else if (card.charAt(0) == 'A') {
           handTotal += 11;
        }
        return handTotal;
    } 

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        ArrayList<String> deck = new ArrayList<String>(Arrays.asList(
            "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH",
            "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD",
            "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC",
            "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS"
        ));

        System.out.println("Give me a seed.");
        long seed = Long.parseLong(scnr.nextLine());
        Random random = new Random(seed);

        System.out.println("How many humans would you like to play with?");
        int amountPlayers = Integer.parseInt(scnr.nextLine());

        ArrayList<ArrayList<String>> arr = new ArrayList <ArrayList<String>>();

        //deals all cards using deal method
        for (int i = 0; i < amountPlayers; i++) {
            ArrayList<String> hand = deal(random, deck);
            arr.add(hand);
            System.out.println("Player " + i + "'s cards:");
            System.out.println(hand);
            System.out.println("Acknowledge that you have seen your cards player " + i + " by entering any key.");
            scnr.nextLine();
        }

        System.out.println("Now that everybody knows their cards, let's play!");

        //goes through hit or stick feature for every player
        for (int i = 0; i < amountPlayers; i++) {
            System.out.println("Player " + i + "'s cards:");
            System.out.println(arr.get(i));
            boolean done = false;
            boolean busted = false;
            while(!done) {
                System.out.println("Player " + i + " would you like to hit or stick?");
                String hitOrS = scnr.nextLine();
                if (hitOrS.equalsIgnoreCase("Hit")) {
                    hit(random, deck, arr.get(i));
                    System.out.print("Player " + i + "'s cards:");
                    System.out.println(arr.get(i));
                    busted = checkBusted(arr.get(i));
                    if (busted) {
                        System.out.println("Player " + i + " you have busted. Enter any key to acknowledge this.");
                        scnr.nextLine();
                        break;
                    }
                }
                else {
                    done = true;
                }
            }
            busted = false;
        }

        //uses the checkBusted method to see what players have busted
        for (int i = 0; i < amountPlayers; i++) {
            boolean didBust = checkBusted(arr.get(i));
            if (didBust) {
                System.out.println("Player " + i + " has busted.");
            }
        }

        int maxScore = 0;
        int maxScorer = -1;
        int otherWinner = 0;
        boolean tie = false;
        boolean winner = false;
        //determines the highest score and player
        List<Integer> maxScorePlayers = new ArrayList<Integer>();
        for (int i = 0; i < amountPlayers; i++) {
            int score = getTotal(arr.get(i));
            if (score > maxScore) {
                if (score <= 21) {
                    maxScore = score;
                    winner = true;
                    maxScorePlayers.clear();
                    maxScorePlayers.add(i);
                }
            }
            else if (score == maxScore) {
                tie = true;
                winner = true;
                maxScorePlayers.add(i);
            }
        }

        //prints the results of game depending on tie or not
        if (tie) {
            System.out.println("Players " + maxScorePlayers.get(0) + " and " + maxScorePlayers.get(1) + " tied for the highest score of " + maxScore);
        }
        else {
            if (winner == false) {
                System.out.println("Nobody won.");
            }
            else {
                System.out.println("Player " + maxScorePlayers.get(0) + " got the highest score of " + maxScore + ".");
            }
        }
    }
}