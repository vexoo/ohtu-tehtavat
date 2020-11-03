package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);
        ArrayList<Player> finPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getNationality().equals("FIN")) {
                finPlayers.add(player);
            }
        }
        Collections.sort(finPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Double.compare(o2.getScore(), o1.getScore());
            }
        });
        
        for (Player player : finPlayers) {
            System.out.println(player);
        }
    }
}
