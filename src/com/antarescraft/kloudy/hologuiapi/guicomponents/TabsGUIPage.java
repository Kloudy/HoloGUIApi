package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIView;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITabsPage;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementList;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

/**
 * Represents a GUI page with a 'tabs' element that allows you to select between gui pages.
 */
public class TabsGUIPage extends GUIPage
{
	@ConfigElementList
	@ConfigProperty(key = "tabs")
	private ArrayList<TabEntry> tabs; // List of configured tab elements
	
	@ConfigElement
	@ConfigProperty(key = "position")
	private ComponentPosition tabsPosition;
	
	private HoloGUIView[] views;

	private TabsGUIPage(){}
	
	@Override
	public PlayerGUITabsPage renderComponentsForPlayer(Player player, Location lookLocation)
	{
		//TODO: finish
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
}