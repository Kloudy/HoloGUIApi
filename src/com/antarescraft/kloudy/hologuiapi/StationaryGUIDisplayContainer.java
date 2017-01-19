package com.antarescraft.kloudy.hologuiapi;

import java.io.File;
import java.io.IOException;
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
import com.antarescraft.kloudy.plugincore.config.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.configobjects.ConfigLocation;

public class StationaryGUIDisplayContainer implements ConfigObject
{	
	@ConfigElementKey
	private String id;
	
	@ConfigProperty(key = "location")
	private ConfigLocation configLocation;
	
	@ConfigProperty(key = "default-gui-container-id")
	private String defaultGUIPageId;
	
	private HashMap<UUID, StationaryPlayerGUIPage> stationaryPlayerGUIPages = new HashMap<UUID, StationaryPlayerGUIPage>();
	private HashMap<UUID, StationaryPlayerGUIPage> previousStationaryPlayerGUIPages = new HashMap<UUID, StationaryPlayerGUIPage>();
	private HashMap<UUID, PlayerGUIPageModel> models = new HashMap<UUID, PlayerGUIPageModel>();
	private HashMap<UUID, PlayerGUIPageModel> prevModels = new HashMap<UUID, PlayerGUIPageModel>();
	
	private HoloGUIPlugin plugin;
	private GUIPage defaultGUIContainer = null;
	private Location location = null;
	
	public StationaryGUIDisplayContainer(HoloGUIPlugin plugin)
	{
		this.plugin = plugin;
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
	
	public void save()
	{
		try
		{
			ConfigParser.saveObject(plugin.getName(), new File(String.format("plugins/%s/config.yml", plugin.getName())), "stationary-gui-displays." + id, this);
		} 
		catch (IOException e) {}
	}
	
	public void delete()
	{
		try
		{
			ConfigParser.saveObject(plugin.getName(), new File(String.format("plugins/%s/config.yml", plugin.getName())), "stationary-gui-displays." + id, null);
		} 
		catch (IOException e) {}
	}
	
	/*private void writeToConfig()
	{
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".default-gui-container-id", defaultGUIContainer.getId());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.world-uuid", location.getWorld().getUID().toString());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.x", location.getX());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.y", location.getY());
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id + ".location.z", location.getZ());
	}*/
	
	/*public void delete()
	{
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.writePropertyToConfigFile(IOManager.PATH_TO_ROOT + "/config.yml", "stationary-gui-displays." + id, null);
	}*/
	
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
		defaultGUIContainer = ConfigManager.loadGUIPage(plugin, defaultGUIPageId);
	}
}