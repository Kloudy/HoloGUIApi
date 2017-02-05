package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ImageComponentProperties;
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
	
	private int frames = 0;
	private int currentFrame = 0;
	private String[][] lines = null;
	
	private ImageComponent(){}

	@Override
	public PlayerGUITextComponent initPlayerGUIComponent(Player player) 
	{
		return new PlayerGUITextComponent(player, this, updateComponentLines(player));
	}

	@Override
	public void updateIncrement()
	{
		currentFrame++;
		if(currentFrame >= frames)
		{
			currentFrame = 0;
		}
	}
	
	public void setLines(String[][] lines)
	{
		this.lines = lines;
	}

	@Override
	public String[] updateComponentLines(Player player)
	{
		return lines[currentFrame];
	}

	@Override
	public double getDisplayDistance()
	{
		return 15;
	}
	
	@Override
	public double getLineHeight()
	{
		return 0.014;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		lines = plugin.loadImage(properties.getImageSource(), properties.getWidth(), properties.getHeight(), properties.isSymmetrical());
		
		frames = lines.length;
	}
	
	@Override
	public ImageComponentProperties getProperties()
	{
		return properties;
	}
}