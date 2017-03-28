package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.config.ImageComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class ImageComponent extends GUIComponent implements ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
	private ImageComponentProperties properties;
	
	private int currentFrame = 0;
	private String[][] lines = null;
	
	private ImageComponent(){}
	
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
		return (1 / properties.getDistance()) * 0.21;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		lines = plugin.loadImage(properties.getImageSource(), properties.getWidth(), properties.getHeight(), properties.isSymmetrical());
	}
	
	@Override
	public ImageComponentProperties getConfig()
	{
		return properties;
	}
}