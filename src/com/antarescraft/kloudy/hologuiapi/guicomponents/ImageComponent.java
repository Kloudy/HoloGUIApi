package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;

public class ImageComponent extends GUIComponent
{
	private int frames = 0;
	private int currentFrame = 0;
	private String[][] lines;
	private String imageName;
	private int width;
	private int height;
	
	private double displayDistance;
	private double lineHeight;
	
	public ImageComponent(GUIComponentProperties properties, String[][] lines, String imageName, int width, int height)
	{
		super(properties);
		
		this.lines = lines;
		this.imageName = imageName;
		this.width = width;
		this.height = height;
		
		displayDistance = 15;
		lineHeight = 0.014;
	}
	
	@Override
	public ImageComponent clone()
	{
		String[][] linesCopy = new String[lines.length][lines[0].length];
		for(int i = 0; i < lines.length; i++)
		{
			for(int j = 0; j < lines[i].length; j++)
			{
				linesCopy[i][j] = lines[i][j];
			}
		}
		
		return new ImageComponent(cloneProperties(), linesCopy, imageName, width, height);
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
		return imageName;
	}
	
	public void setImageName(String imageName)
	{
		this.imageName = imageName;
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