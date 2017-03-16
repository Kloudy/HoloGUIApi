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
	
	/**
	 * Closes the GuiPage the player is currently viewing
	 */
	public void closePage()
	{
		plugin.getHoloGUIApi().closeGUIPage(player);
	}
	
	/**
	 * Opens the specified gui page from the given model for the player
	 */
	public void openGuiPage(PlayerGUIPageModel model)
	{
		plugin.getHoloGUIApi().openGUIPage(plugin, model);
	}
	
	/**
	 * Opens the specifed gui page for the player
	 * @param guiPageId Id of the gui page to open
	 */
	public void openGuiPage(String guiPageId)
	{
		plugin.getHoloGUIApi().openGUIPage(plugin, player, guiPageId);
	}
}