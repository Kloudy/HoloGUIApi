package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

public class ImageComponent extends GUIComponent implements ConfigObject
{
	@ConfigProperty(key = "image-src")
	private String imageFileName;
	
	@ConfigProperty(key = "width")
	private int width;
	
	@ConfigProperty(key = "height")
	private int height;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "symmetrical")
	private boolean symmetrical;
	
	private int frames = 0;
	private int currentFrame = 0;
	private String[][] lines = null;
	
	public ImageComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
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
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public String getImageName()
	{
		return imageFileName;
	}
	
	public void setImageName(String imageName)
	{
		this.imageFileName = imageName;
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
	public void configParseComplete()
	{
		lines = plugin.loadImage(imageFileName, width, height, symmetrical);
		
		frames = lines.length;
	}
}