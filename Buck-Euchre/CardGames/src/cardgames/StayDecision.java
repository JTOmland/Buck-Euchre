package cardgames;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jeffomland
 */
public class StayDecision {
    //to decide to stay need to know suit, bid, and then check pattern

    private static ArrayList<String> stayPatterns = new ArrayList();
    //private static ArrayList<String> cardPattern = new ArrayList();
    private boolean stay = false;
    private boolean patternNotFound = false;
    private Random rnd = new Random();
    int randomInt = rnd.nextInt(100);
    private int patternScore = 0;

    public StayDecision(Player player, int bid, int suit) {
        String sBid = String.valueOf(bid);
        player.getHand().setCombinedBitMap(suit);
        //cardPattern.clear();
       // System.out.println("In stayDecision " + player.name);
        for (String s : stayPatterns) {
            //System.out.println("StayPatterns length " + stayPatterns.size() + " and s " + s);
            //System.out.println(player.getHand().aa + " " + player.getHand().bb + " " + player.getHand().cc + " " + player.getHand().dd );
            if (s.substring(0, 24).equalsIgnoreCase(player.getHand().aa + player.getHand().bb + player.getHand().cc + player.getHand().dd)
                    || s.substring(0, 24).equalsIgnoreCase(player.getHand().aa + player.getHand().bb + player.getHand().dd + player.getHand().cc)) {
                //System.out.println("Value of pattern for stay decision is " + s.substring(s.length() - 2));
                patternScore = Integer.valueOf(s.substring(s.length()-2));
                patternNotFound = false;
                break;
            } else {
                patternNotFound = true;

                // System.out.println("In stayDecision and no pattern match so score is ");
            }

        }
        if (stayPatterns.isEmpty() || patternNotFound) {
            stayPatterns.add(player.getHand().aa + player.getHand().bb + player.getHand().cc + player.getHand().dd + sBid + "50");
            patternScore = 50;
            if (stayPatterns.isEmpty()) {
               // System.out.println("In stayDecision first pass at patterns ");
            }
            if (patternNotFound) {
                //System.out.println("Pattern Not Found ");
            }
            patternNotFound = false;
        }
//        for(String s : stayPatterns) {
//            System.out.println("Pattern " + s);
//            
//        }
        //System.out.println("Returning from stayDecision" + " pattern List length is " + stayPatterns.size());
        if(patternScore > 30){
            stay = true;
        } else{
            stay = false;
        }
       // stay = true;//temp all players stay

    }

    public boolean isStay() {
        return stay;
    }

}
