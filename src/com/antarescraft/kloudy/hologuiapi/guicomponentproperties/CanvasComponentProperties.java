package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

/**
 * Collection of properties that all CanvasComponents have.
 */
public class CanvasComponentProperties extends GUIComponentProperties
{
	private static final double DEFAULT_DISTANCE = 15;
	
	@ConfigProperty(key = "width")
	private int width;
	
	@ConfigProperty(key = "height")
	private int height;
	
	@Override
	protected double getDefaultDistance()
	{
		return DEFAULT_DISTANCE;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public int getHeight()
	{
		return height;
	}
}