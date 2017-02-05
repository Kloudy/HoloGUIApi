package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.configobjects.ConfigVector;
import com.antarescraft.kloudy.plugincore.utils.Utils;

public class ItemButtonComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 6;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 1.3;
	
	@ConfigProperty(key = "item-id")
	private String itemString;
	
	@ConfigElement
	@ConfigProperty(key = "rotation")
	private ConfigVector rotation;

	@Override
	public void configParseComplete(PassthroughParams params) 
	{
		super.configParseComplete(params);
		
		if(labelDistance == -1)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
		
		if(labelZoomDistance == -1)
		{
			labelZoomDistance = DEFAULT_LABEL_ZOOM_DISTANCE;
		}
	}
	
	public ItemStack getItem()	
	{
		try
		{
			return Utils.parseItemString(itemString);
		}
		catch(Exception e){}
		
		return null;
	}
	
	public void setItem(ItemStack item)
	{
		this.itemString = Utils.generateItemString(item);
	}
	
	public Vector getRotation()
	{
		return rotation.toVector();
	}
	
	public void setRotation(Vector rotation)
	{
		this.rotation = new ConfigVector(rotation);
	}
}