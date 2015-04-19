/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import cardgames.Card;
import cardgames.Player;
import cardgames.Trick;

/**
 *
 * @author Jeff Omland
 */
public class CardTest {
    
    public static void main(String[] args){
    
        System.out.println("Testing the Card class:");
        CardTest test = new CardTest();
        
        //test.cardToString();
        //test.cardToSTringBrief();
        test.cardToDeckValue();
        test.cardToFaceValue();
        test.cardToTrumpValue();
        //test.cardEquals();
        //test.cardSuiteValue();
        //test.illegalArguments();
        int num = 2;
        int num2 = 4;
        //System.out.println(" 2 divided by 4 " + num/num2);
        test.trickTest();
        
        System.out.println();
                
}
    
    private void trickTest(){
        Trick trick = new Trick();
        trick.setTrump(3);
        Player playerOne = new Player("playerOne");
         Player playerTwo = new Player("playerTwo");
          Player playerThree = new Player("playerThree");
           Player playerFour = new Player("playerFour");
           Card firstCard = new Card(21);
           Card secondCard = new Card(26);
           Card thirdCard = new Card(23);
           Card fourthCard = new Card(47);
//                   
           
           trick.addCard(firstCard,playerOne);
           trick.addCard(secondCard, playerTwo);
           trick.addCard(thirdCard, playerThree);
           trick.addCard(fourthCard, playerFour);
           trick.printTrick();
           System.out.println("TrickWinner " + trick.getTrickWinner().name);
           //System.out.println("TrickWinner " + trick.getTrickWinner()
        
    }
    
    private void cardSuiteValue(){
       // Card card = new Card(1);
        for(int i = 1; i < 52; i++){
            Card card = new Card(i);
            System.out.println(card.toStringBrief() + " : " + card.getSuitValue());
        }
    }

    private void cardToString() {
        System.out.println("\n****** Cards.toString(): ******");
        
        for (int i=1; i<=52; i++){
            Card card = new Card(i);
            System.out.println(card.toString());
            
            if (i % 13 == 0){
                System.out.println();
            }
        }
        
     
    }

    private void cardToSTringBrief() {
         System.out.println("\n****** Cards.toStringBrief(): ******");
        
        for (int i=1; i<=52; i++){
            Card card = new Card(i);
            System.out.print(card.toStringBrief()  + " ");
            
            if (i % 13 == 0){
                System.out.println();
            }
        }
    }

    private void cardToDeckValue() {
        System.out.println("\n****** Cards.toDeckValu(): *******");
        
        for (int i = 0; i <= 3; i++){
            for ( int j = 2; j <= 14; j++){
                System.out.println((new Card(j,i)).toString() + ": " + Card.toDeckValue(j,i));
            }
            System.out.println();
        }
    }
    
    private void cardToTrumpValue() {
        System.out.println("\n****** Cards.toTrumpValue(): *******");
        System.out.println("Trump = " + 3);
        
        for (int i = 0; i <= 3; i++){
            for ( int j = 2; j <= 14; j++){
                Card myCard = new Card(j,i);
                System.out.println(myCard.getTrumpValue(3,false) + ": " + myCard.toString());
            }
            System.out.println();
        }
    }
    
    private void cardToFaceValue(){
        System.out.println("\n****** Cards.toFaceValue(): *******");
        
        for (int i = 0; i <= 3; i++){
            for ( int j = 2; j <= 14; j++){
                System.out.println((new Card(j,i)).toString() + ": " + Card.toFaceValue(Card.toDeckValue(j,i)));
            }
            System.out.println();
        }
        
    }

    private void cardEquals() {
        System.out.println("\n****** Card.equals(): ******");
        
        Card card1 = new Card(12);
        Card card2 = new Card(25);
        System.out.println(card1.toString() + ((card1.equals(card2)) ? " == " : " != ") + card2.toString());
        
        Card card3 = new Card(52);
        Card card4 = new Card(52);
        System.out.println(card3.toString() + ((card3.equals(card4)) ? " == " : " != ") + card4.toString());
    }
    
    private void illegalArguments() {
        System.out.println("\n****** Illegal arguments: ******");
        
        Card card = new Card(53);
        System.out.println(card.toString());
    }
    
}
