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
public class RootCardScore {

    //create map to track all scores for all players for each rootcard
    Map<Player, Integer> zeroCount = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> oneCount = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> twoCount = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> threeCount = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> fourCount = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> fiveCount = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> sixCount = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> max = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> min = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> average = new LinkedHashMap<Player, Integer>();
    Map<Player, Integer> sum = new LinkedHashMap<Player, Integer>();

    int count = 0;
    int tempCount = 0;

    RootCardScore(ArrayList<Player> playerList) {
        for (Player entry : playerList) {
            zeroCount.put(entry, 0);
            oneCount.put(entry, 0);
            oneCount.put(entry, 0);
            threeCount.put(entry, 0);
            fourCount.put(entry, 0);
            fiveCount.put(entry, 0);
            sixCount.put(entry, 0);
            max.put(entry, 0);
            min.put(entry, 0);
            average.put(entry, 0);
            sum.put(entry, 0);
        }
    }

    public void addScores(Map<Player, Integer> tempScoreMap) {
        count++;
        for (Map.Entry<Player, Integer> entry : tempScoreMap.entrySet()) {
            switch (entry.getValue()) {
                case 0:
                    tempCount = zeroCount.get(entry.getKey()) + 1;
                    zeroCount.put(entry.getKey(), tempCount);
                    tempCount = 0;
                    break;
                case 1:
                    tempCount = oneCount.get(entry.getKey()) + 1;
                    oneCount.put(entry.getKey(), tempCount);
                    tempCount = 0;
                    break;
                case 2:
                    tempCount = oneCount.get(entry.getKey()) + 1;
                    oneCount.put(entry.getKey(), tempCount);
                    tempCount = 0;
                    break;
                case 3:
                    tempCount = threeCount.get(entry.getKey()) + 1;
                    threeCount.put(entry.getKey(), tempCount);
                    tempCount = 0;
                    break;
                case 4:
                    tempCount = fourCount.get(entry.getKey()) + 1;
                    fourCount.put(entry.getKey(), tempCount);
                    tempCount = 0;
                    break;
                case 5:
                    tempCount = fiveCount.get(entry.getKey()) + 1;
                    fiveCount.put(entry.getKey(), tempCount);
                    tempCount = 0;
                    break;
                case 6:
                    tempCount = sixCount.get(entry.getKey()) + 1;
                    sixCount.put(entry.getKey(), tempCount);
                    tempCount = 0;
                    break;
            }
            if (entry.getValue() > max.get(entry.getKey())) {
                max.put(entry.getKey(), entry.getValue());
            }
            if (entry.getValue() < min.get(entry.getKey())) {
                min.put(entry.getKey(), entry.getValue());
            }

            sum.put(entry.getKey(), sum.get(entry.getKey()) + entry.getValue());
            average.put(entry.getKey(), sum.get(entry.getKey())*100 / count);
        }
    }

    public Map<Player, Integer> getOneCount() {
        return oneCount;
    }

    public Map<Player, Integer> getTwoCount() {
        return twoCount;
    }

    public Map<Player, Integer> getZeroCount() {
        return zeroCount;
    }

    public Map<Player, Integer> getThreeCount() {
        return threeCount;
    }

    public Map<Player, Integer> getFourCount() {
        return fourCount;
    }

    public Map<Player, Integer> getFiveCount() {
        return fiveCount;
    }

    public Map<Player, Integer> getSixCount() {
        return sixCount;
    }

    public Map<Player, Integer> getMax() {
        return max;
    }

    public Map<Player, Integer> getMin() {
        return min;
    }

    public Map<Player, Integer> getAverage() {
        return average;
    }

    public Map<Player, Integer> getSum() {
        return sum;
    }

    public int getCount() {
        return count;
    }

    public int getTempCount() {
        return tempCount;
    }
}
