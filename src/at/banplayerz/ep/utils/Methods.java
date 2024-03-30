package at.banplayerz.ep.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginDescriptionFile;

import at.banplayerz.ep.Main;

public class Methods {

	public void addPermissionToPlayer(CommandSender p, String uuid, String permission) {
		if(playerExists(uuid)) {
			if(!getPlayerPermissions(uuid).contains(permission)) {
				if(Main.getInstance().getMySQL().update("INSERT INTO userPermissions (UUID, Permission) VALUES ('"+uuid+"','"+permission+"')")) {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("addPermissionToPlayer")
							.replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))).replace("%permission%", permission));
				} else {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
				}	
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("playerAlreadyHasPermission")
						.replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))).replace("%permission%", permission));
			}
		} else {
			p.sendMessage(Main.getInstance().getLanguage().getMessage("playerError").replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))));
		}
	}
	
	public void removePermissionFromPlayer(CommandSender p, String uuid, String permission) {
		if(playerExists(uuid)) {
			if(getPlayerPermissions(uuid).contains(permission)) {
				if(Main.getInstance().getMySQL().update("DELETE FROM userPermissions WHERE UUID='"+uuid+"' AND Permission='"+permission+"'")) {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("removePermissionFromPlayer")
							.replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))).replace("%permission%", permission));
				} else {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
				}
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("playerNotHasPermission")
						.replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))).replace("%permission%", permission));
			}
		} else {
			p.sendMessage(Main.getInstance().getLanguage().getMessage("playerError").replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))));
		}
	}
	
	public void createGroup(CommandSender p, String group) {
		if(!groupExists(group)) {
			if(Main.getInstance().getMySQL().update("CREATE TABLE IF NOT EXISTS group"+group+" (Permission VARCHAR(64))")) {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("createGroup").replace("%group%", getGroupColor(group)));
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
			}
		} else {
			p.sendMessage(Main.getInstance().getLanguage().getMessage("createGroupAlready").replace("%group%", getGroupColor(group)));
		}
	}
	
	public void deleteGroup(CommandSender p, String group) {
		if(groupExists(group)) {
			if(Main.getInstance().getMySQL().update("DROP TABLE group"+group)) {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("deleteGroup").replace("%group%", getGroupColor(group)));
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
			}
		} else {
			p.sendMessage(Main.getInstance().getLanguage().getMessage("deleteGroupNot").replace("%group%", group));
		}
	}
	
	public void addPermissionToGroup(CommandSender p, String group, String permission) {
		if(groupExists(group)) {
			if(!getGroupPermissions(group).contains(permission)) {
				if(Main.getInstance().getMySQL().update("INSERT INTO group"+group+" (Permission) VALUES ('"+permission+"')")) {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("groupAddPermission").replace("%group%", getGroupColor(group)).replace("%permission%", permission));
				} else {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
				}
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("groupAlreadyHasPermission").replace("%group%", getGroupColor(group)).replace("%permission%", permission));
			}
		} else {
			p.sendMessage(Main.getInstance().getLanguage().getMessage("deleteGroupNot").replace("%group%", group));
		}
	}
	
	public void removePermissionFromGroup(CommandSender p, String group, String permission) {
		if(groupExists(group)) {
			if(getGroupPermissions(group).contains(permission)) {
				if(Main.getInstance().getMySQL().update("DELETE FROM group"+group+" WHERE Permission='"+permission+"'")) {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("groupRemovePermission").replace("%group%", getGroupColor(group)).replace("%permission%", permission));
				} else {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
				}
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("groupNotHasPermission").replace("%group%", getGroupColor(group)).replace("%permission%", permission));
			}
		} else {
			p.sendMessage(Main.getInstance().getLanguage().getMessage("deleteGroupNot").replace("%group%", group));
		}
	}
	
	public void setPlayerGroup(CommandSender p, String uuid, String group, long millis) {
		if(playerExists(uuid)) {
			if(groupExists(group)) {
				String playerGroup = getPlayerGroup(uuid);
				
				if(Main.getInstance().getMySQL().update("UPDATE userGroups SET EPGroup='"+group+"' WHERE UUID='"+uuid+"'")) {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("groupPlayerSet").
							replace("%group%", getGroupColor(group)).replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))));
					
					if(millis > 0) {
						if(!tempRang(uuid)) {
							if(Main.getInstance().getMySQL().update("INSERT INTO tempRangs (UUID, EPGroup, End) VALUES "
									+ "('"+uuid+"','"+playerGroup+"','"+(System.currentTimeMillis()+millis)+"')")) {
								p.sendMessage(Main.getInstance().getLanguage().getMessage("tempRangSet").
										replace("%group%", getGroupColor(playerGroup)).replace("%date%", millisToDate(System.currentTimeMillis()+millis)));
							} else {
								p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
							}
						} else {
							if(Main.getInstance().getMySQL().update("UPDATE tempRangs SET End='"+(System.currentTimeMillis()+millis)+"' WHERE UUID='"+uuid+"'")) {
								p.sendMessage(Main.getInstance().getLanguage().getMessage("tempRangSet").
										replace("%group%", getGroupColor(playerGroup)).replace("%date%", millisToDate(System.currentTimeMillis()+millis)));
							} else {
								p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
							}
						}
					} else {
						if(tempRang(uuid)) {
							Main.getInstance().getMySQL().update("DELETE FROM tempRangs WHERE UUID='"+uuid+"'");
						}
					}
				} else {
					p.sendMessage(Main.getInstance().getLanguage().getMessage("error"));
				}
			} else {
				p.sendMessage(Main.getInstance().getLanguage().getMessage("deleteGroupNot").replace("%group%", group));
			}
		} else {
			p.sendMessage(Main.getInstance().getLanguage().getMessage("playerError").replace("%playername%", UUIDFetcher.getName(UUID.fromString(uuid))));
		}
	}
	
	public boolean playerExists(String uuid) {
		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM userGroups");
		
		try {
			while(rs.next()) {
				if(rs.getString("UUID").equals(uuid)) {
					return true;
				}
			}
		} catch (SQLException e) {}
		return false;
	}
	
	public ArrayList<String> getGroupPermissions(String group) {
		ArrayList<String> permissions = new ArrayList<>();
		
		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM group"+group);
		
		try {
			while(rs.next()) {
				permissions.add(rs.getString("Permission"));
			}
		} catch (SQLException e) {}
		
		return permissions;
	}
	
	public ArrayList<String> getPlayerPermissions(String uuid){
		ArrayList<String> permissions = new ArrayList<>();
		
		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM userPermissions WHERE UUID='"+uuid+"'");
		
		try {
			while(rs.next()) {
				permissions.add(rs.getString("Permission"));
			}
		} catch (SQLException e) {}
		
		return permissions;
	}
	
	public ArrayList<String> getGroups() {
		ArrayList<String> rangs = new ArrayList<>();
		
		ResultSet rs = Main.getInstance().getMySQL().getResult("SHOW TABLES");
		
		try {
			while(rs.next()) {
				if(rs.getString(1).startsWith("group")) {
					rangs.add(rs.getString(1).replace("group", ""));
				}
			}
		} catch (SQLException e) {}
		return rangs;
	}
	
	public ArrayList<String> getGroupPlayers(String group) {
		ArrayList<String> players = new ArrayList<>();
		
		if(getGroups().contains(group)) {
			ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM userGroups WHERE EPGroup='"+group+"'");
			
			try {
				while(rs.next()) {
					players.add(rs.getString("UUID"));
				}
			} catch (SQLException e) {}	
		}
		
		return players;
	}
	
	public String millisToDate(long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		
		SimpleDateFormat s = new SimpleDateFormat(Main.getInstance().getConfig().getString("dateFormat"));
		return s.format(c.getTime());
		
	}
	
	public long unitToMillis(String unit) {
		//s ... Sekunde
		//m ... Minute
		//h ... Stunde
		//d ... Tag
		//w ... Woche
		
		HashMap<String, Integer> units = new HashMap<>();
		units.put("s", 1000);
		units.put("m", 1000*60);
		units.put("h", 1000*60*60);
		units.put("d", 1000*60*60*24);
		units.put("w", 1000*60*60*24*7);
		
		for(String u : units.keySet()) {
			if(unit.endsWith(u)) {
				try {
					int number = Integer.valueOf(unit.replace(u, ""));
					
					return number * units.get(u);
				} catch(NumberFormatException ex) {}
			}
		}
		
		return -1;
	}
	
