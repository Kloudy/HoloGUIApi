package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ComponentPosition;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

/**
 * Collection all of the properties that all gui components share
 */
public abstract class GUIComponentProperties implements ConfigObject
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
	@DoubleConfigProperty(defaultValue = -1, maxValue = 2, minValue = 25)
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
	
	public GUIComponentProperties(){}
	
	@Override
	protected GUIComponentProperties clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, GUIComponentProperties.class);
		}
		catch(Exception e){}
		
		return null;
	}
	
	@Override
	public void configParseComplete(PassthroughParams params){}
	
	@Override
	public String toString()
	{
		return "\n" + ConfigParser.generateConfigString(HoloGUIApi.pluginName, this);
	}
}