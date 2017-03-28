package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ItemTypeComponent;

public class PlayerGUIItemComponent extends PlayerGUIComponent
{
	private ItemStack item;
	
	public PlayerGUIItemComponent(Player player, ItemTypeComponent itemTypeComponent, ItemStack item) 
	{
		super(player, (GUIComponent)itemTypeComponent);
		armorstandLocations = new Location[1];
		componentEntityIds = new int[1];
		
		this.item = item;
	}

	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{		
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		double distance = guiComponent.getConfig().distance;
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		renderLabel(lookOrigin, vect, stationary);
		
		double horizontalRadianAngle = guiComponent.getConfig().position.getX() * Math.PI/3.72;
		float degrees = (float)Math.toDegrees(horizontalRadianAngle);
		
		ItemTypeComponent itemTypeComponent = (ItemTypeComponent)guiComponent;
		
		float xRotation = (float)itemTypeComponent.getRotation().getX();
		float yRotation = (float)itemTypeComponent.getRotation().getY() + lookLocation.getYaw() - degrees + 180f;// - 90f;
		float zRotation = (float)itemTypeComponent.getRotation().getZ();

		armorstandLocations[0] = calculateArmorStandLocation(1, lookOrigin, vect, 
				distance, guiComponent.getLineHeight(), guiComponent.getConfig().position.getY(), guiComponent.getConfig().position.getX());
		
		armorstandLocations[0].setYaw(yRotation);
		
		if(stationary)
		{
			armorstandLocations[0].setX(armorstandLocations[0].getX() + (vect.getX() * -15));
			armorstandLocations[0].setZ(armorstandLocations[0].getZ() + (vect.getZ() * -15));
		}
		
		componentEntityIds[0] = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, armorstandLocations[0], "", false, 
				yRotation, new Vector(-90 + xRotation , -90, zRotation), false);
		
		HoloGUIApi.packetManager.updateArmorStandEquipment(player, componentEntityIds[0], item);
	}

	@Override
	public void updateLocation(Location lookLocation, boolean stationary) 
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		double horizontalRadianAngle = guiComponent.getConfig().position.getX() * Math.PI/3.72;
		float degrees = (float)Math.toDegrees(horizontalRadianAngle);
		
		ItemTypeComponent itemTypeComponent = (ItemTypeComponent)guiComponent;
		
		float xRotation = (float)itemTypeComponent.getRotation().getX();
		float yRotation = (float)itemTypeComponent.getRotation().getY() + lookLocation.getYaw() - degrees + 180f;// - 90f;
		float zRotation = (float)itemTypeComponent.getRotation().getZ();
		
		double distance = guiComponent.getConfig().distance;
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		updateLabelLocation(lookOrigin, vect, stationary);
		
		armorstandLocations[0] = calculateArmorStandLocation(2, lookOrigin, vect,
				distance, guiComponent.getLineHeight(), guiComponent.getConfig().position.getY(), guiComponent.getConfig().position.getX());
		
		armorstandLocations[0].setYaw(yRotation);
		
		if(stationary)
		{
			armorstandLocations[0].setX(armorstandLocations[0].getX() + (vect.getX() * -15));
			armorstandLocations[0].setZ(armorstandLocations[0].getZ() + (vect.getZ() * -15));
		}
		
		HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[0], armorstandLocations[0], yRotation);
		HoloGUIApi.packetManager.updateArmorStandRightArmPos(player, componentEntityIds[0], new Vector(-90 + xRotation, -90, zRotation));
	}
	
	@Override
	public Location getLocation()
	{
		return armorstandLocations[0];
	}
	
	public ItemStack getItem()
	{
		return item;
	}

	@Override
	public void updateComponentLines() 
	{
		updateLabelText();
	}
}