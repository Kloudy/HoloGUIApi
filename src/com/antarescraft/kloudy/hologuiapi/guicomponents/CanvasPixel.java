package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;

/**
 * Represents a single pixel in a CanvasComponent
 */
public class CanvasPixel
{
	private Integer entityId;
	private MinecraftColor color;
	private double distanceOffset;
	private Location location;
	
	public CanvasPixel()
	{
		this(MinecraftColor.TRANSPARENT, 0);
	}
	
	/**
	 * @param color The color of the pixel.
	 * @param distanceOffset The distance zoom offset towards the player (Used to zoom in/out the pixel with relation to the rest of the CanvasComponent).
	 */
	public CanvasPixel(MinecraftColor color, double distanceOffset)
	{
		this.color = color;
		this.distanceOffset = distanceOffset;
	}
	
	/**
	 * Updates this pixel's color for the given player. 
	 * 
	 * @param player
	 * @param color
	 */
	public void updateColor(Player player, MinecraftColor color)
	{
		this.color = color;
		
		if(color == MinecraftColor.TRANSPARENT) // If the color is transparent, just destroy the ArmorStand.
		{
			destroy(player);
			
			return;
		}
		
		if(!isRendered()) // Pixel not already rendered. Render the pixel.
		{
			render(player, location);
		}
		else // Pixel already rendered. Just update the color.
		{
			HoloGUIApi.packetManager.updateEntityText(player, entityId, color.symbol() + "▇");
		}
	}
	
	/**
	 * Teleport's this pixel's ArmorStand to the input Location
	 * @param player
	 * @param location
	 */
	public void updateLocation(Player player, Location location)
	{
		this.location = location;
		
		if(isRendered())
		{
			HoloGUIApi.packetManager.updateEntityLocation(player, entityId, location);
		}
	}
	
	/**
	 * Sets the distance offset towards the player this pixel has in relation to the containing CanvasComponent.
	 */
	public void setDistanceOffset(double distance)
	{
		//TODO: implement.
		this.distanceOffset = distance;
	}
	
	/**
	 * Renders the pixel for the player at the specified location.
	 * @param player The player for whom this pixel will be rendered.
	 * @param location The location that this pixel will be rendered.
	 */
	public void render(Player player, Location location)
	{
		this.location = location;
		
		if(color != MinecraftColor.TRANSPARENT) // If the color is Transparent, don't bother spawning the ArmorStand.
		{
			entityId = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, location, color.symbol() + "▇", true);
		}
	}
	
	/**
	 * @return true if this pixel's ArmorStand has been spawned
	 */
	public boolean isRendered()
	{
		return !(entityId == null);
	}
	
	/**
	 * @return The entity id associated with the ArmorStand that backs this pixel.
	 */
	public int getEntityId()
	{
		return entityId;
	}
	
	/**
	 * Destroys the ArmorStand that displays this pixel for the given player.
	 * 
	 * @param player The player for whom this pixel will be destroyed.
	 */
	public void destroy(Player player)
	{
		if(isRendered())
		{
			HoloGUIApi.packetManager.destroyEntities(player, new int[] { entityId });
			
			entityId = null;
		}
	}
	
	/**
	 * @return the MinecraftColor being displayed on the pixel
	 */
	public MinecraftColor getColor()
	{
		return color;
	}
	
	/**
	 * @return The distance offset towards the player this pixel has in relation to the containing CanvasComponent.
	 */
	public double distance()
	{
		return distanceOffset;
	}
}