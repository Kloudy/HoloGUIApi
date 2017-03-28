package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;

public class StationaryPlayerGUIPage extends PlayerGUIPage
{		
	private String stationaryGUIDisplayPageId;
	
	public StationaryPlayerGUIPage(Player player, HashMap<String, PlayerGUIComponent> components,  Location lookLocation, GUIPage guiPage, String stationaryGUIDisplayPageId)
	{
		super(player, components, lookLocation, guiPage);
		
		this.stationaryGUIDisplayPageId = stationaryGUIDisplayPageId;
	}
	
	public String getStationaryGUIDisplayPageId()
	{
		return stationaryGUIDisplayPageId;
	}
	
	@Override
	public void renderComponents()
	{
		for(PlayerGUIComponent playerGUIComponent : components.values())
		{
			playerGUIComponent.spawnEntities(lookLocation, true);
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof StationaryPlayerGUIPage)
		{
			StationaryPlayerGUIPage stationaryPlayerGUIContainer = (StationaryPlayerGUIPage)obj;
			if(stationaryPlayerGUIContainer.getGUIPage().getConfig().id.equals(this.guiPage.getConfig().id)) return true;
		}
		return false;
	}
}