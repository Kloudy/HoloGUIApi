package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIItemComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.ConfigVector;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

/*
 * Represents a clickable item on a GUI
 */
public class ItemButtonComponent extends ClickableGUIComponent implements ItemTypeComponent, ConfigObject
{
	@ConfigProperty(key = "item-id")
	private String materialString;
	
	@ConfigElement
	@ConfigProperty(key = "rotation")
	private ConfigVector rotation;
	
	private ItemStack item = null;
	
	private static final double DEFAULT_LABEL_DISTANCE = 6;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 1.3;

	private ItemButtonComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
	@Override
	public ItemButtonComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, ItemButtonComponent.class, plugin);
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
		return rotation.toVector();
	}
	
	@Override
	public void setRotation(Vector rotation)
	{
		this.rotation = new ConfigVector(rotation);
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
		return 0.02;
	}
	
	@Override
	public double getZoomedInLineHeight()
	{
		return 0.0145;
	}
	
	@Override
	public String[] updateComponentLines(Player player) {return null;}

	@Override
	public void updateIncrement(){}

	@Override
	public double getDisplayDistance()
	{
		return 6;
	}

	@Override
	public void configParseComplete() 
	{
		if(labelDistance == null)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
		
		if(labelZoomDistance == null)
		{
			labelZoomDistance = DEFAULT_LABEL_ZOOM_DISTANCE;
		}
	}
}