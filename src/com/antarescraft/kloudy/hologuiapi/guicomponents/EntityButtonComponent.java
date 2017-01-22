package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.EntityButtonComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;
import com.antarescraft.kloudy.plugincore.utils.Utils;

/*
 * Represents a clickable Entity on a GUI
 */
public class EntityButtonComponent extends ClickableGUIComponent implements EntityTypeComponent, ConfigObject
{	
	/*private static final double DEFAULT_LABEL_DISTANCE = 8;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 1.3;
	
	@ConfigProperty(key = "type")
	private String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = 0)
	@ConfigProperty(key = "yaw")
	private double yaw;*/
	
	@ConfigProperty(key = "<root>")
	EntityButtonComponentProperties properties;
	
	private EntityType entityType = null;
		
	private EntityButtonComponent(){}
	
	@Override
	public EntityButtonComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, EntityButtonComponent.class);
		}
		catch(Exception e){}
		
		return null;
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
	public EntityButtonComponentProperties getProperties()
	{
		return properties;
	}
	
	@Override
	public float getYaw()
	{
		return (float)properties.yaw;
	}
	
	@Override
	public void setYaw(float yaw)
	{
		properties.yaw = yaw;
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

	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		super.configParseComplete(passthroughParams);
		
		entityType = Utils.parseEntityType(properties.entityTypeString);
	}
}