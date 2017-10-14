package com.antarescraft.kloudy.hologuiapi.imageprocessing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageProcessor
{	
	private static ImageProcessor instance;
	
	private ImageProcessor(){}
	
	public static ImageProcessor getInstance()
	{
		if(instance == null)
		{
			instance = new ImageProcessor();
		}
		
		return instance;
	}
	
	public String[][] processImage(BufferedImage[] image, ImageOptions options)
	{
		String[][] lines = null;
		if(image != null && image.length > 0)
		{
			lines = new String[image.length][options.height];
			
			//process each frame
			for(int i = 0; i < image.length; i++)
			{
				BufferedImage frame = image[i];

				int type = frame.getType() == 0? BufferedImage.TYPE_INT_ARGB : frame.getType();
				frame = resizeImageWithHint(frame, type, options.width, options.height);
				
				//process each rbg value of each pixel
				for(int y = 0; y < options.height; y++)
				{
					MinecraftColor lastColor = MinecraftColor.TRANSPARENT;
					int numTrans = 0;
					for(int x = 0; x < options.width; x++)
					{
						int rgb = frame.getRGB(x, y);
						MinecraftColor closestColor = null;
						
						int mask = 0x0000FF;
						int alpha = (rgb >> 24) & mask;
						int red = (rgb >> 16) & mask;
						int green = (rgb >> 8) & mask;
						int blue = rgb & mask;
						
						int difRGB = Integer.MAX_VALUE;
						
						for(MinecraftColor color: MinecraftColor.values())
						{
							int r = color.red();
							int g = color.green();
							int b = color.blue();
							int dif = Math.abs(r - red) + Math.abs(g - green) + Math.abs(b - blue);
							
							if(dif < difRGB)
							{
								difRGB = dif;
								closestColor = color;
							}
						}
						
						if(alpha < 255)
						{
							closestColor = MinecraftColor.TRANSPARENT;
						}
						
						//repeat color
						if(x != 0 && closestColor == lastColor)
						{
							if(lastColor == MinecraftColor.TRANSPARENT)
							{
								lines[i][y] += "  ";
								numTrans += 1;
							}
							else
							{
								lines[i][y] += "▇";
							}					
						}
						//different color or first character in the row
						else
						{
							//first character
							if(x == 0)
							{
								lines[i][y] = new String();
								
								if(closestColor == MinecraftColor.TRANSPARENT)
								{
									lines[i][y] += closestColor.symbol() + "  ";
									numTrans += 1;
								}
								else
								{
									lines[i][y] += closestColor.symbol() + "▇";
								}
							}
							//different color
							else
							{
								//last color was transparent, insert format reset
								if(lastColor == MinecraftColor.TRANSPARENT)
								{
									int l = lines[i][y].length();
									StringBuilder strbuilder = new StringBuilder(lines[i][y]);
									lines[i][y] = strbuilder.insert(l - numTrans, "§r").toString();
									numTrans = 0;
								}
								
								//new color is transparent
								if(closestColor == MinecraftColor.TRANSPARENT)
								{
									lines[i][y] += closestColor.symbol() + "  ";
									numTrans += 1;
								}
								else
								{
									lines[i][y] += closestColor.symbol() + "▇";
								}
							}
						}
						
						lastColor = closestColor;
					}
					
					//if transparent pixel string goes to the end of the row
					if(numTrans > 0)
					{
						int l = lines[i][y].length();
						StringBuilder strbuilder = new StringBuilder(lines[i][y]);
						lines[i][y] = strbuilder.insert(l - numTrans, "§r").toString();
						numTrans = 0;
					}
					
					if(options.symmetrical)
					{
						//trying to get rid of border
						int beginningIndex = lines[i][y].length();
						int endingIndex = lines[i][y].length()-1;
						for(int k = 0; k < lines[i][y].length(); k++)
						{
							if(lines[i][y].charAt(k) == '▇')
							{
								beginningIndex = k-2;
								break;
							}
						}
						for(int k = lines[i][y].length()-1; k >= 0; k--)
						{
							if(lines[i][y].charAt(k) == '▇')
							{
								endingIndex = k;
								break;
							}
						}
						if(beginningIndex > endingIndex)
						{
							lines[i][y] = "";
						}
						else
						{
							lines[i][y] = lines[i][y].substring(beginningIndex, endingIndex + 1);
						}
					}
				}
			}
		}
		
		return lines;
	}
	
    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, int width, int height)
    {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);
	 
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	 
		return resizedImage;
    }
}
