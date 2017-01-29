package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ComponentPosition;
import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
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
	public String toString()
	{
		return ConfigParser.generateConfigString(HoloGUIApi.pluginName, this);
		
		/*StringBuilder strBuilder = new StringBuilder();
				
		strBuilder.append(String.format("\n{\nid: %s\n", id))
		.append(String.format("position: { x: %f, y: %f }, \n", position.getX(), position.getY()))
		.append(String.format("label: %s,\n", label))
		.append(String.format("label-distance: %f,\n", labelDistance))
		.append(String.format("always-show-label: %b,\n", alwaysShowLabel))
		.append(String.format("hidden: %b,\n", hidden));
		
		return strBuilder.toString();*/
	}
	
	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		HoloGUIApi.debugMessage(String.format("GuiProperties config parse for component: %s complete", id));
	}
}