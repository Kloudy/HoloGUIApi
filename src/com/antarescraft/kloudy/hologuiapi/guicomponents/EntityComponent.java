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

public class EntityComponent extends GUIComponent implements EntityTypeComponent, ConfigObject
{
	@ConfigProperty(key = "type")
	private String entityTypeString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0, maxValue = Double.MAX_VALUE, minValue = 0)
	@ConfigProperty(key = "yaw")
	private double yaw;
	
	private EntityComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
	@Override
	public EntityComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, EntityComponent.class);
		}
		catch(Exception e){}
		
		return null;
	}

	@Override
	public EntityType getEntityType()
	{
		try
		{
			return EntityType.valueOf(entityTypeString);
		}
		catch(Exception e){}
		
		return null;
	}
	
	@Override
	public void setEntityType(EntityType entityType)
	{
		entityTypeString = entityType.toString();
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
	public void configParseComplete(){}
}