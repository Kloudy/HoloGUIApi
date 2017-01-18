package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;
import com.antarescraft.kloudy.plugincore.utils.Utils;

public class EntityComponent extends GUIComponent implements EntityTypeComponent, ConfigObject
{
	private static final double DEFAULT_LABEL_DISTANCE = 8;
	
	@ConfigProperty(key = "type")
	private String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = 360, minValue = 0)
	@ConfigProperty(key = "yaw")
	private double yaw;
	
	private EntityType entityType = null;
	
	private EntityComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
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
		return (float)yaw;
	}
	
	@Override
	public void setYaw(float yaw)
	{
		this.yaw = (double)yaw;
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
	public void configParseComplete()
	{
		if(labelDistance == null)
		{
			labelDistance = DEFAULT_LABEL_DISTANCE;
		}
		
		entityType = Utils.parseEntityType(entityTypeString);
	}
}