/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author jeffomland
 */
public class Search {

    private int[] scores = new int[5];//0-3 scores for east, south, west, north and 4 is reserved for cardNumber 
    private boolean trickWon = false;
    //private String trick = "";
    private int ply = 0;
    private int nodes = 0;
    private int moveNumber = 0;
    //private Trick currentTrick;
    private int lastPlayerNumber = -1;
    private int startingNode = 0;
    private int cardScoreLocation = 0;
    private boolean isFirstRoot = true;
    private int totalCardsPlayed = 0;
    private int terminalCount = 0;
    private boolean firstTimeThru = true;
    //  private int lastPlayerWithChoice = 0;
    private Stack lastChoiceStack = new Stack();
    private ArrayList<Card> originalPlays = new ArrayList();
    private Map<Card, Integer> searchMoveScore = new LinkedHashMap();
    private Card rootCard;
    private ArrayList<Player> leader = new ArrayList();
    private Integer tempChoice;
    private List<Integer> allTricksPlayed = new ArrayList();
    private Map<Card, RootCardScore> searchScoreMap = new LinkedHashMap();
    private RootCardScore searchRootCardScore;

    Search() {

    }

    public Map<Card, Integer> moveSearch(ArrayList<Player> playerList, int playerTurn, Hand hand, Trick currentTrick, int score) {
        //printHands(playerList);
        //System.out.println("playerturn " + playerTurn);
        // if(currentTrick.getCardLed()!=null){
        // System.out.println("Start of move search current trick lead card " + currentTrick.getCardLed().toString());
        // System.out.println("starting node " + startingNode);

        // }
        ArrayList<Card> possibleMoves = playerList.get(playerTurn).potentialPlays(playerList.get(playerTurn), currentTrick);//playerList.get(playerLocation).potentialPlays(playerList.get(playerLocation));
        //return if player no more possible plays
        if (possibleMoves.isEmpty()) {
            // System.out.println("returning from possibleMoves.isEmpty");
            return searchMoveScore;
        }
        //Check if player has a choice of more than one card (branching node)
        if (possibleMoves.size() > 1) {
            lastChoiceStack.push(playerTurn);
        }
        //print possible moves
//        for (Card card : possibleMoves) {
//            System.out.print(card.toStringBrief() + ",");
//        }
        //add cards to originalPlays which keeps track of the original options in hand to identify a root card switch
        if (startingNode == 0) {
            for (int i = 0; i < playerList.get(playerTurn).getHand().getSize(); i++) {
                originalPlays.add(playerList.get(playerTurn).getHand().getCard(i));
               // System.out.println("Original play " + playerList.get(playerTurn).getHand().getCard(i));
                searchMoveScore.put(playerList.get(playerTurn).getHand().getCard(i), 10);
            }
            currentTrick.setTrickNumber(1);
        }
        startingNode = 1;

        //cycle through all possible moves
        for (Card card : possibleMoves) {
            totalCardsPlayed++;
            nodes++; //tacks the node for each card.  not sure this is the right spot given it will reset when going from 3 choices to 2 to 1
            card.setNode(nodes);
            if (originalPlays.contains(card) && totalCardsPlayed == 1) {
                //the card is an original play  and first card played it is a new root card
                rootCard = card;
                // System.out.println("Root CARD " + rootCard.toString());
                searchRootCardScore = new RootCardScore(playerList);//object to track all aspects of scores for root card.
                searchScoreMap.put(rootCard, searchRootCardScore);//the searchScoreMap keeps track of all players scores for each root card for each terminal condition
            }
            //backup position
            BackupState backupState = new BackupState(nodes, currentTrick, playerList, hand, playerTurn, totalCardsPlayed);
            backupState.setCard(card);
            //play card choice to update tick and players hand
            //System.out.println("\nCalling currentTrick.addcard" + playerList.get(playerTurn).name);
            currentTrick.addCard(card, playerList.get(playerTurn));
            //remove card from hand 
            playerList.get(playerTurn).getHand().playCard(card.getDeckValue());
            //play(card, playerList.get(playerTurn), card.getNode(), currentTrick);
            //If trick is over score trick.
            currentTrick.printTrick();
            if (currentTrick.getNumberOfCardsPlayedInTrick() == 4) {
                if (currentTrick != null) {
                    // System.out.println("trick cards = 4 " + " tricknumber " + currentTrick.getTrickNumber() + " trickwinner " + currentTrick.getTrickWinner().playerLocationIndex);
                    //make deep copy of trick to be saved for every trick in tree for data and debug purposes
                    Map<Player, Card> temp = currentTrick.getTrick();
                    allTricksPlayed.add(currentTrick.getTrickNumber());
                    for (Map.Entry<Player, Card> entry : temp.entrySet()) {
                        allTricksPlayed.add(entry.getValue().getDeckValue());
                    }
                    allTricksPlayed.add(currentTrick.getTrickWinner().playerLocationIndex);
                    //       System.out.println("hand.setTrickWinner will be set for" + currentTrick.getTrickWinner().name);
                } else {
                    System.out.println("currentTrick null");
                }
                //actual score of trick recorded in hand
                hand.setTrickScore(currentTrick.getTrickWinner(), 1);
                //is hand over?
                if (hand.getTotalTricks() == originalPlays.size()) {
                    terminalCount++;
                    //      System.out.println("playerTurn " + playerTurn);
                    System.out.println("%%%%%%%%%%%%%%%%%%%%%%***Print of scores at terminal " + terminalCount + "***%%%%%%%%%%%%%%%%% ");
                    System.out.println("Root card " + rootCard.toString() + " node " + nodes);
                    //add scoring information for hand for recording
                    searchRootCardScore.addScores(hand.getTrickScore());
                    //printing player tricks for hand
                    for (Map.Entry<Player, Integer> entry : hand.getTrickScore().entrySet()) {
                        System.out.print(entry.getKey().name + " " + entry.getValue() + ", ");
                    }
                    //score hand as played for root card
                    //TODO with searchRootCardScore and searchScoreMap not using searhMoveScore (I believe)
                    if (firstTimeThru) {
                        searchMoveScore.put(rootCard, hand.getTrickScore().get(0));
                        firstTimeThru = false;
                    }
                    currentTrick.setTrickNumber(0);
                }
                //set player with lead
                playerList.get(playerTurn).setHasLead(currentTrick.getTrickWinner());
                //subtract one from player with lead because playerturn is indexed below
                playerTurn = currentTrick.getTrickWinner().getPlayerLocationIndex() - 1;
                if (playerTurn > 3) {
                    playerTurn = 0;
                }
                //   System.out.println("Trick winner: " + currentTrick.getTrickWinner().name + "\n");
                currentTrick = new Trick();
                currentTrick.setTrickNumber(currentTrick.getTrickNumber() + 1);
            }

            playerTurn++;
            if (playerTurn > 3) {
                playerTurn = 0;
            }
          //  System.out.println("\n player turn " + playerList.get(playerTurn).name);

            //if trick isn't over and hand isn't over then search for move
            Map<Card, Integer> returnMap = moveSearch(playerList, playerTurn, hand, currentTrick, score);

            //undo to previous state before card option was played
            totalCardsPlayed = backupState.getBackupTotalCardsPlayed();
            //If totalCardsPlayed equals 1 it is a root card and needs to be set to zero for its sibling root to be first
            if (totalCardsPlayed == 1) {
                totalCardsPlayed = 0;
            }
            //   System.out.println("undoing trick for " + backupState.getNode() + " player " + playerList.get(backupState.getBackupLastPlayer()).name + backupState.getCard());
            currentTrick = new Trick();
            currentTrick.setTrump(backupState.getBackupTrump());
            //Card lastCard = backupState.getCard();
            //   System.out.println("LastCard prior" + backupState.getCard());
            //add cards from backupstate to the new trick 
            Map<Player, Card> temp = backupState.getBackupTrick();
            for (Map.Entry<Player, Card> entry : temp.entrySet()) {
                currentTrick.addCard(entry.getValue(), entry.getKey());
                //      System.out.println("addedCard " + entry.getValue().toStringBrief());
            }
            //add card back to hand
            playerList.get(backupState.getBackupLastPlayer()).getHand().addCard(backupState.getCard());
         //   System.out.println("player undoing " + playerList.get(backupState.getBackupLastPlayer()).name);
            //   printHands(playerList);
            //reset hand total tricks
            hand.setTotalTricks(backupState.getHandTotalTricks());
            //reset hand trick scores
            Map<Player, Integer> tempS = backupState.getBackupScores();
            for (Map.Entry<Player, Integer> entry : tempS.entrySet()) {
                hand.setBackupTrickScore(entry.getKey(), entry.getValue());
            }
            //reset lastplayer
            lastPlayerNumber = backupState.getBackupLastPlayer();
            //reset playerturn
            playerTurn = backupState.getBackupTurn();
            //reset trick number for new trick from backupstate
            currentTrick.setTrickNumber(backupState.getTrickNumber());
            //   System.out.println("HighCard in trick is " + currentTrick.getHighCard().toStringBrief());
        }
        return searchMoveScore;

    }

    public RootCardScore getSearchRootCardScore() {
        return searchRootCardScore;
    }

    public Map<Card, RootCardScore> getScoreMap() {
        return searchScoreMap;
    }

    private void printHands(ArrayList<Player> playerList) {
        //System.out.println("North at this point has bid? " + north.hasBid);

        System.out.println("                         North " + playerList.get(3).bid + "\n                    " + playerList.get(3).getHand().toStringBrief() + "\n");
        System.out.println("     West " + playerList.get(2).bid + "                              East " + playerList.get(0).bid);
        System.out.println(playerList.get(2).getHand().toStringBrief() + "                        " + playerList.get(0).getHand().toStringBrief() + "\n");
        System.out.println("                         South" + " " + playerList.get(1).bid);
        System.out.println("                    " + playerList.get(1).getHand().toStringBrief() + "\n");

    }

}
