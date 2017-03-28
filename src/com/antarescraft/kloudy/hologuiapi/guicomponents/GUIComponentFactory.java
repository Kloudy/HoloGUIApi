package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.io.StringReader;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.*;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;

/*
 * Factory class used to construct new GUIComponents
 * 
 * Each GUIComponent helper function converts the properties to a .yml config string 
 * so it can be parsed by the ConfigParser lib and perform the input validation.
 */
public class GUIComponentFactory
{	
	private static ConfigurationSection toYaml(String configString)
	{
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new StringReader(configString));
		String[] keys = new String[1];
		yaml.getKeys(false).toArray(keys);
		
		return yaml.getConfigurationSection(keys[0]);
	}
	
	/**
	 * Creates a LabelComponent.
	 * This function parses the input configuration section and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return LabelComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config values did not pass validation.
	 */
	public static LabelComponent createLabelComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		LabelComponentConfig config = ConfigParser.parse(section, LabelComponentConfig.class, plugin.getName());
		
		return new LabelComponent(plugin, config);
	}
	
	/**
	 * Creates a LabelComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return LabelComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static LabelComponent createLabelComponent(HoloGUIPlugin plugin, LabelComponentConfig config)
	{
		String configString = ConfigParser.generateConfigString(plugin.getName(), config);
		
		if(configString != null)
		{
			config = ConfigParser.parse(toYaml(configString), LabelComponentConfig.class, plugin.getName());
		
			return new LabelComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates a ButtonComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return ButtonComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ButtonComponent createButtonComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		ButtonComponentConfig config = ConfigParser.parse(section, ButtonComponentConfig.class, plugin.getName());
	
		return new ButtonComponent(plugin, config);
	}
	
	/**
	 * Creates a ButtonComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return LabelComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ButtonComponent createButtonComponent(HoloGUIPlugin plugin, ButtonComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);
		
		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), ButtonComponentConfig.class, plugin.getName());
		
			return new ButtonComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates an EntityButtonComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return EntityButtonComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static EntityButtonComponent createEntityButtonComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		EntityButtonComponentConfig config = ConfigParser.parse(section, EntityButtonComponentConfig.class, plugin.getName());
	
		return new EntityButtonComponent(plugin, config);
	}
	
	/**
	 * Creates an EntityButtonComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return EntityButtonComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static EntityButtonComponent createEntityButtonComponent(HoloGUIPlugin plugin, EntityButtonComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), EntityButtonComponentConfig.class, plugin.getName());
		
			return new EntityButtonComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates an EntityComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return EntityComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static EntityComponent createEntityComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		EntityComponentConfig config = ConfigParser.parse(section, EntityComponentConfig.class, plugin.getName());
		
		return new EntityComponent(plugin, config);
	}
	
	/**
	 * Creates an EntityComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return EntityComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static EntityComponent createEntityComponent(HoloGUIPlugin plugin, EntityComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), EntityComponentConfig.class, plugin.getName());
		
			return new EntityComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates an ImageComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return ImageComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ImageComponent createImageComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		ImageComponentConfig config = ConfigParser.parse(section, ImageComponentConfig.class, plugin.getName());
	
		return new ImageComponent(plugin, config);
	}
	
	/**
	 * Creates an ImageComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return EntityComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ImageComponent createImageComponent(HoloGUIPlugin plugin, ImageComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), ImageComponentConfig.class, plugin.getName());
		
			return new ImageComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates an ItemButtonComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return ItemButtonComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ItemButtonComponent createItemButtonComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		ItemButtonComponentConfig config = ConfigParser.parse(section, ItemButtonComponentConfig.class, plugin.getName());

		return new ItemButtonComponent(plugin, config);
	}
	
	/**
	 * Creates an ItemButtonComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return ItemButtonComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ItemButtonComponent createItemButtonComponent(HoloGUIPlugin plugin, ItemButtonComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), ItemButtonComponentConfig.class, plugin.getName());
		}
		
		return null;
	}
	
	/**
	 * Creates an ItemComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return ItemComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ItemComponent createItemComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		ItemComponentConfig config = ConfigParser.parse(section, ItemComponentConfig.class, plugin.getName());
	
		return new ItemComponent(plugin, config);
	}
	
	/**
	 * Creates an ItemComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return ItemComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ItemComponent createItemComponent(HoloGUIPlugin plugin, ItemComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), ItemComponentConfig.class, plugin.getName());
		
			return new ItemComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates an TextBoxComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return TextBoxComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static TextBoxComponent createTextBoxComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		TextBoxComponentConfig config = ConfigParser.parse(section, TextBoxComponentConfig.class, plugin.getName());

		return new TextBoxComponent(plugin, config);
	}
	
	/**
	 * Creates an TextBoxComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return TextBoxComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static TextBoxComponent createTextBoxComponentComponent(HoloGUIPlugin plugin, TextBoxComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), TextBoxComponentConfig.class, plugin.getName());
		
			return new TextBoxComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates an ToggleSwitchComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return ToggleSwitchComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ToggleSwitchComponent createToggleSwitchComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		ToggleSwitchComponentConfig config = ConfigParser.parse(section, ToggleSwitchComponentConfig.class, plugin.getName());

		return new ToggleSwitchComponent(plugin, config);
	}
	
	/**
	 * Creates an ToggleSwitchComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return ToggleSwitchComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ToggleSwitchComponent createToggleSwitchComponent(HoloGUIPlugin plugin, ToggleSwitchComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), ToggleSwitchComponentConfig.class, plugin.getName());
		}
		
		return null;
	}
	
	/**
	 * Creates an ValueScrollerComponent.
	 * This function parses the input ConfigurationSection and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param section The ConfigurationSection that defines the component.
	 * @return ValueScrollerComponent hydrated with the given config values after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ValueScrollerComponent createValueScrollerComponent(HoloGUIPlugin plugin, ConfigurationSection section)
	{
		ValueScrollerComponentConfig config = ConfigParser.parse(section, ValueScrollerComponentConfig.class, plugin.getName());

		return new ValueScrollerComponent(plugin, config);
	}
	
	/**
	 * Creates an ValueScrollComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return ValueScrollComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static ValueScrollerComponent createValueScrollerComponent(HoloGUIPlugin plugin, ValueScrollerComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);

		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), ValueScrollerComponentConfig.class, plugin.getName());
		
			return new ValueScrollerComponent(plugin, config);
		}
		
		return null;
	}
	
	/**
	 * Creates an CanvasComponent.
	 * This function parses the input config object and performs validation checks.
	 * 
	 * @param plugin The HoloGUIPlugin associated with the component.
	 * @param config for the component.
	 * @return CanvasComponent hydrated with the given config object after passing validation.
	 * 		    Returns null if the config object did not pass validation.
	 */
	public static CanvasComponent createCanvasComponent(HoloGUIPlugin plugin, CanvasComponentConfig config)
	{
		String propertyString = ConfigParser.generateConfigString(plugin.getName(), config);
		
		if(propertyString != null)
		{
			config = ConfigParser.parse(toYaml(propertyString), CanvasComponentConfig.class, plugin.getName());
			
			return new CanvasComponent(plugin, config);
		}
		
		return null;
	}
}