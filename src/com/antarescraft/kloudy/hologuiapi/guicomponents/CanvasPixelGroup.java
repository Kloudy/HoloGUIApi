package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;

/**
 * This class allows the grouping of CanvasPixels so that operations can be done on them as a unit.
 */
public class CanvasPixelGroup
{
	private ArrayList<CanvasPixel> pixels = new ArrayList<CanvasPixel>();
	
	public void addPixel(CanvasPixel pixel)
	{
		pixels.add(pixel);
	}
	
	public void updatePixel(Player player, MinecraftColor color)
	{
		for(CanvasPixel pixel : pixels)
		{
			pixel.updateColor(player, color);
		}
	}
	
	public void updateDistance(Player player, double distance)
	{
		for(CanvasPixel pixel : pixels)
		{
			pixel.setDistanceOffset(distance);
		}
	}
	
	public void destroy(Player player)
	{
		for(CanvasPixel pixel : pixels)
		{
			pixel.destroy(player);
		}
	}
}