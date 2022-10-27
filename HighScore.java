public class HighScore {
    
    private String name;
    private int highScore;

    //IllFormedHighScore exception used here to make sure name and high score are not null
    public HighScore(String n, int hs) throws IllFormedHighScore {
        if (n == null) {
            throw new IllFormedHighScore("name is null");
        }
        if (hs <= 0) {
            throw new IllFormedHighScore("high score <= 0");
        }

        name = n;
        highScore = hs;
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getHighScore() {
        return highScore;
    }

    //Setters (setName is not necessary)
    public void setHighScore(int hs) {
        if (hs > highScore) {
            highScore = hs;
        }
    }   
}
