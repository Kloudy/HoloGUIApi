package com.antarescraft.kloudy.hologuiapi.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public class GifImageLoader implements ImageLoader
{
	private static GifImageLoader instance;
	
	private GifImageLoader(){}
	
	public static GifImageLoader getInstance()
	{
		if(instance == null)
		{
			instance = new GifImageLoader();
		}
		
		return instance;
	}
	
	@Override
	public BufferedImage[] loadImage(InputStream input)
	{
		GifDecoder decoder = new GifDecoder();
		int code = decoder.read(input);
		
		if(code == 0)
		{
			BufferedImage[] image = new BufferedImage[decoder.getFrameCount()];
			for(int i = 0; i < decoder.getFrameCount(); i++)
			{
				image[i] = decoder.getFrame(i);
			}
			
			return image;
		}
		
		return null;
	}
}