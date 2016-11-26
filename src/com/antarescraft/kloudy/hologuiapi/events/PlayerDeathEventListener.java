package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;

public class PlayerDeathEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PlayerDeathEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event)
	{
		Entity entity = event.getEntity();
		if(entity instanceof Player)
		{
			Player player = (Player)entity;
			
			PlayerGUIPage playerGUIContainer = PlayerData.getPlayerData(player).getPlayerGUIPage();
			if(playerGUIContainer != null)
			{
				playerGUIContainer.destroy();
				PlayerData.getPlayerData(player).setPlayerGUIPage(null);
				PlayerData.getPlayerData(player).setPlayerPreviousGUIContainer(null);
			}
			
			for(StationaryGUIDisplayContainer stationaryDisplay : holoGUI.getStationaryDisplays())
			{
				if(stationaryDisplay.isDisplayingToPlayer(player))
				{
					stationaryDisplay.destroy(player);
					HoloGUIApi.packetManager.resetPlayerNextAvailableEntityId(player);
				}
			}
		}
	}
}