package ChatV3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.alpenblock.bungeeperms.bukkit.Group;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

	ConsoleCommandSender c = Bukkit.getConsoleSender();

	@EventHandler
	public void onChatEVT(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		e.setCancelled(true);
		if (p.hasPermission("chat.chat")) {
			if (main.chat == true || p.hasPermission("chat.bypass")) {

				if (main.msg == true && p.hasPermission("chat.msg")
						&& isOnline(e.getMessage()) != null) {
					String prefix = getPrefix(e.getPlayer());
					sendPrivateMessage(isOnline(e.getMessage()), p, prefix,
							replaceMSG(e.getMessage()));
				} else {
					String message = replaceMSG(e.getMessage());
					ArrayList<Player> reciver = getRange(p);

					String prefix = getPrefix(e.getPlayer());

					for (Player ps : reciver) {
						sendMessage(p, ps, message, prefix);
					}
					c.sendMessage(msg.Prefix + prefix + " " + p.getName()
							+ "§8: §7" + message);
				}
			} else {
				p.sendMessage(msg.Prefix + msg.deaktiviert);
			}
		} else {
			p.sendMessage(msg.Prefix + msg.noPerms);
		}
	}

	private void sendPrivateMessage(Player tar, Player sen, String prefix,
			String replaceMSG) {
		// TODO Auto-generated method stub
		if (sen == tar) {
			sen.sendMessage(msg.Prefix + "§cKeine Freunde?");
			return;
		}
		replaceMSG = replaceMSG.replaceFirst(tar.getName(), "");

		if (replaceMSG.replaceAll(" ", "").equals("")) {
			ArrayList<Player> reciver = getRange(sen);

			String prefix1 = getPrefix(sen);

			for (Player ps : reciver) {
				sendMessage(sen, ps, tar.getName(), prefix1);
			}
			return;
		}
		tar.sendMessage("§4[" + prefix + " " + sen.getName()
				+ "§4] -> §4[§cMir§4] §9" + replaceMSG);

		String pre = getPrefix(tar);

		sen.sendMessage("§4[§cMir§4] -> §4[" + pre + " " + tar.getName()
				+ "§4] §9" + replaceMSG);

		c.sendMessage(msg.Prefix + "§4[" + prefix + " " + sen.getName()
				+ "§4] -> §4[" + pre + " " + tar.getName() + "§4] §9"
				+ replaceMSG);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("chat.spy")) {
				if (p != sen & p != tar) {
					p.sendMessage(msg.Prefix + "§4[" + prefix + " "
							+ sen.getName() + "§4] -> §4[" + pre + " "
							+ tar.getName() + "§4] §8" + replaceMSG);
				}
			}
		}

	}

	public static String getPrefix(Player p) {
		if (main.prefixs.containsKey(p)) {
			return main.prefixs.get(p);

		} else {
			List<Group> groups = net.alpenblock.bungeeperms.bukkit.BungeePerms
					.getInstance().getPermissionsManager().getUser(p.getName())
					.getGroups();
			String pre = ChatColor.translateAlternateColorCodes('&', groups
					.get(0).getPrefix());

			main.prefixs.put(p, pre);
			return main.prefixs.get(p);
		}
	}

	private Player isOnline(String message) {
		// TODO Auto-generated method stub
		String[] ms = message.split(" ");

		try {
			@SuppressWarnings({ "deprecation" })
			Player pd = (Player) Bukkit.getOfflinePlayer(ms[0]);
			return pd;
		} catch (Exception e) {
			return null;

		}
	}

	private String replaceMSG(String message) {
		// TODO Auto-generated method stub
		String msg = message;
		for (String s : main.bos) {
			if (msg.toLowerCase().contains(s)) {
				msg = msg.toLowerCase().replaceAll(s, "§c---§7");

			}
		}
		for (String s : main.repl) {
			String[] temp = s.split("=-=");
			if (msg.toLowerCase().equals(temp[0])) {
				msg = msg.toLowerCase().replaceAll(temp[0], temp[1]);

			}
		}
		return msg;
	}

	private void sendMessage(Player p, Player ps, String message, String prefix) {
		// TODO Auto-generated method stub

		int Rand = getSpezial(p);
		if (Rand != 0) {
			ps.sendMessage("§e" + Rand + "§8* " + prefix + " " + p.getName()
					+ "§8: §7" + message);

		} else {
			ps.sendMessage(prefix + " " + p.getName() + "§8: §7" + message);
		}
	}

	private int getSpezial(Player p) {
		// TODO Auto-generated method stub
		int i = 0;

		File f = new File("plugins/pvp/playerdata", p.getName().toLowerCase()
				+ ".yml");
		if (f.exists()) {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			i = cfg.getInt("TopStreak");
		}

		java.io.File f1 = new java.io.File("plugins/DayZ/PlayerData", p
				.getName().toLowerCase() + ".yml");
		if (f1.exists()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

			int kills = 0;
			if (cfg.contains("Stats.Kills")) {
				kills = kills + cfg.getInt("Stats.Kills");
			}
			if (cfg.contains("Stats.Zombies")) {
				kills = kills + cfg.getInt("Stats.Zombies");
			}
			int tode = 0;
			if (cfg.contains("Stats.Tode")) {
				tode = cfg.getInt("Stats.Tode");
			}
			if (tode != 0) {
				i = kills / tode;
			}

		}
		return i;
	}

	private ArrayList<Player> getRange(Player p) {
		// TODO Auto-generated method stub
		ArrayList<Player> temp = new ArrayList<>();

		for (Player pd : Bukkit.getOnlinePlayers()) {

			if (main.distance != 0) {
				if (p.getLocation().distance(pd.getLocation()) <= main.distance) {
					temp.add(pd);
				}

			} else {
				temp.add(pd);
			}
		}
		return temp;
	}

}
