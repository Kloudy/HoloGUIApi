package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;

/**
 * Represents an abstract data model that can be bound to a gui page.
 * All functions exposed by an implementing class can be accessed with the following syntax: $model.exampleFunction(...);
 */
public abstract class PlayerGUIPageModel 
{
	protected HoloGUIPlugin plugin;
	protected GUIPage guiPage;
	protected Player player;
	
	public PlayerGUIPageModel(HoloGUIPlugin plugin, GUIPage guiPage, Player player)
	{
		this.plugin = plugin;
		this.guiPage = guiPage;
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public GUIPage getGUIPage()
	{
		return guiPage;
	}
}