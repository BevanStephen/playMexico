import java.util.HashMap;
import java.util.HashSet;

/**
 * Scorer is a class for holding the score table for the game Mexico. 
 * 
 * @BevanStephen 6 June 2014
 * @Mk. I
 */
public class Scorer
{
    public static HashMap<String,Integer> scoreTable = new HashMap<>();

    /**
     * Constructor for objects of class Scorer
     */
    public Scorer()
    {
        fillScoreTable();
    }

    /**
    * Method fillScoreTable
    */
    private void fillScoreTable()
    {
        scoreTable.put("3-1", 1);
        scoreTable.put("3-2", 2);
        scoreTable.put("4-1", 3);
        scoreTable.put("4-2", 4);
        scoreTable.put("4-3", 5);
        scoreTable.put("5-1", 6);
        scoreTable.put("5-2", 7);
        scoreTable.put("5-3", 8);
        scoreTable.put("5-4", 9);
        scoreTable.put("6-1", 10);
        scoreTable.put("6-2", 11);
        scoreTable.put("6-3", 12);
        scoreTable.put("6-4", 13);
        scoreTable.put("6-5", 14);
        scoreTable.put("1-1", 15);
        scoreTable.put("2-2", 16);
        scoreTable.put("3-3", 17);
        scoreTable.put("4-4", 18);
        scoreTable.put("5-5", 19);
        scoreTable.put("6-6", 20);
        scoreTable.put("2-1", 21);
    }
}
