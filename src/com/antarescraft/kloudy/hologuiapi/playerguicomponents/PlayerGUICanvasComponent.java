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
		
		pixels = new CanvasPixel[canvasComponent.getConfig().height][canvasComponent.getConfig().width];	
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
		
		fill(0, 0, canvas.getConfig().width-1, canvas.getConfig().height-1, color);
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
	
	/**
	 * Draws the BufferedImage onto the canvas. <x,y> coordinates denote the top left pixel of the image.
	 * @param x 
	 * @param y
	 * @param image The image to be drawn on the canvas.
	 */
	public void drawImage(int x, int y, String imageName, int width, int height)
	{
		MinecraftColor[][] imagePixels = guiComponent.getHoloGUIPlugin().loadImage(imageName, width, height);
		if(imagePixels != null)
		{
			for(int i = 0; i < imagePixels.length; i++)
			{
				for(int j = 0; j < imagePixels[0].length; j++)
				{
					// No more rows left on the canvas, return.
					if(i + y >= pixels.length)
					{
						return;
					}
					
					// No more columns left on this row of the canvas, break.
					if(j + x >= pixels[0].length)
					{
						break;
					}
					
					if(imagePixels[i][j] != MinecraftColor.TRANSPARENT)
					{
						this.updatePixel(i + y, j + x, imagePixels[i][j]);
					}
				}
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
		
		for(int i = 0; i < canvas.getConfig().height; i++)
		{
			for(int j = 0; j < canvas.getConfig().width; j++)
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
				
		for(int i = 0; i < getCanvasComponent().getConfig().height; i++)
		{
			for(int j = 0; j < getCanvasComponent().getConfig().width; j++)
			{
				pixels[i][j] = new CanvasPixel(calculatePixelLocation(i, j, player.getLocation(), vect, 
						guiComponent.getConfig().distance, guiComponent.getLineHeight(), 
						guiComponent.getConfig().position.getY(), 
						guiComponent.getConfig().position.getX()));
			}
		}
	}

	@Override
	public void updateLocation(Location lookLocation, boolean stationary) 
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		for(int i = 0; i < getCanvasComponent().getConfig().height; i++)
		{
			for(int j = 0; j < getCanvasComponent().getConfig().width; j++)
			{
				pixels[i][j].updateLocation(player, calculatePixelLocation(i, j, player.getLocation(), vect, 
						guiComponent.getConfig().distance, guiComponent.getLineHeight(), 
						guiComponent.getConfig().position.getY(), 
						guiComponent.getConfig().position.getX()));// - (j * guiComponent.getLineHeight() * 1.2)));
			}
		}
	}
	
	private Location calculatePixelLocation(int rowIndex, int colIndex, Location origin, Vector vect, double distance, double lineHeight, 
			double verticalOffset, double horizontalOffset)
	{			
		double xi = origin.getX();
		double yi = origin.getY();
		double zi = origin.getZ();
		
		//rotate vect by horizontal offset
		double horizontalRadianAngle = horizontalOffset * Math.PI/3.72;
		double verticalRadianAngle = verticalOffset * Math.PI/4.7;

		Vector rotatedVect = rotateAboutYAxis(vect, horizontalRadianAngle);
		double horizontalDistance = (distance * Math.sin(horizontalRadianAngle)) / Math.sin((Math.PI/2) - horizontalOffset);
		Vector orthogVector = new Vector(vect.getZ(), 0, -vect.getX());
		
		//doing rotation relative origin <0,0,0> will add xi,yi,zi in at the end
		double x0 = (rotatedVect.getX() * distance);
		double y0 = (rotatedVect.getY() * distance);
		double z0 = (rotatedVect.getZ() * distance);
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
		
		x1 += orthogVector.getX() * (horizontalDistance - (colIndex * guiComponent.getLineHeight() * 13));
		z1 += orthogVector.getZ() * (horizontalDistance - (colIndex * guiComponent.getLineHeight() * 13));
		return new Location(origin.getWorld(), x1, y1,  z1);
	}
}