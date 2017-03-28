package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ParsableConfigProperty;
import com.antarescraft.kloudy.plugincore.configwrappers.ItemStackConfigWrapper;

public class GUIPageConfig implements ConfigObject
{
	@ConfigElementKey
	public String id;
	
	@ParsableConfigProperty
	@OptionalConfigProperty
	@ConfigProperty(key = "open-item")
	public ItemStackConfigWrapper openItem;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "item-name")
	public String itemName;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "open-on-login")
	public boolean openOnLogin;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "show-permission")
	public String showPermission;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "hide-permission")
	public String hidePermission;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "close-on-player-move")
	public boolean closeOnPlayerMove;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "close-on-player-item-switch")
	public boolean closeOnPlayerItemSwitch;

	@Override
	public void configParseComplete(PassthroughParams params){}
}