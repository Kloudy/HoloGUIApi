package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.StringConfigProperty;

public class TextBoxComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_DISTANCE = 10;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 2;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "default-text")
	private String defaultLine;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "evaluate-placeholders")
	private boolean evaluatePlaceholders;

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
	
	public String getDefaultLine()
	{
		return defaultLine;
	}
	
	public void setDefaultLine(String defaultLine)
	{
		this.defaultLine = defaultLine;
	}
	
	public boolean evaluationPlaceholders()
	{
		return evaluatePlaceholders;
	}
	
	public void setEvaluatePlaceholders(boolean evaluatePlaceholders)
	{
		this.evaluatePlaceholders = evaluatePlaceholders;
	}
}