package com.antarescraft.kloudy.hologuiapi.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class PngJpgImageLoader implements ImageLoader
{
	private static PngJpgImageLoader instance;
	
	private PngJpgImageLoader(){}
	
	public static PngJpgImageLoader getInstance()
	{
		if(instance == null)
		{
			instance = new PngJpgImageLoader();
		}
		
		return instance;
	}
	
	@Override
	public BufferedImage[] loadImage(InputStream input) throws IOException
	{
		BufferedImage[] image = new BufferedImage[1];
		image[0] = ImageIO.read(input);
		
		return image;
	}
}