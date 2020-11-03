package ohtu;

public class Player {

    private String name;
    private String team;
    private String nationality;
    private int goals, assists;

    public Player(String name, String team, String nationality, int goals, int assists) {
        this.name = name;
        this.team = team;
        this.nationality = nationality;
        this.goals = goals;
        this.assists = assists;
    }

    public String getTeam() {
        return team;
    }

    public String getNationality() {
        return nationality;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getScore() {
        return goals + assists;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getScore() > 0
                ? name + " " + team + " " + nationality + " " + " " + goals + " + " + assists + " = " + getScore()
                : name + " " + team + " " + nationality + " " + getScore();
    }

}
