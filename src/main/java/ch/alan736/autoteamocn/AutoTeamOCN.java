/**
 * @author Alan736
 */
package ch.alan736.autoteamocn;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class AutoTeamOCN extends JavaPlugin implements Listener{

	private CommandsManager<CommandSender> commands;
	
	public void onEnabled(){
		
	}
	
	public void onDisable(){
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent PlayerJoin){
		
	}
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch(CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch(MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch(CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch(WrappedCommandException e) {
			if(e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
				e.printStackTrace();
			}
		} catch(CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}
		return true;
    }
    
    
    private void autoTeamOCN(Player Player){
    	ArrayList<String> PlayerInTheTeam = new ArrayList<String>();
    	String PlayerTeam = "";
    	// Get Player Team
    	try {
			Document PlayerPage = Jsoup.connect("https://oc.tc/" + Player.getDisplayName().toLowerCase()).get();
			PlayerPage.outputSettings().charset("UTF-8");
			Elements PlayerTeamPage = PlayerPage.select("#about.tab-pane.active div.row div.span4 blockquote a[href~=team]");
			PlayerTeam = PlayerTeamPage.text().toLowerCase();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	PlayerTeam = PlayerTeam.replaceAll("\\s","");
    	
    	// Get all the players in a team
    	try {
    		Document TeamPage = Jsoup.connect("https://oc.tc/teams/" + PlayerTeam).get();
    		TeamPage.outputSettings().charset("UTF-8");
    		Elements TeamPlayerPage = TeamPage.select("div.container section div.row div.span12 table.table.table-bordered.table-striped tbody tr td:first-child");
    		for (Element PlayerElement : TeamPlayerPage) {
    			PlayerInTheTeam.add(PlayerElement.text());
    		}
    	}catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	for(Player PlayerOnline : Bukkit.getOnlinePlayers()) {
    		if (PlayerInTheTeam.contains(PlayerOnline.getName())) {
    			
    		}
    	}
    	
    	Bukkit.broadcastMessage(PlayerTeam);
    	for(String pl : PlayerInTheTeam) {
    		Bukkit.broadcastMessage(pl);
    	}
    }
}
