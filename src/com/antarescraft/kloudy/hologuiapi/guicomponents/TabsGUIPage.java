package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.config.TabsGUIPageConfig;
import com.antarescraft.kloudy.hologuiapi.exceptions.DefaultTabIndexOutOfBoundsException;
import com.antarescraft.kloudy.hologuiapi.exceptions.TabsNotDefinedException;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITabsPage;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

/**
 * Represents a GUI page with a 'tabs' element that allows you to select between gui pages.
 */
public class TabsGUIPage extends GUIPage
{
	@ConfigElement
	@ConfigProperty(key = "tabs")
	TabsGUIPageConfig config;
	
	@Override
	public PlayerGUITabsPage renderComponentsForPlayer(Player player, Location lookLocation)
	{
		HashMap<String, PlayerGUIComponent> components = new HashMap<String, PlayerGUIComponent>();
		
		for(GUIComponent component : guiComponents.values())
		{
			PlayerGUIComponent playerGUIComponent = component.initPlayerGUIComponent(player);
			components.put(component.getProperties().getId(), playerGUIComponent);
		}
		
		PlayerGUITabsPage playerGuiTabsPage = new PlayerGUITabsPage(player, components, lookLocation, this);
		
		playerGuiTabsPage.renderComponents();
		PlayerData.getPlayerData(player).setPlayerGUIPage(playerGuiTabsPage);
		
		triggerPageLoadHandler(playerGuiTabsPage);

		return playerGuiTabsPage;
	}
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		// No tabs were defined, treat this as an error scenario.
		if(config.tabsList.size() == 0)
		{
			throw new TabsNotDefinedException(config.id);
		}
		
		// Defined defaultTabIndex out of bounds.
		if(config.defaultOpenTabIndex >= config.tabsList.size())
		{
			throw new DefaultTabIndexOutOfBoundsException(config.defaultOpenTabIndex, config.id);
		}
		
		HoloGUIPlugin plugin = (HoloGUIPlugin)params.getParam("plugin");
		
		for(TabComponent tab : config.tabsList)
		{
			// Attempt to find the GUIPage defined in the TabEntry 'gui-page-id' property.
			GUIPage guiPage = plugin.getGUIPage(tab.getGUIPageId());
			if(guiPage == null)
			{
				MessageManager.error(Bukkit.getConsoleSender(), String.format("No GUIPage exists with id '%s' defined in tab: %s. This tab will not be rendered.", 
						tab.getGUIPageId(), tab.getId()));
				
				break;
			}
		}
	}
	
	public TabsGUIPageConfig getConfig()
	{
		return config;
	}
}