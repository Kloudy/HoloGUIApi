package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.GUIComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUICanvasComponent;

/**
 * Represents a Canvas component on a player's gui.
 * 
 * The Canvas component contains a grid of pixels that can be manipulated 
 * independently of each other. Each pixel can be zoomed in/out independent of its neighbors.
 *
 */
public class CanvasComponent extends GUIComponent
{

	@Override
	public PlayerGUICanvasComponent initPlayerGUIComponent(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUIComponentProperties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateIncrement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] updateComponentLines(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getLineHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}