package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

public class ImageComponent extends GUIComponent
{
	private int frames = 0;
	private int currentFrame = 0;
	private String[][] lines;
	private String imageFileName;
	private int width;
	private int height;
	
	private double displayDistance;
	private double lineHeight;
	
	public ImageComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
	public ImageComponent(HoloGUIPlugin plugin, String[][] lines, String imageFileName, int width, int height)
	{
		super(plugin);
		
		this.lines = lines;
		this.imageFileName = imageFileName;
		this.width = width;
		this.height = height;
		
		displayDistance = 15;
		lineHeight = 0.014;
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
		frames = lines.length;
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
	
	public void setDisplayDistance(double displayDistance)
	{
		this.displayDistance = displayDistance;
	}

	@Override
	public double getDisplayDistance()
	{
		return displayDistance;
	}
	
	public void setLineHeight(double lineHeight)
	{
		this.lineHeight = lineHeight;
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
		return lineHeight;
	}
}