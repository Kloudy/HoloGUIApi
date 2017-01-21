package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.ArrayList;

import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.StringConfigProperty;

public class ValueScrollerComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 10;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 2;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "BLOCK_LAVA_POP")
	@ConfigProperty(key = "onscroll-sound")
	public String onscrollSoundString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0.5, maxValue = 0.0, minValue = 1.0)
	@ConfigProperty(key = "onscroll-sound-volume")
	public double onscrollSoundVolume;
	
	@StringConfigProperty(defaultValue = "", acceptedValues = { "decimal", "integer", "duration", "date", "list" }, acceptedValuesIgnoreCase = true)
	@ConfigProperty(key = "value-type")
	public String valueType;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "default-value")
	public String defaultValueString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "step")
	public String stepString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "min-value")
	public String minValueString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "max-value")
	public String maxValueString;
	
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
	public void configParseComplete()
	{
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