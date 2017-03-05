package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.ArrayList;

import org.bukkit.Sound;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.StringConfigProperty;
import com.antarescraft.kloudy.plugincore.utils.Utils;

public class ValueScrollerComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_DISTANCE = 10;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 2;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "BLOCK_LAVA_POP")
	@ConfigProperty(key = "onscroll-sound")
	private String onscrollSoundString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0.5, maxValue = 1.0, minValue = 0)
	@ConfigProperty(key = "onscroll-sound-volume")
	private double onscrollSoundVolume;
	
	@StringConfigProperty(defaultValue = "", acceptedValues = { "decimal", "integer", "duration", "date", "list" }, acceptedValuesIgnoreCase = true)
	@ConfigProperty(key = "value-type")
	private String valueType;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "default-value")
	private String defaultValue;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "step")
	private String step;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "min-value")
	private String minValue;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "max-value")
	private String maxValue;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "#.#")
	@ConfigProperty(key = "decimal-format")
	private String decimalFormat;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "wrap")
	private boolean wrap;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "list-items")
	private ArrayList<String> listItems;

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
	
	public Sound getOnscrollSound()
	{
		return Utils.parseSound(onscrollSoundString);
	}
	
	public void setOnscrollSound(Sound sound)
	{
		onscrollSoundString = sound.toString();
	}
	
	public float getOnscrollSoundVolume()
	{
		return (float)onscrollSoundVolume;
	}
	
	public void setOnscrollSoundVolume(float onscrollSoundVolume)
	{
		this.onscrollSoundVolume = (double)onscrollSoundVolume;
	}
	
	public String getValueType()	
	{
		return valueType;
	}
	
	public void setValueType(String valueType)
	{
		this.valueType = valueType;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	
	public String getStep()
	{
		return step;
	}
	
	public void setStep(String step)
	{
		this.step = step;
	}
	
	public String getMinValue()
	{
		return minValue;
	}
	
	public void setMinValue(String minValue)
	{
		this.minValue = minValue;
	}
	
	public String getMaxValue()
	{
		return maxValue;
	}
	
	public void setMaxValue(String maxValue)
	{
		this.maxValue = maxValue;
	}
	
	public String getDecimalFormat()
	{
		return decimalFormat;
	}
	
	public void setDecimalFormat(String decimalFormat)
	{
		this.decimalFormat = decimalFormat;
	}
	
	public boolean wrap()
	{
		return wrap;
	}
	
	public void setWrap(boolean wrap)
	{
		this.wrap = wrap;
	}
	
	public ArrayList<String> getListItems()
	{
		return listItems;
	}
	
	public void setListItems(ArrayList<String> listItems)
	{
		this.listItems = listItems;
	}
}