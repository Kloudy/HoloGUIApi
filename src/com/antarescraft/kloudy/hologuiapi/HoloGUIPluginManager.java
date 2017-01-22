package com.antarescraft.kloudy.hologuiapi;

import java.util.Collection;
import java.util.HashMap;

/**
 * Singleton class that handles logic surrounding hooking into 3rd party plugins
 */
public class HoloGUIPluginManager
{	
	private HashMap<String, HoloGUIPlugin> plugins;
	
	public HoloGUIPluginManager()
	{
		plugins = new HashMap<String, HoloGUIPlugin>();
	}
	
	/**
	 * Hooks the HoloGUIPlugin into the HoloGUI core lib and loads its config values
	 */
	public void hookHoloGUIPlugin(HoloGUIPlugin holoGUIPlugin)
	{
		plugins.put(holoGUIPlugin.getName(), holoGUIPlugin);
	}
	
	/**
	 * Unhooks the HoloGUIPlugin from the HoloGUI API
	 */
	public void unhookHoloGUIPlugin(HoloGUIPlugin holoGUIPlugin)
	{
		plugins.remove(holoGUIPlugin.getName());
	}
	
	/**
	 * Unhooks all HoloGUIPlugins from the HoloGUI API
	 */
	public void unhookAllHoloGUIPlugins()
	{
		plugins.clear();
	}
	
	/**
	 * 
	 * @return Collection of all hooked HoloGUI plugins
	 */
	public Collection<HoloGUIPlugin> getHookedHoloGUIPlugins()
	{
		return plugins.values();
	}
	
	public HoloGUIPlugin getHookedHoloGUIPlugin(String pluginName)
	{
		return plugins.get(pluginName);
	}
}