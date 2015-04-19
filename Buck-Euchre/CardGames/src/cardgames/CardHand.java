
package cardgames;

//This class represents a playing cards hand.
import java.util.ArrayList;
import java.util.Random;

//Cards can be played from and or added to the hand.
public class CardHand {

    private final String EMPTY_HAND = "Empty hand";
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Random random = new Random(); // Random number generation
    private int[] trackingOrder = new int[]{
        10, 13, 12, 11, 9, 8, 49, 52, 51, 50, 48, 47, 23, 26, 25, 24, 22, 21, 36, 39, 38, 37, 35, 34
    };
    private int[] a = new int[7];//trump map
    private int[] b = new int[5];
    private int[] c = new int[6];
    private int[] d = new int[6];
    private int [] suitCount = new int[4];
    private int[] combinedBitMap = new int[24];
    public StringBuilder buildHandOfDeck = new StringBuilder("000000000000000000000000");
    public String handOfDeck = "";
    public String handInTrumpOrder = "";
    String aa, bb, cc, dd, club, spade, diamond, heart;
    private int trumpBid;
    

    public int[] getCombinedBitMap() {
        return combinedBitMap;
    }
    public String getHandOfDeck(){
        return this.handOfDeck;
    }

    public void setCombinedBitMap(int trumpSuit) {
        handOfDeck = "";
        
        for(Card card: cards){
            //if card is trump put it in 
        }
        for (Card card : cards) {
            int k = 0;
            for (int j : trackingOrder) {
                if (card.getDeckValue() == j) {
                    buildHandOfDeck.setCharAt(k, '1');
                   // System.out.println("In CardHand setCombinedBitMap");
                   // System.out.println(card.getDeckValue() + " is a " + card.getFaceName()+ " of " + card.toStringBrief() + " with value of " + card.getFaceValue() + " clubs trump value of " + card.getTrumpValue(1));
                    //insert into array
                    combinedBitMap[k] = 1;
                } 
                k++;
            }
        }
        
        
        handOfDeck = String.valueOf(buildHandOfDeck);
        
        //if clubs is trum 0,6
        //if spades is trump 6, 13
        //if diamonds trump 12, 19
        
        club = handOfDeck.substring(0,6);
        spade = handOfDeck.substring(6,12);
        diamond = handOfDeck.substring(12,18);
        heart = handOfDeck.substring(18,24);
        int bowerSuit = 0;
        switch(trumpSuit){
            
            case 0: 
                //System.out.println("CardHand Trump is 0 (clubs)");
                aa = club;//club trump
                bb = spade;
                cc = diamond;
                dd = heart;
                break;
            
            case 1: // is really diamonds
               //System.out.println("Cardhand Trump is 1 (diamonds)");
                aa = diamond;//dieamond trump
                bb = heart;
                cc = club;
                dd = spade;
                break;
            case 2: 
               // System.out.println("CardHand Trump is 2 (hearts)");
                aa=heart;//hearts trump
                bb = diamond;
                cc = spade;
                dd = club;
                break;
            case 3: 
               // System.out.println("CardHand Trump is 3 (spades)");
                aa = spade;//spade trump
                bb = club;
                cc = diamond;
                dd = heart;
                break;
        }
        
        handInTrumpOrder = aa + bb + cc + dd;
        
        
    }

    //Empty hand allows a hand to be built up by adding cards to it
    public CardHand() {
        //Do nothing
    }

    public CardHand(int[] deckValues) {

        for (int i = 0; i < deckValues.length; i++) {
            Card card = new Card(deckValues[i]);
            cards.add(card);
        }
    }

    public CardHand(Card[] cards) {

        for (int i = 0; i < cards.length; i++) {
            Card card = new Card(cards[i].getDeckValue());
            this.cards.add(card);
        }

    }

