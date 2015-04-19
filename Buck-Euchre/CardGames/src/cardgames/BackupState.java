/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgames;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jeffomland
 */
public class BackupState {

    private int backupTurn;
    private Player backupHasLead;
    private int nodeNumber;
    private int lastPlayerNumber;
    private Card card;
    private Player player;
    private Map<Player, Integer> score = new LinkedHashMap();
    private int totalTricks;
    private int backupTotalCardsPlayed;
    private int trickNumber;
    private int trump;
    private Map<Player, Card> backupTrick = new LinkedHashMap();

    BackupState(int nodes) {
        this.nodeNumber = nodes;
    }

    BackupState(int nodes, Trick currentTrick, ArrayList<Player> playerList, Hand hand, int playerTurn, int totalCardsPlayed) {
         //backupState.setTotalCardsPlayed(totalCardsPlayed);
      //  System.out.println("New trick from backupState");
        lastPlayerNumber = playerTurn;

        Map<Player, Card> temp = currentTrick.getTrick();
        for (Map.Entry<Player, Card> entry : temp.entrySet()) {
            backupTrick.put(entry.getKey(), entry.getValue());
            //this.card = entry.getValue();
        }
        trump = currentTrick.getTrump();
        trickNumber = currentTrick.getTrickNumber();
        score.putAll(hand.getTrickScore());
        totalTricks = hand.getTotalTricks();
        player = playerList.get(playerTurn);
        backupTurn = playerTurn;
        backupHasLead = playerList.get(playerTurn).getHasLead();
        backupTotalCardsPlayed = totalCardsPlayed;
        nodeNumber = nodes;
    }
    
    public int getNode(){
        return nodeNumber;
    }

    public Map getBackupTrick() {
        return backupTrick;
    }

    public int getBackupTrump() {
        return trump;
    }

//    public void setBackupTrick(Trick backupTrick) {
//        System.out.println("New trick from backupState");
//        setBackupTrump(backupTrick.getTrump());
//        Map<Player, Card> temp = backupTrick.getTrick();
//        for (Map.Entry<Player, Card> entry : temp.entrySet()) {
//            //entry.getKey().getHand().addCard(entry.getValue());
//            this.backupTrick.addCard(entry.getValue(), entry.getKey());
//            // System.out.println("This is from setBackupTrick adding cards to make a real copy vs pointer");
//        }
//
//    }

    public Map<Player, Integer> getBackupScores() {
        return score;
    }

//    public void setBackupScores(int[] backupScores) {
//        this.backupScores = backupScores;
//    }
    public int getBackupTurn() {
        return backupTurn;
    }

    public Player getBackupHasLead() {
        return backupHasLead;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    int getBackupLastPlayer() {
        return lastPlayerNumber;
    }
    
    void setCard(Card card){
        this.card = card;
    }

    Card getCard() {
        
        return card;
    }

    Player getPlayer() {
        return player;
    }

    void setScore(Map<Player, Integer> trickScore) {
        this.score = new LinkedHashMap();
        Map<Player, Integer> temp = trickScore;
        for (Map.Entry<Player, Integer> entry : temp.entrySet()) {
                    //entry.getKey().getHand().addCard(entry.getValue());
            // System.out.println("In backupstate.setScore player:score " + entry.getKey().name + ":" + entry.getValue());
            this.score.put(entry.getKey(), entry.getValue());
        }

    }

    int getHandTotalTricks() {
        return totalTricks;
    }

    int getTrickNumber() {
        return trickNumber;
    }

    public int getBackupTotalCardsPlayed() {
        return backupTotalCardsPlayed;
    }
    
    public void addCard(Player player, Card card){
        backupTrick.put(player, card);
    }
    

}
