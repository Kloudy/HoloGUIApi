package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
	
	private Location calculateArmorStandLocation(int rowIndex, Location origin, Vector vect, double distance, double lineHeight, 
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
	
	protected Vector rotateAboutYAxis(Vector vector, double radians)
	{
		double x = (vector.getZ() * Math.sin(radians)) + (vector.getX() * Math.cos(radians));
		double y = vector.getY();
		double z = (vector.getZ() * Math.cos(radians) ) - (vector.getX() * Math.sin(radians));
	
		return new Vector(x, y, z);
	}
}