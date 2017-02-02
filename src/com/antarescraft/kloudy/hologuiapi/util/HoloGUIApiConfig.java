package com.antarescraft.kloudy.hologuiapi.util;

import java.util.Collection;
import java.util.HashMap;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementMap;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.IntConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;

public class HoloGUIApiConfig implements ConfigObject
{
	private static HoloGUIApiConfig instance;
	
	@OptionalConfigProperty
	@ConfigElementMap
	@ConfigProperty(key = "stationary-gui-displays")
	private HashMap<String, StationaryGUIDisplayContainer> stationaryGUIDisplayContainers;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 25, maxValue = 50, minValue = 5)
	@ConfigProperty(key = "render-distance")
	private double stationaryDisplayRenderDistance;
	
	@OptionalConfigProperty
	@IntConfigProperty(defaultValue = 2, maxValue = 40, minValue = 1)
	@ConfigProperty(key = "gui-update-tickrate")
	private int guiUpdateTickrate;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "debug-mode")
	private boolean debugMode;
	
	public static void parseConfig(HoloGUIApi plugin)
	{	
		plugin.reloadConfig();
		
		PassthroughParams params = new PassthroughParams();
		params.addParam("plugin", plugin);
		
		instance = ConfigParser.parse(plugin.getConfig().getRoot(), HoloGUIApiConfig.class, HoloGUIApi.pluginName, params);
	}
	
	public static Collection<StationaryGUIDisplayContainer> getStationaryDisplays()
	{
		return instance.stationaryGUIDisplayContainers.values();
	}
	
	public static StationaryGUIDisplayContainer getStationaryDisplay(String stationaryDisplayId)
	{
		return instance.stationaryGUIDisplayContainers.get(stationaryDisplayId);
	}
	
	public static boolean debugMode()
	{
		return instance.debugMode;
	}
	
	public static double stationaryDisplayRenderDistance()
	{
		return instance.stationaryDisplayRenderDistance;
	}
	
	public static int guiUpdateTickrate()
	{
		return instance.guiUpdateTickrate;
	}
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		if(stationaryGUIDisplayContainers == null)
		{
			stationaryGUIDisplayContainers = new HashMap<String, StationaryGUIDisplayContainer>();
		}
	}
}