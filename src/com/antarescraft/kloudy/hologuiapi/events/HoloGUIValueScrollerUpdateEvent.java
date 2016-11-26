package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ValueScrollerComponent;

public class HoloGUIValueScrollerUpdateEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
	private HoloGUIPlugin holoGUIPlugin;
	private ValueScrollerComponent valueScroller;
	private Player player;

	public HoloGUIValueScrollerUpdateEvent(HoloGUIPlugin holoGUIPlugin, ValueScrollerComponent valueScroller, Player player)
	{
		this.holoGUIPlugin = holoGUIPlugin;
		this.valueScroller = valueScroller;
		this.player = player;
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
	
	public ValueScrollerComponent getValueScroller()
	{
		return valueScroller;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}