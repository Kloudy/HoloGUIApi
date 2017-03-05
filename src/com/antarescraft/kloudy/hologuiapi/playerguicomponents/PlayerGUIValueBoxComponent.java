package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ClickableGUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;

public abstract class PlayerGUIValueBoxComponent extends PlayerGUIComponent
{
	public PlayerGUIValueBoxComponent(Player player, GUIComponent guiComponent) 
	{
		super(player, guiComponent);
	}

	@Override
	public void focusComponent(boolean stationary)
	{
		if(guiComponent.getProperties().getLabel() != null && !guiComponent.getProperties().alwaysShowLabel())HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, labelEntityId, true);
		Location l = player.getLocation();
		
		for(int i = 0; i < componentEntityIds.length; i++)
		{
			double x = l.getX() - armorstandLocations[i].getX();
			double y =  l.getY() - armorstandLocations[i].getY();
			double z =  l.getZ() - armorstandLocations[i].getZ();
			Vector v = new Vector(x, y, z).normalize();
			
			if(!stationary)
			{
				ClickableGUIComponent clickableGUIComponent = (ClickableGUIComponent)guiComponent;
				
				Location location = null;
				
				if(i == 0)//valuescroller text
				{
					location = calculateArmorStandLocation(2,armorstandLocations[i], v, clickableGUIComponent.zoomDistance(),
							clickableGUIComponent.getZoomedInLineHeight(), 0, 0);
				}
				else
				{
					location = calculateArmorStandLocation(i,armorstandLocations[i], v, clickableGUIComponent.zoomDistance(),
							clickableGUIComponent.getZoomedInLineHeight(), 0, 0);
				}
				
				if(guiComponent.getProperties().getLabel() != null && i == 0)//update label location
				{
					Location zoomedLabelLocation = calculateArmorStandLocation(i,labelLocation, v, clickableGUIComponent.getProperties().getLabelZoomDistance(),
							clickableGUIComponent.getZoomedInLineHeight(), 0, 0);
					
					HoloGUIApi.packetManager.updateEntityLocation(player, labelEntityId, zoomedLabelLocation);
				}
				
				HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[i], location, armorstandLocations[0].getYaw());
			}
		}
	}
	
	@Override
	public void updateLocation(Location lookLocation, boolean stationary)
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		double distance = guiComponent.getProperties().getDistance();
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		updateLabelLocation(lookOrigin, vect, stationary);
		
		armorstandLocations[0] = calculateArmorStandLocation(2, lookOrigin, vect,
				distance, guiComponent.getLineHeight(), guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
		
		if(stationary)
		{
			armorstandLocations[0].setX(armorstandLocations[0].getX() + (vect.getX() * -15));
			armorstandLocations[0].setZ(armorstandLocations[0].getZ() + (vect.getZ() * -15));
		}
		
		HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[0], armorstandLocations[0]);	
		
		for(int i = 1; i < componentEntityIds.length; i++)
		{
			armorstandLocations[i] = calculateArmorStandLocation(i, lookOrigin, vect,
					distance, guiComponent.getLineHeight(), guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
			
			if(stationary)
			{
				armorstandLocations[i].setX(armorstandLocations[i].getX() + (vect.getX() * -15));
				armorstandLocations[i].setZ(armorstandLocations[i].getZ() + (vect.getZ() * -15));
			}
			
			HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[i], armorstandLocations[i]);			
		}
	}
	
	public void select()
	{
		for(int i = 1; i < componentEntityIds.length; i++)
		{
			String line = "§a" + lines[i];
			StringBuilder strBuilder = new StringBuilder(line);
			strBuilder.insert(line.length()-2, "§a");
			
			HoloGUIApi.packetManager.updateEntityText(player, componentEntityIds[i], strBuilder.toString());
		}
	}
	
	public void unselect()
	{
		for(int i = 1; i < componentEntityIds.length; i++)
		{
			HoloGUIApi.packetManager.updateEntityText(player, componentEntityIds[i], lines[i]);
		}
	}
}