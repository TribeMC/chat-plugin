package chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener{

	

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		
		if(msgInChat(e.getMessage()) != null){
		String msg = e.getMessage();
		String[] D = msg.split(" ");
		D[0] = "";
		msg = D.toString();
		chat.msg.onMSG(e.getPlayer(), Bukkit.getPlayer(D[0]), msg);
		}else if(e.getMessage().toString().startsWith("!")){
			
		}
		
		e.setCancelled(true);
	}
	
	
	private String msgInChat(String message) {
		// TODO Auto-generated method stub
		String cause = null;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(message.startsWith(p.getName())){
				cause = p.getName();
			}
		}
		return cause;
	}



	
}
