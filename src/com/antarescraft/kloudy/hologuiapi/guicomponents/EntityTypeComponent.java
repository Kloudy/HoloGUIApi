package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.EntityType;

public interface EntityTypeComponent 
{
	public EntityType getEntityType();
	public void setEntityType(EntityType entityType);
	public float getYaw();
	public void setYaw(float yaw);
}