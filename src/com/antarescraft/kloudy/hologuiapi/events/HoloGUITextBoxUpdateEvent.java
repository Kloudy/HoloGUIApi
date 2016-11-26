package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TextBoxComponent;

public class HoloGUITextBoxUpdateEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
	private HoloGUIPlugin holoGUIPlugin;
	private TextBoxComponent textBox;
	private Player player;
	
	public HoloGUITextBoxUpdateEvent(HoloGUIPlugin holoGUIPlugin, TextBoxComponent textBox, Player player)
	{
		this.holoGUIPlugin = holoGUIPlugin;
		this.textBox = textBox;
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
	
	public TextBoxComponent getTextBox()
	{
		return textBox;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}