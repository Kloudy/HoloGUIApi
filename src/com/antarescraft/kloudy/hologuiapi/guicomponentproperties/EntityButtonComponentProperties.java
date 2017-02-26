package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import org.bukkit.entity.EntityType;

import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.utils.Utils;

public class EntityButtonComponentProperties extends ClickableGUIComponentProperties
{
	private static final double DEFAULT_LABEL_DISTANCE = 8;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 1.3;
	
	@ConfigProperty(key = "entity-type")
	private String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = 0)
	@ConfigProperty(key = "yaw")
	private double yaw;

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		if(labelDistance == -1)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
		
		if(labelZoomDistance == -1)
		{
			labelZoomDistance = DEFAULT_LABEL_ZOOM_DISTANCE;
		}
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