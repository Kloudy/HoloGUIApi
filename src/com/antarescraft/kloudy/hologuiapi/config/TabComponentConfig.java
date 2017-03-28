package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUICanvasComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class TabComponentConfig implements ConfigObject
{
	@ConfigElementKey
	public String id;
	
	@ConfigProperty(key = "tab-title")
	public String tabTitle;
	
	@ConfigProperty(key = "gui-page-id")
	public String guiPageId;
	
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
	
	@Override
	public void configParseComplete(PassthroughParams params){}
}