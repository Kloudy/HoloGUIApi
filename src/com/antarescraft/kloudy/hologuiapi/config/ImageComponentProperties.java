package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;

public class ImageComponentProperties extends GUIComponentProperties
{
	private static final double DEFAULT_DISTANCE = 15;
	
	@ConfigProperty(key = "image-src")
	private String imageSource;
	
	@ConfigProperty(key = "width")
	private int width;
	
	@ConfigProperty(key = "height")
	private int height;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "symmetrical")
	private boolean symmetrical;
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
	}
	
	@Override
	public double getDefaultDistance()
	{
		return DEFAULT_DISTANCE;
	}
	
	public String getImageSource()
	{
		return imageSource;
	}
	
	public void setImageSource(String imageSource)
	{
		this.imageSource = imageSource;
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
	
	public boolean isSymmetrical()
	{
		return symmetrical;
	}
	
	public void setSymmetrical(boolean symmetrical)
	{
		this.symmetrical = symmetrical;
	}
}