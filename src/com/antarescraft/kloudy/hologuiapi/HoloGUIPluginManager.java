package com.antarescraft.kloudy.hologuiapi;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;

import com.antarescraft.kloudy.hologuiapi.util.ConfigManager;

public class HoloGUIPluginManager
{
	private static HoloGUIPluginManager instance;
	
	private HashMap<String, HoloGUIPlugin> plugins;
	
	private HoloGUIPluginManager()
	{
		plugins = new HashMap<String, HoloGUIPlugin>();
	}
	
	public static HoloGUIPluginManager getInstance()
	{
		if(instance == null) instance = new HoloGUIPluginManager();
		
		return instance;
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
	 * Loads the holoGUIPlugin's GUI pages from config
	 */
	public void loadGUIPages(HoloGUIPlugin holoGUIPlugin)
	{
		ConfigManager.getInstance().loadGUIContainers(Bukkit.getConsoleSender(), holoGUIPlugin);
	}
	
	/**
	 * 
	 * @return Collection of all hooked HoloGUI plugins
	 */
	public Collection<HoloGUIPlugin> getHookedHoloGUIPlugins()
	{
		return plugins.values();
	}
}