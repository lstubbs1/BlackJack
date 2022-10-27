/*
 * Class makes sure invalid high scores are not created
 */

public class IllFormedHighScore extends Exception {

    private String reason;

    public IllFormedHighScore(String rsn) {
        reason = rsn;
    }

    @Override
    public String toString() {
        return "Ill formed high score" + reason;
    }
}