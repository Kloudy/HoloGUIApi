package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.EntityButtonComponentConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.configwrappers.EntityTypeConfigWrapper;

/*
 * Represents a clickable Entity on a GUI
 */
public class EntityButtonComponent extends ClickableGUIComponent implements EntityTypeComponent
{	
	EntityButtonComponentConfig config;
			
	EntityButtonComponent(HoloGUIPlugin plugin, EntityButtonComponentConfig config)
	{
		super(plugin);
		
		this.config = config;
	}
	
	@Override
	public EntityType getEntityType()
	{
		return config.entityType.unwrap();
	}
	
	@Override
	public void setEntityType(EntityType entityType)
	{
		config.entityType = new EntityTypeConfigWrapper(entityType);
	}
	
	@Override
	public EntityButtonComponentConfig getConfig()
	{
		return config;
	}
	
	@Override
	public float getYaw()
	{
		return (float)config.yaw;
	}
	
	@Override
	public void setYaw(float yaw)
	{
		config.yaw = yaw;
	}
	
	@Override
	public double zoomDistance()
	{
		return 1.3;
	}

	@Override
	public AABB.Vec3D getMinBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-0.8, origin.y -  2, origin.z-0.8));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+0.8, origin.y + 0.3, origin.z+0.8));
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-0.8, origin.y - 2, origin.z-0.8));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+0.8, origin.y + 0.2, origin.z+0.8));
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
	public PlayerGUIComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUIEntityComponent(player, this);
	}

	@Override
	public void updateIncrement() {}

	@Override
	public String[] updateComponentLines(Player player){ return null; }
}