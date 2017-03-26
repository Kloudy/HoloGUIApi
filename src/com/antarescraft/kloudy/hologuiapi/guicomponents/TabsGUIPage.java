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
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITabsPage;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementList;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.IntConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

/**
 * Represents a GUI page with a 'tabs' element that allows you to select between gui pages.
 */
public class TabsGUIPage extends GUIPage
{
	@ConfigElementList
	@ConfigProperty(key = "tabs")
	private ArrayList<TabComponent> tabs = new ArrayList<TabComponent>(); // List of configured tab elements
	
	@ConfigElement
	@ConfigProperty(key = "tabs-position")
	private ComponentPosition tabsPosition; // The position of the tabs themselves.
	
	@IntConfigProperty(defaultValue = 15, maxValue = 100, minValue = 1)
	@ConfigProperty(key = "tab-width")
	private int tabWidth; // The width (in pixels) of each tab.
	
	@IntConfigProperty(defaultValue = 7, maxValue = 100, minValue = 1)
	@ConfigProperty(key = "tab-height")
	private int tabHeight; // The height (in pixels) of each tab.
	
	@OptionalConfigProperty
	@IntConfigProperty(defaultValue = 0, maxValue = Integer.MAX_VALUE, minValue = 0)
	@ConfigProperty(key = "default-tab-index")
	private int defaultTabIndex; // The index of the tab that should be open by default.
	
	public void addTab(TabComponent tab)
	{
		tabs.add(tab);
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
		if(tabs.size() == 0)
		{
			throw new TabsNotDefinedException(id);
		}
		
		// Defined defaultTabIndex out of bounds.
		if(defaultTabIndex >= tabs.size())
		{
			throw new DefaultTabIndexOutOfBoundsException(defaultTabIndex, id);
		}
		
		HoloGUIPlugin plugin = (HoloGUIPlugin)params.getParam("plugin");
		
		for(TabComponent tab : tabs)
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
		return new ArrayList<TabComponent>(tabs);
	}
	
	public int getTabWidth()
	{
		return tabWidth;
	}
	
	public void setTabWidth(int tabWidth)
	{
		this.tabWidth = tabWidth;
	}
	
	public int getTabHeight()
	{
		return tabHeight;
	}
	
	public void setTabHeight(int tabHeight)
	{
		this.tabHeight = tabHeight;
	}
	
	public int getDefaultTabIndex()
	{
		return defaultTabIndex;
	}
	
	public void setDefaultTabIndex(int defaultTabIndex)
	{
		this.defaultTabIndex = defaultTabIndex;
	}
	
	public ComponentPosition getTabsPosition()
	{
		return tabsPosition;
	}
	
	public void setTabsPosition(ComponentPosition tabsPosition)
	{
		this.tabsPosition = tabsPosition;
	}
}