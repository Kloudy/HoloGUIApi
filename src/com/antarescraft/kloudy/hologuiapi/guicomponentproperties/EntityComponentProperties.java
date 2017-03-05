package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import org.bukkit.entity.EntityType;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.utils.Utils;

public class EntityComponentProperties extends GUIComponentProperties
{
	private static final double DEFAULT_DISTANCE = 8;
	
	@ConfigProperty(key = "entity-type")
	private String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = 360, minValue = 0)
	@ConfigProperty(key = "yaw")
	private double yaw;
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
	}
	
	@Override
	public double getDefaultDistance()
	{
		return DEFAULT_DISTANCE;
	}
	
	public EntityType getEntityType()
	{
		return Utils.parseEntityType(entityTypeString);
	}
	
	public void setEntityType(EntityType entityType)
	{
		entityTypeString = entityType.toString();
	}
	
	public double getYaw()	
	{
		return yaw;
	}
	
	public void setYaw(double yaw)
	{
		this.yaw = yaw;
	}
}