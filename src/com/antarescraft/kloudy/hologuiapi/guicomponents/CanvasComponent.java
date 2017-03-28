package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.CanvasComponentConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUICanvasComponent;

/**
 * Represents a Canvas component on a player's gui.
 * 
 * The Canvas component contains a grid of pixels that can be manipulated 
 * independently of each other. Each pixel can be zoomed in/out independent of its neighbors.
 */
public class CanvasComponent extends GUIComponent
{
	private CanvasComponentConfig config;
	
	CanvasComponent(HoloGUIPlugin plugin, CanvasComponentConfig config)
	{
		super(plugin);
		
		this.config = config;
	}
	
	@Override
	public PlayerGUICanvasComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUICanvasComponent(player, this);
	}

	@Override
	public CanvasComponentConfig getConfig() 
	{
		return config;
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
		return (1 / config.getDistance()) * 0.19;
	}
}