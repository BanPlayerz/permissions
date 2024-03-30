package at.banplayerz.ep.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import at.banplayerz.ep.Main;

public class Language {

	public String getMessage(String string) {
		File languageFile = new File("plugins/"+Main.getInstance().getDescription().getName()+"/languages");
		if(languageFile.exists() && languageFile.isDirectory()) {
			File file = new File("plugins/"+Main.getInstance().getDescription().getName()+"/languages/"+
					Main.getInstance().getConfig().getString("language")+".yml");
			
			if(file.exists()) {
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				
				if(cfg.getKeys(false).contains(string)) {
					return ChatColor.translateAlternateColorCodes('&', cfg.getString(string).replace("%prefix%", Main.prefix));
				}
			}
		}
		return "";
	}

	public void printSupportedLanguageFiles() {
		File languageFile = new File("plugins/"+Main.getInstance().getDescription().getName()+"/languages");
		if(!languageFile.exists()) {
			languageFile.mkdir();
		}
		
		if(languageFile.exists() && languageFile.isDirectory()) {
			File deutsch = new File("plugins/"+Main.getInstance().getDescription().getName()+"/languages/Deutsch.yml");
			FileConfiguration deutschCfg = YamlConfiguration.loadConfiguration(deutsch);
			
			deutschCfg.options().copyDefaults(true);
			deutschCfg.addDefault("setupLanguage", "%prefix% &aDie Sprache &e%language% &awurde erfolgreich ausgewählt!");
			deutschCfg.addDefault("noUpdates", "%prefix% &aEs sind keine Updates verfügbar!");
			deutschCfg.addDefault("update", "%prefix% &cEs ist ein Update verfügbar! Die neueste Version ist &e%version%&c!");
			deutschCfg.addDefault("downloadUpdate", "%prefix% &cLade das Update hier herunter&7: &e%updateLink%");
			deutschCfg.addDefault("mysqlConnect", "%prefix% &aDie Verbindung zur Datenbank wurde hergestellt!");
			deutschCfg.addDefault("mysqlDisconnect", "%prefix% &aDie Verbindung zur Datenbank wurde getrennt!");
			deutschCfg.addDefault("mysqlError", "%prefix% &cDie Verbindung zur Datenbank konnte nicht aufgebaut werden!");
			deutschCfg.addDefault("error", "%prefix% &cEs ist ein Fehler aufgetreten. Bitte versuche es später erneut.");
			deutschCfg.addDefault("playerAlreadyHasPermission", "%prefix% &cSpieler &e%playername% &chat bereits die Permission &e%permission%&c!");
			deutschCfg.addDefault("addPermissionToPlayer", "%prefix% &aDem Spieler &e%playername% &awurde die Permission &e%permission% &ahinzugefügt!");
			deutschCfg.addDefault("playerNotHasPermission", "%prefix% &cSpieler &e%playername% &chat die Permission &e%permission% &cnicht!");
			deutschCfg.addDefault("removePermissionFromPlayer", "%prefix% &aDem Spieler &e%playername% &awurde die Permission &e%permission% &aentfernt!");
			deutschCfg.addDefault("createGroupAlready", "%prefix% &cGruppe &e%group% &cexistiert bereits!");
			deutschCfg.addDefault("deleteGroupNot", "%prefix% &cGruppe &e%group% &cexistiert nicht!");
			deutschCfg.addDefault("createGroup", "%prefix% &aGruppe &e%group% &awurde erfolgreich erstellt!");
			deutschCfg.addDefault("deleteGroup", "%prefix% &aGruppe &e%group% &awurde erfolgreich gelöscht!");
			deutschCfg.addDefault("groupAlreadyHasPermission", "%prefix% &cGruppe &e%group% &chat bereits die Permission &e%permission%&c!");
			deutschCfg.addDefault("groupAddPermission", "%prefix% &aPermission &e%permission% &awurde zur Gruppe &e%group% &ahinzugefügt!");
			deutschCfg.addDefault("groupNotHasPermission", "%prefix% &cGruppe &e%group% &chat die Permission &e%permission% &cnicht!");
			deutschCfg.addDefault("groupRemovePermission", "%prefix% &aPermission &e%permission% &awurde von der Gruppe &e%group% &aentfernt!");
			deutschCfg.addDefault("playerError", "%prefix% &cSpieler &e%playername% &cwar noch nie am Server!");
			deutschCfg.addDefault("groupPlayerSet", "%prefix% &aSpieler &e%playername% &awurde in die Gruppe &e%group% &agesetzt!");
			deutschCfg.addDefault("tempRangSet", "%prefix% &aDer Spieler wird am &e%date% &awieder in die Gruppe &e%group% &agesetzt!");
			deutschCfg.addDefault("help", "%prefix% &cNutze &e/permissions help &cum Hilfe zu erhalten.");
			deutschCfg.addDefault("groups", "%prefix% &7--- &eListe aller Gruppen &7---");
			deutschCfg.addDefault("playerInformation", "%prefix% &aInformationen über Spieler &e%playername%");
			deutschCfg.addDefault("playerInformationGroup", "&aGruppe&7: &e%group%");
			deutschCfg.addDefault("playerInformationTempRang", "&7(läuft am &e%date% &7ab, &7Gruppe danach: &e%group%&7)");
			deutschCfg.addDefault("playerInformationPermissions", "&aPermissions&7:");
			deutschCfg.addDefault("groupInformation", "%prefix% &aInformationen über Gruppe &e%group%");
			deutschCfg.addDefault("playersInGroup", "%prefix% &aSpieler in der Gruppe&7:");
			deutschCfg.addDefault("unitError", "%prefix% &cDie Einheit &e%unit% &ckann nicht verwendet werden!");
			deutschCfg.addDefault("currentGroup", "&7Dein aktueller Rang ist &e%group%&7.");
			deutschCfg.addDefault("currentGroupTemp", "&7Dein aktueller Rang ist &e%group% &7und läuft am &e%date% &7ab.");
			deutschCfg.addDefault("help1", "%prefix% &7--- Permissions Hilfe &7---");
			deutschCfg.addDefault("help2", "&7Erhalte Informationen über einen Spieler");
			deutschCfg.addDefault("help3", "&7Setze einen Spieler in eine Gruppe");
			deutschCfg.addDefault("help4", "&7Setze einen Spieler zeitlich begrenzt in eine Gruppe");
			deutschCfg.addDefault("help5", "&7Füge einem Spieler eine Permission hinzu");
			deutschCfg.addDefault("help6", "&7Entferne einem Spieler eine Permission");
			deutschCfg.addDefault("help7", "&7Liste alle Gruppen auf");
			deutschCfg.addDefault("help8", "&7Erhalte Informationen über eine Gruppe");
			deutschCfg.addDefault("help9", "&7Erstelle eine Gruppe");
			deutschCfg.addDefault("help10", "&7Lösche eine Gruppe");
			deutschCfg.addDefault("help11", "&7Füge einer Gruppe eine Permission hinzu");
			deutschCfg.addDefault("help12", "&7Entferne einer Gruppe eine Permission");
			deutschCfg.addDefault("rangHelp", "&7Zeige deinen aktuellen Rang an (ohne Permission)");
			
			File english = new File("plugins/"+Main.getInstance().getDescription().getName()+"/languages/English.yml");
			FileConfiguration englishCfg = YamlConfiguration.loadConfiguration(english);
			
			englishCfg.options().copyDefaults(true);
			englishCfg.addDefault("setupLanguage", "%prefix% &aThe language &e%language% &ahas been chosen!");
			englishCfg.addDefault("noUpdates", "%prefix% &aThere are no updates available!");
			englishCfg.addDefault("update", "%prefix% &cAn update is available! The newest version is &e%version%&c!");
			englishCfg.addDefault("downloadUpdate", "%prefix% &cDownload the update here&7: &e%updateLink%");
			englishCfg.addDefault("mysqlConnect", "%prefix% &aThe connection to the database has been established!");
			englishCfg.addDefault("mysqlDisconnect", "%prefix% &aThe connection to the database was disconnected!");
			englishCfg.addDefault("mysqlError", "%prefix% &cThe connection to the database could not be established!");
			englishCfg.addDefault("error", "%prefix% &cAn error has occurred. Please try again later.");
			englishCfg.addDefault("playerAlreadyHasPermission", "%prefix% &cPlayer &e%playername% &calready has the permission &e%permission%&c!");
			englishCfg.addDefault("addPermissionToPlayer", "%prefix% &aThe permission &e%permission% &awas added to the player &e%playername%&a!");
			englishCfg.addDefault("playerNotHasPermission", "%prefix% &cPlayer &e%playername% &cdoes not have the permission &e%permission%&c!");
			englishCfg.addDefault("removePermissionFromPlayer", "%prefix% &aThe permission &e%permission% &awas removed from player &e%playername%!");
			englishCfg.addDefault("createGroupAlready", "%prefix% &cGroup &e%group% &calready exists!");
			englishCfg.addDefault("deleteGroupNot", "%prefix% &cGroup &e%group% &cdoes not exist!");
			englishCfg.addDefault("createGroup", "%prefix% &aGroup &e%group% &awas created!");
			englishCfg.addDefault("deleteGroup", "%prefix% &aGroup &e%group% &awas deleted!");
			englishCfg.addDefault("groupAlreadyHasPermission", "%prefix% &cGroup &e%group% &calready has the permission &e%permission%&c!");
			englishCfg.addDefault("groupAddPermission", "%prefix% &aPermission &e%permission% &awas added to group &e%group%&a!");
			englishCfg.addDefault("groupNotHasPermission", "%prefix% &cGroup &e%group% &cdoes not have the permission &e%permission%&c!");
			englishCfg.addDefault("groupRemovePermission", "%prefix% &aPermission &e%permission% &awas removed from group &e%group%&a!");
			englishCfg.addDefault("playerError", "%prefix% &cPlayer &e%playername% &chas never been on the server!");
			englishCfg.addDefault("groupPlayerSet", "%prefix% &aPlayer &e%playername% &awas set to the group &e%group%&a!");
			englishCfg.addDefault("tempRangSet", "%prefix% &aThe player will be put back into the group &e%group% &aon &e%date%&a!");
			englishCfg.addDefault("help", "%prefix% &cUse &e/permissions help &cto get help.");
			englishCfg.addDefault("groups", "%prefix% &7--- &eList of all groups &7---");
			englishCfg.addDefault("playerInformation", "%prefix% &aInformation about player &e%playername%");
			englishCfg.addDefault("playerInformationGroup", "&aGroup&7: &e%group%");
			englishCfg.addDefault("playerInformationTempRang", "&7(expires on &e%date%, &7Group after: &e%group%&7)");
			englishCfg.addDefault("playerInformationPermissions", "&aPermissions&7:");
			englishCfg.addDefault("groupInformation", "%prefix% &aInformation about group &e%group%");
			englishCfg.addDefault("playersInGroup", "%prefix% &aPlayers in group&7:");
			englishCfg.addDefault("unitError", "%prefix% &cThe unit &e%unit% &ccan not be used!");
			englishCfg.addDefault("currentGroup", "&7Your current rank is &e%group%&7.");
			englishCfg.addDefault("currentGroupTemp", "&7Your current rank is &e%group% &7and expires on &e%date%&7.");
			englishCfg.addDefault("help1", "%prefix% &7--- Permissions Help &7---");
			englishCfg.addDefault("help2", "&7Get information about a player");
			englishCfg.addDefault("help3", "&7Put a player in a group");
			englishCfg.addDefault("help4", "&7Put a player in a group for a limited time");
			englishCfg.addDefault("help5", "&7Add a permission to a player");
			englishCfg.addDefault("help6", "&7Remove a permission from a player");
			englishCfg.addDefault("help7", "&7List all groups");
			englishCfg.addDefault("help8", "&7Get information about a group");
			englishCfg.addDefault("help9", "&7Create a group");
			englishCfg.addDefault("help10", "&7Delete a group");
			englishCfg.addDefault("help11", "&7Add a permission to a group");
			englishCfg.addDefault("help12", "&7Remove a permission from a group");
			englishCfg.addDefault("rangHelp", "&7Show your current rang (without permission)");
			
			try {
				deutschCfg.save(deutsch);
				englishCfg.save(english);
			} catch (IOException e) {}	
		}
			
	}

