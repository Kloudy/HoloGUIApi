package com.antarescraft.kloudy.hologuiapi.config;

import java.util.ArrayList;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.StringConfigProperty;
import com.antarescraft.kloudy.plugincore.configwrappers.SoundConfigWrapper;

public class ValueScrollerComponentConfig extends ClickableGUIComponentConfig
{
	private static final double DEFAULT_DISTANCE = 10;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 2;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "BLOCK_LAVA_POP")
	@ConfigProperty(key = "onscroll-sound")
	public SoundConfigWrapper onscrollSound;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0.5, maxValue = 1.0, minValue = 0)
	@ConfigProperty(key = "onscroll-sound-volume")
	public double onscrollSoundVolume;
	
	@StringConfigProperty(defaultValue = "", acceptedValues = { "decimal", "integer", "duration", "date", "list" }, acceptedValuesIgnoreCase = true)
	@ConfigProperty(key = "value-type")
	public String valueType;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "default-value")
	public String defaultValue;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "step")
	public String step;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "min-value")
	public String minValue;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "max-value")
	public String maxValue;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "#.#")
	@ConfigProperty(key = "decimal-format")
	public String decimalFormat;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "wrap")
	public boolean wrap;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "list-items")
	public ArrayList<String> listItems;

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