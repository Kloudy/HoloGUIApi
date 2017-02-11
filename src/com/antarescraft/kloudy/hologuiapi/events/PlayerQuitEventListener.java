package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ValueScrollerComponent;

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
		
		// Remove all player values from gui components across all HoloGUIPlugins when the player disconnects
		for(HoloGUIPlugin plugin : holoGUI.getHookedHoloGUIPlugins())
		{
			for(GUIPage guiPage : plugin.getGUIPages().values())
			{
				for(GUIComponent guiComponent : guiPage.getComponents())
				{
					if(guiComponent instanceof TextBoxComponent)
					{
						TextBoxComponent textbox = (TextBoxComponent)guiComponent;
						textbox.removePlayerTextBoxValue(player);
					}
					
					else if(guiComponent instanceof ToggleSwitchComponent)
					{
						ToggleSwitchComponent textbox = (ToggleSwitchComponent)guiComponent;
						textbox.removePlayerToggleSwitchState(player);
					}
					
					else if(guiComponent instanceof ValueScrollerComponent)
					{
						ValueScrollerComponent valueScroller = (ValueScrollerComponent)guiComponent;
						valueScroller.removePlayerScrollValue(player);
					}
				}
			}
		}
	}
}