package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUICanvasComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

/**
 * Config object class that parses a tab entry for a TabGUIPage.
 */
public class TabComponent implements ConfigObject
{
	@ConfigElementKey
	private String id;
	
	@ConfigProperty(key = "tab-title")
	private String tabTitle;
	
	@ConfigProperty(key = "gui-page-id")
	private String guiPageId;
	
	public TabComponent(){}
	
	public TabComponent(String id, String tabTitle, String guiPageId)
	{
		this.id = id;
		this.tabTitle = tabTitle;
		this.guiPageId = guiPageId;
	}
	
	/**
	 * Renders this tab with the given canvas.
	 * @param cavas
	 * @param tabIndex The index of this tab.
	 * @param tabWidth The width (in pixels) of the tab
	 * @param tabHeight The height (in pixels of the tab
	 */
	public void renderTab(PlayerGUICanvasComponent canvas, int tabIndex, int tabWidth, int tabHeight)
	{
		
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getTabTitle()
	{
		return tabTitle;
	}
	
	public String getGUIPageId()
	{
		return guiPageId;
	}
	
	@Override
	public void configParseComplete(PassthroughParams params){}
}