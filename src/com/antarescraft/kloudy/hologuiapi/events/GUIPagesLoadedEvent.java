package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;

public class GUIPagesLoadedEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
	private HoloGUIPlugin holoGUIPlugin;
	
	public GUIPagesLoadedEvent(HoloGUIPlugin holoGUIPlugin)
	{
		this.holoGUIPlugin = holoGUIPlugin;
	}
	
	@Override
	public HandlerList getHandlers()
	{
	    return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return holoGUIPlugin;
	}
}