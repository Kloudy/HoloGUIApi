package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

public class EntityButtonComponent extends ClickableGUIComponent implements EntityTypeComponent
{	
	private EntityType entityType;
	private float yaw;
	
	public EntityButtonComponent(GUIComponentProperties properties, ClickableGUIComponentProperties clickableProperties,
			EntityType entityType, float yaw)
	{
		super(properties, clickableProperties);
		
		this.entityType = entityType;
		this.yaw = yaw;
	}
	
	@Override
	public EntityButtonComponent clone()
	{
		return new EntityButtonComponent(cloneProperties(), cloneClickableProperties(), entityType, yaw);
	}
	
	@Override
	public EntityType getEntityType()
	{
		return entityType;
	}
	
	@Override
	public void setEntityType(EntityType entityType)
	{
		this.entityType = entityType;
	}
	
	@Override
	public float getYaw()
	{
		return yaw;
	}
	
	@Override
	public void setYaw(float yaw)
	{
		this.yaw = yaw;
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
		return 0.02;
	}
	
	@Override
	public double getZoomedInLineHeight()
	{
		return 0.0145;
	}

	@Override
	public PlayerGUIComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUIEntityComponent(player, this);
	}

	@Override
	public void updateIncrement() {}

	@Override
	public String[] updateComponentLines(Player player){return null;}

	@Override
	public double getDisplayDistance() 
	{
		return 8;
	}
}