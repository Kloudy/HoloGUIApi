package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;
import com.antarescraft.kloudy.plugincore.config.Parsable;

/**
 * Wrapper class that performs the conversion between a config String and a MinecraftColor.
 */
public class MinecraftColorConfigWrapper implements Parsable<MinecraftColorConfigWrapper, MinecraftColor>
{
	private MinecraftColor color;
	
	public MinecraftColorConfigWrapper(){}
	
	public MinecraftColorConfigWrapper(MinecraftColor color)
	{
		this.color = color;
	}
	
	@Override
	public MinecraftColorConfigWrapper parse(String value)
	{
		try
		{
			color = MinecraftColor.valueOf(value);
						
			return this;
		}
		catch(Exception e){}

		return null;
	}

	@Override
	public String save()
	{
		return color.toString();
	}

	@Override
	public MinecraftColor unwrap()
	{
		return color;
	}
}