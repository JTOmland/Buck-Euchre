/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class GameTest {

    int[] scores = new int[5];//0-3 scores for east, south, west, north and 4 is reserved for cardNumber 
    boolean trickWon = false;
    String trick = "";
    int ply = 0;
    int nodes = 0;
    int moveNumber = 0;
    Trick currentTrick;
    int lastPlayerNumber = -1;
    int startingNode = 0;
    int cardScoreLocation = 0;
    boolean isFirstRoot = true;
    int totalCardsPlayed = 0;
    int terminalCount = 0;
    private boolean firstTimeThru = true;
    //  private int lastPlayerWithChoice = 0;
    private Stack lastChoiceStack = new Stack();
    ArrayList<Card> originalPlays = new ArrayList();
    Map<Card, Integer> searchMoveScore = new LinkedHashMap();
    Card rootCard;
    ArrayList<Player> leader = new ArrayList();
    private Integer tempChoice;
    List<Integer> allTricksPlayed = new ArrayList();
    Map<Card, RootCardScore> scoreMap = new LinkedHashMap();
    RootCardScore rootCardScore;
    public static final String ANSI_RED = "\u001B[31m";
    Trick passTrick;

    public static void main(String[] args) {

        GameTest test = new GameTest();
        test.gameTest();
    }

    public void gameTest() {

        ArrayList<Player> playerList = new ArrayList();

        ArrayList<Player> printOrderPlayerList = new ArrayList();
        int highBid = 2;
        int turn = 0;  //east starts
        int highBidPlayerIndex = 3;  //and north starts deal and is stuck
        boolean bidMade = false;

        Player north = new Player("north");
        Player east = new Player("east");
        Player south = new Player("south");
        Player west = new Player("west");

        north.setPlayerLocationIndex(3);
        east.setPlayerLocationIndex(0);
        south.setPlayerLocationIndex(1);
        west.setPlayerLocationIndex(2);

        Player cardsOut = new Player("CardsOut");

        playerList.add(east); //east bids first (north dealt
        playerList.add(south);
        playerList.add(west);
        playerList.add(north);

        CardDeck deck = createEuchreDeck();
        CardDeck cardsOutDeck = createEuchreDeck();

        north.setHand(new CardHand());
        east.setHand(new CardHand());
        south.setHand(new CardHand());
        west.setHand(new CardHand());
        cardsOut.setHand(new CardHand());
        north.setCardsOut(new CardHand());
        east.setCardsOut(new CardHand());
        south.setCardsOut(new CardHand());
        west.setCardsOut(new CardHand());

        while (cardsOutDeck.getSize() > 0) {
            Card dealCard = cardsOutDeck.dealCard();
            north.getCardsOut().addCard(dealCard);
            east.getCardsOut().addCard(dealCard);
            south.getCardsOut().addCard(dealCard);
            west.getCardsOut().addCard(dealCard);
        }

        System.out.println("********************Now DEALING **************");

        Scanner scan = new Scanner(System.in);
        System.out.println("Do you wish to input hand? ");
        String keep = scan.nextLine();
        if (keep.equals("Yes")) {
            System.out.println("Input cards for east");
            for (int i = 0; i < 3; i++) {
                int cardInput = scan.nextInt();
                east.getHand().addCard(cardInput);
            }
            System.out.println("Input cards for sout");
            for (int i = 0; i < 3; i++) {
                int cardInput = scan.nextInt();
                south.getHand().addCard(cardInput);
            }
            System.out.println("Input cards for west");
            for (int i = 0; i < 3; i++) {
                int cardInput = scan.nextInt();
                west.getHand().addCard(cardInput);
            }
            System.out.println("Input cards for north");
            for (int i = 0; i < 3; i++) {
                int cardInput = scan.nextInt();
                north.getHand().addCard(cardInput);
            }
        } else {

            while (deck.getSize() > 0) {
                Card dealCard = deck.dealCard();
                north.getHand().addCard(dealCard);
                north.getCardsOut().playCard(dealCard.getDeckValue());
                dealCard = deck.dealCard();
                east.getHand().addCard(dealCard);
                east.getCardsOut().playCard(dealCard.getDeckValue());
                dealCard = deck.dealCard();
                south.getHand().addCard(dealCard);
                south.getCardsOut().playCard(dealCard.getDeckValue());
                dealCard = deck.dealCard();
                west.getHand().addCard(dealCard);
                west.getCardsOut().playCard(dealCard.getDeckValue());
            }
            printCardNumbers(playerList);

            //printHands(playerList);
            for (int i = 0; i < playerList.size(); i++) {
                for (int j = 0; j < 1; j++) {
                    playerList.get(i).getHand().playCardAt(0);
                }
            }
        }
        // printHands(playerList);
        // printCardNumbers(playerList);
        System.out.println("Cards have been dealt");

        //System.out.println("cards out from North's perpective " + north.getCardsOut().toStringBrief());
        north.setBid();
        east.setBid();
        south.setBid();
        west.setBid();
        System.out.println("Bids calculated");

        // printHands(playerList);
        //*************Bidding****************
        for (Player currentPlayer : playerList) {
            if (currentPlayer.bid > highBid) {
                bidMade = true;
                highBid = currentPlayer.bid;
                System.out.println("Bid made by " + currentPlayer.name + " of " + highBid);
                highBidPlayerIndex = playerList.indexOf(currentPlayer);
            }
        }
        if (!bidMade) {
            //dealer stuck (dealer is last in list)
            System.out.println("Dealer Stuck " + playerList.get(3).name + "do you want to play or take the set");
        }

        //***** Bidding finished *******
        System.out.println("Bidding finished " + playerList.get(highBidPlayerIndex).name + " has bid in suit " + playerList.get(highBidPlayerIndex).getBidSuit());
        playerList.get(highBidPlayerIndex).setHasBid(playerList.get(highBidPlayerIndex));
        playerList.get(highBidPlayerIndex).setTrump(playerList.get(highBidPlayerIndex).getBidSuit());

        for (Player currentPlayer : playerList) {
            currentPlayer.getHand().setCombinedBitMap(playerList.get(highBidPlayerIndex).getBidSuit());//sending 1 is clubs trump 2 is diamonds, 3 is hearts and 4 is spades

        }

        for (Player currentPlayer : playerList) {
            if (!currentPlayer.equals(currentPlayer.getHasBid())) {//don't include bid owner in stay decisions
                StayDecision stay = new StayDecision(currentPlayer, highBid, playerList.get(highBidPlayerIndex).getBidSuit());
            }
        }
        east.setHasLead(east);
        ArrayList<Card> potential = new ArrayList();
        leader.add(east);

        System.out.println("east hand " + east.getHand().toStringBrief());
        System.out.println("south hand " + south.getHand().toStringBrief());
        System.out.println("west hand " + west.getHand().toStringBrief());
        System.out.println("north hand " + north.getHand().toStringBrief());
        printHands(playerList);
        //TODO trim tree by stopping when root card gets a zero value
        Trick realTrick = new Trick();
        realTrick.setTrump(playerList.get(highBidPlayerIndex).getBidSuit());
        Search searchMoves = new Search();
        passTrick = new Trick();
        passTrick.setTrump(playerList.get(highBidPlayerIndex).getBidSuit());
        for (Map.Entry<Player, Card> entry : realTrick.getTrick().entrySet()) {
            passTrick.addCard(entry.getValue(), entry.getKey());
        }
        long startTime = System.currentTimeMillis();
        Map<Card, Integer> eastScore = searchMoves.moveSearch(playerList, 0, new Hand(playerList), passTrick, 0);
         long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        
        
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        System.out.println(ANSI_RED + "############################################################################################################ ");
        printHands(playerList);
        System.out.println("TRUMP " + playerList.get(highBidPlayerIndex).getBidSuit());
        scoreMap = searchMoves.getScoreMap();
       
        //    RootCardScore = searchMoves.getSearchRootCardScore();
        Card bestCard = new Card(playerList.get(0).getHand().getCard(0).getDeckValue());
        int bestScore = 0;
        System.out.println(ANSI_RED + "#############################################################  scoreMap  " + scoreMap.size());
        for (Map.Entry<Card, RootCardScore> entry : scoreMap.entrySet()) {
            if (entry.getValue().getAverage().get(playerList.get(0)) > bestScore) {
                bestCard = entry.getKey();
            }
            System.out.println(ANSI_RED + "Average for " + entry.getKey().toStringBrief() + " " + entry.getValue().getAverage().get(playerList.get(0)));
            System.out.println(ANSI_RED + "Max for " + entry.getKey().toStringBrief() + " " + entry.getValue().getMax().get(playerList.get(0)));
            System.out.println(ANSI_RED + "Average for " + entry.getKey().toStringBrief() + " " + entry.getValue().getAverage().get(playerList.get(1)) + " for player " + playerList.get(1).name);
        }

        realTrick.addCard(bestCard, playerList.get(0));
        System.out.println("Played card " + bestCard.toString());
        playerList.get(0).getHand().playCard(bestCard.getDeckValue());
        printHands(playerList);
        Search searchMoves2 = new Search();
        passTrick = new Trick();
        passTrick.setTrump(playerList.get(highBidPlayerIndex).getBidSuit());
        for (Map.Entry<Player, Card> entry : realTrick.getTrick().entrySet()) {
            passTrick.addCard(entry.getValue(), entry.getKey());
        }
        eastScore = searchMoves2.moveSearch(playerList, 1, new Hand(playerList), passTrick, 0);
        
        printHands(playerList);
        System.out.println("TRUMP " + playerList.get(highBidPlayerIndex).getBidSuit());
        System.out.println("Card played previously was " + bestCard.toString());
        bestScore = 0;
        scoreMap = searchMoves2.getScoreMap();
        for (Map.Entry<Card, RootCardScore> entry : scoreMap.entrySet()) {
            if (entry.getValue().getAverage().get(playerList.get(1)) > bestScore) {
                bestCard = entry.getKey();
            }
            System.out.println("Average for " + entry.getKey().toStringBrief() + " " + entry.getValue().getAverage().get(playerList.get(1)));
            System.out.println("Max for " + entry.getKey().toStringBrief() + " " + entry.getValue().getMax().get(playerList.get(1)));
            //System.out.println("Average for " + entry.getKey().toStringBrief() + " " + entry.getValue().getAverage().get(playerList.get(0)) + " for player " + playerList.get(0).name);
        }

        realTrick.addCard(bestCard, playerList.get(1));
        playerList.get(1).getHand().playCard(bestCard.getDeckValue());
        printHands(playerList);

        int count = 0;
        // print 0,6,12 as tricknumber, 1,2,3,4 as cards, 5, 11 as player integer
        while (!allTricksPlayed.isEmpty()) {
            if (count == 0 || count % 6 == 0) {
                System.out.print(count / 6 + " " + allTricksPlayed.get(0) + " ");
            } else if ((count + 1) % 6 == 0) {
                System.out.println(" " + allTricksPlayed.get(0));
            } else {
                Card tempCard = new Card(allTricksPlayed.get(0));
                System.out.print(tempCard.toStringBrief() + ",");
            }
            allTricksPlayed.remove(0);
            count++;
        }

        
    }

    public void play(Card card, Player player, int node, Trick trick) {
        lastPlayerNumber = player.getPlayerLocationIndex();
        trick.addCard(card, player);
        trick.printTrick();
        player.getHand().playCard(card.getDeckValue());
    }

    private CardDeck createEuchreDeck() {
        int[] exclusion = new int[]{
            1, 2, 3, 4, 5, 6, 7,
            14, 15, 16, 17, 18, 19, 20,
            27, 28, 29, 30, 31, 32, 33,
            40, 41, 42, 43, 44, 45, 46,};

        CardDeck deck = new CardDeck(exclusion);
        //System.out.println("Euchre card deck (9 thru Aces)");
        return deck;
    }

    private Card[] dealCards() {
        System.out.println("****** Deal cards:  ******");
        Card[] myHand = new Card[6];
        CardDeck deck = createEuchreDeck();
        int deckSize = deck.getSize();
        for (int i = 0; i < deckSize / 4; i++) {
            Card card = deck.dealCard();
            myHand[i] = card;
            // System.out.println(" " + card.toString());
            //System.out.println("Deck size: " + deck.getSize());
        }
        return myHand;

    }

    private void printHands(ArrayList<Player> playerList) {
        //System.out.println("North at this point has bid? " + north.hasBid);

        System.out.println("                         North " + playerList.get(3).bid + "\n                    " + playerList.get(3).getHand().toStringBrief() + "\n");
        System.out.println("     West " + playerList.get(2).bid + "                              East " + playerList.get(0).bid);
        System.out.println(playerList.get(2).getHand().toStringBrief() + "                        " + playerList.get(0).getHand().toStringBrief() + "\n");
        System.out.println("                         South" + " " + playerList.get(1).bid);
        System.out.println("                    " + playerList.get(1).getHand().toStringBrief() + "\n");

    }

    private void printCardNumbers(ArrayList<Player> playerList) {
        for (int j = 0; j < playerList.size(); j++) {
            System.out.println(playerList.get(j).name);
            for (int i = 0; i < playerList.get(j).getHand().getSize(); i++) {
                System.out.println(playerList.get(j).getHand().getCard(i).getDeckValue());
            }
        }
    }

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
