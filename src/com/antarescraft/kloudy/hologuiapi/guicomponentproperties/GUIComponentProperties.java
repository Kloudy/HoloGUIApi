package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.hologuiapi.guicomponents.ComponentPosition;
import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
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
	
	public GUIComponentProperties(String id, ComponentPosition position, String label, 
			double labelDistance, boolean alwaysShowLabel, boolean hidden)
	{
		this.id = id;
		this.position = position;
		this.label = label;
		this.labelDistance = labelDistance;
		this.alwaysShowLabel = alwaysShowLabel;
		this.hidden = hidden;
	}
	
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
}