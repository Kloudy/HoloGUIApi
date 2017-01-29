package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;

public class ComponentPosition implements ConfigObject
{
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = Double.MIN_VALUE)
	@ConfigProperty(key = "x")
	private double x;
	
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = Double.MIN_VALUE)
	@ConfigProperty(key = "y")
	private double y;
	
	@SuppressWarnings("unused")
	private ComponentPosition(){}
	
	public ComponentPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	@Override
	public ComponentPosition clone()
	{
		return new ComponentPosition(x, y);
	}

	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams){}
}