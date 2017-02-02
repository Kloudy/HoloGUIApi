package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.GUIComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;

public abstract class GUIComponent implements ConfigObject
{	
	protected HoloGUIPlugin plugin;
	
	public abstract PlayerGUIComponent initPlayerGUIComponent(Player player);
	public abstract GUIComponentProperties getProperties();
	public abstract void updateIncrement();//updates the guicomponent's next incremental state
	public abstract String[] updateComponentLines(Player player);
	public abstract double getDisplayDistance();
	public abstract double getLineHeight();
	public abstract GUIComponent clone();
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return plugin;
	}
	
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		plugin = (HoloGUIPlugin)params.getParam("plugin");		
	}
}