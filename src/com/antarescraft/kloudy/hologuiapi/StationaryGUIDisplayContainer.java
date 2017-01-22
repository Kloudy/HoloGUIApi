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
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIApiConfig;
import com.antarescraft.kloudy.plugincore.config.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.configobjects.ConfigLocation;

public class StationaryGUIDisplayContainer implements ConfigObject
{	
	@ConfigElementKey
	private String id;
	
	@ConfigElement
	@ConfigProperty(key = "location")
	private ConfigLocation location;
	
	@ConfigProperty(key = "default-gui-container-id")
	private String defaultGUIPageId;
	
	@ConfigProperty(key = "plugin-name")
	private String pluginName;
	
	private HashMap<UUID, StationaryPlayerGUIPage> stationaryPlayerGUIPages = new HashMap<UUID, StationaryPlayerGUIPage>();
	private HashMap<UUID, StationaryPlayerGUIPage> previousStationaryPlayerGUIPages = new HashMap<UUID, StationaryPlayerGUIPage>();
	private HashMap<UUID, PlayerGUIPageModel> models = new HashMap<UUID, PlayerGUIPageModel>();
	private HashMap<UUID, PlayerGUIPageModel> prevModels = new HashMap<UUID, PlayerGUIPageModel>();
	
	private GUIPage defaultGUIPage = null;
	
	@SuppressWarnings("unused")
	private StationaryGUIDisplayContainer(){System.out.println("why am i being initialize?");}
	
	public StationaryGUIDisplayContainer(String id, String pluginName, String defaultGUIPageId, Location location)
	{
		this.id = id;
		this.pluginName = pluginName;
		this.defaultGUIPageId = defaultGUIPageId;
		this.location = new ConfigLocation(location);
	}
	
	public String getId()
	{
		return id;
	}
	
	public GUIPage getGUIDefaultPage()
	{
		return defaultGUIPage;
	}
	
	public Location getLocation()
	{
		return location.toLocation();
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
	
	public void save(String pluginName)
	{
		try
		{
			ConfigParser.saveObject(pluginName, new File(String.format("plugins/%s/config.yml", pluginName)), "stationary-gui-displays." + id, this);
		} 
		catch (IOException e) {}
	}
	
	public void delete(String pluginName)
	{
		try
		{
			ConfigParser.saveObject(pluginName, new File(String.format("plugins/%s/config.yml", pluginName)), "stationary-gui-displays." + id, null);
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
		display(player, defaultGUIPage);
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
			Location locationCopy = location.toLocation();

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

		return (playerLocation.getWorld().getUID().equals(location.toLocation().getWorld().getUID()) && playerLocation.distance(location.toLocation()) <= HoloGUIApiConfig.stationaryDisplayRenderDistance());
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
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		HoloGUIPlugin plugin = ((HoloGUIApi)passthroughParams.get("plugin")).getHookedHoloGUIPlugin(pluginName);
		if(plugin != null)
		{
			defaultGUIPage = plugin.getGUIPage(defaultGUIPageId);
		}
	}
}