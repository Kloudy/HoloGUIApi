package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ClickableGUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIPlaceholders;

import me.clip.placeholderapi.PlaceholderAPI;

public abstract class PlayerGUIComponent 
{
	protected Player player;
	protected GUIComponent guiComponent;
	protected Location labelLocation;
	protected String prevLabel;
	protected int labelEntityId;
	protected boolean isFocused = false;
	protected String[] lines;
	protected int[] componentEntityIds;
	protected Location[] armorstandLocations;
	protected boolean hidden;
	
	public PlayerGUIComponent(Player player, GUIComponent guiComponent)
	{		
		this.player = player;
		this.guiComponent = guiComponent;
		this.hidden = guiComponent.isHidden();
	}
	
	public abstract void updateComponentLines();
	public abstract void spawnEntities(Location lookLocation, boolean stationary);
	public abstract void updateLocation(Location lookLocation, boolean stationary);
	
	public boolean isHidden()
	{
		return hidden;
	}
	
	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Location getLocation() 
	{
		return armorstandLocations[0];
	}
	
	public boolean isFocused()
	{
		return isFocused;
	}
	
	public void setIsFocused(boolean isFocused)
	{
		this.isFocused = isFocused;
	}
	
	public GUIComponent getGUIComponent()
	{
		return guiComponent;
	}
	
	public void destroyArmorStands()
	{
		//destroy the component and label ArmorStands
		HoloGUIApi.packetManager.destroyEntities(player, componentEntityIds);
		HoloGUIApi.packetManager.destroyEntities(player, new int[]{labelEntityId});
	}
	
