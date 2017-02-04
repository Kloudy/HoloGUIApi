package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ItemComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIItemComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.configobjects.ConfigVector;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;
import com.antarescraft.kloudy.plugincore.utils.Utils;

/*
 * Represents a non-clickable item in a GUI
 */
public class ItemComponent extends GUIComponent implements ItemTypeComponent, ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
	ItemComponentProperties properties;
	
	private ItemStack item = null;
	
	private ItemComponent(){}
	
	@Override
	public ItemComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, ItemComponent.class, plugin);
		}
		catch(Exception e){}
		
		return null;
	}

	@Override
	public PlayerGUIItemComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUIItemComponent(player, this, item);
	}

	@Override
	public void updateIncrement(){}

	@Override
	public double getDisplayDistance()
	{
		return 6;
	}

	@Override
	public double getLineHeight()
	{
		return 0.02;
	}
	
	@Override
	public ItemStack getItem()
	{
		return item;
	}
	
	@Override
	public void setItem(ItemStack item)
	{
		this.item = item;
	}
	
	@Override
	public Vector getRotation()
	{
		return properties.rotation.toVector();
	}
	
	@Override
	public void setRotation(Vector rotation)
	{
		properties.rotation = new ConfigVector(rotation);
	}

	@Override
	public String[] updateComponentLines(Player player) {return null;}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		item = Utils.parseItemString(properties.itemString);
	}
	
	@Override
	public ItemComponentProperties getProperties()
	{
		return properties;
	}
}