package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.hologuiapi.HoloGUIView;

/**
 * Represents a GUI page with a 'tabs' element that allows you to select between gui pages.
 */
public class TabsGUIPage extends GUIPage
{
	private HoloGUIView[] views;
	
	/**
	 * 
	 * @param views An Array of HoloGUIViews in the order that their tabs will be displayed
	 */
	public TabsGUIPage(HoloGUIView... views)
	{
		super();
		
		if(views == null || (views != null && views.length == 0)) throw new IllegalArgumentException();
		
		this.views = views;
	}
}
