package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.StringConfigProperty;

public class ToggleSwitchComponentConfig extends ClickableGUIComponentConfig
{
	private static final double DEFAULT_DISTANCE = 15;
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
	@ConfigProperty(key = "execute-onclick-on-as-console")
	public boolean executeOnclickOnAsConsole;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-onclick-off-as-console")
	public boolean executeOnclickOffAsConsole;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "on-value")
	public String onValue;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "off-value")
	public String offValue;
	
	@Override
	public void configParseComplete(PassthroughParams params) 
	{
		super.configParseComplete(params);
	}
	
	@Override
	public double getDefaultDistance()
	{
		return DEFAULT_DISTANCE;
	}
	
	@Override
	public double getDefaultLabelZoomDistance()
	{
		return DEFAULT_LABEL_ZOOM_DISTANCE;
	}
}