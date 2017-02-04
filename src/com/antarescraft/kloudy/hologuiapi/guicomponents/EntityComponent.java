package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.EntityComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;
import com.antarescraft.kloudy.plugincore.utils.Utils;

public class EntityComponent extends GUIComponent implements EntityTypeComponent, ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
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
	public void configParseComplete(PassthroughParams params)
	{	
		super.configParseComplete(params);
		
		entityType = Utils.parseEntityType(properties.entityTypeString);
	}

	@Override
	public EntityComponentProperties getProperties()
	{
		return properties;
	}
}