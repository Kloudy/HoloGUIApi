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
	public void configParseComplete(HashMap<String, Object> passthroughParams){}

	@Override
	public String toString()
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(super.toString())
		.append(String.format("image-src: %s,\n", imageFileName))
		.append(String.format("width: %d,\n", width))
		.append(String.format("height: %d,\n", height))
		.append(String.format("symmetrical: %b }", symmetrical));
		
		return strBuilder.toString();
	}
}