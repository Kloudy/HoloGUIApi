package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIItemComponent;

public class ItemComponent extends GUIComponent implements ItemTypeComponent
{
	protected ItemStack item;
	private Vector rotation;
	
	public ItemComponent(GUIComponentProperties properties, ItemStack item, Vector rotation)
	{
		super(properties);
		
		this.item = item;
		this.rotation = rotation;
	}
	
	@Override
	public ItemComponent clone()
	{
		return new ItemComponent(cloneProperties(), item.clone(), rotation.clone());
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
}