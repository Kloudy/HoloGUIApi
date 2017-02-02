package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.handlers.GUIPageCloseHandler;
import com.antarescraft.kloudy.hologuiapi.handlers.GUIPageLoadHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.StationaryPlayerGUIPage;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigParser;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.utils.Utils;

/**
 * Represents a GUI Page containing many different GUI Components
 * 
 * This class is initialized and hydrated by the ConfigParser library
 */
public class GUIPage implements ConfigObject
{
	private HoloGUIPlugin plugin;
	
	public String toString()
	{
		return ConfigParser.generateConfigString(HoloGUIApi.pluginName, this);
	}
	
	@ConfigElementKey
	private String id;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "open-item")
	private String openItemString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "item-name")
	private String itemName;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "open-on-login")
	private boolean openOnLogin;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "show-permission")
	private String showPermission;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "hide-permission")
	private String hidePermission;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "close-on-player-move")
	private boolean closeOnPlayerMove;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "close-on-player-item-switch")
	private boolean closeOnPlayerItemSwitch;
	
	private ItemStack openItem = null;
	
	private HashMap<String, GUIComponent> guiComponents = new HashMap<String, GUIComponent>();
	private HashMap<UUID, GUIPageLoadHandler> pageLoadHandlers = new HashMap<UUID, GUIPageLoadHandler>();
	private HashMap<UUID, GUIPageCloseHandler> pageCloseHandlers = new HashMap<UUID, GUIPageCloseHandler>();
	
	private GUIPage(){}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return plugin;
	}
	
	public void registerPageLoadHandler(Player player, GUIPageLoadHandler pageLoadHandler)
	{
		pageLoadHandlers.put(player.getUniqueId(), pageLoadHandler);
	}
	
	public void triggerPageLoadHandler(PlayerGUIPage playerGUIPage)
	{
		GUIPageLoadHandler pageLoadHandler = pageLoadHandlers.get(playerGUIPage.getPlayer().getUniqueId());
		if(pageLoadHandler != null)
		{
			pageLoadHandler.onPageLoad(playerGUIPage);
		}
	}
	
	public void removePageLoadHandler(Player player)
	{
		pageLoadHandlers.remove(player.getUniqueId());
	}
	
	public void registerPageCloseHandler(Player player, GUIPageCloseHandler pageCloseHandler)
	{
		pageCloseHandlers.put(player.getUniqueId(), pageCloseHandler);
	}
	
	public void trigglerPageCloseHandler(Player player)
	{
		GUIPageCloseHandler pageCloseHandler = pageCloseHandlers.get(player.getUniqueId());
		if(pageCloseHandler != null)
		{
			pageCloseHandler.onPageClose();
		}
	}
	
	public void removePageCloseHandler(Player player)
	{
		pageCloseHandlers.remove(player.getUniqueId());
	}
	
	/**
	 * Returns the player's gui page
	 * @param player
	 * @return playerGUIPage that belong to the player. returns null if the player isn't looking at an intance of this gui page
	 */
	public PlayerGUIPage getPlayerGUIPage(Player player)
	{
		PlayerGUIPage playerGUIPage = PlayerData.getPlayerData(player).getPlayerGUIPage();
		if(playerGUIPage.getGUIPage().getId().equals(id))
		{
			return playerGUIPage;
		}
		
		return null;
	}
	
	public boolean componentExists(String componentId)
	{
		return guiComponents.containsKey(componentId);
	}
	
	public Collection<GUIComponent> getComponents()
	{
		return guiComponents.values();
	}
	
	public GUIComponent getComponent(String componentId)
	{
		return guiComponents.get(componentId);
	}
	
	public void removeComponent(String componentId)
	{
		guiComponents.remove(componentId);
	}
	
	public void addComponent(GUIComponent component)
	{
		guiComponents.put(component.getProperties().id, component);
	}
	
	public void updateIncrement()
	{
		for(GUIComponent guiComponent : guiComponents.values())
		{
			guiComponent.updateIncrement();
		}
	}
	
	public StationaryPlayerGUIPage renderStationaryComponentsForPlayer(Player player, Location origin, String stationaryGUIDisplayPageId)
	{
		HashMap<String, PlayerGUIComponent> components = new HashMap<String, PlayerGUIComponent>();
		
		for(GUIComponent component : guiComponents.values())
		{
			PlayerGUIComponent playerGUIComponent = component.initPlayerGUIComponent(player);
			components.put(component.getProperties().id, playerGUIComponent);
		}
		
		StationaryPlayerGUIPage stationaryPlayerGUIPage = new StationaryPlayerGUIPage(player, components, origin, this, stationaryGUIDisplayPageId);
		
		stationaryPlayerGUIPage.renderComponents();
		
		return stationaryPlayerGUIPage;
	}

	public PlayerGUIPage renderComponentsForPlayer(Player player, Location lookLocation)
	{
		HashMap<String, PlayerGUIComponent> components = new HashMap<String, PlayerGUIComponent>();
		
		for(GUIComponent component : guiComponents.values())
		{
			PlayerGUIComponent playerGUIComponent = component.initPlayerGUIComponent(player);
			components.put(component.getProperties().id, playerGUIComponent);
		}
		
		PlayerGUIPage playerGUIPage = new PlayerGUIPage(player, components, lookLocation, this);
		
		playerGUIPage.renderComponents();
		PlayerData.getPlayerData(player).setPlayerGUIPage(playerGUIPage);
		
		triggerPageLoadHandler(playerGUIPage);

		return playerGUIPage;
	}
	
	public boolean playerHasPermission(Player player)
	{
		return(((showPermission == null || (showPermission != null && player.hasPermission(showPermission))) &&
				(hidePermission == null || (hidePermission != null && !player.hasPermission(hidePermission)))) || player.isOp());
	}

	public String getId()
	{
		return id;
	}
	
	public void setOpenItem(ItemStack openItem)
	{
		this.openItem = openItem;
	}
	
	public ItemStack getOpenItem()
	{
		return openItem;
	}
	
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	
	public String getItemName()
	{
		return itemName;
	}
	
	public void setOpenOnLogin(boolean openOnLogin)
	{
		this.openOnLogin = openOnLogin;
	}
	
	public boolean getOpenOnLogin()
	{
		return openOnLogin;
	}
	
	public void setShowPermission(String showPermission)
	{
		this.showPermission = showPermission;
	}
	
	public String getShowPermission()
	{
		return showPermission;
	}
	
	public void setHidePermission(String hidePermission)
	{
		this.hidePermission = hidePermission;
	}
	
	public String getHidePermission()
	{
		return hidePermission;
	}
	
	public void setCloseOnPlayerMove(boolean closeOnPlayerMove)
	{
		this.closeOnPlayerMove = closeOnPlayerMove;
	}
	
	public boolean getCloseOnPlayerMove()
	{
		return closeOnPlayerMove;
	}
	
	public void setCloseOnPlayerItemSwitch(boolean closeOnPlayerItemSwitch)
	{
		this.closeOnPlayerItemSwitch = closeOnPlayerItemSwitch;
	}
	
	public boolean getCloseOnPlayerItemSwitch()
	{
		return closeOnPlayerItemSwitch;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		plugin = (HoloGUIPlugin)params.getParam("plugin");
		
		openItem = Utils.parseItemString(openItemString);
	}
}