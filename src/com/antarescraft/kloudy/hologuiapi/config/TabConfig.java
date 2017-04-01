package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class TabConfig implements ConfigObject
{
	@ConfigElementKey
	public String id;
	
	@ConfigProperty(key = "tab-title")
	public String tabTitle;
	
	@ConfigProperty(key = "gui-page-id")
	public String guiPageId;
	
	@Override
	public void configParseComplete(PassthroughParams params){}
}