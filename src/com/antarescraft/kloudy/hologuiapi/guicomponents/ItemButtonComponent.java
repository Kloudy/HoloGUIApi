package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.ItemButtonComponentConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIItemComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.configwrappers.ItemStackConfigWrapper;
import com.antarescraft.kloudy.plugincore.configwrappers.VectorConfigWrapper;

/*
 * Represents a clickable item on a GUI
 */
public class ItemButtonComponent extends ClickableGUIComponent implements ItemTypeComponent
{
	ItemButtonComponentConfig config;
	
	ItemButtonComponent(HoloGUIPlugin plugin, ItemButtonComponentConfig config)
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
	public double zoomDistance()
	{
		return 1.3;
	}

	@Override
	public AABB.Vec3D getMinBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-1.2, origin.y -  0.4, origin.z-1.2));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+1.2, origin.y + 0.15, origin.z+1.2));
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-1.2, origin.y - 0.4, origin.z-1.2));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+1.2, origin.y + 0.15, origin.z+1.2));
	}
	
	@Override
	public double getLineHeight()
	{
		return (1 / config.distance) * 0.21;
	}
	
	@Override
	public double getZoomedInLineHeight()
	{
		return getLineHeight() + 0.0005;
	}
	
	@Override
	public String[] updateComponentLines(Player player) {return null;}

	@Override
	public void updateIncrement(){}
	
	@Override
	public ItemButtonComponentConfig getConfig()
	{
		return config;
	}
}