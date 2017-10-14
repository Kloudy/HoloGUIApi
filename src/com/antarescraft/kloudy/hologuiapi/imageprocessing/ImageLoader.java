package com.antarescraft.kloudy.hologuiapi.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public interface ImageLoader 
{
	public BufferedImage[] loadImage(InputStream inputStream) throws IOException;
}