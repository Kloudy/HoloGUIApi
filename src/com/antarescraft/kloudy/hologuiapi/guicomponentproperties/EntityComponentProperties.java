package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;

public class EntityComponentProperties extends GUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 8;
	
	@ConfigProperty(key = "type")
	public String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = 360, minValue = 0)
	@ConfigProperty(key = "yaw")
	public double yaw;
	
	@Override
	public void configParseComplete()
	{
		if(labelDistance == null)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
	}
}