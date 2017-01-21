package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

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