package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;

/**
 * Collection all of the properties that all gui components share
 */
public abstract class GUIComponentConfig implements ConfigObject
{
	@ConfigElementKey
	public String id;
	
	@ConfigElement
	@ConfigProperty(key = "position")
	public ComponentPosition position;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "label")
	public String label = null;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "label-distance")
	public double labelDistance = -1;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "always-show-label")
	public boolean alwaysShowLabel = false;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "hidden")
	public boolean hidden = false;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = -1, maxValue = 50, minValue = 2)
	@ConfigProperty(key="distance")
	public double distance;
	
	protected abstract double getDefaultDistance();
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		if(distance == -1)
		{
			distance = getDefaultDistance();
		}
				
		if(labelDistance == -1)
		{
			labelDistance = getDefaultDistance();
		}
	}
	
	@Override
	public String toString()
	{
		return "\n" + ConfigParser.generateConfigString(HoloGUIApi.pluginName, this);
	}
}