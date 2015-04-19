/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

import java.util.Random;

/**
 *
 * @author Jeff Omland
 */
public class CardDeck {
    
    //One entry for each card deck value (1-52)
    //deck value = array index + 1
    //values:
    //true = card still present
    //false = card dealt (gone)
    private boolean [] deck = new boolean [52];
    private final String EMPTY_DECK = "Empty Deck";
    //Deck avlue (1-52) to exclude from the deck
    private int [] exclusions;
    
    private int size;  //Cards remaining
    
    private Random random = new Random();
    
   public CardDeck(){
       initDeck(null);
       
   } 
   
   //exclusions - deck values 1-52 to exclude from deck
   
   public CardDeck(int [] exclusions){
       initDeck(exclusions);
     
   }
    
    //

    private void initDeck(int [] exclusions) {
      //mark all cards present
       for (int i = 0; i < 52; i++){
         deck[i] = true;  
       }
       size = 52;
       if (exclusions == null){
           return;
       }
       
       //Save exclusions for later deck reset
       if (this.exclusions == null){
           this.exclusions = new int [exclusions.length];
           System.arraycopy(exclusions,0,this.exclusions,0,exclusions.length);
       }
       
       //Mark excluded cards absent
       for ( int i = 0; i < exclusions.length; i++){
           int deckValue = exclusions[i];
           if ((deckValue < 1) || (deckValue > 52)){
               throw new IllegalArgumentException();
           }
           deck[deckValue - 1] = false;
           //System.out.println("removed card with deckvalue " + deckValue);
           size--;
       }
    
    }
    
    //returns
    //A random card from the deck if the deck has remaining
    //otherwise null
    public Card dealCard(){
       if (size==0){
           return null;
       } 
       
       // get a random number between 1 and size
       //when size is 1 always return 1
       int randomNumber = random.nextInt(size) + 1;
       int present = 0;
       for (int i = 0; i < 52; i++){
           if (deck[i] == false){
               continue;  //Continue looking
           }
           present++;
           if (present == randomNumber){
               Card card = new Card(i + 1); // deck value = i + 1
               deck[i] = false;  //set card gone
               size--;
               return card;
           }
       }
       //this should never be reached.
       return null;
    }
    
    public CardHand dealHand(int handSize){
        if ((handSize <= 0) || (handSize > 52)){
            throw new IllegalArgumentException();
        }
        if (handSize > size){
            return null;
        }
        
        CardHand hand = new CardHand();
        for (int i = 0; i < handSize; i++){
            hand.addCard(dealCard());
        }
        return hand;
    }
    
    public int getSize(){
        return size;
    }
    
    public void resetDeck(){
        initDeck(exclusions);
    }
    
    public String toString(){
        if (size == 0){
            return EMPTY_DECK;
        }
        
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 52; i++){
        if (deck[i]){
            Card card = new Card(i+1);
            sb.append(card.toString());
            sb.append("\n");
        }
       
    }
         return sb.toString();  
    }
    
    public String toStringBrief(){
        if (size == 0){
            return EMPTY_DECK;
        }
        
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 52; i++){
        if (deck[i]){
            Card card = new Card(i+1);
            sb.append(card.toStringBrief());
            sb.append("\n");
        }
       
    }
         return sb.toString();  
        
    }
}
