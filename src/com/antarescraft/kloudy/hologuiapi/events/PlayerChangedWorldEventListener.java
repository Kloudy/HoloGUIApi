package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;

public class PlayerChangedWorldEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PlayerChangedWorldEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void playerChangedWorldEvent(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		
		PlayerGUIPage playerGUIContainer = PlayerData.getPlayerData(player).getPlayerGUIPage();
		if(playerGUIContainer != null)
		{
			playerGUIContainer.destroy();
			PlayerData.getPlayerData(player).setPlayerGUIPage(null);
			PlayerData.getPlayerData(player).setPlayerPreviousGUIPage(null);
		}
		
		for(StationaryGUIDisplayContainer stationaryDisplay : holoGUI.getStationaryDisplays())
		{
			if(stationaryDisplay.playerInRange(player))
			{
				if(!stationaryDisplay.isDisplayingToPlayer(player))//player in range of display but not currently viewing the stationary gui, make gui visible
				{
					stationaryDisplay.display(player);
				}
			}
			else if(stationaryDisplay.isDisplayingToPlayer(player))//player was viewing the stationary gui, but moved out of range. Remove the gui for that player
			{
				stationaryDisplay.destroy(player);
			}
		}
	}
}