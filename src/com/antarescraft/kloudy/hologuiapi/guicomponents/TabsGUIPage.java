package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.exceptions.DefaultTabIndexOutOfBoundsException;
import com.antarescraft.kloudy.hologuiapi.exceptions.TabsNotDefinedException;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITabsPage;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementList;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.IntConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.StringConfigProperty;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

/**
 * Represents a GUI page with a 'tabs' element that allows you to select between gui pages.
 */
public class TabsGUIPage extends GUIPage
{
	@ConfigElement
	@ConfigProperty(key = "tabs")
	TabsConfig tabsConfig;
	
	public void addTab(TabComponent tab)
	{
		tabsConfig.tabs.add(tab);
	}
	
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
		if(tabsConfig.tabs.size() == 0)
		{
			throw new TabsNotDefinedException(id);
		}
		
		// Defined defaultTabIndex out of bounds.
		if(tabsConfig.defaultTabIndex >= tabsConfig.tabs.size())
		{
			throw new DefaultTabIndexOutOfBoundsException(tabsConfig.defaultTabIndex, id);
		}
		
		HoloGUIPlugin plugin = (HoloGUIPlugin)params.getParam("plugin");
		
		for(TabComponent tab : tabsConfig.tabs)
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
	
	public ArrayList<TabComponent> getTabs()
	{
		return new ArrayList<TabComponent>(tabsConfig.tabs);
	}
	
	public int getTabWidth()
	{
		return tabsConfig.tabWidth;
	}
	
	public void setTabWidth(int tabWidth)
	{
		tabsConfig.tabWidth = tabWidth;
	}
	
	public int getTabHeight()
	{
		return tabsConfig.tabHeight;
	}
	
	public void setTabHeight(int tabHeight)
	{
		tabsConfig.tabHeight = tabHeight;
	}
	
	public int getDefaultTabIndex()
	{
		return tabsConfig.defaultTabIndex;
	}
	
	public void setDefaultTabIndex(int defaultTabIndex)
	{
		tabsConfig.defaultTabIndex = defaultTabIndex;
	}
	
	public ComponentPosition getTabsPosition()
	{
		return tabsConfig.tabsPosition;
	}
	
	public void setTabsPosition(ComponentPosition tabsPosition)
	{
		tabsConfig.tabsPosition = tabsPosition;
	}
	
	public String getTabImageName()
	{
		return tabsConfig.tabImageName;
	}
	
	public double getTabsDistance()
	{
		return tabsConfig.tabDistance;
	}
	
	public MinecraftColor getTabLineColor()
	{
		try
		{
			return MinecraftColor.valueOf(tabsConfig.tabLineColor);
		}
		catch(Exception e)
		{
			MessageManager.error(Bukkit.getConsoleSender(), String.format("%s is not a valid color for the tab-line-color. Defaulting to 'BLACK'.", 
					tabsConfig.tabLineColor));
		}
		
		return MinecraftColor.BLACK;
	}
	
	private class TabsConfig
	{
		@ConfigElementList
		@ConfigProperty(key = "tabs-list")
		public ArrayList<TabComponent> tabs = new ArrayList<TabComponent>(); // List of configured tab elements
		
		@ConfigElement
		@ConfigProperty(key = "tabs-position")
		public ComponentPosition tabsPosition; // The position of the tabs themselves.
		
		@IntConfigProperty(defaultValue = 15, maxValue = 100, minValue = 1)
		@ConfigProperty(key = "tab-width")
		public int tabWidth; // The width (in pixels) of each tab.
		
		@IntConfigProperty(defaultValue = 7, maxValue = 100, minValue = 1)
		@ConfigProperty(key = "tab-height")
		public int tabHeight; // The height (in pixels) of each tab.
		
		@OptionalConfigProperty
		@IntConfigProperty(defaultValue = 0, maxValue = Integer.MAX_VALUE, minValue = 0)
		@ConfigProperty(key = "default-open-tab-index")
		public int defaultTabIndex; // The index of the tab that should be open by default.
		
		@ConfigProperty(key = "tab-image")
		public String tabImageName; // The image filename of the tab image.
		
		@OptionalConfigProperty
		@DoubleConfigProperty(defaultValue = 10, maxValue = 50, minValue = 2)
		@ConfigProperty(key = "tabs-distance")
		public double tabDistance; // The distance away from the Player the Tabs are.
		
		@OptionalConfigProperty
		@StringConfigProperty(defaultValue = "BLACK")
		@ConfigProperty(key = "tab-line-color")
		public String tabLineColor; // The color of the dividing line under the tabs.
	}
}