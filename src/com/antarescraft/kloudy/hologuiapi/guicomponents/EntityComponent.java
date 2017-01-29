package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.EntityComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;
import com.antarescraft.kloudy.plugincore.utils.Utils;

public class EntityComponent extends GUIComponent implements EntityTypeComponent, ConfigObject
{
	/*private static final double DEFAULT_LABEL_DISTANCE = 8;
	
	@ConfigProperty(key = "type")
	private String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = 360, minValue = 0)
	@ConfigProperty(key = "yaw")
	private double yaw;*/
	
	@ConfigProperty(key = "<root>")
	private EntityComponentProperties properties;
	
	private EntityType entityType = null;
	
	private EntityComponent(){}
	
	@Override
	public EntityComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, EntityComponent.class, plugin);
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
	public float getYaw()
	{
		return (float)properties.yaw;
	}
	
	@Override
	public void setYaw(float yaw)
	{
		properties.yaw = (double)yaw;
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
	public double getLineHeight() 
	{
		return 0.02;
	}

	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{	
		super.configParseComplete(passthroughParams);
		
		entityType = Utils.parseEntityType(properties.entityTypeString);
	}

	@Override
	public EntityComponentProperties getProperties()
	{
		return properties;
	}
}