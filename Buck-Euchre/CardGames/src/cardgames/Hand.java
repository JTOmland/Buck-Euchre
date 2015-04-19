
package cardgames;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Hand {
    private Map<Player, Integer> score = new LinkedHashMap();
    private int totalTricks;
    
   
    public Hand(ArrayList<Player> playerList){
       for(int i = 0; i < playerList.size(); i++){
            score.put(playerList.get(i), 0);
        } 
       totalTricks=0;
    }
    public Map<Player, Integer> getTrickScore() {
        return score;
    }
    public void setTrickScore(Player player, int scoreAdd) {
        totalTricks++;
       // System.out.println("in hand.settrickscore and player is " + player.name + " scoreadd " + scoreAdd + " score before " + score.get(player));
        //System.out.println("");
        int fullScore = score.get(player) + scoreAdd;
        this.score.put(player, fullScore);
    }
    
    public void setBackupTrickScore(Player player, int score){
        this.score.put(player, score);
    }
     int getTrickScoreForPlayer(Player player) {
            return score.get(player);
    }

    int getTotalTricks() {
        return totalTricks;
    }
    
    void setTotalTricks(int totalTricks){
        this.totalTricks = totalTricks;
    }
    
   
        
        
}
