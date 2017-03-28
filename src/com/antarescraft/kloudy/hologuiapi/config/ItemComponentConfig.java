package com.antarescraft.kloudy.hologuiapi.config;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ParsableConfigProperty;
import com.antarescraft.kloudy.plugincore.configwrappers.ItemStackConfigWrapper;
import com.antarescraft.kloudy.plugincore.configwrappers.VectorConfigWrapper;

public class ItemComponentConfig extends GUIComponentConfig
{
	private static final double DEFAULT_DISTANCE = 6;
	
	@ParsableConfigProperty
	@ConfigProperty(key = "item-id")
	public ItemStackConfigWrapper item;
	
	@ConfigElement
	@ConfigProperty(key = "rotation")
	public VectorConfigWrapper rotation;

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
	}
	
	@Override
	public double getDefaultDistance()
	{
		return DEFAULT_DISTANCE;
	}
}