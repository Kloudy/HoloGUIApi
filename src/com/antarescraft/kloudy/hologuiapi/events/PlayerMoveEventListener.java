package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;

public class PlayerMoveEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PlayerMoveEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(player.hasPermission("hg.see"))
		{
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
}