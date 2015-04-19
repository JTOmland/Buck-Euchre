/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import cardgames.Card;
import cardgames.CardHand;
import java.util.ArrayList;

/**
 *
 * @author Jeff Omland
 */
public class CardHandTest {
    
      private int [] suitCounter = new int[4];
      private String[] suitNames = new String[]{"Clubs", "Diamonds", "Hearts", "Spades"};
    
    public static void main(String[] args) {
        System.out.println("Testing the CardHand class:");
        CardHandTest test = new CardHandTest();
        
        test.cardHandConstructor1();
        test.cardHandConstructor2();
        test.cardHandConstructor3();
        test.cardHandConstructor4();
        test.hasCards();
        test.playSequenceOfCards();
        test.playRandomCards();
        test.playSpecificCards();
        
        
    }

    private void cardHandConstructor1() {
   
        System.out.println("\n****** CardHand constructor (default): ******");
        
        CardHand straight = new CardHand();
        System.out.println(straight.toString());
        
        straight.addCard(1);
        straight.addCard(28);
        straight.addCard(16);
        straight.addCard(4);
        straight.addCard(44);
        
        System.out.println("\nCards added to empty hand to make a Straight ");
        System.out.println(straight.toString());
        System.out.println(straight.toStringBrief());
        suitCounter = straight.suitCount();
        for(int i = 0; i < suitNames.length; i++){
            System.out.println("The hand has " + suitCounter[i] + " " + suitNames[i]);
        }
        
    }

    private void cardHandConstructor2() {
        System.out.println("\n****** CardHand constructor (int deckValues)");
        
        int aRoyalFlush [] = new int []{
            35, 36, 37, 38, 39
        };
        
        CardHand royalFlush = new CardHand(aRoyalFlush);
        System.out.println("Royal Flush\n");
        System.out.println(royalFlush.toString());
        System.out.println(royalFlush.toStringBrief());
        System.out.println(royalFlush.getCard(1).getFaceValue());
        suitCounter = royalFlush.suitCount();
        for(int i = 0; i < suitNames.length; i++){
            System.out.println("The hand has " + suitCounter[i] + " " + suitNames[i]);
        }
        
    }

    private void cardHandConstructor3() {
        System.out.println("\n****** CardHand constructor (Card cards []");
        
        Card aDiamonds [] = new Card []{
            new Card(14), new Card(15), new Card(16), new Card(17), 
            new Card(18), new Card(19), new Card(20), new Card(21),
            new Card(22), new Card(23), new Card(24), new Card(25),
            new Card(26)};
        CardHand diamonds = new CardHand(aDiamonds);
        System.out.println("Diamonds:\n");
        System.out.println(diamonds.toString());
        System.out.println(diamonds.toStringBrief());
      
       suitCounter = diamonds.suitCount();
        }
    

    private void cardHandConstructor4() {
        System.out.println("\n****** CardHand constructor (ArrayList<Cards>)");
        
        ArrayList<Card> cards = new ArrayList();
        cards.add(new Card(12));
        cards.add(new Card(25));
        cards.add(new Card(38));
        cards.add(new Card(9));
        cards.add(new Card(22));
        
        CardHand fullHouse = new CardHand(cards);
        System.out.println("Full House:\n");
        System.out.println(fullHouse.toString());
        System.out.println(fullHouse.toStringBrief());
        suitCounter = fullHouse.suitCount();
    }

