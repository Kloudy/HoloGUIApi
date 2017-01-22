package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.io.StringReader;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.*;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;

/*
 * Factory class used to construct new GUIComponents
 * 
 * Each GUIComponent helper function converts the properties to a .yml config string 
 * so it can be parsed by the ConfigParser lib and perform the input validation.
 */
public class GUIComponentFactory
{
	private static HashMap<String, Object> passthroughParams = new HashMap<String, Object>();
	
	private static YamlConfiguration toYaml(String propertyString)
	{
		return YamlConfiguration.loadConfiguration(new StringReader(propertyString));
	}
	
	private static HashMap<String, Object> getPassthroughParams(HoloGUIPlugin plugin)
	{
		if(!passthroughParams.containsKey("plugin"))
		{
			passthroughParams.put("plugin", plugin);
		}
		
		return passthroughParams;
	}
	
	public static LabelComponent createLabelComponent(HoloGUIPlugin plugin, LabelComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("label: " + propertyString);
				
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), LabelComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ButtonComponent createButtonComponent(HoloGUIPlugin plugin, ButtonComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("button: " + propertyString);
		
		HashMap<String, Object> passthroughParams = new HashMap<String, Object>();
		passthroughParams.put("plugin", plugin);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), ButtonComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static EntityButtonComponent createEntityButtonComponent(HoloGUIPlugin plugin, EntityButtonComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("EntityButtonComponent: " + propertyString);
		
		HashMap<String, Object> passthroughParams = new HashMap<String, Object>();
		passthroughParams.put("plugin", plugin);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), EntityButtonComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static EntityComponent createEntityComponent(HoloGUIPlugin plugin, EntityComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("EntityComponent: " + propertyString);
		
		HashMap<String, Object> passthroughParams = new HashMap<String, Object>();
		passthroughParams.put("plugin", plugin);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), EntityComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ImageComponent createImageComponent(HoloGUIPlugin plugin, ImageComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("ImageComponent: " + propertyString);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), ImageComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ItemButtonComponent createItemButtonComponent(HoloGUIPlugin plugin, ItemButtonComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("ItemButtonComponent: " + propertyString);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), ItemButtonComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ItemComponent createItemComponent(HoloGUIPlugin plugin, ItemComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("ItemComponent: " + propertyString);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), ItemComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static TextBoxComponent createTextBoxComponentComponent(HoloGUIPlugin plugin, TextBoxComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("TextBoxComponent: " + propertyString);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), TextBoxComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ToggleSwitchComponent createToggleSwitchComponent(HoloGUIPlugin plugin, ToggleSwitchComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("ToggleSwitchComponent: " + propertyString);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), ToggleSwitchComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ValueScrollerComponent createValueScrollerComponent(HoloGUIPlugin plugin, ValueScrollerComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		System.out.println("ValueScrollerComponent: " + propertyString);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(plugin.getName(), toYaml(propertyString), ValueScrollerComponent.class, getPassthroughParams(plugin));
		}
		
		return null;
	}
}