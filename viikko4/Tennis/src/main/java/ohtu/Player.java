package ohtu;

public class Player {

    private final String name;
    private int score;

    public Player(String playerName) {
        this.name = playerName;
        this.score = 0;
    }

    public void increaseScore() {
        this.score++;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    
}
