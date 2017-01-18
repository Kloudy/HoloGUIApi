package com.antarescraft.kloudy.hologuiapi;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.antarescraft.kloudy.hologuiapi.events.HoloGUITextBoxUpdateEvent;
import com.antarescraft.kloudy.hologuiapi.events.PlayerChangedWorldEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerDeathEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerInteractEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerItemHeldEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerJoinEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerMoveEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerQuitEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerRespawnEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerTeleportEventListener;
import com.antarescraft.kloudy.hologuiapi.events.PlayerToggleSneakEventListener;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPageModel;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIUpdateTask;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.StationaryPlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.util.IOManager;
import com.antarescraft.kloudy.hologuiapi.util.Metrics;
import com.antarescraft.kloudy.plugincore.protocol.PacketManager;
import com.antarescraft.kloudy.plugincore.protocol.WrapperPlayClientChat;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

/**
 * Contains all of the api methods used by 3rd party plugins to interact with HoloGUI
 */
public class HoloGUIApi extends JavaPlugin
{	
	private HashMap<String, StationaryGUIDisplayContainer> stationaryGUIDisplayContainers;
	
	private HoloGUIPluginManager pluginManager;
	
	public static boolean hasPlaceholderAPI = false;
	public static double stationaryDisplayRenderDistance;
	public static String fileHash;
	public static boolean debugMode;
	public static String PATH_TO_IMAGES = "resources/images";
	public static String PATH_TO_YAMLS = "resources/yamls";
	
	public static PacketManager packetManager;
	
	@Override
	public void onEnable()
	{			
		pluginManager = new HoloGUIPluginManager();
		
		debugMode = this.getConfig().getRoot().getBoolean("debug-mode", false);
		
		stationaryGUIDisplayContainers = new HashMap<String, StationaryGUIDisplayContainer>();
		
		IOManager.initFileStructure();
		
		saveDefaultConfig();
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
		{
			hasPlaceholderAPI = true;
		}
		
		packetManager = PacketManager.getInstance(-25000, -35000);
		packetManager.registerPacketListener(new PacketAdapter(this,
				PacketType.Play.Client.CHAT){
			
			@Override
			public void onPacketReceiving(PacketEvent event)
			{
				if(event.getPacketType() == PacketType.Play.Client.CHAT)
				{
					Player player = event.getPlayer();
					
					PlayerGUITextBoxComponent textBox = PlayerData.getPlayerData(player).getTextBoxEditor();
					if(textBox != null)
					{
						WrapperPlayClientChat chatPacket = new WrapperPlayClientChat(event.getPacket());
						
						String value = chatPacket.getMessage();
						textBox.stopEditing(value);
						
						TextBoxComponent textBoxComponent = textBox.getTextBoxComponent();
						textBoxComponent.triggerTextBoxUpdateHandler(player, value);
						
						HoloGUITextBoxUpdateEvent textBoxUpdateEvent = new HoloGUITextBoxUpdateEvent(textBoxComponent.getHoloGUIPlugin(), textBoxComponent, player);
						Bukkit.getPluginManager().callEvent(textBoxUpdateEvent);
						
						event.setCancelled(true);
					}					
				}
			}
			
			@Override
			public void onPacketSending(PacketEvent event)
			{
				
			}
		});
		
		getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerChangedWorldEventListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerTeleportEventListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveEventListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawnEventListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerItemHeldEventListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerToggleSneakEventListener(), this);
		
		int tickrate = getConfig().getRoot().getInt("gui-update-tickrate");
		PlayerGUIUpdateTask playerGUIUpdateTask = PlayerGUIUpdateTask.getInstance(this);
		playerGUIUpdateTask.start(tickrate);
		
		try
		{
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {}		
	}

	@Override
	public void onDisable()
	{
		destroyAllGUIPages();
		PlayerGUIUpdateTask.getInstance(this).cancel();
	}
	
	/**
	 * Hook your plugin into HoloGUI
	 * @param holoGUIPlugin your HoloGUIPlugin class
	 */
	public void hookHoloGUIPlugin(HoloGUIPlugin holoGUIPlugin)
	{
		pluginManager.hookHoloGUIPlugin(holoGUIPlugin);
	}
	
	/**
	 * Unhooks your plugin from HoloGUI
	 * @param holoGUIPlugin
	 */
	public void unhookHoloGUIPlugin(HoloGUIPlugin holoGUIPlugin)
	{
		pluginManager.unhookHoloGUIPlugin(holoGUIPlugin);
	}
	
