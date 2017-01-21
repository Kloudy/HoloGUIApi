package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;

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