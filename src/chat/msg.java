package chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class msg implements Listener{

	
	public static void onMSG(Player sender, Player empf, String msg){
		empf.sendMessage(sender.getDisplayName() + "->" + "[MIR] : " + msg);
		sender.sendMessage("[MIR]" + "->" + empf.getDisplayName() + ": " + msg);
	}
}
