package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.CanvasComponentProperties;
import com.antarescraft.kloudy.hologuiapi.guicomponents.CanvasComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponentFactory;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TabEntry;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TabsGUIPage;

public class PlayerGUITabsPage extends PlayerGUIPage
{
	private TabsGUIPage tabs;
	
	private PlayerGUICanvasComponent canvas;
	
	public PlayerGUITabsPage(Player player, HashMap<String, PlayerGUIComponent> components, Location lookLocation, TabsGUIPage tabs)
	{
		super(player, components, lookLocation, tabs);
		
		this.tabs = tabs;
		
		// Initialize the canvas.
		
		int width = tabs.getTabWidth() * tabs.getTabs().size();
		int height = tabs.getTabHeight();
		
		CanvasComponentProperties properties = new CanvasComponentProperties();
		properties.setId("###tab-canvas###");
		properties.setPosition(tabs.getTabsPosition());
		properties.setWidth(width);
		properties.setHeight(height);
		
		CanvasComponent canvasComponent = GUIComponentFactory.createCanvasComponent(tabs.getHoloGUIPlugin(), properties);
		canvas = (PlayerGUICanvasComponent) renderComponent(canvasComponent);
		
		// Render the tabs.
		
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
		TabEntry tab = tabs.getTabs().get(tabIndex);
		
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
		TabEntry tab = null;
		
		for(TabEntry t : tabs.getTabs())
		{
			if(t.getId().equals(tabId))
			{
				tab = t;
				break;
			}
		}
		
		openTab(tab, model);
	}
	
	private void openTab(TabEntry tab, PlayerGUIPageModel model)
	{
		//TODO: implement.
	}
}