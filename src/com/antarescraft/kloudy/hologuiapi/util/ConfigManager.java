package com.antarescraft.kloudy.hologuiapi.util;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.GUIPageConfig;
import com.antarescraft.kloudy.hologuiapi.config.TabsGUIPageConfig;
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
		
		ConfigurationSection guiPageSection = yaml.getConfigurationSection(guiPageId);
		
		GUIPage guiPage = null;
		
		GUIPageConfig guiPageConfig = ConfigParser.parse(guiPageSection, GUIPageConfig.class, plugin.getName(), params);
		
		// GUIPage is a TabsGUIPage
		if(guiPageSection.contains("tabs"))
		{
			TabsGUIPageConfig tabsConfig = ConfigParser.parse(guiPageSection.getConfigurationSection("tabs"), TabsGUIPageConfig.class, plugin.getName(), params);
		
			guiPage = new TabsGUIPage(plugin, guiPageConfig, tabsConfig);
		}
		// Regular GUIPage
		else
		{
			guiPage = new GUIPage(plugin, guiPageConfig);
		}
		
		System.out.println("GUI PAGE: " + guiPage);
				
		ConfigurationSection componentsSection = guiPageSection.getConfigurationSection("components");
				
		for(String key : componentsSection.getKeys(false))
		{
			ConfigurationSection componentSection = componentsSection.getConfigurationSection(key);
			
			String type = componentSection.getString("type");
			boolean clickable = componentSection.getBoolean("clickable", false);
			
			if(type == null)
			{
				MessageManager.error(Bukkit.getConsoleSender(), 
						String.format("Component '%s' in GUI Page '%s' does not contain a 'type'. Config parsing skipped for this component.", key, guiPage.getConfig().id));
				
				continue;
			}
			
			GUIComponent component = null;
			
			if(type.equalsIgnoreCase("label"))
			{		
				component = GUIComponentFactory.createLabelComponent(plugin, componentSection);
				
				//config = (LabelComponentConfig)ConfigParser.parse(section, LabelComponentConfig.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("button"))
			{
				component = GUIComponentFactory.createButtonComponent(plugin, componentSection);
				
				//config = (ButtonComponentConfig)ConfigParser.parse(section, ButtonComponentConfig.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("image"))
			{
				component = GUIComponentFactory.createImageComponent(plugin, componentSection);
				
				//config = (ImageComponentConfig)ConfigParser.parse(componentSection, ImageComponentConfig.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("entity"))
			{
				if(clickable) component = GUIComponentFactory.createEntityButtonComponent(plugin, componentSection);
				else component = GUIComponentFactory.createEntityComponent(plugin, componentSection);
				
				//if(clickable)config = (EntityButtonComponentConfig)ConfigParser.parse(componentSection, EntityButtonComponentConfig.class, plugin.getName(), params);
				//else config = (EntityComponentConfig)ConfigParser.parse(componentSection, EntityComponentConfig.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("item"))
			{
				if(clickable) component = GUIComponentFactory.createItemButtonComponent(plugin, componentSection);
				else component = GUIComponentFactory.createItemComponent(plugin, componentSection);
				
				//if(clickable) config = (ItemButtonComponentConfig)ConfigParser.parse(componentSection, ItemButtonComponentConfig.class, plugin.getName(), params);
				//else config = (ItemComponentConfig)ConfigParser.parse(componentSection, ItemComponentConfig.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("toggle-switch"))
			{
				component = GUIComponentFactory.createToggleSwitchComponent(plugin, componentSection);
				
				//config = (ToggleSwitchComponentConfig)ConfigParser.parse(componentSection, ToggleSwitchComponentConfig.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("text-box"))
			{
				component = GUIComponentFactory.createTextBoxComponent(plugin, componentSection);
				
				//config = (TextBoxComponentConfig)ConfigParser.parse(componentSection, TextBoxComponentConfig.class, plugin.getName(), params);
			}
			else if(type.equalsIgnoreCase("value-scroller"))
			{
				component = GUIComponentFactory.createValueScrollerComponent(plugin, componentSection);
				
				//config = (ValueScrollerComponentConfig)ConfigParser.parse(componentSection, ValueScrollerComponentConfig.class, plugin.getName(), params);
			}
			
			if(guiPage != null)
			{
				if(component != null)
				{
					guiPage.addComponent(component);
				}
				else
				{
					MessageManager.error(Bukkit.getConsoleSender(), 
							String.format("Component '%s' in GUI Page '%s' does not have a valid 'type' value. Config parsing skipped for this component.", key, guiPage.getConfig().id));
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