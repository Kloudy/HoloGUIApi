package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;

public class PlayerQuitEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PlayerQuitEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
		HoloGUIApi.packetManager.resetPlayerNextAvailableEntityId(player);
		
		PlayerData.removePlayerData(player);
		
		for(StationaryGUIDisplayContainer stationaryDisplay : holoGUI.getStationaryDisplays())
		{
			stationaryDisplay.destroy(player);
		}
	}
}