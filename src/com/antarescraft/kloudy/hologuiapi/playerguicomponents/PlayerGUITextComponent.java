package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;

public class PlayerGUITextComponent extends PlayerGUIComponent
{	
	protected HashSet<Integer> hiddenLines;
	
	public PlayerGUITextComponent(Player player, GUIComponent guiComponent, String[] lines)
	{
		super(player, guiComponent);
		this.lines = lines;
		isFocused = false;
		
		componentEntityIds = new int[lines.length];
		armorstandLocations = new Location[componentEntityIds.length];
		hiddenLines = new HashSet<Integer>();
	}
	
	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{		
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		double distance = guiComponent.getDisplayDistance();
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		renderLabel(lookOrigin, vect, stationary);

		for(int i = 0; i < lines.length; i++)
		{
			armorstandLocations[i] = calculateArmorStandLocation(i+1, lookOrigin, vect, distance, guiComponent.getLineHeight(), 
					guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
			
			if(stationary)
			{
				armorstandLocations[i].setX(armorstandLocations[i].getX() + (vect.getX() * -15));
				armorstandLocations[i].setZ(armorstandLocations[i].getZ() + (vect.getZ() * -15));
			}
						
			componentEntityIds[i] = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, armorstandLocations[i], lines[i], true);
			
			if(lines[i].equals(""))
			{
				hiddenLines.add(i);
				HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, componentEntityIds[i], false);
			}
		}
	}
	
	@Override
	public void updateComponentLines()
	{
		updateLabelText();
		
		String[] newLines = guiComponent.updateComponentLines(player);
		for(int i = 0; i < newLines.length; i++)
		{
			if(lines[i] != null && !lines[i].equals(newLines[i]))//don't update the line if it hasn't changed from the previous frame
			{
				if(newLines[i].equals("") && !hiddenLines.contains(i))
				{
					HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, componentEntityIds[i], false);
					hiddenLines.add(i);
				}
				else if(!newLines[i].equals("") && hiddenLines.contains(i))
				{
					HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, componentEntityIds[i], true);
					hiddenLines.remove(i);
				}
				HoloGUIApi.packetManager.updateEntityText(player, componentEntityIds[i], newLines[i]);
			}
		}
		
		lines = newLines;
	}
	
	/**
	 * Teleports this playerGUIComponent to the given verticalOffset and horizontalOffset
	 */
	@Override
	public void updateLocation(Location lookLocation, boolean stationary)
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		double distance = guiComponent.getDisplayDistance();
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		updateLabelLocation(lookOrigin, vect, stationary);
		
		for(int i = 0; i < componentEntityIds.length; i++)
		{
			armorstandLocations[i] = calculateArmorStandLocation(i+1, lookOrigin, vect,
					distance, guiComponent.getLineHeight(), guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
			
			if(stationary)
			{
				armorstandLocations[i].setX(armorstandLocations[i].getX() + (vect.getX() * -15));
				armorstandLocations[i].setZ(armorstandLocations[i].getZ() + (vect.getZ() * -15));
			}
			
			HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[i], armorstandLocations[i]);			
		}
	}
}