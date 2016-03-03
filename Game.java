import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Game is the class that facilitates the running of the game Mexico. 
 * 
 * @BevanStephen 5 June 2014
 * @Mk. I
 */
public class Game
{
    private int pot;
    private int bet;
    Scanner in = new Scanner(System.in);
    
    Player user = new Player();
    Player geoff = new Player();
    Player gavin = new Player();
    Scorer scoreTable = new Scorer();
    Random rand = new Random(System.currentTimeMillis());

    /**
     * Constructor for objects of class Game
     */
    public Game()
    {
        pot = 0;
        bet = 0;
        user.name = "";
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        game.startGame();
    }
    
    /**
     * Method for starting and playing through the game
     */
    public void startGame()
    {
        boolean newGame = true;
        
        displayHelp();
       
        System.out.println("What is your name? ");
        user.name = in.next();
        
        System.out.println("Okay " + user.name + ", get ready to play Mexico!");
        
        while(newGame)
        {
            int suggestBet;
            int random;
            char temp;
            char again;
            user.inGame = true;
            geoff.inGame = true;
            gavin.inGame = true;
            user.timesRolled = 0;
            geoff.timesRolled = 0;
            gavin.timesRolled = 0;
            
            int bet = setBet(); 
            
            user.money = bet * 3;
            geoff.money = bet * 3;
            gavin.money = bet * 3; 
            
            pot = bet * 9;
                
            while(user.inGame && (geoff.inGame || gavin.inGame))
            {
                user.timesRolled = 0;
                geoff.timesRolled = 0;
                gavin.timesRolled = 0;
                bet = 0;
                suggestBet = ' ';
                random = ' ';
                temp = ' ';
                again = ' ';
                
                displayStatus();
                
                userTakeTurn();                
                geoffTakeTurn();                
                gavinTakeTurn();
                
                System.out.println("");
                System.out.println("Rolling is concluded");
                System.out.println("----");
                System.out.println(user.name + " rolled a " + user.rollResult + ". ");
                if(geoff.inGame)
                    System.out.println("Geoff rolled a " + geoff.rollResult + ". ");
                if(gavin.inGame)
                    System.out.println("Gavin rolled a " + gavin.rollResult + ". ");
                System.out.println("----");
                System.out.println("");
                
                if(!geoff.inGame && gavin.inGame)
                {
                    while(user.rollScore == gavin.rollScore)
                    {
                        userVsGavinDuel();
                    }
                }
                if(geoff.inGame && !gavin.inGame)
                {
                    while(user.rollScore == geoff.rollScore)
                    {
                        userVsGeoffDuel();
                    }
                }
                else
                {
                    while(geoff.rollScore == user.rollScore && user.rollScore == gavin.rollScore)
                    {
                        threeWayDuel();
                    }
                    while(user.rollScore == geoff.rollScore && user.rollScore < gavin.rollScore)
                    {
                        userVsGeoffDuel();
                    }
                    while(user.rollScore == gavin.rollScore && user.rollScore < geoff.rollScore)
                    {
                        userVsGavinDuel();
                    }
                    while(gavin.rollScore == geoff.rollScore && gavin.rollScore < user.rollScore)
                    {
                        geoffVsGavinDuel();
                    }
                }
                
                settleMoneyScoring();
                
                System.out.println("Press Y and ENTER to continue to next round. ");
                again = ' ';
                again = in.next().charAt(0);
                while(!(again == 'Y' || again == 'y' || again == 'N' || again == 'n'))
                {
                    System.out.println("Please press Y and ENTER to continue.");
                    again = in.next().charAt(0);
                }
                System.out.println("\033[H\033[2J");
            }
           
            if(!geoff.inGame && !gavin.inGame)
            {
                System.out.println("\033[H\033[2J");
                System.out.println("Congratulations " + user.name + ", you have won the game of Mexico! ");
                System.out.println("You have received the pot, totalling at $" + pot + "! ");
            }
            else
            {
                System.out.println("\033[H\033[2J");
                System.out.println("Sorry " + user.name + ", you're out of the game. ");
            }
            
            System.out.println("Would you like to play again? Y/N");
            temp = in.next().charAt(0);
            while(!(temp == 'Y' || temp == 'y' || temp == 'N' || temp == 'n'))
            {
                System.out.println("Would you like to play again?");
                temp = in.next().charAt(0);
            }
            if(temp == 'N' || temp == 'n')
                newGame = false;
            else
                newGame = true;
        }    
        
        System.out.println("Goodbye, " + user.name + ". ");
        System.exit(0);
    }
    
