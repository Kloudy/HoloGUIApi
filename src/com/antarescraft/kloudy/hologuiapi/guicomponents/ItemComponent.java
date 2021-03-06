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

/*
 * Represents a non-clickable item in a GUI
 */
public class ItemComponent extends GUIComponent implements ItemTypeComponent, ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
	ItemComponentProperties properties;
		
	private ItemComponent(){}

	@Override
	public PlayerGUIItemComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUIItemComponent(player, this, properties.getItem());
	}

	@Override
	public void updateIncrement(){}

	@Override
	public double getLineHeight()
	{
		return (1 / properties.getDistance()) * 0.21;
	}
	
	@Override
	public ItemStack getItem()
	{
		return properties.getItem();
	}
	
	@Override
	public void setItem(ItemStack item)
	{
		properties.setItem(item);
	}
	
	@Override
	public Vector getRotation()
	{
		return properties.getRotation();
	}
	
	@Override
	public void setRotation(Vector rotation)
	{
		properties.setRotation(rotation);
	}

	@Override
	public String[] updateComponentLines(Player player) {return null;}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
	}
	
	@Override
	public ItemComponentProperties getProperties()
	{
		return properties;
	}
}