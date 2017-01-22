package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.ArrayList;
import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;

public class LabelComponentProperties extends GUIComponentProperties implements ConfigObject
{
	private static final double DEFAULT_LABEL_DISTANCE = 10;
	
	@ConfigProperty(key = "text")
	public ArrayList<String> lines;
	
	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		if(labelDistance == null)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
	}
}