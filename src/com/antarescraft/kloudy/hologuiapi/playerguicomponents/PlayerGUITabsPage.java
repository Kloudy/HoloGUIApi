package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.config.CanvasComponentConfig;
import com.antarescraft.kloudy.hologuiapi.config.ComponentPosition;
import com.antarescraft.kloudy.hologuiapi.config.TabConfig;
import com.antarescraft.kloudy.hologuiapi.config.TabsGUIPageConfig;
import com.antarescraft.kloudy.hologuiapi.guicomponents.CanvasComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponentFactory;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TabsGUIPage;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;

public class PlayerGUITabsPage extends PlayerGUIPage
{
	private TabsGUIPageConfig config;
	
	private PlayerGUICanvasComponent canvas;
	
	private int currentlyOpenTabIndex;
	
	public PlayerGUITabsPage(Player player, HashMap<String, PlayerGUIComponent> components, Location lookLocation, TabsGUIPage tabs)
	{
		super(player, components, lookLocation, tabs);
		
		config = tabs.getTabsConfig();
		
		currentlyOpenTabIndex = config.defaultOpenTabIndex;
	}
	
	public int getCurrentlyOpenTabIndex()
	{
		return currentlyOpenTabIndex;
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

		// Render the tabs.
		int i = 0;
		for(TabConfig tab : config.tabsList)
		{
			boolean open = config.defaultOpenTabIndex == i;
			
			PlayerGUITabComponent playerTab = new PlayerGUITabComponent(player, canvas, tab.tabTitle, i, config.tabImageName, config.tabWidth, config.tabHeight, open);
			playerTab.spawnEntities(lookLocation, false);
			components.put(playerTab.getGUIComponent().getConfig().id, playerTab);
			
			i++;
		}
		
		openTab(currentlyOpenTabIndex);
	}
	
	// Render the line under the tabs.
	private void renderDividerLine()
	{
		canvas.fill(0, config.tabHeight-1, canvas.getCanvasComponent().getConfig().width, config.tabHeight - 1, config.tabLineColor.unwrap());
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
	 * Opens the tab at the given index and binds the model to the gui page.
	 * @param tabIndex
	 * @param model
	 */
	public void openTab(int tabIndex, PlayerGUIPageModel model)
	{
		renderDividerLine();
		
		int x1 = (tabIndex * config.tabWidth) + tabIndex + 2 + 1;
		int y1 = config.tabHeight - 1;
		int x2 = x1 + config.tabWidth - 3;
		int y2 = y1;
					
		// Removes the bottom line on the tab, making it appear 'open'
		canvas.fill(x1, y1, x2, y2, MinecraftColor.TRANSPARENT);
		
		
	}
}