	protected void renderLabel(Location lookLocation, Vector lookDirection, boolean stationary)
	{
		if(guiComponent.getLabel() != null)
		{
			double distance = guiComponent.getLabelDistance();
			Location lookOrigin = player.getLocation();
			if(stationary) 
			{
				lookOrigin = lookLocation;
				distance = 15;
			}
			
			labelLocation =  calculateArmorStandLocation(-1, lookOrigin, lookDirection, distance, guiComponent.getLineHeight(), 
					guiComponent.getPosition().getY(), guiComponent.getPosition().getX());
			
			if(stationary)
			{
				labelLocation.setX(labelLocation.getX() + (lookDirection.getX() * -15));
				labelLocation.setZ(labelLocation.getZ() + (lookDirection.getZ() * -15));
			}
			
			String labelText = guiComponent.getLabel();
			
			labelText = HoloGUIPlaceholders.setHoloGUIPlaceholders(guiComponent.getHoloGUIPlugin(), labelText, player);
			
			if(HoloGUIApi.hasPlaceholderAPI)
			{
				labelText = PlaceholderAPI.setPlaceholders(player, labelText);
			}
			
			PlayerData playerData = PlayerData.getPlayerData(player);
			if(playerData != null) labelText = HoloGUIPlaceholders.setModelPlaceholders(getGUIComponent().getHoloGUIPlugin(), playerData.getPlayerGUIPageModel(), labelText);
			
			labelEntityId = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, labelLocation, labelText, guiComponent.getAlwaysShowLabel());
		}
	}
	
	protected void updateLabelText()
	{
		String labelText = guiComponent.getLabel();
		if(labelText != null)
		{
			labelText = HoloGUIPlaceholders.setHoloGUIPlaceholders(guiComponent.getHoloGUIPlugin(), labelText, player);
			
			if(HoloGUIApi.hasPlaceholderAPI)
			{
				labelText = PlaceholderAPI.setPlaceholders(player, labelText);
			}
			
			PlayerData playerData = PlayerData.getPlayerData(player);
			if(playerData != null) labelText = HoloGUIPlaceholders.setModelPlaceholders(getGUIComponent().getHoloGUIPlugin(), playerData.getPlayerGUIPageModel(), labelText);
			
			HoloGUIApi.packetManager.updateEntityText(player, labelEntityId, labelText);
		}
	}
	
	protected void updateLabelLocation(Location lookLocation, Vector lookDirection, boolean stationary)
	{
		if(guiComponent.getLabel() != null)
		{
			double distance = guiComponent.getLabelDistance();
			Location lookOrigin = player.getLocation();
			if(stationary) 
			{
				lookOrigin = lookLocation;
				distance = 15;
			}
			
			labelLocation = calculateArmorStandLocation(-1, lookOrigin, lookDirection,
					distance, guiComponent.getLineHeight(), guiComponent.getPosition().getY(), guiComponent.getPosition().getX());
			
			if(stationary)
			{
				labelLocation.setX(labelLocation.getX() + (lookDirection.getX() * -15));
				labelLocation.setZ(labelLocation.getZ() + (lookDirection.getZ() * -15));
			}
			
			HoloGUIApi.packetManager.updateEntityLocation(player, labelEntityId, labelLocation);
		}
	}
	
	protected Location calculateArmorStandLocation(int rowIndex, Location origin, Vector vect, double distance, double lineHeight, 
			double verticalOffset, double horizontalOffset)
	{			
		double xi = origin.getX();
		double yi = origin.getY();
		double zi = origin.getZ();
		
		//rotate vect by horizontal offset
		double horizontalRadianAngle = horizontalOffset * Math.PI/3.72;
		double verticalRadianAngle = verticalOffset * Math.PI/4.7;

		vect = rotateAboutYAxis(vect, horizontalRadianAngle);
		//double horizontalDistance = (distance * Math.sin(horizontalRadianAngle)) / Math.sin((Math.PI/2) - horizontalOffset);
		//Vector orthogVector = new Vector(vect.getZ(), 0, -vect.getX());
		
		//doing rotation relative origin <0,0,0> will add xi,yi,zi in at the end
		double x0 = (vect.getX() * distance);
		double y0 = (vect.getY() * distance);
		double z0 = (vect.getZ() * distance);
		//cartesian conversion to spherical coordinates of where player is looking 'distance' blocks away
		double r0 = Math.sqrt((x0*x0)+(y0*y0)+(z0*z0));
		double theta0 = Math.acos((y0 / r0));
		double phi0 = Math.atan2(z0,  x0);
		
		//rowIndex 0 is the label, rotate the label up a line length so it sits with a one row gap above the component
		if(rowIndex == 0) lineHeight *= 2;
		
		//rotate theta0 by deltaTheta
		double r1 = r0;
		double theta1 = theta0 + (rowIndex * lineHeight) - verticalRadianAngle;
		double phi1 = phi0;
		
		//convert spherical coordinates back into cartesian coordinates
		double y1 = r1 * Math.cos(theta1);
		double x1 = r1 * Math.cos(phi1) * Math.sin(theta1);
		double z1 = r1 * Math.sin(theta1) * Math.sin(phi1);
		x1 += xi;
		y1 += yi;
		z1 += zi;
		
		//x1 += orthogVector.getX() * horizontalDistance;
		//z1 += orthogVector.getZ() * horizontalDistance;
		return new Location(origin.getWorld(), x1, y1,  z1);
	}
	
	//normalizes the input vector such that the x and z components always add to one. This keeps the display looking the same at all viewing angles
	protected Vector customNormalize(Vector v)
	{
		Vector vect = v.clone();
		double ratio = 1 / (vect.getX() + vect.getZ());
		double x = vect.getX() * ratio;
		x = Math.copySign(x, v.getX());
		double y = vect.getY();
		double z = vect.getZ() * ratio;
		z = Math.copySign(z, v.getZ());

		vect.setX(x);
		vect.setZ(z);
		vect.normalize();
		vect.setY(y);
		
		return vect;
	}
	
	protected Vector rotateAboutYAxis(Vector vector, double radians)
	{
		double x = (vector.getZ() * Math.sin(radians)) + (vector.getX() * Math.cos(radians));
		double y = vector.getY();
		double z = (vector.getZ() * Math.cos(radians) ) - (vector.getX() * Math.sin(radians));
	
		return new Vector(x, y, z);
	}
	
	public void focusComponent(boolean stationary)
	{
		if(guiComponent.getLabel() != null && !guiComponent.getAlwaysShowLabel())HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, labelEntityId, true);
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
				
				Location location = calculateArmorStandLocation(i,armorstandLocations[i], v, clickableGUIComponent.zoomDistance(),
						clickableGUIComponent.getZoomedInLineHeight(), 0, 0);
				
				if(guiComponent.getLabel() != null && i == 0)//update label location
				{
					Location zoomedLabelLocation = calculateArmorStandLocation(i,labelLocation, v, clickableGUIComponent.getLabelZoomDistance(),
							clickableGUIComponent.getZoomedInLineHeight(), 0, 0);
					
					HoloGUIApi.packetManager.updateEntityLocation(player, labelEntityId, zoomedLabelLocation);
				}
				
				HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[i], location, armorstandLocations[0].getYaw());
			}
		}
	}
	
	public void unfocusComponent(boolean stationary)
	{
		if(guiComponent.getLabel() != null && !guiComponent.getAlwaysShowLabel())HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, labelEntityId, false);
		
		for(int i = 0; i < componentEntityIds.length; i++)
		{
			if(!stationary)
			{
				HoloGUIApi.packetManager.updateEntityLocation(player, componentEntityIds[i], armorstandLocations[i], armorstandLocations[i].getYaw());
				
				if(guiComponent.getLabel() != null && i == 0)//update label location
				{
					HoloGUIApi.packetManager.updateEntityLocation(player, labelEntityId, labelLocation);
				}
			}
		}
	}
}