/**
 * @author Alan736
 */
package ch.alan736.bishoppgm;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class BishopPGM extends JavaPlugin {

	private CommandsManager<CommandSender> commands;
	
	@Override
	public void onEnable(){
		setupCommands();
	}
	
	@Override
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
    
    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(Commands.class);
        cmdRegister.register(WhitelistCommands.WhitelistParentCommand.class);
    } 
}
