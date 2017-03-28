package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.ImageComponentConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;

public class ImageComponent extends GUIComponent
{
	private ImageComponentConfig config;
	
	private int currentFrame = 0;
	private String[][] lines = null;
	
	ImageComponent(HoloGUIPlugin plugin, ImageComponentConfig config)
	{
		super(plugin);
		
		this.config = config;
		
		lines = plugin.loadImage(config.imageSource, config.width, config.height, config.symmetrical);
	}
	
	public void setLines(String[][] lines)
	{
		this.lines = lines;
		
		currentFrame = 0;
	}

	@Override
	public PlayerGUITextComponent initPlayerGUIComponent(Player player) 
	{
		return new PlayerGUITextComponent(player, this, updateComponentLines(player));
	}

	@Override
	public void updateIncrement()
	{
		currentFrame = (currentFrame + 1) % lines.length;
	}

	@Override
	public String[] updateComponentLines(Player player)
	{
		return lines[currentFrame];
	}
	
	@Override
	public double getLineHeight()
	{
		return (1 / config.getDistance()) * 0.21;
	}
	
	@Override
	public ImageComponentConfig getConfig()
	{
		return config;
	}
}