    private void hasCards() {
        System.out.println("\n****** Check for presence of certain cards:");
        
        int aTwosAndFours [] = new int []{
            1, 3, 14, 16, 27, 29, 40, 42
        };
        
        CardHand twosAndFours = new CardHand(aTwosAndFours);
        System.out.println("Twos and fours\n");
        System.out.println(twosAndFours.toString());
        System.out.println(twosAndFours.toStringBrief());
        
        if (twosAndFours.hasSuit(4)){
            System.out.println("Has a spade");
        }
        else{
            System.out.println("Does not have a spade.");
        }
        
        if (twosAndFours.hasFaceValue(4)){
            System.out.println("Has a 4.");
        }
        else{
            System.out.println("Does NOT have a 4.");
        }
        
        if (twosAndFours.hasDeckValue(16)){
            System.out.println("Has a 4 of diamonds");
        }
        else{
            System.out.println("Does NOT have the four of diamonds.");
        }
        
        if (twosAndFours.hasDeckValue(52)){
            System.out.println("Has a ace of spades.");
        }
        else{
            System.out.println("Does NOT have the ace of spades.");
        }
    }

    private void playSequenceOfCards() {
        System.out.println("\n****** Play a sequence of cards: ******");
        
        Card aDiamonds [] = new Card []{
            new Card(14), new Card(15), new Card(16), new Card(17), 
            new Card(18), new Card(19), new Card(20), new Card(21),
            new Card(22), new Card(23), new Card(24), new Card(25),
            new Card(26)};
        
        CardHand diamonds = new CardHand(aDiamonds);
        System.out.println("Hand before play (all diamonds:)\n");
        System.out.println(diamonds.toString());
        System.out.println(diamonds.toStringBrief());
        
        System.out.println("\nPlay first 7 cards: \n");
        for (int i = 0; i < 7; i++){
        //Since the card is removed from teh hand when played,
        //the index of all its following cards becomes one less
        //than before;
        Card card = diamonds.playCardAt(0);//uses o each time to avoid index change
        System.out.println(card);
        }
        
        System.out.println("\nHand after playing first 7 cards:\n");
        System.out.println(diamonds.toString());
        System.out.println(diamonds.toStringBrief());
       
    }
    
 
   

    private void playRandomCards() {
        
        System.out.println("\n****** Play random cards: ******");
        
        Card aDiamonds [] = new Card []{
            new Card(14), new Card(15), new Card(16), new Card(17), 
            new Card(18), new Card(19), new Card(20), new Card(21),
            new Card(22), new Card(23), new Card(24), new Card(25),
            new Card(26)};
        
        CardHand diamonds = new CardHand(aDiamonds);
        System.out.println("Hand before play (all diamonds:)\n");
        System.out.println(diamonds.toString());
        System.out.println(diamonds.toStringBrief());
        
        System.out.println("\nPlay 7 random cards: \n");
        for (int i = 0; i < 7; i++){
        //Since the card is removed from teh hand when played,
        //the index of all its following cards becomes one less
        //than before;
        Card card = diamonds.playCardRandom();//uses o each time to avoid index change
        System.out.println(card);
        }
        
        System.out.println("\nHand after playiny 7 random cards:\n");
        System.out.println(diamonds.toString());
        System.out.println(diamonds.toStringBrief());
       
    }

    private void playSpecificCards() {
        
        System.out.println("\n****** Play specific cards: ******");
        
        Card aDiamonds [] = new Card []{
            new Card(14), new Card(15), new Card(16), new Card(17), 
            new Card(18), new Card(19), new Card(20), new Card(21),
            new Card(22), new Card(23), new Card(24), new Card(25),
            new Card(26)};
        
        CardHand diamonds = new CardHand(aDiamonds);
        System.out.println("Hand before play (all diamonds:)\n");
        System.out.println(diamonds.toString());
        System.out.println(diamonds.toStringBrief());
        
        System.out.println("\nPlay face cards (Jack, Queen, and King): \n");
        Card card = diamonds.playCard(23);//jack
        System.out.println(card);
        card = diamonds.playCard(24);//queen
        System.out.println(card);
        card = diamonds.playCard(25);//king
        System.out.println(card);
        
        System.out.println("\nHand after playiny face cards:\n");
        System.out.println(diamonds.toString());
        System.out.println(diamonds.toStringBrief());
       
    }
    
    
}
