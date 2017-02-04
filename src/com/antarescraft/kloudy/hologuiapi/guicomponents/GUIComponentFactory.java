package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.io.StringReader;

import org.bukkit.configuration.file.YamlConfiguration;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.*;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;

/*
 * Factory class used to construct new GUIComponents
 * 
 * Each GUIComponent helper function converts the properties to a .yml config string 
 * so it can be parsed by the ConfigParser lib and perform the input validation.
 */
public class GUIComponentFactory
{	
	private static PassthroughParams params;
	
	private static YamlConfiguration toYaml(String propertyString)
	{
		return YamlConfiguration.loadConfiguration(new StringReader(propertyString));
	}
	
	private static PassthroughParams getPassthroughParams(HoloGUIPlugin plugin)
	{
		if(params == null)
		{
			params = new PassthroughParams();
			params.addParam("plugin", plugin);
		}
		
		return params;
	}
	
	public static LabelComponent createLabelComponent(HoloGUIPlugin plugin, LabelComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
				
		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), LabelComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ButtonComponent createButtonComponent(HoloGUIPlugin plugin, ButtonComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), ButtonComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static EntityButtonComponent createEntityButtonComponent(HoloGUIPlugin plugin, EntityButtonComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), EntityButtonComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static EntityComponent createEntityComponent(HoloGUIPlugin plugin, EntityComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), EntityComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ImageComponent createImageComponent(HoloGUIPlugin plugin, ImageComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), ImageComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ItemButtonComponent createItemButtonComponent(HoloGUIPlugin plugin, ItemButtonComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), ItemButtonComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ItemComponent createItemComponent(HoloGUIPlugin plugin, ItemComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), ItemComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static TextBoxComponent createTextBoxComponentComponent(HoloGUIPlugin plugin, TextBoxComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), TextBoxComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ToggleSwitchComponent createToggleSwitchComponent(HoloGUIPlugin plugin, ToggleSwitchComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), ToggleSwitchComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
	
	public static ValueScrollerComponent createValueScrollerComponent(HoloGUIPlugin plugin, ValueScrollerComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);

		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), ValueScrollerComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
}