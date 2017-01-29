package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.ArrayList;
import java.util.HashMap;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class LabelComponentProperties extends GUIComponentProperties implements ConfigObject
{
	private static final double DEFAULT_LABEL_DISTANCE = 10;
	
	@ConfigProperty(key = "text")
	public ArrayList<String> lines;
	
	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		super.configParseComplete(passthroughParams);
		
		if(lines == null) lines = new ArrayList<String>();
		
		HoloGUIApi.debugMessage(id + " (label) num lines: " + lines.size());
				
		if(labelDistance == -1)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
	}
}