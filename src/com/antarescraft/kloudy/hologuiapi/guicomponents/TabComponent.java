package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;
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
	 * Renders the tab
	 * @param canvas
	 * @param tabIndex
	 * @param image
	 * @param open
	 */
	public void renderTab(PlayerGUICanvasComponent canvas, int tabIndex, String tabImageName, int tabWidth, int tabHeight, boolean open)
	{
		int imageX = (tabIndex * tabWidth) + tabIndex + 2;
		int imageY = 0;
		
		canvas.drawImage(imageX, imageY, tabImageName, tabWidth, tabHeight);
		
		//canvas.fill(0, tabHeight-1, canvas.getCanvasComponent().getProperties().getWidth(), tabHeight - 1, MinecraftColor.BLACK);
		
		if(open)
		{
			int x1 = imageX + 1;
			int y1 = tabHeight - 1;
			int x2 = x1 + tabWidth - 3;
			int y2 = y1;
						
			// Removes the bottom line on the tab, making it appear 'open'
			canvas.fill(x1, y1, x2, y2, MinecraftColor.TRANSPARENT);
		}
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