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
				pixels[i][j] = new CanvasPixel(j, i);
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
		
		for(int i = 0; i < canvas.getProperties().getHeight(); i++)
		{
			for(int j = 0; j < canvas.getProperties().getWidth(); j++)
			{
				pixels[i][j].setColor(color);
				pixels[i][j].update(player);
			}
		}
	}

	@Override
	public void updateComponentLines()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{
		
	}

	@Override
	public void updateLocation(Location lookLocation, boolean stationary) 
	{
		
	}
}