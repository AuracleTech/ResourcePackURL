package ca.fastis;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ResourcePackURL extends JavaPlugin implements Listener  {
	static Server server;
	static ConsoleCommandSender console;
	static Plugin plugin;

	public void onEnable() {
		plugin = this;
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
		try {
			File path = new File("plugins//ResourcePackURL");
			File fileYourFile = new File("plugins//ResourcePackURL//Config.yml");
			YamlConfiguration ymlYourFile = YamlConfiguration.loadConfiguration(fileYourFile);
			if(!path.exists()) path.mkdir();
			if(!fileYourFile.exists()) plugin.saveResource("Config.yml", true);
			String resourcePackURL = ymlYourFile.getString("ResourcePackURL");
			if(resourcePackURL == null ||resourcePackURL.isEmpty()) {
				return;
			} else {
				final URL url = new URL(ymlYourFile.getString("ResourcePackURL"));
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				int responseCode = huc.getResponseCode();
				if(responseCode == 200) player.setResourcePack(url.toString());
			}
		} catch (UnknownHostException uhe) {
			console.sendMessage(ChatColor.RED + "Resource Pack URL UnknownHostException : " + uhe.getMessage());
		} catch (FileNotFoundException fnfe) {
			console.sendMessage(ChatColor.RED + "Resource Pack URL FileNotFoundException : " + fnfe.getMessage());
		} catch (Exception e) {
			console.sendMessage(ChatColor.RED + "Resource Pack URL Exception : " + e.getMessage());
		}
	}

	@Override
	public void onDisable() {
		console.sendMessage(ChatColor.RED + "Resource Pack URL Disabled");
	}
}