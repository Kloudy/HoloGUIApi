package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;

public class PluginEnableEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PluginEnableEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void pluginEnableEvent(PluginEnableEvent event)
	{
		Plugin plugin = event.getPlugin();
		if(plugin instanceof HoloGUIPlugin && !(plugin instanceof HoloGUIApi))
		{
			holoGUI.getHoloGUIPluginManager().hookHoloGUIPlugin((HoloGUIPlugin)plugin);
		}
	}
}