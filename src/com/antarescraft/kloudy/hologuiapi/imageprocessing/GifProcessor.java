package com.antarescraft.kloudy.hologuiapi.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public class GifProcessor 
{
	public static String[][] processGif(String gifName, InputStream input, int width, int height, boolean symmetrical)
	{
		String[][] lines = null;
		
		GifDecoder decoder = new GifDecoder();
		int code = decoder.read(input);
		
		if(code == 0)
		{	
			BufferedImage[] frames = new BufferedImage[decoder.getFrameCount()];
			for(int i = 0; i < decoder.getFrameCount(); i++)
			{
				frames[i] = decoder.getFrame(i);
			}
			
			lines = ImageProcessor.processImage(frames, width, height, symmetrical);
		}

		return lines;
	}
}