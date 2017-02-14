package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;

public class PlayerTeleportEventListener implements Listener
{
	@EventHandler
	public void playerTeleportEvent(PlayerTeleportEvent event)
	{
		Player player = event.getPlayer();
		
		PlayerGUIPage playerGUIContainer = PlayerData.getPlayerData(player).getPlayerGUIPage();
		if(playerGUIContainer != null)
		{
			if(event.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN)
			{
				playerGUIContainer.destroy();
				PlayerData.getPlayerData(player).setPlayerGUIPage(null);
				PlayerData.getPlayerData(player).setPlayerPreviousGUIPage(null);
			}
		}
	}
}