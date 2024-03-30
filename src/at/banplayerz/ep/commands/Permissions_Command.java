package at.banplayerz.ep.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import at.banplayerz.ep.Main;
import at.banplayerz.ep.utils.UUIDFetcher;

public class Permissions_Command implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			Main.getInstance().getMethods().sendCommandInfo(sender);
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("groups")) {
				if(sender.hasPermission("permissions.groups")) {
					sender.sendMessage(Main.getInstance().getLanguage().getMessage("groups"));
					for(String group : Main.getInstance().getMethods().getGroups()) {
						sender.sendMessage("§7» §f"+group+" §7("+Main.getInstance().getMethods().getGroupColor(group)+"§7)");
					}
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else if(args[0].equalsIgnoreCase("help")) {
				if(sender.hasPermission("permissions.info")) {
					Main.getInstance().getMethods().sendHelp(sender);
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else {
				Main.getInstance().getMethods().sendCommandInfo(sender);
			}
		} else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("user")) {
				if(sender.hasPermission("permissions.userinfo")) {
					String uuid;
					
					try {
						 uuid = UUIDFetcher.getUUID(args[1]).toString();
					} catch(Exception ex) {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerError").replace("%playername%", args[1]));
						return true;
					}
					
					String exactPlayerName;
					
					try {
						exactPlayerName = UUIDFetcher.getName(UUID.fromString(uuid));
					} catch(Exception ex) {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerError").replace("%playername%", args[1]));
						return true;
					}
					
					if(Main.getInstance().getMethods().playerExists(uuid)) {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerInformation").replace("%playername%", exactPlayerName));
						if(Main.getInstance().getMethods().tempRang(uuid)) {
							sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerInformationGroup").
									replace("%group%", Main.getInstance().getMethods().getGroupColor(Main.getInstance().getMethods().getPlayerGroup(uuid)))+" "+
									Main.getInstance().getLanguage().getMessage("playerInformationTempRang").replace("%group%",
									Main.getInstance().getMethods().getGroupColor(Main.getInstance().getMethods().getTempRangGroup(uuid))).replace("%date%", 
									Main.getInstance().getMethods().millisToDate(Main.getInstance().getMethods().getTempRangEnd(uuid))));
						} else {
							sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerInformationGroup").
									replace("%group%", Main.getInstance().getMethods().getGroupColor(Main.getInstance().getMethods().getPlayerGroup(uuid))));
						}
						
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerInformationPermissions"));
						
						for(String permission : Main.getInstance().getMethods().getPlayerPermissions(uuid)) {
							sender.sendMessage("§7» §e"+permission);
						}
					} else {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerError").replace("%playername%", exactPlayerName));
					}
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else if(args[0].equalsIgnoreCase("group")) {
				if(sender.hasPermission("permissions.groupinfo")) {
					if(Main.getInstance().getMethods().groupExists(args[1])) {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("groupInformation").
								replace("%group%", Main.getInstance().getMethods().getGroupColor(args[1])));
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("playersInGroup"));
						
						for(String uuid : Main.getInstance().getMethods().getGroupPlayers(args[1])) {
							sender.sendMessage("§7» §e"+UUIDFetcher.getName(UUID.fromString(uuid)));
						}
						
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerInformationPermissions"));
						
						for(String permission : Main.getInstance().getMethods().getGroupPermissions(args[1])) {
							sender.sendMessage("§7» §e"+permission);
						}
					} else {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("deleteGroupNot").
								replace("%group%", Main.getInstance().getMethods().getGroupColor(args[1])));
					}
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else if(args[0].equalsIgnoreCase("creategroup")) {
				if(sender.hasPermission("permissions.creategroup")) {
					Main.getInstance().getMethods().createGroup(sender, args[1]);
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else if(args[0].equalsIgnoreCase("deletegroup")) {
				if(sender.hasPermission("permissions.deletegroup")) {
					Main.getInstance().getMethods().deleteGroup(sender, args[1]);
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else {
				Main.getInstance().getMethods().sendCommandInfo(sender);
			}
		} else if(args.length == 4) {
			if(args[0].equalsIgnoreCase("user")) {
				String uuid;
				
				try {
					uuid = UUIDFetcher.getUUID(args[1]).toString();
				} catch(Exception ex) {
					sender.sendMessage(Main.getInstance().getLanguage().getMessage("playerError").replace("%playername%", args[1]));
					return true;
				}
				
				if(args[2].equalsIgnoreCase("group")) {
					if(sender.hasPermission("permissions.setgroup")) {
						Main.getInstance().getMethods().setPlayerGroup(sender, uuid, args[3], -1);
					} else {
						Main.getInstance().getMethods().sendCommandInfo(sender);
					}
				} else if(args[2].equalsIgnoreCase("add")) {
					if(sender.hasPermission("permissions.adduserpermission")) {
						Main.getInstance().getMethods().addPermissionToPlayer(sender, uuid, args[3]);
					} else {
						Main.getInstance().getMethods().sendCommandInfo(sender);
					}
				} else if(args[2].equalsIgnoreCase("remove")) {
					if(sender.hasPermission("permissions.removeuserpermission")) {
						Main.getInstance().getMethods().removePermissionFromPlayer(sender, uuid, args[3]);
					} else {
						Main.getInstance().getMethods().sendCommandInfo(sender);
					}	
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else if(args[0].equalsIgnoreCase("group")) {
				if(args[2].equalsIgnoreCase("add")) {
					if(sender.hasPermission("permissions.addgrouppermission")) {
						Main.getInstance().getMethods().addPermissionToGroup(sender, args[1], args[3]);
					} else {
						Main.getInstance().getMethods().sendCommandInfo(sender);
					}
				} else if(args[2].equalsIgnoreCase("remove")) {
					if(sender.hasPermission("permissions.removegrouppermission")) {
						Main.getInstance().getMethods().removePermissionFromGroup(sender, args[1], args[3]);
					} else {
						Main.getInstance().getMethods().sendCommandInfo(sender);
					}
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else {
				Main.getInstance().getMethods().sendCommandInfo(sender);
			}
		} else if(args.length == 5) {
			if(args[0].equalsIgnoreCase("user")) {
				if(args[2].equalsIgnoreCase("tempgroup")) {	
					if(sender.hasPermission("permissions.tempgroup")) {
						if(Main.getInstance().getMethods().unitToMillis(args[4]) != -1) {
							Main.getInstance().getMethods().setPlayerGroup(sender, UUIDFetcher.getUUID(args[1]).toString(), 
									args[3], Main.getInstance().getMethods().unitToMillis(args[4]));
						} else {
							sender.sendMessage(Main.getInstance().getLanguage().getMessage("unitError").replace("%unit%", args[4]));
						}
					} else {
						Main.getInstance().getMethods().sendCommandInfo(sender);
					}
				} else {
					Main.getInstance().getMethods().sendCommandInfo(sender);
				}
			} else {
				Main.getInstance().getMethods().sendCommandInfo(sender);
			}
		} else {
			Main.getInstance().getMethods().sendCommandInfo(sender);
		}
		return false;
	}

}
