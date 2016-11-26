package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public interface ItemTypeComponent
{
	public ItemStack getItem();
	public void setItem(ItemStack item);
	public Vector getRotation();
	public void setRotation(Vector rotation);
}
