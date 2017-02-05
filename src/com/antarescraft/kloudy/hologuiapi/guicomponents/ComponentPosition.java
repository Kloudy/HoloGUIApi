package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class ComponentPosition implements ConfigObject
{
	//@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = Double.MIN_VALUE)
	@ConfigProperty(key = "x")
	private double x = 0;
	
	//@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = Double.MIN_VALUE)
	@ConfigProperty(key = "y")
	private double y = 0;
	
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
	public void configParseComplete(PassthroughParams params){}
}