	/**
	 * 
	 * @return a Collection<HoloGUIPlugin> of all hooked HoloGUI plugins
	 */
	public Collection<HoloGUIPlugin> getHookedHoloGUIPlugins()
	{
		return pluginManager.getHookedHoloGUIPlugins();
	}

	public StationaryGUIDisplayContainer getStationaryGUIDisplay(String stationaryGUIDisplayId)
	{
		return stationaryGUIDisplayContainers.get(stationaryGUIDisplayId);
	}
	
	public void addStationaryDisplay(String stationaryGUIDisplayId, StationaryGUIDisplayContainer stationaryDisplay)
	{
		stationaryGUIDisplayContainers.put(stationaryGUIDisplayId, stationaryDisplay);
	}
	
	public void removeStationaryDisplay(String stationaryGUIDisplayId)
	{
		stationaryGUIDisplayContainers.remove(stationaryGUIDisplayId);
	}
	
	/**
	 * 
	 * @return Collection of all defined StationaryDisplays (static guis)
	 */
	public Collection<StationaryGUIDisplayContainer> getStationaryDisplays()
	{
		return stationaryGUIDisplayContainers.values();
	}
	
	/**
	 * Opens the GUI page with the specified guiPageId
	 * 
	 * @param holoGUIPlugin
	 * @param player
	 * @param guiPageId
	 */
	public void openGUIPage(HoloGUIPlugin holoGUIPlugin, Player player, String guiPageId)
	{
		openGUIPage(holoGUIPlugin, player, guiPageId, null, null);
	}
	
	/**
	 * Opens the GUI page with the specified guiPageId and data model
	 * @param holoGUIPlugin
	 * @param player
	 * @param guiPageId
	 * @param model PlayerGUIPageModel object bound to the GUIPage
	 */
	public void openGUIPage(HoloGUIPlugin holoGUIPlugin, Player player, String guiPageId, PlayerGUIPageModel model)
	{
		openGUIPage(holoGUIPlugin, player, guiPageId, null, model);
	}
	
	/**
	 * @param holoGUIPlugin
	 * @param player
	 * @param guiPageId
	 * @param staticGUIDisplayId
	 */
	public void openGUIPage(HoloGUIPlugin holoGUIPlugin, Player player, String guiPageId, String staticGUIDisplayId)
	{
		openGUIPage(holoGUIPlugin, player, guiPageId, staticGUIDisplayId, null);
	}
	
	/**
	 * 
	 * @param holoGUIPlugin
	 * @param player
	 * @param model PlayerGUIPageModel object bound to the GUIPage
	 */
	public void openGUIPage(HoloGUIPlugin holoGUIPlugin, Player player, PlayerGUIPageModel model)
	{
		openGUIPage(holoGUIPlugin, player, model.getGUIPage().getId(), null, model);
	}
	
	/**
	 * Opens the GUI page with the specified guiPageId. The gui page opens on a personal GUI unless the static GUI display id is specified.
	 * This method will do nothing under the following conditions:
	 *  - The player doesn't have permission to view the specified GUI page
	 *  - No GUI Page exists with the specified guiPageId
	 *  - No static gui display exists with the specified staticGUIDisplayId
	 * 
	 * @param holoGUIPlugin 
	 * @param player
	 * @param guiContainerId The id of the GUI page to open
	 * @param staticGUIDisplayId The id of the static display to open the gui page on
	 */
	public void openGUIPage(HoloGUIPlugin holoGUIPlugin, Player player, String guiPageId, String staticGUIDisplayId, PlayerGUIPageModel model)
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		if(staticGUIDisplayId == null)
		{
			playerData.setPlayerGUIPageModel(model);
		}
		
