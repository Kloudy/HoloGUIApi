package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;

public class PluginDisableEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PluginDisableEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void pluginDisableEvent(PluginDisableEvent event)
	{
		Plugin plugin = event.getPlugin();
		if(plugin instanceof HoloGUIPlugin && !(plugin instanceof HoloGUIApi))
		{
			HoloGUIPlugin holoGUIPlugin = (HoloGUIPlugin)plugin;
			for(PlayerData playerData : PlayerData.getAllPlayerData())
			{
				PlayerGUIPage playerGUIContainer = playerData.getPlayerGUIPage();
				if(playerGUIContainer != null)
				{
					if(playerGUIContainer.getGUIPage().getHoloGUIPlugin().getName().equals(holoGUIPlugin.getName()))
					{
						playerGUIContainer.destroy();
					}
				}
			}
			
			holoGUI.getHoloGUIPluginManager().unhookHoloGUIPlugin(holoGUIPlugin);
		}
	}
}