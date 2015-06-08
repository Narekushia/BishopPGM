package ch.alan736.bishoppgm;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;

import in.twizmwaz.cardinal.event.TeamNameChangeEvent;
import in.twizmwaz.cardinal.module.modules.team.TeamModule;
import in.twizmwaz.cardinal.util.TeamUtils;


public class Commands {

    @SuppressWarnings("deprecation")
	@Command(aliases = {"autoteam"}, desc = "Compose a team with an OCN Team", min = 2, max = 2, usage = "<teamname> <OCNteamname>")
    @CommandPermissions("autoteam.autoteam")
	public static void autoteam(final CommandContext cmd, CommandSender sender) throws CommandException {
    	@SuppressWarnings("rawtypes")
		TeamModule team = TeamUtils.getTeamByName(cmd.getString(0));
    	
    	if (team == null)
    		throw new CommandException("No teams matched query");
    	
    	ArrayList<String> Players = OCNUtils.GetPlayersInATeam(cmd.getString(1));
    	
    	if (Players == null)
    		throw new CommandException("No OCN teams matched query");
    	
    	// Remove all players from the team
    	for (Player p : Bukkit.getOnlinePlayers()) {
    		if (TeamUtils.getTeamByPlayer(p) == team)
    			Bukkit.getServer().dispatchCommand(p, "join observers");
    	}
    	
    	// Add players to the team
    	for (String p : Players) {
    		OfflinePlayer BukkitPlayer = Bukkit.getOfflinePlayer(p);
    		if (BukkitPlayer.isOnline())
    			team.add(BukkitPlayer.getPlayer());
    	}
    	
    	// Change Display Name
    	team.setName(OCNUtils.GetStylisedTeamName(cmd.getString(1)));
    	Bukkit.getServer().getPluginManager().callEvent(new TeamNameChangeEvent(team));
    	
    	sender.sendMessage(ChatColor.GREEN + "" + team.size() + " players moved to the team '" + team.getName() + "'");
	}
}
    

