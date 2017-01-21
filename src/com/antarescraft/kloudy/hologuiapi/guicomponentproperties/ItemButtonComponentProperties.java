package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.configobjects.ConfigVector;

public class ItemButtonComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 6;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 1.3;
	
	@ConfigProperty(key = "item-id")
	public String itemString;
	
	@ConfigElement
	@ConfigProperty(key = "rotation")
	public ConfigVector rotation;

	@Override
	public void configParseComplete() 
	{
		super.configParseComplete();
		
		if(labelDistance == null)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
		
		if(labelZoomDistance == null)
		{
			labelZoomDistance = DEFAULT_LABEL_ZOOM_DISTANCE;
		}
	}
}