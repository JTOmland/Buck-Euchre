/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

import java.util.ArrayList;
import java.util.Collections;

public class PlayDecision {

    public PlayDecision() {

    }

    
        public void potentialPlay(Player player, int trump){
        
    }

    

    public Card getCardLead(Player player, int trump) {
        System.out.println("In getCardLead " + player.name + " has bid? " + player.hasBid);
        System.out.println(player.getHand().toStringBrief());
        //get potential plays
        ArrayList<Card> potentialPlays = new ArrayList();
        ArrayList<Card> sortedPlays = new ArrayList();

        //player.getHand().aa
        if (player.equals(player.getHasBid())) {
            //either lead trump or lead aces or lead suit to draw trump
            //Two strategies initially.  Either lead trump out until gone and then aces or lead out no trump to draw out trump
            //System.out.println("player bid " + player.getBid() + " : " + player.trumpBid);
            int ratio = player.trumpBid * 10 / player.bid;
            if (ratio > 5) {
                System.out.println("High trump content of bid ... lead trump out");
            } else {
                System.out.println("Low trump content lead out non trump");
            }
            if (!player.getHand().aa.isEmpty()) { // has trump
                for (int j = 0; j < player.getHand().getSize(); j++) {
                    if (player.getHand().getCard(j).getSuitValue() == trump) {
                        potentialPlays.add(player.getHand().getCard(j));
                    }
                }
              //  sortedPlays = sort(potentialPlays, trump, true);

            }
        }

        //  }
        System.out.println("Printing sorted play list");
        for (int k = 0; k < sortedPlays.size(); k++) {
            System.out.println(k + " : " + sortedPlays.get(k).toStringBrief());
        }
        Card temp = new Card(43);
        return temp;
    }

    public Card getCardPlay(Player player, Card leadCard, Card highCard, int trump) {
        Card temp = new Card(43);
        return temp;
    }

    

    private ArrayList<Card> sort(ArrayList<Card> unSortedList, int trump) {

        boolean swaps = true;
        // int index = 0;
        Card temp;//used for swapping orders in list
        Card temp1;
        //ArrayList<Card> sortedList = new ArrayList();
        // if (isTrump) {
        while (swaps) {
            swaps = false;
            for (int i = 0; i < unSortedList.size() - 1; i++) {
                    //System.out.println("I = " + i + " " + unSortedList.get(i).toStringBrief() + " : " + unSortedList.get(i+ 1).toStringBrief());
                //System.out.println("unsortedList size recheck " + unSortedList.size());
                if (unSortedList.get(i).getTrumpValue(trump,false) > unSortedList.get(i + 1).getTrumpValue(trump,false)) {
                    System.out.println("Swapping in sort of trumplist");
                    temp = unSortedList.get(i);
                    temp1 = unSortedList.get(i + 1);
                    unSortedList.remove(i);
                    unSortedList.remove(i);
                    unSortedList.add(i, temp);
                    // System.out.println("unsorted i" + unSortedList.get(i).toStringBrief());
                    unSortedList.add(i, temp1);
                    System.out.println("unsorted i" + unSortedList.get(i).toStringBrief());
                    System.out.println("unsorted i + 1" + unSortedList.get(i + 1).toStringBrief());
                       // sortedList.add(i, unSortedList.get(i + 1));

                    //sortedList.add(i + 1, temp);
                    swaps = true;
                }

            }
        }
        return unSortedList;

    }
}
