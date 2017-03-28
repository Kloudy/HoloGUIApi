package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.GUIComponentConfig;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;

public abstract class GUIComponent
{	
	protected HoloGUIPlugin plugin;
	
	public abstract PlayerGUIComponent initPlayerGUIComponent(Player player);
	public abstract GUIComponentConfig getConfig();
	public abstract void updateIncrement();//updates the guicomponent's next incremental state
	public abstract String[] updateComponentLines(Player player);
	public abstract double getLineHeight();
	
	GUIComponent(HoloGUIPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return plugin;
	}
}