    public CardHand(ArrayList<Card> cards) {
        for (Card card : cards) {
            Card newCard = new Card(card.getDeckValue());
            this.cards.add(newCard);
        }
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCard(int deckValue) {
        Card card = new Card(deckValue);
        cards.add(card);
    }

    public int getSize() {
        return cards.size();
    }

    public boolean hasDeckValue(int deckValue) {
        for (Card card : cards) {
            if (card.getDeckValue() == deckValue) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFaceValue(int faceValue) {
        //does hand contain this faceValue card
        for (Card card : cards) {
            if (card.getFaceValue() == faceValue) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSuit(int suitValue) {
        for (Card card : cards) {
            if (card.getSuitValue() == suitValue) {
                return true;
            }
        }
        return false;
    }

    public int[] suitCount() {

        int[] suitSplit = new int[4];
        for (int i = 0; i < suitSplit.length; i++) {
            for (int j = 0; j < cards.size(); j++) {
                if (cards.get(j).getSuitValue() == i + 1) {
                    suitSplit[i]++;
                }
            }
        }
        return suitSplit;
    }

    public Card getCard(int handLocation) {
        return cards.get(handLocation);
    }

    // Plays the card at a specified index
    //Removes the played card from the hand
    //The specified card, if present in the hand.  Null otehrwise.
    public Card playCardAt(int index) {
        int sizeHand = cards.size();
        if (index > sizeHand - 1) {
            return null;  //can't play out more than exist
        }

        Card card = cards.get(index);
        cards.remove(index);
        return card;
    }

    //Plays the card with a specified deck value.
    //REmoves the played card
    //Returns the specified card if present or null if not
    public Card playCard(int deckValue) {
       // System.out.println("PlayCard " + deckValue);
        int sizeHand = cards.size();
        for (int i = 0; i < sizeHand; i++) {
            Card card = cards.get(i);
            if (card.getDeckValue() == deckValue) {
                //matches card
                Card playCard = new Card(deckValue);
                cards.remove(i);
                return playCard;
            }
        }
        return null;
    }

    //plays a random card from the hand
    //removes played card from hand
    //The card played if there is a card to play null otherwise
    public Card playCardRandom() {
        int sizeHand = cards.size();
        if (sizeHand == 0) {
            return null;
        }
        //Get a random number between 0 and sizehadn - 1):
        int randomNumber = random.nextInt(sizeHand);
        Card card = cards.get(randomNumber);
        cards.remove(randomNumber);
        return card;
    }

    public String toString() {
        if (cards.isEmpty()) {
            return EMPTY_HAND;
        }

        StringBuilder sb = new StringBuilder("");
        for (Card card : cards) {
            sb.append(card.toString());
            sb.append(" \n");

        }
        return sb.toString().trim(); //trim() removes trailing space
    }

    public String toStringBrief() {
        if (cards.isEmpty()) {
            return EMPTY_HAND;
        }
        StringBuilder sb = new StringBuilder("");
        for (Card card : cards) {
            sb.append(card.toStringBrief());
            sb.append(" ");

        }
        return sb.toString().trim(); //trim() removes trailing space
    }
    public void countCardsBySuit(){
        int cCounter = 0, dCounter=0, hCounter=0, sCounter=0;
        for(int i = 0; i < cards.size(); i++){
        switch (cards.get(i).getSuitValue()){
                        case 1: cCounter++;
                            System.out.println("sorting hand counter of case 1 clubes " + cards.get(i).toStringBrief());
                        break;
                        case 2: dCounter++;
                        System.out.println("sorting hand counter of case 2 diamonds " + cards.get(i).toStringBrief());
                        break;
                        case 3: hCounter++;
                        break;
                        case 4: sCounter++;
                        break;
                }
                suitCount[0] = cCounter;
                suitCount[1] = dCounter;
                suitCount[2] = hCounter;
                suitCount[3] = sCounter;
        }
    }
    
    public void sortHand(int trump) {
        Card temp;
        Card temp1;
        boolean swaps = true;
        while (swaps) {
            swaps = false;
            for (int i = 0; i < cards.size()-1; i++) {                                      
                if(cards.get(i).getTrumpValue(trump, false) > cards.get(i+1).getTrumpValue(trump, false)){
                    temp = cards.get(i);
                    temp1 = cards.get(i+1);
                    cards.remove(i);
                    cards.remove(i);
                    cards.add(i, temp);
                    cards.add(i, temp1);
                    swaps = true;
                }

            }

        }
//        System.out.println("Printing sorted hand ");
//        for(Card card : cards){
//            System.out.println(card.toStringBrief() + " ");
//        }
        
        

    }

    

}
