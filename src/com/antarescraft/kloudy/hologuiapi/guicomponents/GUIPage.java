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
import com.antarescraft.kloudy.hologuiapi.handlers.GUIPageUpdateHandler;
import com.antarescraft.kloudy.hologuiapi.handlers.MousewheelAction;
import com.antarescraft.kloudy.hologuiapi.handlers.MousewheelScrollHandler;
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
	protected HoloGUIPlugin plugin;
	
	public String toString()
	{
		return ConfigParser.generateConfigString(HoloGUIApi.pluginName, this);
	}
	
	@ConfigElementKey
	protected String id;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "open-item")
	protected String openItemString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "item-name")
	protected String itemName;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "open-on-login")
	protected boolean openOnLogin;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "show-permission")
	protected String showPermission;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "hide-permission")
	protected String hidePermission;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "close-on-player-move")
	protected boolean closeOnPlayerMove;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "close-on-player-item-switch")
	protected boolean closeOnPlayerItemSwitch;
	
	protected ItemStack openItem = null;
	
	protected HashMap<String, GUIComponent> guiComponents = new HashMap<String, GUIComponent>();
	private HashMap<UUID, GUIPageLoadHandler> pageLoadHandlers = new HashMap<UUID, GUIPageLoadHandler>();
	private HashMap<UUID, GUIPageCloseHandler> pageCloseHandlers = new HashMap<UUID, GUIPageCloseHandler>();
	private HashMap<UUID, MousewheelScrollHandler> mouseWheelScrollHandlers = new HashMap<UUID, MousewheelScrollHandler>();
	private HashMap<UUID, GUIPageUpdateHandler> guiPageUpdateHandlers = new HashMap<UUID, GUIPageUpdateHandler>();
	
	protected GUIPage(){}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return plugin;
	}
	
	/**
	 * Registers a page load handler that gets executed when the gui page loads.
	 * @param player
	 * @param pageLoadHandler
	 */
	public void registerPageLoadHandler(Player player, GUIPageLoadHandler pageLoadHandler)
	{
		pageLoadHandlers.put(player.getUniqueId(), pageLoadHandler);
	}
	
	/**
	 * Triggers the gui page load handler and passes through the PlayerGUIPage.
	 * @param playerGUIPage
	 */
	public void triggerPageLoadHandler(PlayerGUIPage playerGUIPage)
	{
		GUIPageLoadHandler pageLoadHandler = pageLoadHandlers.get(playerGUIPage.getPlayer().getUniqueId());
		if(pageLoadHandler != null)
		{
			pageLoadHandler.onPageLoad(playerGUIPage);
		}
	}
	
	/**
	 * Removes the GUIPageLoadHandler for the player.
	 * @param player
	 */
	public void removePageLoadHandler(Player player)
	{
		pageLoadHandlers.remove(player.getUniqueId());
	}
	
	/**
	 * Registers a GUIPageUpdateHandler for the player.
	 * This handler gets called every time the GUIPage updates. (Default: 2 ticks)
	 * @param player
	 * @param handler
	 */
	public void registerGUIPageUpdateHandler(Player player, GUIPageUpdateHandler handler)
	{
		guiPageUpdateHandlers.put(player.getUniqueId(), handler);
	}
	
	/**
	 * Removes the GUIPageUpdateHandler for the player.
	 * @param player
	 */
	public void removeGUIPageUpdateHandler(Player player)
	{
		guiPageUpdateHandlers.remove(player.getUniqueId());
	}
	
	/**
	 * Triggers the GUIPageUpdateHandler for the player.
	 * @param player
	 */
	public void triggerGUIPageUpdateHandler(Player player)
	{
		GUIPageUpdateHandler handler = guiPageUpdateHandlers.get(player.getUniqueId());
		if(handler != null)
		{
			handler.onUpdate();
		}
	}
	
	/**
	 * Registers a GUIPageCloseHandler for the player.
	 * This handler gets triggered when the player's gui page gets closed.
	 * @param player
	 * @param pageCloseHandler
	 */
	public void registerPageCloseHandler(Player player, GUIPageCloseHandler pageCloseHandler)
	{
		pageCloseHandlers.put(player.getUniqueId(), pageCloseHandler);
	}
	
	/**
	 * Triggers the GUIPageClose handler for the player.
	 * @param player
	 */
	public void triggerPageCloseHandler(Player player)
	{
		GUIPageCloseHandler pageCloseHandler = pageCloseHandlers.get(player.getUniqueId());
		if(pageCloseHandler != null)
		{
			pageCloseHandler.onPageClose();
		}
	}
	
	/**
	 * Removes the GUIPageCloseHandler for the player.
	 * @param player
	 */
	public void removePageCloseHandler(Player player)
	{
		pageCloseHandlers.remove(player.getUniqueId());
	}
	
	/**
	 * Registers a MousewheelScrollHandler for the player.
	 * This handler gets triggered if the player scrolls their mousewheel while a gui page is open.
	 * @param player
	 * @param handler
	 */
	public void registerMouseWheelScrollHandler(Player player, MousewheelScrollHandler handler)
	{
		mouseWheelScrollHandlers.put(player.getUniqueId(), handler);
	}
	
	/**
	 * Triggers the MousewheelScrollHandlers for the player and passes through the MousewheelAction.
	 * The Mousewheel action contains useful information about the mousewheel scroll event that occurred.
	 * @param player
	 * @param action
	 */
	public void triggerMouseWheelScrollHandler(Player player, MousewheelAction action)
	{
		MousewheelScrollHandler mouseWheelHandler = mouseWheelScrollHandlers.get(player.getUniqueId());
		if(mouseWheelHandler != null)
		{
			mouseWheelHandler.onMousewheelScroll(action);
		}
	}
	
	/**
	 * Removes the MousewheelScrollHandler for the player.
	 * @param player
	 */
	public void removeMouseWheelScrollHandler(Player player)
	{
		mouseWheelScrollHandlers.remove(player.getUniqueId());
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
	
	/**
	 * @param componentId
	 * @return true if the specified componentId exists in this GUIPage.
	 */
	public boolean componentExists(String componentId)
	{
		return guiComponents.containsKey(componentId);
	}
	
	/**
	 * @return A Collection<GUIComponent> of all GUIComponents contained in this GUIPage.
	 */
	public Collection<GUIComponent> getComponents()
	{
		return guiComponents.values();
	}
	
	/**
	 * 
	 * @param componentId
	 * @return The GUIComponent contained in this GUIPage with the given id if it exists.
	 * Returns null if no component exists with the given id.
	 */
	public GUIComponent getComponent(String componentId)
	{
		return guiComponents.get(componentId);
	}
	
	/**
	 * Removes the specified component with the given id from this GUIPage.
	 * @param componentId
	 */
	public void removeComponent(String componentId)
	{
		guiComponents.remove(componentId);
	}
	
	/**
	 * Adds the input GUIComponent to this GUIPage.
	 * @param component
	 */
	public void addComponent(GUIComponent component)
	{
		guiComponents.put(component.getProperties().getId(), component);
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
			components.put(component.getProperties().getId(), playerGUIComponent);
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
			components.put(component.getProperties().getId(), playerGUIComponent);
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