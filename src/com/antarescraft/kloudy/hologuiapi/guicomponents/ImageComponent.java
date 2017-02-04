package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ImageComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

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
	public ImageComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, ImageComponent.class, plugin);
		}
		catch(Exception e){}
		
		return null;
	}

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
	
	public int getWidth()
	{
		return properties.width;
	}
	
	public void setWidth(int width)
	{
		properties.width = width;
	}
	
	public int getHeight()
	{
		return properties.height;
	}
	
	public void setHeight(int height)
	{
		properties.height = height;
	}
	
	public String getImageName()
	{
		return properties.imageFileName;
	}
	
	public void setImageName(String imageFileName)
	{
		properties.imageFileName = imageFileName;
	}
	
	public void setLines(String[][] lines)
	{
		this.lines = lines;
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
		
		lines = plugin.loadImage(properties.imageFileName, properties.width, properties.height, properties.symmetrical);
		
		frames = lines.length;
	}
	
	@Override
	public ImageComponentProperties getProperties()
	{
		return properties;
	}
}