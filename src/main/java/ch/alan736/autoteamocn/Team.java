package ch.alan736.autoteamocn;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Team {
	
	private List<String> TeamMate = new ArrayList<String>();
	private ChatColor Color = ChatColor.WHITE;
	
	public void addMembers(Player Player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		org.bukkit.scoreboard.Team equipe = board.registerNewTeam("teamname");
	}
}
