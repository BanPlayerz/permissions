package at.banplayerz.ep;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import at.banplayerz.ep.commands.Permissions_Command;
import at.banplayerz.ep.commands.Group_Command;
import at.banplayerz.ep.events.PlayerJoinPS;
import at.banplayerz.ep.utils.Language;
import at.banplayerz.ep.utils.Methods;
import at.banplayerz.ep.utils.MySQL;

public class Main extends JavaPlugin {
	
	public static Main instance;
	public Language language;
	public Methods methods;
	
	public static String prefix;
	public String URL = "";
	public MySQL mySQL;
	
	@Override
	public void onEnable() {
		instance = this;
		language = new Language();
		methods = new Methods();
		
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("prefix", "&7[&cPermission&7]&r");
		getConfig().addDefault("language", "English");
		getConfig().addDefault("dateFormat", "dd.MM.yyyy HH:mm:ss");
		List<String> groupSynonyms = getConfig().getStringList("groupSynonyms");
		groupSynonyms.add("default: &aPlayer");
		getConfig().addDefault("groupSynonyms", groupSynonyms);
		getConfig().addDefault("host", "localhost");
		getConfig().addDefault("database", "database");
		getConfig().addDefault("user", "username");
		getConfig().addDefault("password", "password");
		saveConfig();
		
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
		
		getLanguage().printSupportedLanguageFiles();
		if(getLanguage().chosenLanguageIsOkay()) {
			Bukkit.getConsoleSender().sendMessage(getLanguage().getMessage("setupLanguage").replace("%language%", getConfig().getString("language")));
		} else {
			getLanguage().setupCustomLanguage(getConfig().getString("language"));
		}	
		
		mySQL = new MySQL(getConfig().getString("host"), getConfig().getString("database"),
				getConfig().getString("user"), getConfig().getString("password"));
		
		if(getMySQL().isConnected()) {
			getMySQL().update("CREATE TABLE IF NOT EXISTS userGroups (UUID VARCHAR(64), EPGroup VARCHAR(16))");
			getMySQL().update("CREATE TABLE IF NOT EXISTS userPermissions (UUID VARCHAR(64), Permission VARCHAR(64))");
			getMySQL().update("CREATE TABLE IF NOT EXISTS tempRangs (UUID VARCHAR(64), EPGroup VARCHAR(16), End LONG)");
			getMySQL().update("CREATE TABLE IF NOT EXISTS groupdefault (Permission VARCHAR(64))");
			
			getServer().getPluginManager().registerEvents(new PlayerJoinPS(), this);
			getCommand("permissions").setExecutor(new Permissions_Command());
			getCommand("group").setExecutor(new Group_Command());
		}
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public Methods getMethods() {
		return methods;
	}
	
	public MySQL getMySQL() {
		return mySQL;
	}
	
	public String getURL() {
		return URL;
	}

}
