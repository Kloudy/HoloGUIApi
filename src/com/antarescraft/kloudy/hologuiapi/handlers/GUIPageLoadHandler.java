package com.antarescraft.kloudy.hologuiapi.handlers;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;

/**
 * Used to define a callback function that executes when a gui page is rendered for a player
 */
public interface GUIPageLoadHandler 
{
	public void onPageLoad(PlayerGUIPage loadedPlayerGUIPage);
}