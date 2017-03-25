package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;

/**
 * Represents a single pixel in a CanvasComponent
 */
public class CanvasPixel
{
	private int entityId;
	private MinecraftColor color;
	private double distance;
	
	public CanvasPixel(int entityId, MinecraftColor color, double distance)
	{
		this.entityId = entityId;
		this.color = color;
		this.distance = distance;
	}
	
	/**
	 * @return The entityId associated with the ArmorStand displaying the pixel.
	 */
	public int getEntityId()
	{
		return entityId;
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
		return distance;
	}
	
	/**
	 * Sets the distance offset towards the player this pixel has in relation to the containing CanvasComponent.
	 */
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
}