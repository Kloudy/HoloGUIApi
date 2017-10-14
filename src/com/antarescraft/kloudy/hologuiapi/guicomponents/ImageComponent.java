package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.exceptions.InvalidImageException;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ImageComponentProperties;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.ImageOptions;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

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
		
		ImageOptions options = new ImageOptions();
		options.width = properties.getWidth();
		options.height = properties.getHeight();
		options.symmetrical = properties.isSymmetrical();
		
		try
		{
			lines = plugin.loadImage(properties.getImageSource(), options);
		} 
		catch (InvalidImageException e)
		{
			MessageManager.error(Bukkit.getConsoleSender(), "An error occured while attempting to load the image for GUI component: " + properties.getId());
		}
	}
	
	@Override
	public ImageComponentProperties getProperties()
	{
		return properties;
	}
}