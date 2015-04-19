/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

import java.util.ArrayList;

/**
 *
 * @author jeffomland
 */
public class Player {

    //10 = JC, 49 JS, 13 AC, KC, QC, 10c 9C, JD, JH, AD, KD, QD...
    private final int[] trumpRankOrder = new int[]{
        10, 49, 13, 12, 11, 9, 8, 23, 36, 26, 25, 24, 22, 21, 36, 23, 39, 38, 37, 35, 34, 49, 10, 52, 51, 50, 48, 47
    };
    private final int[] noTrumpRankOrder = new int[]{
        13, 12, 11, 10, 9, 8, 26, 25, 24, 23, 22, 21, 39, 38, 37, 36, 35, 34, 52, 51, 50, 49, 48, 47
    };
    Boolean finishedBidding;
    int numberOfTricksWon, score, bid, numberSets, trumpBid;
    String name;
    int playerLocationIndex;
    // public static int cardsPlayed = 0; //keep track of how many cards played in trick
    public static int leadSuit = 1; //keep track of what suit was led
    public Card highCardInTrick;//keep track of highest card in trick
    public static int trump; //trump set after bid

    //   public static int highestCardPlayed = 0;//keep track of highest card in trick
    private CardHand hand;
    private CardHand cardsOut;
    private double trumpBidPoints = 0;
    private double highBid = 0;
    private int bidSuit; //this is tracking as a player bids .. the trump he would choose if he wins bid
    private double nonTrumpBidPoints = 0;
    private double[] nonTrumpBids = new double[4];//nontrump score for each suit
    private double constant = 1.0;
    private final double reduction = 0.3;
    private int currentSuit = 0;  // 1 = clubs ?? clubs is zero
    private boolean cardNotMatched = true;  //no match for card in hand
    private int blanks = 0;
    public static Player playersTrick;
    public static Player hasLead;
    public static Player hasBid;
    public static Player isTurn;
    //private ArrayList<String><int> scoring = new ArraList();  

    public int getPlayerLocationIndex() {
        return playerLocationIndex;
    }

    public void setPlayerLocationIndex(int playerLocationIndex) {
        this.playerLocationIndex = playerLocationIndex;
    }

    public Player(String name) {
        //this.hasLead = false;
        //this.hasBid = false;
        //this.isTurn = false;
        this.finishedBidding = false;
        this.numberOfTricksWon = 0;
        this.score = 0;
        this.bid = 0;
        //this.bidSuit = 0;
        this.numberSets = 0;
        this.name = name;
        this.trumpBid = 0;
    }

    public static int getTrump() {
        return trump;
    }

    public static void setTrump(int trump) {
        Card temp = new Card(2);

        Player.trump = trump;
    }

    public static int getLeadSuit() {
        return leadSuit;
    }

    public static void setLeadSuit(int leadSuit) {
        Player.leadSuit = leadSuit;
    }

//    public static int getHighCardInTrick() {
//        return highCardInTrick;
//    }
//
//    public static void setHighCardInTrick(int highCardInTrick) {
//        Player.highCardInTrick = highCardInTrick;
//    }

    public Player getHasLead() {
        return hasLead;
    }

    public void setHasLead(Player hasLead) {

        this.hasLead = hasLead;
    }

    public Player getHasBid() {
        return hasBid;
    }

    public void setHasBid(Player hasBid) {
        // System.out.println("Switching hasBid for " + this.name);
        this.hasBid = hasBid;
    }

    public Player getIsTurn() {
        return isTurn;
    }

    public void setIsTurn(Player isTurn) {
        this.isTurn = isTurn;
    }

    public int getTricks() {
        return numberOfTricksWon;
    }

