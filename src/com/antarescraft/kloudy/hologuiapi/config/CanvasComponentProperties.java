package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

/**
 * Collection of properties that all CanvasComponents have.
 */
public class CanvasComponentProperties extends GUIComponentProperties
{
	private static final double DEFAULT_DISTANCE = 15;
	
	@ConfigProperty(key = "width")
	public int width;
	
	@ConfigProperty(key = "height")
	public int height;
	
	@Override
	protected double getDefaultDistance()
	{
		return DEFAULT_DISTANCE;
	}
}