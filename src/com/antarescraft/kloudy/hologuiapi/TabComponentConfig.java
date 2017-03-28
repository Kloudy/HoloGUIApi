package com.antarescraft.kloudy.hologuiapi;

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
	
	@Override
	public void configParseComplete(PassthroughParams params) 
	{
		// TODO Auto-generated method stub
		
	}
}