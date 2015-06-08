package ch.alan736.bishoppgm;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OCNUtils {
	
	/**
	 * Get the members of an OCN Team
	 * @param Team The name of the team
	 * @return An array with the members of the team
	 */
    public static ArrayList<String> GetPlayersInATeam(String Team){
    	ArrayList<String> PlayerInTheTeam = new ArrayList<String>();

    	try {
    		Document TeamPage = Jsoup.connect("https://oc.tc/teams/" + Team.toLowerCase()).get();
    		TeamPage.outputSettings().charset("UTF-8");
    		if (TeamPage.select("div.container section div.page-header h1").text().contains("Teams"))
    			return null;
    		
    		Elements TeamPlayerPage = TeamPage.select("div.container section div.row div.span12 table.table.table-bordered.table-striped tbody tr td:first-child");

    		for (Element PlayerElement : TeamPlayerPage) {
    			PlayerInTheTeam.add(PlayerElement.text());
    		}
    	}catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return PlayerInTheTeam;
    }
    
    /**
     * Get an OCN team name from one of its member
     * @param player A player in the team we want to know
     * @return A string with the name of the team
     */
    public static String GetTeamOfAPlayer(Player player) {
    	String team = "";

    	try {
			Document PlayerPage = Jsoup.connect("https://oc.tc/" + player.getDisplayName().toLowerCase()).get();
			PlayerPage.outputSettings().charset("UTF-8");
			Elements PlayerTeamPage = PlayerPage.select("#about.tab-pane.active div.row div.span4 blockquote a[href~=team]");
			if (PlayerTeamPage.isEmpty())
				return null;
			team = PlayerTeamPage.text().toLowerCase();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
    	return team;
    }
    
    /**
     * Get the real OCN team name of a team (Example : istenxudo => Isten Xudo)
     * @param teamname The team name without spaces or special characters
     * @return The team name with spaces and special characters
     */
    public static String GetStylisedTeamName(String teamname) {
    	String StylisedName = "";
    	
    	try {
    		Document TeamPage = Jsoup.connect("https://oc.tc/teams/" + teamname.toLowerCase()).get();
    		TeamPage.outputSettings().charset("UTF-8");
    		StylisedName = TeamPage.select("html body div.container section div.page-header h1").text();
    	}
    	catch (IOException e) {
    		return null;
    	}
    	
    	return StylisedName;
    	
    }
}
