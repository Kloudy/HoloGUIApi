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
	private int x;
	private int y; 
	private Integer entityId;
	private MinecraftColor color;
	private double distanceOffset = 0;
	
	/**
	 * @param x This pixel's x index in the grid of pixels in the containing CanvasComponent.
	 * @param y This pixel's y index in the grid of pixels in the containing CanvasComponent.
	 */
	public CanvasPixel(int x, int y)
	{
		this(x, y, MinecraftColor.TRANSPARENT, 0);
	}
	
	/**
	 * @param x This pixel's x index in the grid of pixels in the containing CanvasComponent.
	 * @param y This pixel's y index in the grid of pixels in the containing CanvasComponent.
	 * @param color The color of the pixel.
	 * @param distanceOffset The distance zoom offset towards the player (Used to zoom in/out the pixel with relation to the rest of the CanvasComponent).
	 */
	public CanvasPixel(int x, int y, MinecraftColor color, double distanceOffset)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.distanceOffset = distanceOffset;
	}
	
	/**
	 * Updates this pixel's color and distance for the given player. 
	 * 
	 * @param player
	 */
	public void update(Player player)
	{
		if(color == MinecraftColor.TRANSPARENT) // If the color is transparent, just destroy the ArmorStand.
		{
			destroy(player);
			
			return;
		}
		
		HoloGUIApi.packetManager.updateEntityText(player, entityId, color.symbol() + "▇");
	}
	
	/**
	 * Renders the pixel for the player at the specified location.
	 * @param player The player for whom this pixel will be rendered.
	 * @param location The location that this pixel will be rendered.
	 */
	public void render(Player player, Location location)
	{
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
	 * Sets the color for this pixel.
	 */
	public void setColor(MinecraftColor color)
	{
		this.color = color;
	}
	
	/**
	 * @return The distance offset towards the player this pixel has in relation to the containing CanvasComponent.
	 */
	public double distance()
	{
		return distanceOffset;
	}
	
	/**
	 * Sets the distance offset towards the player this pixel has in relation to the containing CanvasComponent.
	 */
	public void setDistance(double distance)
	{
		this.distanceOffset = distance;
	}
}