//	public boolean playerHasPermission(String uuid, String permission) {
//		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM userPermissions WHERE UUID='"+uuid+"'");
//		
//		try {
//			while(rs.next()) {
//				if(rs.getString("Permission").equals(permission)) {
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			
//		}
//		return false;
//	}
	
	public boolean groupHasPermission(String group, String permission) {
		if(groupExists(group)) {
			ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM group"+group+" WHERE Group='"+group+"'");
			
			try {
				while(rs.next()) {
					if(rs.getString("Permission").equals(permission)) {
						return true;
					}
				}
			} catch (SQLException e) {
			}
		}
		return false;
	}
	
	public String getPlayerGroup(String uuid) {
		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM userGroups WHERE UUID='"+uuid+"'");
		
		try {
			if(rs.next()) {
				return rs.getString("EPGroup");
			}
		} catch (SQLException e) {
		}
		return "default";
	}
	
	public boolean groupExists(String group) {
		ResultSet rs = Main.getInstance().getMySQL().getResult("SHOW TABLES");
		
		try {
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase("group"+group)) {
					return true;
				}
			}
		} catch (SQLException e) {
	
		}
		return false;
	}
	
	public boolean tempRang(String uuid) {
		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM tempRangs WHERE UUID='"+uuid+"'");
		
		try {
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}
	
	public long getTempRangEnd(String uuid) {
		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM tempRangs WHERE UUID='"+uuid+"'");
		
		try {
			if(rs.next()) {
				return rs.getLong("End");
			}
		} catch (SQLException e) {

		}
		return -1;
	}
	
	public String getTempRangGroup(String uuid) {
		ResultSet rs = Main.getInstance().getMySQL().getResult("SELECT * FROM tempRangs WHERE UUID='"+uuid+"'");
		
		try {
			if(rs.next()) {
				return rs.getString("EPGroup");
			}
		} catch (SQLException e) {

		}
		return "default";
	}
	
	public String getGroupColor(String group) {		
		for(String line : Main.getInstance().getConfig().getStringList("groupSynonyms")) {
			if(line.contains(":")) {
				String[] splitter = line.split(":");
				if(splitter[0].equalsIgnoreCase(group)) {			
					return ChatColor.translateAlternateColorCodes('&', splitter[1].replace(" ", ""));
				}
			}
		}
		return "§f"+group;
	}
	
	public void setPermissionAttachment(Player p, String permission, boolean type) {
		PermissionAttachment attachment = p.addAttachment(Main.getInstance());
		attachment.setPermission(permission, type);
	}
	
	public void sendPluginInfo(CommandSender sender, boolean showInfo, boolean showUpdates, boolean showBCMessage) {
		try {
			PluginDescriptionFile plugin = Main.getInstance().getDescription();
			if(showInfo) {
				sender.sendMessage(Main.prefix+" §a"+plugin.getName()+" v."+plugin.getVersion()+" by "+plugin.getAuthors().get(0));
			}
	
			if(showUpdates) {
				URL url = new URL(Main.getInstance().getURL()+"/epCurrentVersion.txt");
				Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));		
				
				if(scanner.hasNextLine()) {
					String version = scanner.nextLine();
					
					if(version.equals(plugin.getVersion())) {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("noUpdates"));
					} else {
						sender.sendMessage(Main.getInstance().getLanguage().getMessage("update").replace("%version%", version));
						
						URL url2 = new URL(Main.getInstance().getURL()+"/epUpdateLink.txt");
						Scanner scanner2 = new Scanner(new InputStreamReader(url2.openStream()));
						
						if(scanner2.hasNextLine()) {
							sender.sendMessage(Main.getInstance().getLanguage().getMessage("downloadUpdate").replace("%updateLink%", scanner2.nextLine()));
						}
						
						scanner2.close();	
					}
				}
				
				scanner.close();
			}
			
			if(showBCMessage) {
				URL url4 = new URL(Main.getInstance().getURL()+"/epBCMessage.txt");
				Scanner scanner4 = new Scanner(new InputStreamReader(url4.openStream()));
				
				boolean found = false;
				
				while(scanner4.hasNextLine()) {
					String line = scanner4.nextLine();
					if(line.contains(";")) {
						String[] splitter = line.split(";");
						if(splitter[0].equals(Main.getInstance().getConfig().getString("language"))) {
							line = line.replace(splitter[0]+";", "");
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.prefix+" "+line
									.replaceAll("#a#", "ä").replaceAll("#o#", "ö").replaceAll("#u#", "ü").replaceAll("#s#", "ß")));
							found = true;
						}
					}
				}
				
				scanner4.close();
				
				if(!found) {
					URL url5 = new URL(Main.getInstance().getURL()+"/epBCOtherLanguages.txt");
					Scanner scanner5 = new Scanner(new InputStreamReader(url5.openStream()));
					
					while(scanner5.hasNextLine()) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.prefix+" "+scanner5.nextLine()
								.replaceAll("#a#", "ä").replaceAll("#o#", "ö").replaceAll("#u#", "ü").replaceAll("#s#", "ß")));
					}
					
					scanner5.close();
				}
			}
		} catch (IOException e) {
			
		}
	}
	
	public void sendCommandInfo(CommandSender sender) {
		if(sender.hasPermission("permissions.info")) {
			sendPluginInfo(sender, true, true, true);
			sender.sendMessage(Main.getInstance().getLanguage().getMessage("help"));
		} else {
			sendPluginInfo(sender, true, false, false);
		}
	}
	
	public void sendHelp(CommandSender sender) {
		sender.sendMessage(Main.getInstance().getLanguage().getMessage("help1"));
		sender.sendMessage("§e/perm user <Name> "+Main.getInstance().getLanguage().getMessage("help2"));
		sender.sendMessage("§e/perm user <Name> group <Group> "+Main.getInstance().getLanguage().getMessage("help3"));
		sender.sendMessage("§e/perm user <Name> tempgroup <Group> <Time> "+Main.getInstance().getLanguage().getMessage("help4"));
		sender.sendMessage("§e/perm user <Name> add <Permission> "+Main.getInstance().getLanguage().getMessage("help5"));
		sender.sendMessage("§e/perm user <Name> remove <Permission> "+Main.getInstance().getLanguage().getMessage("help6"));
		sender.sendMessage("§e/perm groups "+Main.getInstance().getLanguage().getMessage("help7"));
		sender.sendMessage("§e/perm group <Group> "+Main.getInstance().getLanguage().getMessage("help8"));
		sender.sendMessage("§e/perm creategroup <Group> "+Main.getInstance().getLanguage().getMessage("help9"));
		sender.sendMessage("§e/perm deletegroup <Group> "+Main.getInstance().getLanguage().getMessage("help10"));
		sender.sendMessage("§e/perm group <Group> add <Permission> "+Main.getInstance().getLanguage().getMessage("help11"));
		sender.sendMessage("§e/perm group <Group> remove <Permission> "+Main.getInstance().getLanguage().getMessage("help12"));
		sender.sendMessage("§e/rang, /rank, /group "+Main.getInstance().getLanguage().getMessage("rangHelp"));
	}
}
