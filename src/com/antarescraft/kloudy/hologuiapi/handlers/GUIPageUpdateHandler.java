package com.antarescraft.kloudy.hologuiapi.handlers;

public interface GUIPageUpdateHandler 
{
	/**
	 * Called every time the GUIPage update loop is executed. (Default: 2 ticks)
	 */
	public void onUpdate();
}