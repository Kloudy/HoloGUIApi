package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponents.CanvasComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.CanvasPixel;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;

public class PlayerGUICanvasComponent extends PlayerGUIComponent
{
	CanvasPixel[][] pixels;

	public PlayerGUICanvasComponent(Player player, CanvasComponent canvasComponent)
	{
		super(player, canvasComponent);
		
		pixels = new CanvasPixel[canvasComponent.getProperties().getHeight()][canvasComponent.getProperties().getWidth()];
		
		// Initialize all of the CanvasPixels.
		for(int i = 0; i < canvasComponent.getProperties().getHeight(); i++)
		{
			for(int j = 0; j < canvasComponent.getProperties().getWidth(); j++)
			{
				pixels[i][j] = new CanvasPixel();
			}
		}
	}
	
	public CanvasComponent getCanvasComponent()
	{
		return (CanvasComponent)guiComponent;
	}
	
	/**
	 * Fills the canvas with the given MinecraftColor
	 * @param color
	 */
	public void fill(MinecraftColor color)
	{
		CanvasComponent canvas = getCanvasComponent();
		
		fill(0, 0, canvas.getProperties().getWidth()-1, canvas.getProperties().getHeight()-1, color);
	}
	
	public void fill(int x1, int y1, int x2, int y2, MinecraftColor color)
	{
		for(int i = y1; i <= y2; i++)
		{
			for(int j = x1; j <= x2; j++)
			{
				if(i < pixels.length && j < pixels[0].length)
				{
					pixels[i][j].updateColor(player, color);
				}
			}
		}
	}
	
	public void drawImage(int x, int y, String imageName)
	{
		guiComponent.getHoloGUIPlugin().loadImage("warp_icon_3.png", 9, 9, true);
		
		for(int i = y; i < pixels.length; i++)
		{
			for(int j = x; j < pixels[0].length; j++)
			{
				
			}
		}
	}
	
	/**
	 * Clears the canvas.
	 */
	public void clear()
	{
		destroyArmorStands();
	}
	
	@Override
	public void destroyArmorStands()
	{
		CanvasComponent canvas = getCanvasComponent();
		
		for(int i = 0; i < canvas.getProperties().getHeight(); i++)
		{
			for(int j = 0; j < canvas.getProperties().getWidth(); j++)
			{
				pixels[i][j].destroy(player);
			}
		}
	}
	
	/**
	 * Updates the pixel at the input coordinates with the given color.
	 * @param row Zero indexed row of the pixel.
	 * @param column Zero index column of the pixel.
	 * @param color The color.
	 */
	public void updatePixel(int row, int column, MinecraftColor color)
	{
		pixels[row][column].updateColor(player, color);
	}

	@Override
	public void updateComponentLines(){}

	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
				
		for(int i = 0; i < getCanvasComponent().getProperties().getHeight(); i++)
		{
			for(int j = 0; j < getCanvasComponent().getProperties().getWidth(); j++)
			{
				pixels[i][j].render(player, calculateArmorStandLocation(i, player.getLocation(), vect, 
						guiComponent.getProperties().getDistance(), guiComponent.getLineHeight(), 
						guiComponent.getProperties().getPosition().getY(), 
						guiComponent.getProperties().getPosition().getX() - (j * guiComponent.getLineHeight() * 1.2)));
			}
		}
	}

	@Override
	public void updateLocation(Location lookLocation, boolean stationary) 
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		for(int i = 0; i < getCanvasComponent().getProperties().getHeight(); i++)
		{
			for(int j = 0; j < getCanvasComponent().getProperties().getWidth(); j++)
			{
				pixels[i][j].updateLocation(player, calculateArmorStandLocation(i, player.getLocation(), vect, 
						guiComponent.getProperties().getDistance(), guiComponent.getLineHeight(), 
						guiComponent.getProperties().getPosition().getY(), 
						guiComponent.getProperties().getPosition().getX() - (j * guiComponent.getLineHeight() * 1.2)));
			}
		}
	}
	
	/*
	 * Calculates the location to spawn/move a pixel to.
	 */
	/*private Location calculatePixelLocation(int row, int column, Location origin, Vector vect, double distance, double lineHeight, 
			double verticalOffset, double horizontalOffset)
	{		
		double xi = origin.getX();
		double yi = origin.getY();
		double zi = origin.getZ();
		
		// Rotate vect by horizontal offset.
		double horizontalRadianAngle = horizontalOffset * Math.PI/3.72;
		double verticalRadianAngle = verticalOffset * Math.PI/4.7;

		vect = rotateAboutYAxis(vect, horizontalRadianAngle);
		double horizontalDistance = (distance * Math.sin(horizontalRadianAngle)) / Math.sin((Math.PI/2) - horizontalOffset);
		Vector orthogVector = new Vector(vect.getZ(), 0, -vect.getX());
		
		// Doing rotation relative origin <0,0,0> will add xi,yi,zi in at the end.
		double x0 = (vect.getX() * distance);
		double y0 = (vect.getY() * distance);
		double z0 = (vect.getZ() * distance);
		
		// Cartesian conversion to spherical coordinates of where player is looking 'distance' blocks away
		double r0 = Math.sqrt((x0*x0)+(y0*y0)+(z0*z0));
		double theta0 = Math.acos((y0 / r0));
		double phi0 = Math.atan2(z0,  x0);
		
		
		// Rotate theta0 by deltaTheta.
		double r1 = r0;
		double theta1 = theta0 + (row * lineHeight) - verticalRadianAngle;
		double phi1 = phi0;
		
		// Convert spherical coordinates back into Cartesian coordinates.
		double y1 = r1 * Math.cos(theta1);
		double x1 = r1 * Math.cos(phi1) * Math.sin(theta1);
		double z1 = r1 * Math.sin(theta1) * Math.sin(phi1);
		x1 += xi;
		y1 += yi;
		z1 += zi;
		
		x1 += (orthogVector.getX() * horizontalDistance) + (column * 0.15);
		z1 += (orthogVector.getZ() * horizontalDistance) + (column * 0.15);
		
		return new Location(origin.getWorld(), x1, y1,  z1);
	}*/
}