		GUIPage guiPage = holoGUIPlugin.getGUIPages().get(guiPageId);
		if(guiPage != null)
		{
			if(guiPage.playerHasPermission(player))
			{
				if(staticGUIDisplayId != null)//stationaryGUIDisplayId was specified
				{
					StationaryGUIDisplayContainer stationaryDisplay = stationaryGUIDisplayContainers.get(staticGUIDisplayId);
					if(stationaryDisplay != null)
					{
						stationaryDisplay.display(player, guiPage, model);
					}
				}
				else
				{
					PlayerGUIPage playerGUIContainer = playerData.getPlayerGUIPage();
					
					Location lookLocation = null;
					if(playerGUIContainer != null)//player is already looking at a gui
					{
						playerGUIContainer.destroy();
						
						lookLocation = playerGUIContainer.getLookLocation();

						playerData.setPlayerPreviousGUIContainer(playerGUIContainer);
					}
					
					if(lookLocation == null) lookLocation = player.getLocation().clone();
					
					playerData.setPlayerGUIPage(guiPage.renderComponentsForPlayer(player, lookLocation));
				}
			}
		}
	}
	
	/**
	 * Closes the GUI page the player currently has open
	 * 
	 * @param player
	 */
	public void closeGUIPage(Player player)
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		playerData.setPlayerGUIPageModel(null);
		PlayerGUIPage playerGUIPage = playerData.getPlayerGUIPage();
		if(playerGUIPage != null)
		{
			playerGUIPage.getGUIPage().trigglerPageCloseHandler(player);
			playerGUIPage.destroy();
			playerData.setPlayerGUIPage(null);
			playerData.setPlayerPreviousGUIContainer(null);
		}
	}
	
	/**
	 * Displays the previous GUI page to the player if it exists 
	 * 
	 * @param player
	 */
	public void displayPreviousGUIPage(Player player)
	{
		displayPreviousGUIPage(player, null);
	}
	
	/**
	 * Displays the previous GUI page to the player if it exists
	 * 
	 * @param player
	 * @param stationaryDisplayId id of the stationary display to display the previous page on
	 */
	public void displayPreviousGUIPage(Player player, String stationaryDisplayId)
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		if(stationaryDisplayId != null)//stationary gui display id was specified
		{
			StationaryGUIDisplayContainer stationaryDisplay = stationaryGUIDisplayContainers.get(stationaryDisplayId);
			if(stationaryDisplay != null)
			{
				StationaryPlayerGUIPage stationaryPlayerGUIContainer = stationaryDisplay.getPreviousStationaryPlayerGUIContainer(player);
				if(stationaryPlayerGUIContainer != null)
				{
					stationaryDisplay.display(player, stationaryPlayerGUIContainer.getGUIPage());
				}
			}
		}
		else
		{
			PlayerGUIPage previousPlayerGUIContainer = playerData.getPlayerPreviousGUIContainer();
			PlayerGUIPageModel prevModel = playerData.getPrevPlayerGUIPageModel();
			if(previousPlayerGUIContainer != null)
			{
				//remove current GUI
				PlayerGUIPage playerGUIPage = playerData.getPlayerGUIPage();
				if(playerGUIPage != null)
				{
					previousPlayerGUIContainer.setLookLocation(playerGUIPage.getLookLocation());
					playerGUIPage.destroy();
					playerData.setPlayerGUIPage(previousPlayerGUIContainer);
					playerData.setPlayerPreviousGUIContainer(playerGUIPage);
					previousPlayerGUIContainer.renderComponents();
					
					playerData.setPlayerGUIPageModel(prevModel);
				}
			}
		}
	}
	
	/**
	 * Destroys all PlayerGUIPages associated with the given HoloGUIPlugin
	 * @param holoGUIPlugin
	 */
	public void destroyGUIPages(HoloGUIPlugin holoGUIPlugin)
	{
		for(PlayerData playerData : PlayerData.getAllPlayerData())
		{
			PlayerGUIPage playerGUIPage = playerData.getPlayerGUIPage();
			if(playerGUIPage != null && playerGUIPage.getGUIPage().getHoloGUIPlugin().equals(holoGUIPlugin))
			{
				playerGUIPage.destroy();
			}
		}
	}
	
	/**
	 * Destroys all PlayerGUIPages. Be careful when calling this plugin as it will remove all PlayerGUIPages for all hooked hologui plugins.
	 */
	public void destroyAllGUIPages()
	{
		for(PlayerData playerData : PlayerData.getAllPlayerData())
		{
			PlayerGUIPage playerGUIPage = playerData.getPlayerGUIPage();
			if(playerGUIPage != null)
			{
				playerGUIPage.destroy();
			}
		}
		
		for(StationaryGUIDisplayContainer stationaryDisplay : stationaryGUIDisplayContainers.values())
		{
			stationaryDisplay.destroyAll();
		}
		
		packetManager.resetAllPlayerNextAvailableEntityIds();
		stationaryGUIDisplayContainers.clear();
		
		PlayerData.removeAllPlayerData();
	}
}