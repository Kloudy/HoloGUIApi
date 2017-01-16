package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIItemComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

/*
 * Represents a non-clickable item in a GUI
 */
public class ItemComponent extends GUIComponent implements ItemTypeComponent, ConfigObject
{
	protected ItemStack item;
	private Vector rotation;
	
	private ItemComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
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
		return rotation;
	}
	
	@Override
	public void setRotation(Vector rotation)
	{
		this.rotation = rotation;
	}

	@Override
	public String[] updateComponentLines(Player player) {return null;}

	@Override
	public void configParseComplete(){}
}