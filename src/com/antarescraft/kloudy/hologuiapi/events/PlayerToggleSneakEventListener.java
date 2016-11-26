package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.antarescraft.kloudy.hologuiapi.PlayerData;

public class PlayerToggleSneakEventListener implements Listener
{
	@EventHandler
	public void playerToggleSneakEvent(PlayerToggleSneakEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		playerData.setPlayerSneaking(event.isSneaking());
	}
}