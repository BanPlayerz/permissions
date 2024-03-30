package at.banplayerz.ep.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.banplayerz.ep.Main;

public class Group_Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(Main.getInstance().getMethods().playerExists(p.getUniqueId().toString())) {
				if(Main.getInstance().getMethods().tempRang(p.getUniqueId().toString())) {					
					p.sendMessage(Main.getInstance().getLanguage().getMessage("currentGroupTemp").replace("%group%", 
							Main.getInstance().getMethods().getGroupColor(Main.getInstance().getMethods().
							getPlayerGroup(p.getUniqueId().toString()))).replace("%date%", Main.getInstance().getMethods().
							millisToDate(Main.getInstance().getMethods().getTempRangEnd(p.getUniqueId().toString()))));
				} else {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("currentGroup").replace("%group%", 
							Main.getInstance().getMethods().getGroupColor(Main.getInstance().getMethods().getPlayerGroup(p.getUniqueId().toString()))));
				}
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
			}
		}
		return false;
	}

}
