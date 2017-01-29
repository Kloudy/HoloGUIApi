package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.configobjects.ConfigVector;

public class ItemComponentProperties extends GUIComponentProperties
{
private static final double DEFAULT_LABEL_DISTANCE = 6;
	
	@ConfigProperty(key = "item-id")
	public String itemString;
	
	@ConfigElement
	@ConfigProperty(key = "rotation")
	public ConfigVector rotation;

	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		super.configParseComplete(passthroughParams);
		
		if(labelDistance == -1)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
	}
}