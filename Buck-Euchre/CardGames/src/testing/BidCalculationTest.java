/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import cardgames.BidCalculation;
import cardgames.Card;
import cardgames.CardDeck;
import cardgames.CardHand;
import java.util.Random;

/**
 *
 * @author Jeff Omland
 */
public class BidCalculationTest {

    public static void main(String[] args) {
        
        CardHand north = new CardHand();
        CardHand east = new CardHand();
        CardHand south = new CardHand();
        CardHand west = new CardHand();
        BidCalculationTest test = new BidCalculationTest();
        test.bidCalc();

    }

    public void bidCalc() {

       
        for (int numberOfHands = 0; numberOfHands < 10; numberOfHands++) {
            CardHand aHand = new CardHand(dealCards());
            BidCalculation bid = new BidCalculation();
           // bid.getTrumpBid()
           
           // System.out.println("Bid " + bid.calculateNonTrumpScore(aHand) + bid.calculateNonTrumpScore(aHand) + " " + bid.getTrumpBid());
            for (int i = 0; i < 4; i++) {
                System.out.println("NonTrump " + (i + 1) + ":" + bid.getNonTrumpBid(i));
            }
             
            System.out.println("Full Bid " + bid.getBid() + ":" + bid.getSuitName());
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

    private CardDeck createEuchreDeck() {
        int[] exclusion = new int[]{
            1, 2, 3, 4, 5, 6, 7,
            14, 15, 16, 17, 18, 19, 20,
            27, 28, 29, 30, 31, 32, 33,
            40, 41, 42, 43, 44, 45, 46,};

        CardDeck deck = new CardDeck(exclusion);
        System.out.println("Euchre card deck (9 thru Aces)");
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
}
