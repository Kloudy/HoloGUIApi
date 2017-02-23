package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ClickableGUIComponent;
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
		
		// Remove all player values from gui pages & components across all HoloGUIPlugins when the player disconnects
		for(HoloGUIPlugin plugin : holoGUI.getHookedHoloGUIPlugins())
		{
			for(GUIPage guiPage : plugin.getGUIPages().values())
			{
				// Trigger the page close handler if it exists for the player
				guiPage.triggerPageCloseHandler(player);
				
				// Remove player GUIPage handlers
				guiPage.removePageCloseHandler(player);
				guiPage.removePageLoadHandler(player);
				
				for(GUIComponent guiComponent : guiPage.getComponents())
				{
					// Remove component player handlers and values
					if(guiComponent instanceof ClickableGUIComponent)
					{
						ClickableGUIComponent clickableComponent = (ClickableGUIComponent)guiComponent;
						
						clickableComponent.removePlayerHandlers(player);
					}
					
					if(guiComponent instanceof TextBoxComponent)
					{
						TextBoxComponent textbox = (TextBoxComponent)guiComponent;
						textbox.removePlayerTextBoxValue(player);
						textbox.removePlayerHandlers(player);
					}
					
					else if(guiComponent instanceof ToggleSwitchComponent)
					{
						ToggleSwitchComponent toggleSwitch = (ToggleSwitchComponent)guiComponent;
						toggleSwitch.removePlayerToggleSwitchState(player);
						toggleSwitch.removePlayerHandlers(player);
					}
					
					else if(guiComponent instanceof ValueScrollerComponent)
					{
						ValueScrollerComponent valueScroller = (ValueScrollerComponent)guiComponent;
						valueScroller.removePlayerScrollValue(player);
						valueScroller.removePlayerHandlers(player);
					}
				}
			}
		}
	}
}