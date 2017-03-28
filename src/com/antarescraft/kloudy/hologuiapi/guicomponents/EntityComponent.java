package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.EntityComponentConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;
import com.antarescraft.kloudy.plugincore.configwrappers.EntityTypeConfigWrapper;

public class EntityComponent extends GUIComponent implements EntityTypeComponent
{
	private EntityComponentConfig config;
	
	EntityComponent(HoloGUIPlugin plugin, EntityComponentConfig config)
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
		return (1 / config.distance) * 0.21;
	}

	@Override
	public EntityComponentConfig getConfig()
	{
		return config;
	}
}