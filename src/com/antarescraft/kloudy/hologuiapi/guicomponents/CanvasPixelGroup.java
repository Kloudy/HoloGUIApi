package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerCanvasPixel;

/**
 * This class allows the grouping of CanvasPixels so that operations can be done on them as a unit.
 */
public class CanvasPixelGroup
{
	private ArrayList<PlayerCanvasPixel> pixels = new ArrayList<PlayerCanvasPixel>();
	
	public void addPixel(PlayerCanvasPixel pixel)
	{
		pixels.add(pixel);
	}
	
	public void updatePixel(Player player, MinecraftColor color)
	{
		for(PlayerCanvasPixel pixel : pixels)
		{
			pixel.updateColor(player, color);
		}
	}
	
	public void updateDistance(Player player, double distance)
	{
		for(PlayerCanvasPixel pixel : pixels)
		{
			pixel.setDistanceOffset(distance);
		}
	}
	
	public void destroy(Player player)
	{
		for(PlayerCanvasPixel pixel : pixels)
		{
			pixel.destroy(player);
		}
	}
}