package com.antarescraft.kloudy.hologuiapi.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;

public class GUIPagesLoadedEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public GUIPagesLoadedEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler
	public void guiPagesLoaded(GUIPagesLoadedEvent event)
	{
		ConfigurationSection root = holoGUI.getConfig();
		
		//load stationary displays from config.yml
		ConfigurationSection stationaryDisplaysSection = root.getConfigurationSection("stationary-gui-displays");
		if(stationaryDisplaysSection != null)
		{
			for(String key : stationaryDisplaysSection.getKeys(false))
			{
				ConfigurationSection stationaryGUIDisplaySection = stationaryDisplaysSection.getConfigurationSection(key);
				
				String guiContainerId = stationaryGUIDisplaySection.getString("default-gui-container-id");
				if(guiContainerId != null)
				{
					GUIPage guiContainer = holoGUI.getGUIPages().get(guiContainerId);
					if(guiContainer != null)
					{
						ConfigurationSection locationSection = stationaryGUIDisplaySection.getConfigurationSection("location");
						if(locationSection != null)
						{
							double x = locationSection.getDouble("x");
							double y = locationSection.getDouble("y");
							double z = locationSection.getDouble("z");
							String worldId = locationSection.getString("world-uuid");
							
							if(worldId != null)
							{
								World world = Bukkit.getWorld(UUID.fromString(worldId));
								if(world != null)
								{
									Location location = new Location(world, x, y, z);
									StationaryGUIDisplayContainer stationaryDisplay = new StationaryGUIDisplayContainer(key, guiContainer, location);
									
									holoGUI.addStationaryDisplay(key, stationaryDisplay);
								}
							}
						}
					}
				}
			}
		}
				
		for(StationaryGUIDisplayContainer stationaryDisplay : holoGUI.getStationaryDisplays())
		{
			World world = stationaryDisplay.getLocation().getWorld();
			for(Player player : world.getPlayers())
			{
				if(player != null && stationaryDisplay.playerInRange(player))
				{
					stationaryDisplay.display(player);
				}
			}
		}
	}
}