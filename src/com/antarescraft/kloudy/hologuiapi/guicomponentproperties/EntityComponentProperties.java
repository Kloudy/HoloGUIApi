package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;

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
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		if(labelDistance == -1)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
	}
}