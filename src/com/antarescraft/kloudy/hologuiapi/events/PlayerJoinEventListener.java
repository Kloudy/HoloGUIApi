package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;

public class PlayerJoinEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PlayerJoinEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.hasPermission("hg.see"))
		{
			for(HoloGUIPlugin holoGUIPlugin : holoGUI.getHookedHoloGUIPlugins())
			{
				for(GUIPage guiContainer : holoGUIPlugin.getGUIPages().values())
				{
					if(guiContainer.playerHasPermission(player))
					{
						if(guiContainer.getOpenOnLogin() && guiContainer.getHoloGUIPlugin().guiPagesLoaded())
						{
							guiContainer.renderComponentsForPlayer(player, player.getLocation().clone());
							break;
						}
					}
				}
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
}