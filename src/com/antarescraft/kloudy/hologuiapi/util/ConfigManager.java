package com.antarescraft.kloudy.hologuiapi.util;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponents.*;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
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
		
		PassthroughParams params = new PassthroughParams();
		params.addParam("plugin", plugin);
		
		GUIPage guiPage = ConfigParser.parse(yaml.getConfigurationSection(guiPageId), GUIPage.class, plugin.getName(), params);
				
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
				component = (LabelComponent)ConfigParser.parse(section, LabelComponent.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("button"))
			{
				component = (ButtonComponent)ConfigParser.parse(section, ButtonComponent.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("image"))
			{
				component = (ImageComponent)ConfigParser.parse(section, ImageComponent.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("entity"))
			{
				if(clickable)component = (EntityButtonComponent)ConfigParser.parse(section, EntityButtonComponent.class, plugin.getName(), params);
				else component = (EntityComponent)ConfigParser.parse(section, EntityComponent.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("item"))
			{
				if(clickable) component = (ItemButtonComponent)ConfigParser.parse(section, ItemButtonComponent.class, plugin.getName(), params);
				else component = (ItemComponent)ConfigParser.parse(section, ItemComponent.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("toggle-switch"))
			{
				component = (ToggleSwitchComponent)ConfigParser.parse(section, ToggleSwitchComponent.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("text-box"))
			{
				component = (TextBoxComponent)ConfigParser.parse(section, TextBoxComponent.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("value-scroller"))
			{
				component = (ValueScrollerComponent)ConfigParser.parse(section, ValueScrollerComponent.class, plugin.getName(), params);
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