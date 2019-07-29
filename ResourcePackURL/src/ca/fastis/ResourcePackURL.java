package ca.fastis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ResourcePackURL extends JavaPlugin implements Listener  {
	static Server server;
	static ConsoleCommandSender console;

	public void onEnable() {
		server = this.getServer();
		console = server.getConsoleSender();
		server.getPluginManager().registerEvents(this, this);
		console.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Resource Pack URL Loaded");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		getResourcePack(event.getPlayer());
	}


	public static void getResourcePack(Player player) {
		URL url = null;
		try {
			url = new URL("http://fastis.ca:8080/SimplyPerfectVersion.txt");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		String PackVersion = "";
		
		try {
			if(url != null) {
				@SuppressWarnings("resource")
				Scanner s = new Scanner(url.openStream());
				PackVersion = s.nextLine();
			}
			player.setResourcePack("http://fastis.ca:8080/SimplyPerfectv" + PackVersion + ".zip");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		console.sendMessage(ChatColor.RED + "Resource Pack URL Disabled");
	}
}