package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ToggleSwitchComponent;

public class HoloGUIToggleSwitchUpdateEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
	private HoloGUIPlugin holoGUIPlugin;
	private ToggleSwitchComponent toggleSwitch;
	private Player player;
	
	public HoloGUIToggleSwitchUpdateEvent(HoloGUIPlugin holoGUIPlugin, ToggleSwitchComponent toggleSwitch, Player player)
	{
		this.holoGUIPlugin = holoGUIPlugin;
		this.toggleSwitch = toggleSwitch;
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
	
	public ToggleSwitchComponent getToggleSwitch()
	{
		return toggleSwitch;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}