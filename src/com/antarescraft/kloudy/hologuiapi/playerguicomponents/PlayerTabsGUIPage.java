package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.guicomponents.TabsGUIPage;

public class PlayerTabsGUIPage extends PlayerGUIPage
{
	public PlayerTabsGUIPage(Player player, HashMap<String, PlayerGUIComponent> components, Location lookLocation, TabsGUIPage guiPage)
	{
		super(player, components, lookLocation, guiPage);
	}
}
