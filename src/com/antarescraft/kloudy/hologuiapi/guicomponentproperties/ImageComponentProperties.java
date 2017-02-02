package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;

public class ImageComponentProperties extends GUIComponentProperties
{
	@ConfigProperty(key = "image-src")
	public String imageFileName;
	
	@ConfigProperty(key = "width")
	public int width;
	
	@ConfigProperty(key = "height")
	public int height;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "symmetrical")
	public boolean symmetrical;
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
	}
}