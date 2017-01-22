package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.StringConfigProperty;

public class ToggleSwitchComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 15;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 4;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "value")
	public boolean defaultState;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-on")
	public String onclickOn;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-off")
	public String onclickOff;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "default-toggle-on.png")
	@ConfigProperty(key = "icon-on")
	public String onIcon;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "default-toggle-off.png")
	@ConfigProperty(key = "icon-off")
	public String offIcon;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-onclick-as-console")
	public boolean executeOnClickOnAsConsole;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-onclick-off-as-console")
	public boolean executeOnClickOffAsConsole;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "on-value")
	public String onValue;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "off-value")
	public String offValue;
	
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