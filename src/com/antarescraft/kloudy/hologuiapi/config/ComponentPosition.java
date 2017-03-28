package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class ComponentPosition implements ConfigObject
{
	@ConfigProperty(key = "x")
	private double x = 0;
	
	@ConfigProperty(key = "y")
	private double y = 0;
	
	@SuppressWarnings("unused")
	private ComponentPosition(){}
	
	public ComponentPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public ComponentPosition(ComponentPosition position)
	{
		x = position.getX();
		y = position.getY();
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
	public void configParseComplete(PassthroughParams params)
	{
		x *= -1;
	}
}