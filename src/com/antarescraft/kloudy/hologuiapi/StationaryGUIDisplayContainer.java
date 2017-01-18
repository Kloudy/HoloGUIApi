package com.antarescraft.kloudy.hologuiapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPageModel;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.StationaryPlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.util.ConfigManager;
import com.antarescraft.kloudy.hologuiapi.util.IOManager;
import com.antarescraft.kloudy.plugincore.config.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;

public class StationaryGUIDisplayContainer implements ConfigObject
{	
	@ConfigElementKey
	private String id;
	private HashMap<UUID, StationaryPlayerGUIPage> stationaryPlayerGUIPages;
	private HashMap<UUID, StationaryPlayerGUIPage> previousStationaryPlayerGUIPages;
	private HashMap<UUID, PlayerGUIPageModel> models;
	private HashMap<UUID, PlayerGUIPageModel> prevModels;
	
	private GUIPage defaultGUIContainer;
	private Location location;
	
	public StationaryGUIDisplayContainer(String id, GUIPage guiContainer, Location location)
	{
		this.id = id;
		this.defaultGUIContainer = guiContainer;
		this.location = location;
		
		stationaryPlayerGUIPages = new HashMap<UUID, StationaryPlayerGUIPage>();
		previousStationaryPlayerGUIPages = new HashMap<UUID, StationaryPlayerGUIPage>();
		models = new HashMap<UUID, PlayerGUIPageModel>();
		prevModels = new HashMap<UUID, PlayerGUIPageModel>();
		
		writeToConfig();
	}
	
	public String getId()
	{
		return id;
	}
	
	public GUIPage getGUIDefaultContainer()
	{
		return defaultGUIContainer;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public ArrayList<PlayerGUIPage> getPlayerGUIContainers()
	{
		ArrayList<PlayerGUIPage> playerGUIComponents = new ArrayList<PlayerGUIPage>();
		
		playerGUIComponents.addAll(stationaryPlayerGUIPages.values());
		
		return playerGUIComponents;
	}
	
	public HashMap<UUID, StationaryPlayerGUIPage> getPlayerGUIContainersTable()
	{
		return stationaryPlayerGUIPages;
	}
	
	private void writeToConfig()
	{
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".default-gui-container-id", defaultGUIContainer.getId());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.world-uuid", location.getWorld().getUID().toString());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.x", location.getX());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.y", location.getY());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.z", location.getZ());
	}
	
	public void deleteConfigProperties()
	{
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id, null);
	}
	
	public void display(Player player)
	{
		display(player, defaultGUIContainer);
	}
	
	public void display(Player player, GUIPage guiPage)
	{
		display(player, guiPage, null);
	}
	
	public void display(Player player, GUIPage guiPage, PlayerGUIPageModel model)
	{
		if(guiPage.playerHasPermission(player))
		{
			if(model != null)
			{
				prevModels.put(player.getUniqueId(), models.get(player.getUniqueId()));
				models.put(player.getUniqueId(), model);
			}
			
			StationaryPlayerGUIPage prevStationaryPlayerGUIContainer = stationaryPlayerGUIPages.get(player.getUniqueId());
			if(prevStationaryPlayerGUIContainer != null)
			{
				previousStationaryPlayerGUIPages.put(player.getUniqueId(), prevStationaryPlayerGUIContainer);
				prevStationaryPlayerGUIContainer.destroy();
			}
			
			Location playerLoc = player.getLocation();
			Location locationCopy = location.clone();

			double xi = locationCopy.getX() - playerLoc.getX();
			double yi = locationCopy.getY() - playerLoc.getY();
			double zi = locationCopy.getZ() - playerLoc.getZ();
			Vector vect = new Vector(xi, yi , zi).normalize();
			
			locationCopy.setDirection(vect);
			
			StationaryPlayerGUIPage stationaryPlayerGUIContainer = guiPage.renderStationaryComponentsForPlayer(player, locationCopy, id);
			
			stationaryPlayerGUIPages.put(player.getUniqueId(), stationaryPlayerGUIContainer);
		}
	}
	
	public StationaryPlayerGUIPage getPreviousStationaryPlayerGUIContainer(Player player)
	{
		return previousStationaryPlayerGUIPages.get(player.getUniqueId());
	}
	
	public void removePreviousStationaryPlayerGUIContainer(Player player)
	{
		previousStationaryPlayerGUIPages.remove(player.getUniqueId());
	}
	
	public boolean playerInRange(Player player)
	{
		Location playerLocation = player.getLocation();

		return (playerLocation.getWorld().getUID().equals(location.getWorld().getUID()) && playerLocation.distance(location) <= HoloGUIApi.stationaryDisplayRenderDistance);
	}
	
	public boolean isDisplayingToPlayer(Player player)
	{
		return stationaryPlayerGUIPages.containsKey(player.getUniqueId());
	}
	
	public void destroy(Player player)
	{
		StationaryPlayerGUIPage stationaryPlayerGUIContainer = stationaryPlayerGUIPages.get(player.getUniqueId());
		if(stationaryPlayerGUIContainer != null)
		{
			stationaryPlayerGUIContainer.destroy();
		}
		
		stationaryPlayerGUIPages.remove(player.getUniqueId());
		previousStationaryPlayerGUIPages.remove(player.getUniqueId());
	}
	
	public void destroyAll()
	{
		for(StationaryPlayerGUIPage stationaryPlayerGUIContainer : stationaryPlayerGUIPages.values())
		{
			stationaryPlayerGUIContainer.destroy();
		}
		
		stationaryPlayerGUIPages.clear();
		previousStationaryPlayerGUIPages.clear();
	}

	@Override
	public void configParseComplete()
	{
		// TODO Auto-generated method stub
		
	}
}