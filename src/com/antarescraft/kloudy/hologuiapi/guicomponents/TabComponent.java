package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.hologuiapi.TabComponentConfig;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUICanvasComponent;

/**
 * Config object class that parses a tab entry for a TabGUIPage.
 */
public class TabComponent
{
	private TabComponentConfig config;
	
	TabComponent(TabComponentConfig config)
	{
		this.config = config;
	}
	
	public TabComponentConfig getTabConfig()
	{
		return config;
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
}