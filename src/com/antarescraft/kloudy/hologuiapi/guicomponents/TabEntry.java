package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

/**
 * Config object class that parses a tab entry for a TabGUIPage.
 */
public class TabEntry implements ConfigObject
{
	@ConfigElementKey
	private String id;
	
	@ConfigProperty(key = "tab-title")
	private String tabTitle;
	
	@ConfigProperty(key = "gui-page-id")
	private String guiPageId;
	
	@Override
	public void configParseComplete(PassthroughParams params){}
	
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
}