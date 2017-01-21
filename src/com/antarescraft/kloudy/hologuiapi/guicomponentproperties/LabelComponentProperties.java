package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.ArrayList;

import com.antarescraft.kloudy.plugincore.config.ConfigProperty;

public class LabelComponentProperties extends GUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 10;
	
	@ConfigProperty(key = "text")
	public ArrayList<String> lines;
	
	@Override
	public void configParseComplete()
	{
		if(labelDistance == null)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
	}
}