    /**
     * Method for settling a duel between the User and Geoff
     */
    private void userVsGeoffDuel()
    {
        gavin.rollScore = 22;
        gavin.rollResult = "0-0";
        
        System.out.println("It's a tie between " + user.name + " and Geoff!");
        System.out.println("Time for a playoff round!");
        System.out.println("----");
        
        user.timesRolled = 0;
        geoff.timesRolled = 0;
        gavin.timesRolled = 0;
        
        userTakeTurn();        
        geoffTakeTurn();
            
        System.out.println("");
        System.out.println("Rolling is concluded");
        System.out.println("----");
        System.out.println(user.name + " rolled a " + user.rollResult + ". ");
        System.out.println("Geoff rolled a " + geoff.rollResult + ". ");
        System.out.println("----");
        System.out.println("");
        
        System.out.println("\033[H\033[2J");
    }
    
    /**
     * Method for settling a duel between the user and Gavin.
     */
    private void userVsGavinDuel()
    {
        geoff.rollScore = 22;
        geoff.rollResult = "0-0";
        
        System.out.println("It's a tie between " + user.name + " and Gavin!");
        System.out.println("Time for a playoff round!");
        System.out.println("----");
        
        user.timesRolled = 0;
        geoff.timesRolled = 0;
        gavin.timesRolled = 0;
        
        userTakeTurn();
        
        if(gavin.inGame)
        {
            gavin.rollTheDice(1,6);
            gavin.timesRolled++;
            System.out.println("Gavin has rolled a " + gavin.rollResult + ". ");
            try 
            {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            while((gavin.rollScore < user.rollScore) && gavin.timesRolled <= user.timesRolled)
            {
                gavin.rollTheDice(1,6);
                gavin.rollTheDice(1,6);
                System.out.println("Gavin has rolled a " + gavin.rollResult + ". ");
                gavin.timesRolled++;
                try 
                {
                    Thread.sleep(1000);
                }   
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        else
            gavin.inGame = false;
            
        System.out.println("");
        System.out.println("Rolling is concluded");
        System.out.println("----");
        System.out.println(user.name + " rolled a " + user.rollResult + ". ");
        System.out.println("Gavin rolled a " + gavin.rollResult + ". ");
        System.out.println("----");
        System.out.println("");
    }

    /**
     * Method to settle a duel between Geoff and Gavin.
     */
    private void geoffVsGavinDuel()
    {
        user.rollScore = 22;
        user.rollResult = "0-0";
        
        System.out.println("It's a tie between Geoff and Gavin!");
        System.out.println("Time for a playoff round!");
        System.out.println("----");
        
        user.timesRolled = 0;
        geoff.timesRolled = 0;
        gavin.timesRolled = 0;
        
        if(geoff.inGame)
        {
            geoff.rollTheDice(1,6);
            geoff.rollTheDice(1,6);
            geoff.timesRolled++;
            System.out.println("Geoff has rolled a " + geoff.rollResult + ". ");
            try 
            {
                Thread.sleep(1000);
            } catch(InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
            while(geoff.rollScore < 11 && geoff.timesRolled < 3)
            {
                geoff.rollTheDice(1,6);
                System.out.println("Geoff has now rolled a " + geoff.rollResult + ". ");
                geoff.timesRolled++;
                try 
                {
                    Thread.sleep(1000);
                }   
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        else
            geoff.inGame = false;
            
        if(gavin.inGame)
        {
            gavin.rollTheDice(1,6);
            gavin.timesRolled++;
            System.out.println("Gavin has rolled a " + gavin.rollResult + ". ");
            try 
            {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            while(gavin.rollScore < geoff.rollScore  && gavin.timesRolled <= geoff.timesRolled)
            {
                gavin.rollTheDice(1,6);
                gavin.rollTheDice(1,6);
                System.out.println("Gavin has rolled a " + gavin.rollResult + ". ");
                gavin.timesRolled++;
                try 
                {
                    Thread.sleep(1000);
                }   
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        else
            gavin.inGame = false;
            
        System.out.println("");
        System.out.println("Rolling is concluded");
        System.out.println("----");
        System.out.println("Geoff rolled a " + geoff.rollResult + ". ");
        System.out.println("Gavin rolled a " + gavin.rollResult + ". ");
        System.out.println("----");
        System.out.println("");
    }

    /**
     * Method for settling a three way tie.
     */
    private void threeWayDuel()
    {
        System.out.println("It's a tie between All three players!");
        System.out.println("Time for a playoff round!");
        System.out.println("----");
        
        user.timesRolled = 0;
        geoff.timesRolled = 0;
        gavin.timesRolled = 0;
        
        userTakeTurn();
        geoffTakeTurn();
        gavinTakeTurn();
        
        System.out.println("");
        System.out.println("Rolling is concluded");
        System.out.println("----");
        System.out.println(user.name + " rolled a " + user.rollResult + ". ");
        System.out.println("Geoff rolled a " + geoff.rollResult + ". ");
        System.out.println("Gavin rolled a " + gavin.rollResult + ". ");
        System.out.println("----");
        System.out.println("");
    }

    /**
     * Method for checking who had the lowest roll in the round.
     */
    private void settleMoneyScoring()
    {
        findLowestScores();
        settleBet();
    }
    
    /**
     * Method for identifying who had the lowest score in the round.
     */
    private void findLowestScores()
    {
        if(geoff.inGame && gavin.inGame)
        {
            if(user.rollScore < geoff.rollScore && user.rollScore < gavin.rollScore)
                user.lowest = true;
            else if(user.rollScore > geoff.rollScore && geoff.rollScore < gavin.rollScore)
                geoff.lowest = true;
            else if(user.rollScore > gavin.rollScore && geoff.rollScore > gavin.rollScore)
                gavin.lowest = true;
            else if(user.rollScore == geoff.rollScore && user.rollScore < gavin.rollScore)
            {
                user.lowest = true;
                geoff.lowest = true;
            }
            else if(user.rollScore == gavin.rollScore && user.rollScore < geoff.rollScore)
            {
                user.lowest = true;
                gavin.lowest = true;
            }
            else if(geoff.rollScore == gavin.rollScore && geoff.rollScore < user.rollScore)
            {
                geoff.lowest = true;
                gavin.lowest = true;
            }
            else if(geoff.rollScore == gavin.rollScore && geoff.rollScore == user.rollScore)
            {
                user.lowest = true;
                geoff.lowest = true;
                gavin.lowest = true;
            }
        }
        else if(!geoff.inGame && gavin.inGame)
        {
            if(user.rollScore < gavin.rollScore)
                user.lowest = true;
            else if(gavin.rollScore < user.rollScore)
                gavin.lowest = true;
            else if(gavin.rollScore == user.rollScore)
            {
                user.lowest = true;
                gavin.lowest = true;
            }
        }
        else if(geoff.inGame && !gavin.inGame)
        {
            if(user.rollScore < geoff.rollScore)
                user.lowest = true;
            else if(geoff.rollScore < user.rollScore)
                geoff.lowest = true;
            else if(geoff.rollScore == user.rollScore)
            {
                user.lowest = true;
                geoff.lowest = true;
            }
        }
    }

    /**
     * Checks which players lost and puts their bet into the pot.
     */
    private void settleBet()
    {
        if(user.lowest)
        {
            if(user.money < bet || user.money == bet)
            {
                user.money = 0;
                user.inGame = false;
            }
            else
            {
                user.money -= bet;
                System.out.println("Geoff: 'Hah! You lose this round, " + user.name + "! '");
            }
        }
        if(geoff.lowest)
        {
            if(geoff.money < bet || geoff.money == bet)
            {
                geoff.rollScore = 22;
                geoff.rollResult = "0-0";
                geoff.money = 0;
                geoff.inGame = false;
                System.out.println("Geoff: 'Damnit, I'm out! Guess I'll be at the bar 'til you're done. '");
            }
            else
            {
                geoff.money -= bet;
                System.out.println("Geoff: 'Looks like I lost that round. '");
            }
        }
        if(gavin.lowest)
        {
            if(gavin.money < bet || gavin.money == bet)
            {
                gavin.rollResult = "0-0";
                gavin.rollScore = 22;
                gavin.money = 0;
                gavin.inGame = false;
                System.out.println("Gavin: 'Aww, I lost! See you later then. '");
            }
            else
            {
                gavin.money -= bet;
                System.out.println("Gavin: 'I lost this round, but the next round awaits!'");
            }
        }
        user.lowest = false;
        geoff.lowest = false;
        gavin.lowest = false;
    }

    
    /**
     * Method for displaying players, status and money.
     */
    private void displayStatus()
    {
        String geoffIn;
        String gavinIn;
        String userIn;
        
        if(!user.inGame)
            userIn = "ELIMINATED";
        else
            userIn = "INGAME";
            
        if(!geoff.inGame)
            geoffIn = "ELIMINATED";
        else
            geoffIn = "INGAME";
            
        if(!gavin.inGame)
            gavinIn = "ELIMINATED";
        else
            gavinIn = "INGAME";
        
        System.out.println(userIn + " - " + user.name + " - $" + user.money);
        System.out.println(geoffIn + " - Geoff - $" + geoff.money);
        System.out.println(gavinIn + " - Gavin - $" + gavin.money);
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
     * Displays a brief "How to Play" guide for the player if they are unsure on how to play the game.
     */
    private void displayHelp()
    {
        char help;
        System.out.println("Would you like to view the 'How-To-Play' guide? Y/N");
        help = in.next().charAt(0);
        
        while(!(help == 'Y' || help == 'y' || help == 'N' || help == 'n'))
        {
            System.out.println("Would you like to view the 'How-To-Play' guide? Please select Y/N");
            help = in.next().charAt(0);
        }
        if(help == 'Y' || help == 'y')
        {
            System.out.println("This is a gambling-dice game called 'Mexico'. ");
            System.out.println("In this game, we have three players - You, Geoff and Gavin.");
            System.out.println("At the start of the game, the players decide what the bet for the entire game is. ");
            System.out.println("One of the NPCs will suggest a bet amount, which the player can either agree to or suggest their own amount. "); 
            System.out.println("If there are different amounts suggested, the other NPC will weigh in for a tie breaker. ");
            System.out.println("---- SCORING ----");
            System.out.println("The players take turns at rolling the two dice. When you roll, your result is displayed as the highest number, ");
            System.out.println("a dash, and then the lower number. An example of what your score would look like for rolling a 3 and a 5 might look like this: ");
            System.out.println("5-3");
            System.out.println("The scoring goes from 3-1, 3-2, 4-1, 4-2, 4-3, 5-1, upwards until 6-5. This is the lowest tier of score. Any double (5-5, 3-3 etc)");
            System.out.println("roll is in the second tier of score, which are stronger than the low tier. The final tier is a roll of 2-1 - This is called ");
            System.out.println("a 'Mexico'. It is the highest possible roll in the game. ");
            System.out.println("---- PLAYING THE GAME ----");
            System.out.println("The player rolls first, and then it goes to the NPCs. The objective is to not have the lowest score after the rolling is finished,");
            System.out.println("because the lowest roller has to place their bet into the pot. The winner is the only player with money at the end of the game. ");
            System.out.println("You may roll up to a total of three times if you are unhappy with your current roll, but by rolling you forfeit the previous ");
            System.out.println("roll you had for the new roll, so if you roll a third time and get a worse score than the previous one you must stay with it. ");
            System.out.println("If the first roller re-rolls a certain number of times, the other players can re-roll too if they wish, but it is not mandatory. ");
            System.out.println("Good luck!");
        }
    }
    
    /**
     * Method run at the start of a new game, used to set the bet for the game. 
     *
     * Returns an int to be set to Bet.
     */
    private int setBet()
    {
        String leader;
        String follower;
        int suggestBet = ' ';
        int random;
        char temp;
        
        if(geoff.inGame)
        {
            leader = "Geoff";
            follower = "Gavin";
        }
        else
        {
            leader = "Gavin";
            follower = "Geoff";
        }
        
        random = roll(5,10);
        if(leader == "Geoff")
        {
            System.out.println(leader + ": 'So how about a bet of... $" + random + "?'");
        }
        else
        {
            System.out.println(leader + ": 'So... $" + random + "?'");
        }
        System.out.println("Y/N?");
        temp = in.next().charAt(0);
        
        while(!(temp == 'Y' || temp == 'y' || temp == 'N' || temp == 'n'))
        {
            System.out.println(leader + ": Huh? I didn't catch that. How's $" + random + "?");
            System.out.println("Y/N?");
            temp = in.next().charAt(0);
        }
        
        if(temp == 'Y' || temp == 'y')
        {
            System.out.println(leader + ": 'Okay, $" + random + " it is!'");
            bet = random;
        }
        
        else if(temp == 'n' || temp == 'N')
        {
            System.out.println(leader + ": 'Then what do you think the bet should be? $1-$10'");
            System.out.print("$");
            if(in.hasNextInt())
                suggestBet = in.nextInt();
                            
            while(suggestBet > 10 || suggestBet < 1)
            {
                System.out.println(leader + ": 'Dude come on, be realistic!'");
                System.out.print("$");
                in.next().charAt(0);
                if(in.hasNextInt())
                    suggestBet = in.nextInt();
            }
            if(suggestBet == random)
            {
                System.out.println(leader + ": ...That's what I said. $" + random + " is the bet.'");
                bet = random;
            }
            else
            {
                System.out.println(leader + ": 'Damnit. " + follower + ", we need a tie-breaker!'");
                int support = roll(1,2);
                if(support == 2)
                {
                    System.out.println(follower + ": 'Hrm... Guess I'll agree with " + leader +
                                      " this time. $" + random + " it is!'");
                    bet = random;
                }
                else
                {
                    System.out.println(follower + ": 'Hrm... Guess I'll agree with " + user.name
                                    + " this time! $" + suggestBet + " it is.'");
                    bet = suggestBet;
                }
            }
        }
        return bet;
    }
    
    /**
     * method for user taking their turn
     */
    private void userTakeTurn()
    {
        user.rollTheDice(1,6);
        System.out.println("You rolled a " + user.rollResult + ". Roll again? Y/N");
        char again = ' ';
        again = in.next().charAt(0);
        
        while(!(again == 'Y' || again == 'y' || again == 'N' || again == 'n'))
        {
            System.out.println("Did you want to roll your " + user.rollResult + " again?");
            again = in.next().charAt(0);
        }
        while((again == 'y' || again == 'Y') && (user.timesRolled < 3))
        {
            user.rollTheDice(1,6);
            
            System.out.println("You rolled a " + user.rollResult + ". "); 
            System.out.println("You have rolled " + user.timesRolled + " times.");
            if(user.timesRolled < 3)
            {
                System.out.println("Roll again? Y/N");
            }
            else
            {
                System.out.println("You have rolled the maximum amount of retries. You may not roll again.");
                System.out.println("Press Y and ENTER to continue.");
            }
            again = ' ';
            again = in.next().charAt(0);
        }
    }
    
    /**
     * Method for Geoff taking his turn
     */
    private void geoffTakeTurn()
    {
        if(geoff.inGame)
        {
            geoff.rollTheDice(1,6);
            geoff.rollTheDice(1,6);
            geoff.timesRolled++;
            System.out.println("Geoff has rolled a " + geoff.rollResult + ". ");
            try 
            {
                Thread.sleep(1000);
            } catch(InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
            while(geoff.rollScore <= user.rollScore && geoff.timesRolled <= user.timesRolled)
            {
                geoff.rollTheDice(1,6);
                System.out.println("Geoff has now rolled a " + geoff.rollResult + ". ");
                geoff.timesRolled++;
                try 
                {
                    Thread.sleep(1000);
                }   
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        else
            geoff.inGame = false;
    }
    
    /**
     * Method for Gavin taking his turn.
     */
    private void gavinTakeTurn()
    {
        if(gavin.inGame)
        {
            gavin.rollTheDice(1,6);
            gavin.timesRolled++;
            System.out.println("Gavin has rolled a " + gavin.rollResult + ". ");
            try 
            {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            while((gavin.rollScore < user.rollScore || gavin.rollScore < geoff.rollScore) && gavin.timesRolled <= user.timesRolled)
            {
                gavin.rollTheDice(1,6);
                gavin.rollTheDice(1,6);
                System.out.println("Gavin has rolled a " + gavin.rollResult + ". ");
                gavin.timesRolled++;
                try 
                {
                    Thread.sleep(1000);
                }   
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        else
            gavin.inGame = false;
    }
}
