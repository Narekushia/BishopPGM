package ch.alan736.autoteamocn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class Commands {

    @Command(aliases = {"autoteam"}, desc = "Sends a message from the console.", min = 1, usage = "<message>")
    @CommandPermissions("cardinal.say")
	public static void autoteam(final CommandContext cmd, CommandSender sender) {
    	if(!(sender instanceof Player)) {
    		sender.sendMessage(ChatColor.RED + "Only Player can do that !");
    		return;
    	}
    			autoTeamOCN(Bukkit.getPlayer(sender.getName()));
    			return true;
    		}
    		
    		if (args.length == 1) {
    	    	if (Bukkit.getPlayer(args[0]) != null) {
        			autoTeamOCN(Bukkit.getPlayer(args[0]));
    	    	}
    	    	else {
    	    		sender.sendMessage(ChatColor.RED + "This Player doesn't exist !");
    	    	}

    			return true;
    		}
    		
    		if (args.length > 1) {
    			sender.sendMessage(ChatColor.RED + "I'm Sorry " + sender.getName() + ", I'm afraid I can't do that (Max 1 argument)");
    			return true;
    		}
    		
    	}
	}
}
