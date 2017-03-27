package com.antarescraft.kloudy.hologuiapi.config;

import java.util.ArrayList;

import com.antarescraft.kloudy.hologuiapi.guicomponents.ComponentPosition;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TabComponent;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementList;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.IntConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ParsableConfigProperty;

public class TabsGUIPageConfig implements ConfigObject
{
	@ConfigElementList
	@ConfigProperty(key = "tabs-list")
	private ArrayList<TabComponent> tabsList = new ArrayList<TabComponent>(); // List of configured tab elements
	
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
	@ConfigProperty(key = "default-open-tab-index")
	private int defaultOpenTabIndex; // The index of the tab that should be open by default.
	
	@ConfigProperty(key = "tab-image")
	private String tabImageName; // The image filename of the tab image.
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 10, maxValue = 50, minValue = 2)
	@ConfigProperty(key = "tabs-distance")
	private double tabDistance; // The distance away from the Player the Tabs are.
	
	@OptionalConfigProperty
	@ParsableConfigProperty(defaultValue = "BLACK")
	@ConfigProperty(key = "tab-line-color")
	private MinecraftColor tabLineColor; // The color of the dividing line under the tabs.
	
	public ArrayList<TabComponent> tabs()
	{
		return new ArrayList<TabComponent>(tabsList);
	}
	
	public ComponentPosition tabsPosition()
	{
		return new ComponentPosition(tabsPosition.getX(), tabsPosition.getY());
	}
	
	public int tabWidth()
	{
		return tabWidth;
	}
	
	public int tabHeight()
	{
		return tabHeight;
	}
	
	public int defaultOpenTabIndex()
	{
		return defaultOpenTabIndex;
	}
	
	public String tabImageName()
	{
		return tabImageName;
	}
	
	public double tabDistance()
	{
		return tabDistance;
	}
	
	public MinecraftColor tabLineColor()
	{
		return tabLineColor;
	}

	@Override
	public void configParseComplete(PassthroughParams params) {}
}