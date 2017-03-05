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
	protected String id;
	
	@ConfigElement
	@ConfigProperty(key = "position")
	protected ComponentPosition position;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "label")
	protected String label = null;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "label-distance")
	protected double labelDistance = -1;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "always-show-label")
	protected boolean alwaysShowLabel = false;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "hidden")
	protected boolean hidden = false;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 15, maxValue = 50, minValue = 0)
	@ConfigProperty(key="distance")
	protected double distance = -1;
	
	public GUIComponentProperties(){}
	
	protected abstract double getDefaultDistance();
	
	@Override
	public GUIComponentProperties clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, GUIComponentProperties.class);
		}
		catch(Exception e){}
		
		return null;
	}
	
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
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public ComponentPosition getPosition()
	{
		return position;
	}
	
	public void setPosition(ComponentPosition position)
	{
		this.position = position;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public double getLabelDistance()
	{
		return labelDistance;
	}
	
	public void setLabelDistance(double labelDistance)
	{
		this.labelDistance = labelDistance;
	}
	
	public boolean alwaysShowLabel()
	{
		return alwaysShowLabel;
	}
	
	public void setAlwaysShowLabel(boolean alwaysShowLabel)
	{
		this.alwaysShowLabel = alwaysShowLabel;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
	
	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
}