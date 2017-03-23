package com.antarescraft.kloudy.hologuiapi;

import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPageModel;

/**
 * A container class that holds a named binding of a PlayerGUIPageModel and GUIPage.
 * The contained GUIPage does not have to have a bound PlayerGUIPageModel.
 */
public class HoloGUIView
{
	private String name;
	private GUIPage guiPage;
	private PlayerGUIPageModel model;
	
	public HoloGUIView(String name, GUIPage guiPage)
	{
		this(name, guiPage, null);
	}
	
	public HoloGUIView(String name, GUIPage guiPage, PlayerGUIPageModel model)
	{
		this.name = name;
		this.guiPage = guiPage;
		this.model = model;
	}
	
	public String getName()
	{
		return name;
	}
	
	public GUIPage getGUIPage()
	{
		return guiPage;
	}
	
	public PlayerGUIPageModel getModel()
	{
		return model;
	}
}