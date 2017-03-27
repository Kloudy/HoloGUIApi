package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.config.CanvasComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUICanvasComponent;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

/**
 * Represents a Canvas component on a player's gui.
 * 
 * The Canvas component contains a grid of pixels that can be manipulated 
 * independently of each other. Each pixel can be zoomed in/out independent of its neighbors.
 */
public class CanvasComponent extends GUIComponent
{
	@ConfigElement
	@ConfigProperty(key = "")
	private CanvasComponentProperties properties;
	
	private CanvasComponent(){}
	
	@Override
	public PlayerGUICanvasComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUICanvasComponent(player, this);
	}

	@Override
	public CanvasComponentProperties getProperties() 
	{
		return properties;
	}

	@Override
	public void updateIncrement(){}
	
	@Override
	public String[] updateComponentLines(Player player) 
	{
		return null;
	}

	@Override
	public double getLineHeight()
	{
		return (1 / properties.getDistance()) * 0.19;
	}
}