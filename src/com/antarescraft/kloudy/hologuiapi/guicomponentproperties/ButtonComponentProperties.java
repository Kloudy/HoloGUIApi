package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;

public class ButtonComponentProperties extends ClickableGUIComponentProperties implements ConfigObject
{
	private static final double DEFAULT_LABEL_DISTANCE = 15;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 4;
	
	@ConfigProperty(key = "icon")
	public String icon;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "mini")
	public boolean mini;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "symmetrical")
	public boolean symmetrical;

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