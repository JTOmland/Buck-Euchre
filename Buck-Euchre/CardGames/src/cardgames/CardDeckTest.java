/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

/**
 *
 * @author Jeff Omland
 */
public class CardDeckTest {
    
  public CardDeckTest(){};
    
//    public static void main(String[] args) {
//        System.out.println("Testing the CardDeck class");
//        CardDeckTest test = new CardDeckTest();
//        
//        test.cardDeckConstructor1();
//        test.cardDeckConstructor2();
//        test.dealCards();
//        test.dealHands();
//        
//        System.out.println();
//    }

    private void cardDeckConstructor1() {
        System.out.println("\n****** CardDeck constructor (default):******");
        System.out.println("Standard 52 card deck:");
        
        CardDeck deck = new CardDeck();
        System.out.println(deck.toString());
        
    }

    private void cardDeckConstructor2() {
        System.out.println("****** CardDeck constructor (int [] exlusions) ******");
        
        CardDeck deck = createEuchreDeck();
        System.out.println(deck.toString());
    }

    private CardDeck createEuchreDeck() {
        int [] exclusion = new int []{
          1,2,3,4,5,6,7,
          14,15,16,17,18,19,20,
          27,28,29,30,31,32,33,
          40,41,42,43,44,45,46,};
        
        CardDeck deck = new CardDeck (exclusion);
        System.out.println("Euchre card deck (9 thru Aces)");
        return deck;
    }

    private void dealCards() {
        System.out.println("****** Deal cards:  ******");
        
        CardDeck deck = createEuchreDeck();
        System.out.println("Deck size: " + deck.getSize());
        int deckSize = deck.getSize();
        for (int i = 0; i < deckSize; i++){
            Card card = deck.dealCard();
            System.out.println(" " + card.toString());
            System.out.println("Deck size: " + deck.getSize());
        }
    }

    private void dealHands() {
        System.out.println("\n****** Deal Hands: ******");
        
        CardDeck deck = createEuchreDeck();
        int deckSize = deck.getSize();
        System.out.println("Deck size: " + deck.getSize());
        for (int i = 0; i < deckSize; i++){
            CardHand hand = deck.dealHand(5);
            if (hand == null){
                return;
            }
            System.out.println(" " + hand.toString());
            System.out.println("Deck size: " + deck.getSize());
        }
        
    }
    
}
