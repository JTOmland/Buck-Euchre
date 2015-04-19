/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

/**
 *
 * @author Jeff Omland
 */
public class BidCalculation {

    //Deck values 1-52 with suit ordered clubs, diamonds, hearts and spades
    //face values from 2-14 (2-10, J, Q, K, A)
    //Suit values 1-4 (c,d,h,s)
    private int[] trumpRankOrder = new int[]{
        10, 49, 13, 12, 11, 9, 8, 23, 36, 26, 25, 24, 22, 21, 36, 23, 39, 38, 37, 35, 34, 49, 10, 52, 51, 50, 48, 47
    };
    private int[] trackingOrder = new int[]{
        10, 13, 12, 11, 9, 8, 49, 52, 51, 50, 48, 47, 23, 26, 25, 24, 22, 21, 36, 39, 38, 37, 35, 34
    };
    private int[] noTrumpRankOrder = new int[]{
        13, 12, 11, 10, 9, 8, 26, 25, 24, 23, 22, 21, 39, 38, 37, 36, 35, 34, 52, 51, 50, 49, 48, 47
    };//100 is place holder to make this array match trumpRankByDeckValue array
     //Suit name constants:
    private final String[] suitNames = new String[]{"clubs", "diamonds","hearts","spades"};
    CardHand hand;
    private double trumpBidPoints = 0;
    private double highBid = 0;
    private int highSuit = 0;
    private double nonTrumpBidPoints = 0;
    private double[] nonTrumpBids = new double[4];//nontrump score for each suit
    private double[] trumpBids = new double[4];//trump bid by suit
    private double constant = 1.0;
    private double reduction = 0.3;
    private int currentSuit = 1;  // 1 = clubs
    private boolean cardNotMatched = true;  //no match for card in hand
    private int blanks = 0;
    //private ArrayList<String><int> scoring = new ArraList();
    
    public BidCalculation(){
        System.out.println("WARNING WARNING WARNING Bid Calcualtion being used");
        
    }

    public void calculateTrumpScore(CardHand hand){
        
        this.hand = hand;
        //System.out.println(hand.toStringBrief());
        for (int i = 0; i < trumpRankOrder.length; i++) {
            if (i / (trumpRankOrder.length / 4) > 0 && i % (trumpRankOrder.length / 4) == 0) {
                //if remainder is o suit should be changing
                if (trumpBidPoints > highBid) {
                    highBid = trumpBidPoints;
                    highSuit = currentSuit;
                }
                trumpBids[currentSuit - 1] = trumpBidPoints;
                trumpBidPoints = 0;
                currentSuit++;
                constant = 1;
            }
            for (int j = 0; j < hand.getSize(); j++) {
                if (trumpRankOrder[i] == hand.getCard(j).getDeckValue()) {
                    trumpBidPoints += constant;
                    cardNotMatched = false;
                    blanks = 0;
                    constant += reduction;
                }
            } // end of hand check of one card

            if (cardNotMatched) {
                constant -= reduction;
                blanks++;
            }
            cardNotMatched = true;
        } // end of rankorder
        
       // return (int) getTrumpBid();
    }
    
    public void calculateNonTrumpScore(CardHand hand){
        
        this.hand = hand;
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
        //System.out.println("Ended non Trump bid calculation");
        
     //   return nonTrumpBids;
        
    }

    public int getHighTrump() {
        return highSuit;
    }

    public double getTrumpBid() {
        return highBid;
    }

    public double getNonTrumpBid(int suit) {
        return nonTrumpBids[suit];
    }

    public int getBid() {
        double noTrumpAdd = 0;
        for (int i = 0; i < nonTrumpBids.length; i++) {
            if (nonTrumpBids[i] > 0 && i != (getHighTrump() -1)) {
                noTrumpAdd += nonTrumpBids[i];
            }
        }
        int roundedBid = (int) (getTrumpBid() + noTrumpAdd);
        return roundedBid;
    }
     public String getSuitName() {
        return suitNames[getHighTrump()-1];
    }
}
