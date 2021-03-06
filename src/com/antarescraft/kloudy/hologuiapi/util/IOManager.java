package com.antarescraft.kloudy.hologuiapi.util;

import java.io.File;

public class IOManager
{
	public static final String PATH_TO_ROOT = "plugins/HoloGUIApi";
	public static final String PATH_TO_ICONS = PATH_TO_ROOT + "/icons";
	public static final String PATH_TO_IMAGES = PATH_TO_ROOT + "/images";
	public static final String PATH_TO_GUI_CONFIGURATION_FILES = PATH_TO_ROOT + "/gui configuration files";
	
	public static void initFileStructure()
	{
		try
		{
			File folder = new File(PATH_TO_ROOT);
			if(!folder.exists())
			{
				folder.mkdir();
			}
			
			folder = new File(PATH_TO_IMAGES);
			if(!folder.exists())
			{
				folder.mkdir();
			}
			
			folder = new File(PATH_TO_GUI_CONFIGURATION_FILES);
			if(!folder.exists())
			{
				folder.mkdir();
			}
		}
		catch(Exception e){}		
	}
}