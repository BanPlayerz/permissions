package at.banplayerz.ep.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import at.banplayerz.ep.Main;

public class MySQL {

	public Connection con;
	
	public MySQL(String host, String database, String user, String password) {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, password);
				Bukkit.getConsoleSender().sendMessage(Main.getInstance().getLanguage().getMessage("mysqlConnect"));
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage(Main.getInstance().getLanguage().getMessage("mysqlError"));
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
			}
		}
	}
	
	public void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				Bukkit.getConsoleSender().sendMessage(Main.getInstance().getLanguage().getMessage("mysqlDisconnect"));
			} catch (SQLException e) {}
		}
	}
	
	public boolean isConnected() {
		return con != null;
	}
	
	public boolean update(String qry) {
		if(isConnected()) {
			try {
				con.createStatement().executeUpdate(qry);
				return true;
			} catch (SQLException e) {}
		}
		return false;
	}
	
	public ResultSet getResult(String qry) {
		if(isConnected()) {
			try {
				return con.createStatement().executeQuery(qry);
			} catch (SQLException e) {}
		}
		return null;
	}

}
