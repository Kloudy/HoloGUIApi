package com.antarescraft.kloudy.hologuiapi.util;

import org.bukkit.util.Vector;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;

public class ConfigVector implements ConfigObject
{
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = Double.MIN_VALUE)
	@ConfigProperty(key = "x")
	private double x;
	
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = Double.MIN_VALUE)
	@ConfigProperty(key = "y")
	private double y;
	
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = Double.MIN_VALUE)
	@ConfigProperty(key = "z")
	private double z;
	
	public ConfigVector(Vector vector)
	{
		x = vector.getX();
		y = vector.getY();
		z = vector.getZ();
	}
	
	public Vector toVector()
	{
		return new Vector(x, y, z);
	}

	@Override
	public void configParseComplete(){}
}