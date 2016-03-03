import java.util.*;
import java.util.Random;
/**
 * Player is the class that represents the players in the game. It holds it's own money, score and 
 * ingame status. It also handles rolling it's own dice and checking its scores. 
 * 
 * @BevanStephen 5 June 2014
 * @Mk. I
 */
public class Player
{
    public int timesRolled;
    public int money;
    public boolean inGame;
    public int firstRoll;
    public int secRoll;
    public String rollResult;
    public int rollScore;
    public int score;
    public String name;
    public boolean lowest;
    Random rand = new Random(System.currentTimeMillis());

    /**
     * Constructor for objects of class User
     */
    public Player()
    {
        money = 30;
        timesRolled = 0;
        lowest = false;
    }
    
    /**
     * rollTheDice is an amalgamation of methods that deals with rolling the dice, 
     * getting the results and interpreting them.
     */
    public void rollTheDice(int first, int second)
    {
       Random rand = new Random(System.currentTimeMillis());
       firstRoll = roll(first,second);
       secRoll = roll(first,second);
       rollResult = thrownResult(firstRoll,secRoll);
       rollScore = findScore(rollResult);
       timesRolled++;
    }

    /**
     * Method for testing strength of current roll.
     */
    private int findScore(String rollResult)
    {
        return Scorer.scoreTable.get(rollResult);
    }
    
    /**
     * Method for rolling random numbers
     * 
     * @param min Minimun value
     * @param max Maximum value
     */
    private int roll(int min, int max)
    {
        int randomNum = min + (int)(Math.random() * ((max - min) + 1));
        return randomNum;
    }

    /**
     * Method for checking what the roller's score is.
     * 
     * @param firstNum firstNum is the largest of the two rolls, the first one. 
     * @param secNum secNum is the smaller of the two rolls, the second placed one. 
     */
    private String thrownResult(int rollOne, int rollTwo)
    {
        String preResult;
        if(rollOne < rollTwo)
        {
            int temp = rollTwo;
            rollTwo = rollOne;
            rollOne = temp;
        }
        
        preResult = rollOne + "-" + rollTwo;
        return preResult;
    }
}