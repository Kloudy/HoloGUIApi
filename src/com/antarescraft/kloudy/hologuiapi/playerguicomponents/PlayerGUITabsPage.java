package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.config.CanvasComponentConfig;
import com.antarescraft.kloudy.hologuiapi.config.ComponentPosition;
import com.antarescraft.kloudy.hologuiapi.config.TabComponentConfig;
import com.antarescraft.kloudy.hologuiapi.config.TabsGUIPageConfig;
import com.antarescraft.kloudy.hologuiapi.guicomponents.CanvasComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponentFactory;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TabsGUIPage;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;

public class PlayerGUITabsPage extends PlayerGUIPage
{
	private TabsGUIPageConfig config;
	
	private PlayerGUICanvasComponent canvas;
	
	public PlayerGUITabsPage(Player player, HashMap<String, PlayerGUIComponent> components, Location lookLocation, TabsGUIPage tabs)
	{
		super(player, components, lookLocation, tabs);
		
		config = tabs.getTabsConfig();
	}
	
	@Override
	public void renderComponents()
	{
		super.renderComponents();
		
		// Initialize the canvas.
		
		int width = config.tabWidth * config.tabsList.size() + (config.tabsList.size()-1) + 10;
		int height = config.tabHeight;
		
		CanvasComponentConfig properties = new CanvasComponentConfig();
		properties.id = "###tab-canvas###";
		properties.position = new ComponentPosition(config.tabsPosition);
		properties.width = width;
		properties.height = height;
		properties.distance = config.tabDistance;
		
		CanvasComponent canvasComponent = GUIComponentFactory.createCanvasComponent(guiPage.getHoloGUIPlugin(), properties);
		canvas = (PlayerGUICanvasComponent) renderComponent(canvasComponent);
		
		renderDividerLine();
		
		// Render the tabs.
		int i = 0;
		for(TabComponentConfig tab : config.tabsList)
		{
			boolean open = config.defaultOpenTabIndex == i;
			
			tab.renderTab(canvas, i, config.tabImageName, config.tabWidth, config.tabHeight, open);
			
			i++;
		}
	}
	
	// Render the line under the tabs.
	private void renderDividerLine()
	{
		canvas.fill(0, config.tabHeight-1, canvas.getCanvasComponent().getConfig().width, config.tabHeight - 1, MinecraftColor.BLACK);
	}
	
	/**
	 * Destroys this PlayerGUITabsPage.
	 */
	@Override
	public void destroy()
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		guiPage.triggerPageCloseHandler(player);
		
		for(PlayerGUIComponent playerGUIComponent : components.values())
		{
			playerGUIComponent.destroyArmorStands();
		}
		
		PlayerGUIPage focusedPage = playerData.getPlayerFocusedPage();//remove the page as focused if it was focused
		if(focusedPage != null && focusedPage.equals(this))
		{
			playerData.setPlayerFocusedPage(null);
		}
		
		playerData.setPlayerTextBoxEditor(null);
		playerData.setPlayerValueScrollerEditor(null);
		
		canvas.clear();
	}
	
	/**
	 * Opens the tab at the given index.
	 * @param tabIndex
	 */
	public void openTab(int tabIndex)
	{
		openTab(tabIndex, null);
	}
	
	/**
	 * Opens the tab at the given index and binds the given PlayerGUIPageModel to the GUIPage associated with the tab.
	 * @param tabIndex
	 * @param model
	 */
	public void openTab(int tabIndex, PlayerGUIPageModel model)
	{
		TabComponentConfig tab = config.tabsList.get(tabIndex);
		
		openTab(tab, model);
	}
	
	/**
	 * Opens the tab with the given tabId
	 * @param tabId
	 */
	public void openTab(String tabId)
	{
		openTab(tabId, null);
	}
	
	/**
	 * Opens the tab with the given tabId and binds the given PlayerGUIPageModel to the GUIPage associated with the tab.
	 * @param tabId
	 * @param model
	 */
	public void openTab(String tabId, PlayerGUIPageModel model)
	{
		TabComponentConfig tab = null;
		
		for(TabComponentConfig tabConfig : config.tabsList)
		{
			if(tabConfig.id.equals(tabId))
			{
				tab = tabConfig;
				break;
			}
		}
		
		openTab(tab, model);
	}
	
	private void openTab(TabComponentConfig tab, PlayerGUIPageModel model)
	{
		//TODO: implement.
	}
}