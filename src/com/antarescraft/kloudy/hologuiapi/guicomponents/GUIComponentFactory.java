package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.io.StringReader;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.*;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

/*
 * Factory class used to construct new GUIComponents
 * 
 * Each GUIComponent helper function converts the properties to a .yml config string 
 * so it can be parsed by the ConfigParser lib and perform the input validation.
 */
public class GUIComponentFactory
{	
	private static PassthroughParams params;
	
	private static ConfigurationSection toYaml(String configString)
	{
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new StringReader(configString));
		String[] keys = new String[1];
		yaml.getKeys(false).toArray(keys);
		
		return yaml.getConfigurationSection(keys[0]);
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
	
	/**
	 * Clones the input component and returns a deep copy of the component with the new id
	 * @param component The component from which a deep copy will be returned
	 * @param id The id of the newly created GUIComponent
	 * @return deep copy of the input gui components
	 */
	@SuppressWarnings("unchecked")
	public static <T extends GUIComponent> T createComponentFromExistingComponent(T component, String id)
	{	
		try 
		{
			return (T) ObjectMapper.mapObject(component, component.getClass());
		} 
		catch (Exception e){}
		
		return null;
	}
	
	public static LabelComponent createLabelComponent(HoloGUIPlugin plugin, LabelComponentProperties properties)
	{
		String configString = ConfigParser.generateConfigString(plugin.getName(), properties);
		
		if(configString != null)
		{
			return ConfigParser.parse(toYaml(configString), LabelComponent.class, plugin.getName(), getPassthroughParams(plugin));
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
	
	public static CanvasComponent createCanvasComponent(HoloGUIPlugin plugin, CanvasComponentProperties properties)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), properties);
		
		if(propertyString != null)
		{
			return ConfigParser.parse(toYaml(propertyString), CanvasComponent.class, plugin.getName(), getPassthroughParams(plugin));
		}
		
		return null;
	}
}