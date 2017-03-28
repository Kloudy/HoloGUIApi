package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.config.GUIPageConfig;
import com.antarescraft.kloudy.hologuiapi.config.TabsGUIPageConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITabsPage;

/**
 * Represents a GUI page with a 'tabs' element that allows you to select between gui pages.
 */
public class TabsGUIPage extends GUIPage
{
	TabsGUIPageConfig tabsConfig;
	
	public TabsGUIPage(HoloGUIPlugin plugin, GUIPageConfig config, TabsGUIPageConfig tabsConfig)
	{
		super(plugin, config);
		
		this.tabsConfig = tabsConfig;
	}
	
	@Override
	public PlayerGUITabsPage renderComponentsForPlayer(Player player, Location lookLocation)
	{
		HashMap<String, PlayerGUIComponent> components = new HashMap<String, PlayerGUIComponent>();
		
		for(GUIComponent component : guiComponents.values())
		{
			PlayerGUIComponent playerGUIComponent = component.initPlayerGUIComponent(player);
			components.put(component.getConfig().id, playerGUIComponent);
		}
		
		PlayerGUITabsPage playerGuiTabsPage = new PlayerGUITabsPage(player, components, lookLocation, this);
		
		playerGuiTabsPage.renderComponents();
		PlayerData.getPlayerData(player).setPlayerGUIPage(playerGuiTabsPage);
		
		triggerPageLoadHandler(playerGuiTabsPage);

		return playerGuiTabsPage;
	}
	
	public TabsGUIPageConfig getTabsConfig()
	{
		return tabsConfig;
	}
}