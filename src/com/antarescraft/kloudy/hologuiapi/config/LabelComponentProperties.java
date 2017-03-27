package com.antarescraft.kloudy.hologuiapi.config;

import java.util.ArrayList;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class LabelComponentProperties extends GUIComponentProperties implements ConfigObject
{
	private static final double DEFAULT_DISTANCE = 10;
	
	@ConfigProperty(key = "text")
	private ArrayList<String> lines;
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		if(lines == null) lines = new ArrayList<String>();
	}
	
	@Override
	public double getDefaultDistance()
	{
		return DEFAULT_DISTANCE;
	}
	
	public ArrayList<String> getLines()
	{
		return lines;
	}
	
	public void setLines(ArrayList<String> lines)
	{
		this.lines = lines;
	}
}