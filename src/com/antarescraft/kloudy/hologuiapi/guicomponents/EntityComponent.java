package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIEntityComponent;

public class EntityComponent extends GUIComponent implements EntityTypeComponent
{
	private EntityType entityType;
	private float yaw;
	
	public EntityComponent(GUIComponentProperties properties, EntityType entityType, float yaw)
	{
		super(properties);
		
		this.entityType = entityType;
		this.yaw = yaw;
	}
	
	@Override
	public EntityComponent clone()
	{
		return new EntityComponent(cloneProperties(), entityType, yaw);
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
}