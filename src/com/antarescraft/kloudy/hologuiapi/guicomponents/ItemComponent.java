package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.ItemComponentConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIItemComponent;
import com.antarescraft.kloudy.plugincore.configwrappers.ItemStackConfigWrapper;
import com.antarescraft.kloudy.plugincore.configwrappers.VectorConfigWrapper;

/*
 * Represents a non-clickable item in a GUI
 */
public class ItemComponent extends GUIComponent implements ItemTypeComponent
{
	ItemComponentConfig config;
		
	ItemComponent(HoloGUIPlugin plugin, ItemComponentConfig config)
	{
		super(plugin);
		
		this.config = config;
	}

	@Override
	public PlayerGUIItemComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUIItemComponent(player, this, config.item.unwrap());
	}

	@Override
	public void updateIncrement(){}

	@Override
	public double getLineHeight()
	{
		return (1 / config.getDistance()) * 0.21;
	}
	
	@Override
	public ItemStack getItem()
	{
		return config.item.unwrap();
	}
	
	@Override
	public void setItem(ItemStack item)
	{
		config.item = new ItemStackConfigWrapper(item);
	}
	
	@Override
	public Vector getRotation()
	{
		return config.rotation.toVector();
	}
	
	@Override
	public void setRotation(Vector rotation)
	{
		config.rotation = new VectorConfigWrapper(rotation);
	}

	@Override
	public String[] updateComponentLines(Player player) {return null;}

	@Override
	public ItemComponentConfig getConfig()
	{
		return config;
	}
}