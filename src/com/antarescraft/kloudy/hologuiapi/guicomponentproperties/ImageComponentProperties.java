package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;

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
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		super.configParseComplete(passthroughParams);
	}
}