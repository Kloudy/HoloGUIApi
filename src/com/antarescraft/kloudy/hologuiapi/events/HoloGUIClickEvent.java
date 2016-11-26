package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ClickableGUIComponent;

public class HoloGUIClickEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
	private HoloGUIPlugin holoGUIPlugin;
	private ClickableGUIComponent clickedComponent;
	private Player player;
	
	public HoloGUIClickEvent(HoloGUIPlugin holoGUIPlugin, ClickableGUIComponent clickedComponent, Player player)
	{
		this.holoGUIPlugin = holoGUIPlugin;
		this.clickedComponent = clickedComponent;
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
	
	public Player getPlayer()
	{
		return player;
	}
	
	public ClickableGUIComponent getClickedComponent()
	{
		return clickedComponent;
	}
}