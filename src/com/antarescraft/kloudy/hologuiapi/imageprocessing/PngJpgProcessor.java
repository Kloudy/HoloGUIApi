package com.antarescraft.kloudy.hologuiapi.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class PngJpgProcessor 
{
	public static String[][] processImage(String imageName, InputStream input, int width, int height, boolean symmetrical)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(input);
		} 
		catch (IOException e) {}
		
		if(image != null)
		{
			return ImageProcessor.processImage(new BufferedImage[]{image}, width, height, symmetrical);
		}
		else
		{
			return null;
		}
	}
	
	public static MinecraftColor[][] processImage(InputStream input, int width, int height)
	{
		// TODO: finish
		return null;
	}
}