package com.antarescraft.kloudy.hologuiapi.util;
import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponents.*;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

public class ConfigManager
{
	public static GUIPage loadGUIPage(HoloGUIPlugin plugin, File yamlFile)
	{
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(yamlFile);
		
		String[] keys = new String[1];
		yaml.getKeys(false).toArray(keys);
		
		String guiPageId = keys[0];
		if(guiPageId == null)
		{
			MessageManager.error(Bukkit.getConsoleSender(), "No GUI Page Id has been defined");
			return null;
		}
		
		HashMap<String, Object> passthroughParams = new HashMap<String, Object>();
		passthroughParams.put("plugin", plugin);
		
		GUIPage guiPage = ConfigParser.parse(plugin.getName(), yaml.getConfigurationSection(guiPageId), GUIPage.class, passthroughParams);
				
		ConfigurationSection guiPageSection = yaml.getConfigurationSection(guiPage.getId());
		ConfigurationSection componentsSection = guiPageSection.getConfigurationSection("components");
				
		for(String key : componentsSection.getKeys(false))
		{
			ConfigurationSection section = componentsSection.getConfigurationSection(key);
			
			String type = section.getString("type");
			boolean clickable = section.getBoolean("clickable", false);
			
			if(type == null)
			{
				MessageManager.error(Bukkit.getConsoleSender(), 
						String.format("Component '%s' in GUI Page '%s' does not contain a 'type'. Config parsing skipped for this component.", key, guiPage.getId()));
				
				continue;
			}
			
			GUIComponent component = null;
			
			if(type.equalsIgnoreCase("label"))
			{				
				component = (LabelComponent)ConfigParser.parse(plugin.getName(), section, LabelComponent.class, passthroughParams);
			}
			else if(type.equalsIgnoreCase("button"))
			{
				component = (ButtonComponent)ConfigParser.parse(plugin.getName(), section, ButtonComponent.class, passthroughParams);
			}
			else if(type.equalsIgnoreCase("image"))
			{
				component = (ImageComponent)ConfigParser.parse(plugin.getName(), section, ImageComponent.class, passthroughParams);
			}
			else if(type.equalsIgnoreCase("entity"))
			{
				if(clickable)component = (EntityButtonComponent)ConfigParser.parse(plugin.getName(), section, EntityButtonComponent.class, passthroughParams);
				else component = (EntityComponent)ConfigParser.parse(plugin.getName(), section, EntityComponent.class, passthroughParams);
			}
			else if(type.equalsIgnoreCase("item"))
			{
				if(clickable) component = (ItemButtonComponent)ConfigParser.parse(plugin.getName(), section, ItemButtonComponent.class, passthroughParams);
				else component = (ItemComponent)ConfigParser.parse(plugin.getName(), section, ItemComponent.class, passthroughParams);
			}
			else if(type.equalsIgnoreCase("toggle-switch"))
			{
				component = (ToggleSwitchComponent)ConfigParser.parse(plugin.getName(), section, ToggleSwitchComponent.class, passthroughParams);
			}
			else if(type.equalsIgnoreCase("text-box"))
			{
				component = (TextBoxComponent)ConfigParser.parse(plugin.getName(), section, TextBoxComponent.class, passthroughParams);
			}
			else if(type.equalsIgnoreCase("value-scroller"))
			{
				component = (ValueScrollerComponent)ConfigParser.parse(plugin.getName(), section, ValueScrollerComponent.class, passthroughParams);
			}
			
			if(guiPage != null)
			{
				if(component != null)
				{
					guiPage.addComponent(component);
					
					HoloGUIApi.debugMessage(component.getProperties());
				}
				else
				{
					MessageManager.error(Bukkit.getConsoleSender(), 
							String.format("Component '%s' in GUI Page '%s' does not have a valid 'type' value. Config parsing skipped for this component.", key, guiPage.getId()));
				}
			}
			else
			{
				MessageManager.error(Bukkit.getConsoleSender(), 
						String.format("An error occurred while attempting to parse a GUI Page"));
			}			
		}
		
		return guiPage;
	}
}