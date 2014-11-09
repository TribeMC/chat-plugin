package ChatV3;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Commands.ChatCMD;

public class main extends JavaPlugin {

	public static boolean chat;
	public static boolean msg;

	public static int distance;
	public static List<String> bos;
	public static List<String> repl;

	public static HashMap<Player, String> prefixs = new HashMap<>();
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		loadConfig();

		getServer().getPluginManager().registerEvents(new ChatEvent(), this);

		getCommand("chat").setExecutor(new ChatCMD());

		super.onEnable();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		ChangesaveConfig();
		super.onDisable();
	}

	private void ChangesaveConfig() {
		// TODO Auto-generated method stub
		getConfig().set("Aktiviert", chat);
		getConfig().set("Distance", distance);
		getConfig().set("MSG", msg);
		getConfig().set("Bose", bos);
		getConfig().set("Replacements", repl);

		saveConfig();
	}

	private void loadConfig() {
		// TODO Auto-generated method stub
		getConfig().options().copyDefaults(true);
		saveConfig();

		chat = getConfig().getBoolean("Aktiviert");
		distance = getConfig().getInt("Distance");
		msg = getConfig().getBoolean("MSG");
		bos = getConfig().getStringList("Bose");

		repl = getConfig().getStringList("Replacements");

	}

}
