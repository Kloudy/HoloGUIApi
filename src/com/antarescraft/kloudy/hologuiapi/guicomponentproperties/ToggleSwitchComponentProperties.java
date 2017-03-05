package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.StringConfigProperty;

public class ToggleSwitchComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_DISTANCE = 15;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 4;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "value")
	private boolean defaultState;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-on")
	private String onclickOn;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-off")
	private String onclickOff;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "default-toggle-on.png")
	@ConfigProperty(key = "icon-on")
	private String onIcon;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "default-toggle-off.png")
	@ConfigProperty(key = "icon-off")
	private String offIcon;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-onclick-on-as-console")
	private boolean executeOnclickOnAsConsole;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-onclick-off-as-console")
	private boolean executeOnclickOffAsConsole;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "on-value")
	private String onValue;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "off-value")
	private String offValue;
	
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
	
	public boolean getDefaultState()
	{
		return defaultState;
	}
	
	public void setDefaultState(boolean defaultState)
	{
		this.defaultState = defaultState;
	}
	
	public String getOnclickOn()
	{
		return onclickOn;
	}
	
	public void setOnclickOn(String onclickOn)
	{
		this.onclickOn = onclickOn;
	}
	
	public String getOnclickOff()
	{
		return onclickOff;
	}
	
	public void setOnclickOff(String onclickOff)
	{
		this.onclickOff = onclickOff;
	}
	
	public String getOnIcon()
	{
		return onIcon;
	}
	
	public void setOnIcon(String onIcon)
	{
		this.onIcon = onIcon;
	}
	
	public String getOffIcon()
	{
		return offIcon;
	}
	
	public void setOffIcon(String offIcon)
	{
		this.offIcon = offIcon;
	}
	
	public boolean executeOnclickOnAsConsole()
	{
		return executeOnclickOnAsConsole;
	}
	
	public void setExecuteOnclickOnAsConsole(boolean executeOnclickOnAsConsole)
	{
		this.executeOnclickOnAsConsole = executeOnclickOnAsConsole;
	}
	
	public boolean executeOnclickOffAsConsole()
	{
		return executeOnclickOffAsConsole;
	}
	
	public void setExecuteOnClickOffAsConsole(boolean executeOnclickOffAsConsole)
	{
		this.executeOnclickOffAsConsole = executeOnclickOffAsConsole;
	}
	
	public String getOnValue()
	{
		return onValue;
	}
	
	public void setOnValue(String onValue)
	{
		this.onValue = onValue;
	}
	
	public String getOffValue()
	{
		return offValue;
	}
	
	public void setOffValue(String offValue)
	{
		this.offValue = offValue;
	}
}