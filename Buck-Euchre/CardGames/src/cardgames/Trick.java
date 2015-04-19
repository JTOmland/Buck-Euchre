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

/**
 *
 * @author jeffomland
 */
public class Trick {

    // private CardHand trick;
    private Player leader;
    private Card highCard;
    private Player lastToPlay;
    private Card cardLed;
    private static int trickNumber =0;
    private Player trickWinner;
    private int trump;
    private int suitLed;
    private boolean trickComplete = false;
    private boolean trumpHasBeenPlayed = false;
    private Map<Player, Card> trick = new LinkedHashMap();

    public Trick() {
       // System.out.println("Creating new trick");
        trickComplete = false;
      //  trickNumber += 1;
        highCard = new Card(2);
        //initial scores for a trick are zero

    }

    public int getSuitLed() {
        return suitLed;
    }

  

    public void addCard(Card card, Player player) {
        this.setLastToPlay(player);
        if (this.getTrick().isEmpty()) {
      //      System.out.println("first card in trick " + this.getTrick().size());
            setCardLed(card);
            this.suitLed = card.getSuitValue();
            setLeader(player);
            //suit is trump is jick is led
            if(card.getTrumpValue(trump,trumpHasBeenPlayed)>19){
                this.suitLed = trump;
            }
           // highCard = card;
        }
        if (card.getSuitValue() == trump || card.getTrumpValue(trump,trumpHasBeenPlayed)>19) {
            setTrumpHasBeenPlayed(true);
        }
       // System.out.println("trick.put " + player.name);
        trick.put(player, card);
        if (trick.size() == 4) {
            //trick completed
            trickComplete = true;
            trumpHasBeenPlayed = false;
            //System.out.println("Trick add card trick completed");
        }
        setHighCard();
    }

    public Card getCardLed() {
        return cardLed;
    }

    public void setCardLed(Card cardLed) {
        this.cardLed = cardLed;
    }

    public Map<Player,Card> getTrick() {
        return trick;
    }

    public void setTrick(LinkedHashMap trick) {
        this.trick = trick;
    }

    public Player getLeader() {
        return leader;
    }

    public void setHighCard() {
        if(trick.isEmpty()){
            System.out.println("Trick is empty");
        }
        for (Map.Entry<Player, Card> entry : trick.entrySet()) {
            //if card is suit led or trump then check
            if (entry.getValue().getSuitValue() == this.getSuitLed() || entry.getValue().getSuitValue() == this.getTrump() || entry.getValue().getTrumpValue(trump,trumpHasBeenPlayed)>19) {
                //check that it is the high card
                //System.out.println("getTrickWinner cardValue " + entry.getValue().toStringBrief() + " "+ entry.getValue().getTrumpValue(trump, trumpHasBeenPlayed) + " highCard " + highCard.getTrumpValue(trump,trumpHasBeenPlayed));
                if (entry.getValue().getTrumpValue(trump,trumpHasBeenPlayed) > highCard.getTrumpValue(trump,trumpHasBeenPlayed)) {
                    highCard = entry.getValue();
                    setTrickWinner(entry.getKey());// = entry.getKey();
                }
              // System.out.println("HighCard " + highCard.toStringBrief());
               // System.out.println("Trick size " + trick.size() + " trump " + trump);
            }
        }
       // System.out.println("returning from getTrickWinner");
    }

    public int getTrickNumber() {
        return trickNumber;
    }

    public void setTrickNumber(int trickNumber) {
        this.trickNumber = trickNumber;
    }
    
    public Player getTrickWinner(){
        return trickWinner;
        
    }

    public void setTrickWinner(Player trickWinner) {
        this.trickWinner = trickWinner;
    }
    
    public Card getHighCard(){
        return highCard;
    }

    public int getTrump() {
        return trump;
    }

    public void setTrump(int trump) {
        this.trump = trump;
    }

    public int getNumberOfCardsPlayedInTrick() {
        return trick.keySet().size();
    }

    public Player getLastToPlay() {
        return lastToPlay;
    }

    public void setLastToPlay(Player lastToPlay) {
        this.lastToPlay = lastToPlay;
    }
    
     public boolean isTrumpHasBeenPlayed() {
        return trumpHasBeenPlayed;
    }

    public void setTrumpHasBeenPlayed(boolean trumpHasBeenPlayed) {
        this.trumpHasBeenPlayed = trumpHasBeenPlayed;
    }

    public void printTrick() {
       // System.out.println("TrickNumber " + trickNumber);
       // System.out.println("Trick leader: " + getTrickWinner().name);
       // System.out.println("Leadcard " + cardLed.toStringBrief());

        for (Map.Entry<Player, Card> entry : trick.entrySet()) {
            System.out.print(entry.getKey().name + ":" + entry.getValue().toStringBrief() + ", ");
        }
      //  System.out.println("\n");
    }

    private void setLeader(Player player) {
        this.leader = player;
    }

}
