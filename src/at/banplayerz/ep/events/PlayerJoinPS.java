package at.banplayerz.ep.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import at.banplayerz.ep.Main;

public class PlayerJoinPS implements Listener {
	
	@EventHandler
	public void onJoin(PlayerLoginEvent e) {
		if(!Main.getInstance().getMethods().playerExists(e.getPlayer().getUniqueId().toString())) {
			Main.getInstance().getMySQL().update("INSERT INTO userGroups (UUID, EPGroup) VALUES ('"+e.getPlayer().getUniqueId().toString()+"', 'default')");
		}
		
		if(Main.getInstance().getMethods().tempRang(e.getPlayer().getUniqueId().toString())) {
			if(System.currentTimeMillis() > Main.getInstance().getMethods().getTempRangEnd(e.getPlayer().getUniqueId().toString())) {
				Main.getInstance().getMethods().setPlayerGroup(Bukkit.getConsoleSender(), e.getPlayer().getUniqueId().toString(),
						Main.getInstance().getMethods().getTempRangGroup(e.getPlayer().getUniqueId().toString()), -1);
				Main.getInstance().getMySQL().update("DELETE FROM tempRangs WHERE UUID='"+e.getPlayer().getUniqueId().toString()+"'");
			}
		}
		
		for(String permission : Main.getInstance().getMethods().
				getGroupPermissions(Main.getInstance().getMethods().getPlayerGroup(e.getPlayer().getUniqueId().toString()))) {
			Main.getInstance().getMethods().setPermissionAttachment(e.getPlayer(), permission, true);
		}
		
		for(String permission : Main.getInstance().getMethods().getPlayerPermissions(e.getPlayer().getUniqueId().toString())) {
			Main.getInstance().getMethods().setPermissionAttachment(e.getPlayer(), permission, true);
		}
	}
	
}
