package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.guicomponents.EntityTypeComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;

public class PlayerGUIEntityComponent extends PlayerGUIComponent
{		
	public PlayerGUIEntityComponent(Player player, EntityTypeComponent entityTypeComponent)
	{
		super(player, (GUIComponent)entityTypeComponent);

		componentEntityIds = new int[1];
		armorstandLocations = new Location[1];
	}

	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{		
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);

		EntityTypeComponent entityTypeComponent = (EntityTypeComponent)guiComponent;
		
		double distance = guiComponent.getProperties().getDistance();
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		renderLabel(lookOrigin, vect, stationary);
		
		double horizontalRadianAngle = guiComponent.getProperties().getPosition().getX() * Math.PI/3.72;
		float degrees = (float)Math.toDegrees(horizontalRadianAngle);
		float yRotation = lookLocation.getYaw() +  (float)entityTypeComponent.getYaw() - degrees + 180f;
		
		armorstandLocations[0] = calculateArmorStandLocation(0, lookOrigin, vect,
				distance, guiComponent.getLineHeight(), guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
		
		if(stationary)
		{
			armorstandLocations[0].setX(armorstandLocations[0].getX() + (vect.getX() * -15));
			armorstandLocations[0].setZ(armorstandLocations[0].getZ() + (vect.getZ() * -15));
		}

		armorstandLocations[0].setYaw(yRotation);
		
		componentEntityIds[0] = HoloGUIApi.packetManager.spawnEntity(entityTypeComponent.getEntityType(), player, armorstandLocations[0],
		null, false, yRotation, null, new Boolean(true));
		HoloGUIApi.packetManager.updateEntityHeadRotation(player, componentEntityIds[0], yRotation);
	}
	
	@Override
	public void updateComponentLines()
	{
		updateLabelText();
	}

	@Override
	public void updateLocation(Location lookLocation, boolean stationary) 
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		EntityTypeComponent entityTypeComponent = (EntityTypeComponent)guiComponent;
		
		double horizontalRadianAngle = guiComponent.getProperties().getPosition().getX() * Math.PI/3.72;
		float degrees = (float)Math.toDegrees(horizontalRadianAngle);
		float yRotation = lookLocation.getYaw() +  (float)entityTypeComponent.getYaw() - degrees + 180f;
		
		double distance = guiComponent.getProperties().getDistance();
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		updateLabelLocation(lookOrigin, vect, stationary);
		
		armorstandLocations[0] = calculateArmorStandLocation(0, lookOrigin, vect,
				distance, guiComponent.getLineHeight(), guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
		
		armorstandLocations[0].setYaw(yRotation);
		
		if(stationary)
		{
			armorstandLocations[0].setX(armorstandLocations[0].getX() + (vect.getX() * -15));
			armorstandLocations[0].setZ(armorstandLocations[0].getZ() + (vect.getZ() * -15));
		}
		
		HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[0], armorstandLocations[0], yRotation);
		HoloGUIApi.packetManager.updateEntityHeadRotation(player, componentEntityIds[0], yRotation);
	}
	
	@Override
	public Location getLocation()
	{
		return armorstandLocations[0];
	}
}