package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;

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
	
	@Override
	public String toString()
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(super.toString())
		.append(String.format("icon: %s,\n", icon))
		.append(String.format("mini: %s,\n", mini))
		.append(String.format("symmetrical: %b }", symmetrical));
		
		return strBuilder.toString();
	}
}