package ohtu;

public class TennisGame {

    private Player player1;
    private Player player2;
    private final String[] scores;

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);

        scores = new String[5];
        scores[0] = "Love";
        scores[1] = "Fifteen";
        scores[2] = "Thirty";
        scores[3] = "Forty";
        scores[4] = "Deuce";
    }

    public void wonPoint(String playerName) {
        if (player1.getName().equals(playerName)) {
            player1.increaseScore();
        } else {
            player2.increaseScore();
        }
    }

    public String getScore() {
        if (gameIsWon()) {
            return "Win for " + leader().getName();
        }

        if (playerHasAdvantage()) {
            return "Advantage " + leader().getName();
        }

        if (scoreDifference() == 0) {
            return leader().getScore() >= 4 ? scores[4] : scores[player1.getScore()] + "-All";
        }
        
        return scores[player1.getScore()] + "-" + scores[player2.getScore()]; 
    }

    private boolean gameIsWon() {
        return leader().getScore() >= 4 && scoreDifference() >= 2;
    }

    private boolean playerHasAdvantage() {
        return leader().getScore() >= 4 && scoreDifference() == 1;
    }

    private Player leader() {
        return player1.getScore() > player2.getScore() ? player1 : player2;
    }

    private int scoreDifference() {
        return Math.abs(player1.getScore() - player2.getScore());
    }
}
