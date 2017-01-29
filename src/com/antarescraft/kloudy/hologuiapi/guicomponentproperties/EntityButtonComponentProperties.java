package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;

public class EntityButtonComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 8;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 1.3;
	
	@ConfigProperty(key = "type")
	public String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = 0)
	@ConfigProperty(key = "yaw")
	public double yaw;

	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		super.configParseComplete(passthroughParams);
		
		if(labelDistance == -1)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
		
		if(labelZoomDistance == -1)
		{
			labelZoomDistance = DEFAULT_LABEL_ZOOM_DISTANCE;
		}
	}
}