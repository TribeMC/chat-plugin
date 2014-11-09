package Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ChatV3.main;
import ChatV3.msg;

public class ChatCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command arg1, String arg2,
			String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			sendChatInfo(cs);
		} else if (args.length == 1) {
			if (cs.hasPermission("chat.edit")) {
				if (args[0].equalsIgnoreCase("on")) {
					tryChange(cs, true);
				} else if (args[0].equalsIgnoreCase("off")) {
					tryChange(cs, false);
				} else if (args[0].equalsIgnoreCase("status")
						|| args[0].equalsIgnoreCase("info")) {
					sendStatus(cs);
				} else {
					sendHelp(cs);
				}
			} else {
				cs.sendMessage(msg.Prefix + msg.noPerms);
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("distance")) {
				setDistance(cs, args[1]);
			} else if (args[0].equalsIgnoreCase("msg")) {

				if (args[1].equalsIgnoreCase("off")) {
					changeMSGBoolean(cs, false);
				} else {
					changeMSGBoolean(cs, true);
				}
			} else {
				sendHelp(cs);
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("block")
					|| args[0].equalsIgnoreCase("denied")) {
				if (args[1].equalsIgnoreCase("add")) {
					addBlocked(cs, args[2]);
				} else if (args[1].equalsIgnoreCase("remove")) {
					removeBlocked(cs, args[2]);
				} else {
					sendHelp(cs);
				}

			} else {
				sendHelp(cs);
			}
		} else if (args.length == 4) {
			if (args[0].equalsIgnoreCase("replace")
					|| args[0].equalsIgnoreCase("change")) {
				if (args[1].equalsIgnoreCase("add")) {
					addChange(cs, args[2], args[3]);
				} else if (args[1].equalsIgnoreCase("remove")) {
					removeChange(cs, args[2], args[3]);
				} else {
					sendHelp(cs);
				}

			} else {
				sendHelp(cs);
			}
		}
		return false;
	}

	private void removeChange(CommandSender cs, String string, String string2) {

		cs.sendMessage(msg.Prefix + "§dComming soon...");
	}

	private void addChange(CommandSender cs, String string, String string2) {
		// TODO Auto-generated method stub
		String rdyRpl = ChatColor.translateAlternateColorCodes('&', string2);
		for (String s : main.repl) {
			String[] temp = s.split("=-=");
			if (temp[0].equalsIgnoreCase(string)) {
				cs.sendMessage(msg.Prefix + msg.containsAlready);
				return;
			}
		}
		main.repl.add(string.toLowerCase() + "=-=" + rdyRpl);
		cs.sendMessage(msg.Prefix + "§7Nun wird §6" + string + " §7gegen §6"
				+ rdyRpl + " §7getauscht!");
	}

	private void removeBlocked(CommandSender cs, String string) {
		if (main.bos.contains(string.toLowerCase())) {
			main.bos.remove(string.toLowerCase());
			cs.sendMessage(msg.Prefix + "§7Das Wort §c" + string
					+ "§7wurde Freigegeben!");
		} else {
			cs.sendMessage(msg.Prefix + msg.notContain);
		}

	}

	private void addBlocked(CommandSender cs, String string) {

		if (!main.bos.contains(string.toLowerCase())) {
			main.bos.add(string.toLowerCase());
			cs.sendMessage(msg.Prefix + "§7Das Wort §c" + string
					+ "§7wurde Verboten!");
		} else {
			cs.sendMessage(msg.Prefix + msg.containsAlready);
		}
	}

	private void changeMSGBoolean(CommandSender cs, boolean b) {
		// TODO Auto-generated method stub
		if (main.msg == b) {
			cs.sendMessage(msg.Prefix + msg.sameStat);
		} else {
			main.msg = b;
			cs.sendMessage(msg.Prefix + "§7Private Nachrichten sind nun "
					+ ((b == true) ? "§aERLAUBT" : "§cVERBOTEN") + "§7!");
		}
	}

	private void setDistance(CommandSender cs, String string) {
		// TODO Auto-generated method stub
		int i = 0;
		try {
			i = Integer.valueOf(string);
			if (i <= 0) {

				cs.sendMessage(msg.Prefix
						+ "§cDie Distanz kann nicht negativ sein!");
				return;
			}

		} catch (Exception e) {
			cs.sendMessage(msg.Prefix + msg.noNumber);
			return;
		}
		if (i != main.distance) {

			main.distance = i;
			cs.sendMessage(msg.Prefix + "§7Die Distance wurde auf §6"
					+ main.distance + " §7gesetzt!");
		} else {
			cs.sendMessage(msg.Prefix + msg.sameStat);

		}
	}

	private void sendStatus(CommandSender cs) {
		// TODO Auto-generated method stub
		cs.sendMessage("§b=-=-=-=-=-=-=-=-= " + msg.Prefix
				+ "§b=-=-=-=-=-=-=-=-=");
		cs.sendMessage("");
		cs.sendMessage(msg.Prefix + "§1Distance: §b" + main.distance);
		cs.sendMessage(msg.Prefix + "§2Aktiviert: §a" + main.chat);
		cs.sendMessage(msg.Prefix + "§5Private MSG's: §d" + main.msg);

		String ss = "";
		for (String s : main.bos) {
			ss = ss + s + ", ";
		}
		cs.sendMessage(msg.Prefix + "§4Verbotene Wörter: §c" + ss);
	}

	private void tryChange(CommandSender cs, boolean b) {
		// TODO Auto-generated method stub
		if (main.chat == b) {
			cs.sendMessage(msg.Prefix + msg.sameStat);
		} else {
			main.chat = b;
			cs.sendMessage(msg.Prefix + "§7Der Chat ist nun "
					+ ((b == true) ? "§aAKTIVIERT" : "§cDEAKTIVIERT") + "§7!");
		}
	}

	private void sendHelp(CommandSender cs) {
		// TODO Auto-generated method stub
		cs.sendMessage("§b=-=-=-=-=-=-=-=-= " + msg.Prefix
				+ "§b=-=-=-=-=-=-=-=-=");
		cs.sendMessage("");
		cs.sendMessage(msg.Prefix + "§c/Chat ON &8: &7Aktiviert den Chat");
		cs.sendMessage(msg.Prefix + "§c/Chat OFF &8: &7Deaktiviert den Chat");
		cs.sendMessage(msg.Prefix + "§c/Chat Status &8: &7Gibt aktuelle Infos!");
		cs.sendMessage("");
		cs.sendMessage(msg.Prefix
				+ "§c/Chat Distance [int] &8: &7Setzt die Distance des Chats!");
		cs.sendMessage(msg.Prefix
				+ "§c/Chat msg [on|off] &8: &7DeAktiviert Private Nachrichten!");
		cs.sendMessage("");
		cs.sendMessage(msg.Prefix
				+ "§c/Chat block [add|remove] &8: &7Ändert Liste der Verbotenen Wörter!");
		cs.sendMessage("");
		cs.sendMessage(msg.Prefix
				+ "§c/Chat change add &8: &7Fügt ein Wort zum ändern der Liste hinzu!");

	}

	private void sendChatInfo(CommandSender cs) {
		// TODO Auto-generated method stub

		cs.sendMessage(msg.Prefix + msg.Info);
	}

}
