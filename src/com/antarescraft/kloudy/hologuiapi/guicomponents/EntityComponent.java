package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.config.EntityComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

public class EntityComponent extends GUIComponent implements EntityTypeComponent, ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
	private EntityComponentProperties properties;
	
	private EntityComponent(){}

	@Override
	public EntityType getEntityType()
	{
		return properties.getEntityType();
	}
	
	@Override
	public void setEntityType(EntityType entityType)
	{
		properties.setEntityType(entityType);
	}
	
	@Override
	public float getYaw()
	{
		return (float)properties.getYaw();
	}
	
	@Override
	public void setYaw(float yaw)
	{
		properties.setYaw(yaw);
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
	public double getLineHeight()
	{
		return (1 / properties.getDistance()) * 0.21;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{	
		super.configParseComplete(params);
	}

	@Override
	public EntityComponentProperties getProperties()
	{
		return properties;
	}
}