	public boolean chosenLanguageIsOkay() {
		File languageFile = new File("plugins/"+Main.getInstance().getDescription().getName()+"/languages");
		
		if(languageFile.exists() && languageFile.isDirectory()) {
			for(File f : languageFile.listFiles()) {
				if(f.getName().equals(Main.getInstance().getConfig().getString("language")+".yml")) {
					return true;
				}
			}
		}
		return false;
		
	}

	public void setupCustomLanguage(String language) {
		File f = new File("plugins/"+Main.getInstance().getDescription().getName()+"/languages/"+language+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.options().copyDefaults(true);

		cfg.addDefault("setupLanguage", "");
		cfg.addDefault("noUpdates", "");
		cfg.addDefault("update", "");
		cfg.addDefault("downloadUpdate", "");
		cfg.addDefault("mysqlConnect", "");
		cfg.addDefault("mysqlDisconnect", "");
		cfg.addDefault("mysqlError", "");
		cfg.addDefault("error", "");
		cfg.addDefault("playerAlreadyHasPermission", "");
		cfg.addDefault("addPermissionToPlayer", "");
		cfg.addDefault("playerNotHasPermission", "");
		cfg.addDefault("removePermissionFromPlayer", "");
		cfg.addDefault("createGroupAlready", "");
		cfg.addDefault("deleteGroupNot", "");
		cfg.addDefault("createGroup", "");
		cfg.addDefault("deleteGroup", "");
		cfg.addDefault("groupAlreadyHasPermission", "");
		cfg.addDefault("groupAddPermission", "");
		cfg.addDefault("groupNotHasPermission", "");
		cfg.addDefault("groupRemovePermission", "");
		cfg.addDefault("playerError", "");
		cfg.addDefault("groupPlayerSet", "");
		cfg.addDefault("tempRangSet", "");
		cfg.addDefault("help", "");
		cfg.addDefault("groups", "");
		cfg.addDefault("playerInformation", "");
		cfg.addDefault("playerInformationGroup", "");
		cfg.addDefault("playerInformationTempRang", "");
		cfg.addDefault("playerInformationPermissions", "");
		cfg.addDefault("groupInformation", "");
		cfg.addDefault("playersInGroup", "");
		cfg.addDefault("unitError", "");
		cfg.addDefault("currentGroup", "");
		cfg.addDefault("currentGroupTemp", "");
		cfg.addDefault("help1", "");
		cfg.addDefault("help2", "");
		cfg.addDefault("help3", "");
		cfg.addDefault("help4", "");
		cfg.addDefault("help5", "");
		cfg.addDefault("help6", "");
		cfg.addDefault("help7", "");
		cfg.addDefault("help8", "");
		cfg.addDefault("help9", "");
		cfg.addDefault("help10", "");
		cfg.addDefault("help11", "");
		cfg.addDefault("help12", "");
		cfg.addDefault("rangHelp", "");
		
		try {
			cfg.save(f);
			Bukkit.getConsoleSender().sendMessage(Main.prefix+"§aCustom language §e"+language+" §ahas been chosen!");
		} catch (IOException e) {}
	}
	
}
