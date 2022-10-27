import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;
import java.io.PrintWriter;


public class BlackJack {
    
    public static ArrayList<HighScore> list = new ArrayList<>();
    public static Scanner s = new Scanner(System.in);

    public static String playerName;
    public static int score;

    //Print all the scores saved on highscores.csv
    public static void printAllScores() {
        for (HighScore hs : list) {
            System.out.println(hs.getHighScore() + "," + hs.getName());
        }
    }

    //Print individual high score of the player
    public static void printHighScores(String playerName) {
        for (HighScore hs : list) {
            String name = hs.getName();
            if (name.equals(playerName)) {
                System.out.println(hs.getHighScore());
            }
        }
    }

    //play BlackJack
    public static void playBlackJack() throws IllFormedHighScore {
        //Random is used to create hands for the player and dealer
        Random rand = new Random();
        int w = rand.nextInt(11) + 1;
        int x = rand.nextInt(11) + 1;
        int y = rand.nextInt(11) + 1;
        int z = rand.nextInt(11) + 1;
        int dealer = x + y;
        int player = w + z;
        while (true) {
            //If player is above 21, break is used to exit the while loop
            if (player > 21) {
                break;
            }
            System.out.println("Your hand: " + player);
            //Options for blackjack using integers
            System.out.println("Do you wish to: 1. HIT or 2. STAY?");
            int desc = s.nextInt();
            if (desc == 1) {
                int a = rand.nextInt(11) + 1;
                player += a;
            }
            else if (desc == 2) {
                break;
            }
            else {
                System.out.println("Invalid Option");
            }
        }

        //Print hands of both dealer and player to see both scores
        System.out.println("Your hand: " + player);
        System.out.println("Dealer's hand: " + dealer);

        //Player loses because above 21
        if (player > 21) {
            System.out.println("You broke");
            System.out.println("Dealer wins");
        }
        else {
            //dealer has to be under 21 and must be above player hand to win
            if (player <= dealer && dealer <= 21) {
                System.out.println("Dealer wins");
            }
            else {
                System.out.println("Congrats, you won");
                score += 1;
            }
        }

        //boolean used to see if there is an existing score for player
        boolean found = false;
        for (HighScore hs : list) {
            String name = hs.getName();
            int highScore = hs.getHighScore();
            //if existing name exists, and if the current score is higher than high score 
            //set high score to current score
            if (name.equals(playerName)) {
                if (score >= highScore) {
                    hs.setHighScore(score);
                    System.out.println("New high score");
                    found = true;
                }
            }
        }
        //if found is false, create a new HighScore instance, keeping in mind 
        //the IllFormedHighScore exception
        if (found == false) {
            if (score > 0) {
                HighScore h = new HighScore(playerName, score);
                list.add(h);
            }
        }
    }
    
    
    public static void main(String[] args) throws IllFormedHighScore {

        try {
            //Read in data from highscores.csv
            Scanner sc = new Scanner(new FileInputStream("highscores.csv"));
            sc.useDelimiter(",|\n");

            String name;
            int hscore;

            while (sc.hasNext()) {
                hscore = sc.nextInt();
                name = sc.next();
                HighScore hisc = new HighScore(name, hscore);
                list.add(hisc);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e.toString());
            return;
        }
        catch (IllFormedHighScore e) {
            System.out.println(e.toString());
            return;
        }
        

        //Prompt user to enter name and set their player name and score
        System.out.println("Please enter your name");
        playerName = s.next();
        score = 0;
        while (true) {
            //Menu
            System.out.println("Please select one of the options:");
            System.out.println("1. See your score");
            System.out.println("2. Print all high scores");
            System.out.println("3. Print your high score");
            System.out.println("4. Play BlackJack");
            System.out.println("5. Quit");

            int option = s.nextInt();

            //Print current score
            if (option == 1) {
                System.out.println("Current score: " + score);
            }
            //Print all high scores in highscores.csv
            else if (option == 2) {
                printAllScores();
            }
            //Print high score of player
            else if (option == 3) {
                printHighScores(playerName);
            }
            //play black jack
            else if (option == 4) {
                playBlackJack();
            }
            //quit
            else if (option == 5) {
                try {
                    //read player data into higscores.csv
                    PrintWriter pw = new PrintWriter(new FileOutputStream("highscores.csv"));
                    for (HighScore hs : list) {
                        String name = hs.getName();
                        int hscore = hs.getHighScore();
                        pw.println(hscore + "," + name);
                    }
                    list.clear();
                    pw.close();
                    s.close();
                    System.exit(0);
                }
                catch (FileNotFoundException e) {
                    System.out.println("File not found " + e.toString());
                    return;
                }
            }
            //return to menu to enter new value
            else {
                System.out.println("Not a valid option");
            }
        }
    }
}