    public void setTricks(int tricks) {
        this.numberOfTricksWon += tricks;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setBid() {
        calculateTrumpScore();
        calculateNonTrumpScore();
        double noTrumpAdd = 0;
        for (int i = 0; i < nonTrumpBids.length; i++) {
            if (nonTrumpBids[i] > 0 && i != (bidSuit)) {
                noTrumpAdd += nonTrumpBids[i];
            }
        }
        bid = (int) (highBid + noTrumpAdd);
    }

    public int getNumberSets() {
        return numberSets;
    }

    public void setNumberSets(int numberSets) {
        this.numberSets = numberSets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardHand getHand() {
        return hand;
    }

    public void setHand(CardHand hand) {
        this.hand = hand;
    }

    public CardHand getCardsOut() {
        return cardsOut;
    }

    public void setCardsOut(CardHand cardsOut) {
        this.cardsOut = cardsOut;
    }

    //****************************************************************
    private void calculateTrumpScore() {

        Card tempCard;
        //currentSuit = 0;  // for first iteration
        //Go through each card in trump rankOrder to score cards in hand.
        // System.out.println("********************************NEW PLAYER bid calc*************************** " + this.name);
        for (int i = 0; i < trumpRankOrder.length; i++) {
            //System.out.println("I = " + i + " i mod trump rank " + i % (trumpRankOrder.length / 4) + " bidpoints " + trumpBidPoints);
            //first determine if suit needs to change
            cardNotMatched = true;
            for (int j = 0; j < hand.getSize(); j++) {
                if (trumpRankOrder[i] == hand.getCard(j).getDeckValue()) {
                    //  System.out.println("Card: " + hand.getCard(j).toStringBrief() + " constant " + constant + " bidPoints before added " + trumpBidPoints);
                    trumpBidPoints += constant;
                    //  System.out.println("Suit " + currentSuit + " trumpBidpoints " + trumpBidPoints);
                    cardNotMatched = false;
                    blanks = 0;
                    constant += reduction;
                    if (constant > 1.6) {
                        constant = 1.6;
                    }
                }
            } // end of hand check of one card

            if (cardNotMatched) {

                constant -= reduction;
                //  System.out.println("Card not matched " + trumpRankOrder[i] + " constant " + constant);
                blanks++;
            }
            //i / (trumpRankOrder.length / 4) > 0 &&

            if (new Card(trumpRankOrder[i]).getFaceValue() == 9) {
                //if card is a 9 suit changes
                //System.out.println("Changing suits " + this.name);
                if (trumpBidPoints > highBid) {
                    // System.out.println("new high bid so recording highbid and bidsuit " + this.bidSuit);
                    // System.out.println("The currentSuit is " + currentSuit);
                    highBid = trumpBidPoints;
                    this.bidSuit = currentSuit;
                    // System.out.println("This bidsuit now " + bidSuit);

                }
                //trumpBids[currentSuit - 1] = trumpBidPoints;
                trumpBidPoints = 0;
                currentSuit++;
                // System.out.println("Current suit changed " + currentSuit + " bidSuit " + this.bidSuit);
                constant = 1;
            }
        } // end of rankorder

        trumpBid = (int) highBid;
    }

    private void calculateNonTrumpScore() {

        currentSuit = 0;
        for (int i = 0; i < noTrumpRankOrder.length; i++) {
            //Card suitCard = new Card(noTrumpRankOrder[i]);
            if (i / (noTrumpRankOrder.length / 4) > 0 && i % (noTrumpRankOrder.length / 4) == 0) { //if remainder is o suit should be changing
                nonTrumpBids[currentSuit] = nonTrumpBidPoints;
                nonTrumpBidPoints = 0;
                currentSuit++;
                constant = 1;
            }
            for (int j = 0; j < hand.getSize(); j++) {

                if (noTrumpRankOrder[i] == hand.getCard(j).getDeckValue()) {
                    nonTrumpBidPoints += constant;
                    cardNotMatched = false;
                    blanks = 0;
                }
            } // end of hand check of one card

            if (cardNotMatched) {
                constant -= reduction;
                blanks++;
            }
            cardNotMatched = true;
        } // end of rankorder
    }

    public ArrayList<Card> potentialPlays(Player player, Trick trick) {
        //TODO Leadsuit is using inital setting and is not updated prior to running this code
        //TODO if canot follow consider adding the lowest card only to trim tree size
        //TODO consider adding code if only one card left so play it.
        //TODO  When undoing hand for choice on last player it played a card that won without being winning card
        //TODO Currently trump value is zero if card is not trump suit and this code says Ad can ill Jd led with diamond trump
        //TODO cannot set all trump value to zero and use trump value on non tricks with no trump
        //TODO when jick is played follow suit is looking at jick suit in place of trump suit
        //TODO chec for can follow can kill can trump not correct.  If can follow and can trump it will put both true when this is not right

        // Player player = playerList.get(index);
       
        highCardInTrick = trick.getHighCard();
        ArrayList<Card> potentialPlays = new ArrayList();
         if(player.getHand().getSize()==0){
      //      System.out.println("returning from potentialplays due to empty hand for player " + player.name);
            return potentialPlays;
        }
        boolean higherFollow = false;
        boolean canFollow = false;
        boolean canTrump = false;
        boolean canKill = false;
        boolean trumpLed = false;
        boolean trumpHasBeenPlayed = trick.isTrumpHasBeenPlayed();

        if (player.equals(player.getHasLead())) {
            //System.out.println("Player has lead");
            for (int i = 0; i < player.getHand().getSize(); i++) {
                potentialPlays.add(player.getHand().getCard(i));
            }
        } else {
            leadSuit = trick.getSuitLed();
           // System.out.println("potential plays leadsuit = " + leadSuit + " trump " + trump);
            if (leadSuit == trump) {
         //       System.out.println("#######################  TRUMP LED ###################");
                trumpLed = true;
            }
            //first loop hand and determine if there are cards to follow suite, or trump and if they can kill
            for (int i = 0; i < player.getHand().getSize(); i++) {
                int valueOfCard = player.getHand().getCard(i).getTrumpValue(trump, trumpHasBeenPlayed);
         //       System.out.println("card " + player.getHand().getCard(i).toStringBrief() + " suit " + player.getHand().getCard(i).getSuitValue());
                if (player.getHand().getCard(i).getSuitValue() == leadSuit && valueOfCard < 20) {
                    canFollow = true;
                    if (valueOfCard > highCardInTrick.getTrumpValue(trump, trumpHasBeenPlayed)) {
                       // System.out.println("HigCardInTrick value " + highCardInTrick + " This card trumpValue " + player.getHand().getCard(i).getTrumpValue(trump));
                        higherFollow = true;
                    }
                }
                if (player.getHand().getCard(i).getSuitValue() == trump || valueOfCard > 19) {
                    canTrump = true;
                    if(trumpLed){
                        canFollow = true;
                    }
                    if (valueOfCard > highCardInTrick.getTrumpValue(trump,trumpHasBeenPlayed)) {
                        canKill = true;
                    }
                }
                

            }
            for (int i = 0; i < player.getHand().getSize(); i++) {
                if (trumpLed) {
                    if (canFollow && canKill && (player.getHand().getCard(i).getSuitValue() == leadSuit || player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) > 19) && player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) > highCardInTrick.getTrumpValue(trump,trumpHasBeenPlayed)) {
                        potentialPlays.add(player.getHand().getCard(i));
                       // System.out.println("Adding potential play " + player.getHand().getCard(i).toStringBrief() + " from canFollow canKill trump was Led isLead suit");
                        //break;
                    }

                    if (canFollow && !canKill && (player.getHand().getCard(i).getSuitValue() == leadSuit || player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) > 19)) {
                        potentialPlays.add(player.getHand().getCard(i));
                     //   System.out.println("Adding potential play " + player.getHand().getCard(i).toStringBrief() + " from canFollow CANNOT KILL trump was Led isLead suit");

                        //break;
                    }

                    if (!canFollow && !canKill) {
                        potentialPlays.add(player.getHand().getCard(i));
                        //System.out.println("Adding potential play " + player.getHand().getCard(i).toStringBrief() + " from CANNOTFOLLOW CANNOT KILL trumpLed");

                       // break;
                    }
                } else {
                    //trump not led have a higher card in suit led
                    if (canFollow && higherFollow && player.getHand().getCard(i).getSuitValue() == leadSuit && player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) < 20 && player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) > highCardInTrick.getTrumpValue(trump,trumpHasBeenPlayed)) {
                        potentialPlays.add(player.getHand().getCard(i));
                        //System.out.println("Adding potential play " + player.getHand().getCard(i).toStringBrief() + " from canFollow and higher card TRUMP NOT LED isLead suit");
                     //   System.out.println("Leadsuit " + leadSuit);
                     //   System.out.println("Card suit " + player.getHand().getCard(i).getSuitValue());
                       // break;
                    }
                    //trump not led and can follow but no cards higher
                    if (canFollow && !higherFollow && player.getHand().getCard(i).getSuitValue() == leadSuit && player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) < 20) {
                        potentialPlays.add(player.getHand().getCard(i));
                       // System.out.println("Adding potential play " + player.getHand().getCard(i).toStringBrief() + " from canFollow but lower trump NOT LED isLead suit");

                       // break;
                    }
                    //cannot follow but can trump and trump is higher
                    if (!canFollow && canKill && (player.getHand().getCard(i).getSuitValue() == trump || player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) > 19) && player.getHand().getCard(i).getTrumpValue(trump,trumpHasBeenPlayed) > highCardInTrick.getTrumpValue(trump,trumpHasBeenPlayed)) {
                        potentialPlays.add(player.getHand().getCard(i));
                        //System.out.println("addint card from cannot follow but can trump and trump is higher");
                       // break;
                    }
                    //cannot follow and cannot trump higher
                    if (!canFollow && !canKill) {
                        potentialPlays.add(player.getHand().getCard(i));
                        
                       // System.out.println("Adding card from cant follow cant kill trump not led");
                       // break;

                    }
                }
            }
        }

       // System.out.println("Leadsuit " + leadSuit + " Can follow is " + canFollow + " Can follow higher is " + higherFollow +" Can Kill " + canKill + " Can trump " + canTrump);

        return potentialPlays;
    }

    public Player getPlayersTrick() {
        return playersTrick;
    }

    public void setPlayersTrick(Player playersTrick) {
        this.playersTrick = playersTrick;
    }

    public int getBidSuit() {
        return bidSuit;
    }

    public void setBidSuit(int bidSuit) {
        this.bidSuit = bidSuit;
    }

}
