package ch.alan736.bishoppgm;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;

public class WhitelistCommands {

	public static class WhitelistParentCommand {
		@Command(aliases = {"whitelistteam", "wlteam"}, desc = "All Whitelist Command for Teams")
		@NestedCommand(WhitelistCommands.class)
	    public static void WithelistTeam(final CommandContext cmd, CommandSender sender) throws CommandException {
		}		
	}

	@SuppressWarnings("deprecation")
	@Command(aliases = {"add"}, desc = "Add all team members in the whitelist", min = 1, max = 1, usage = "<OCNteamname>")
    @CommandPermissions("autoteam.wlteam.add")
	public static void add(final CommandContext cmd, CommandSender sender) throws CommandException {
		ArrayList<String> Players = OCNUtils.GetPlayersInATeam(cmd.getString(0));
		if (Players == null)
			throw new CommandException("Team not found on OCN");
		
    	for(String p : Players) {
    		Bukkit.getOfflinePlayer(p).setWhitelisted(true);
    	}
    	
    	sender.sendMessage(ChatColor.GREEN + "" + Players.size() + " players added to the whitelist (" + cmd.getString(0) + ")");
    }
	
	@SuppressWarnings("deprecation")
	@Command(aliases = {"remove"}, desc = "Remove all team members in the whitelist", min = 1, max = 1, usage = "<OCNteamname>")
	@CommandPermissions("autoteam.wlteam.remove")
	public static void remove(final CommandContext cmd, CommandSender sender) throws CommandException {
		ArrayList<String> Players = OCNUtils.GetPlayersInATeam(cmd.getString(0));
		if (Players == null)
			throw new CommandException("Team not found on OCN");
		
    	for(String p : Players) {
    		UUID PlayerUUID = Bukkit.getOfflinePlayer(p).getUniqueId();
    		Bukkit.getOfflinePlayer(PlayerUUID).setWhitelisted(false);
    	}
    	
    	sender.sendMessage(ChatColor.RED + "" + Players.size() + " players removed from the whitelist (" + cmd.getString(0) + ")");
    }
}
	
