package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.handlers.GUIPageCloseHandler;
import com.antarescraft.kloudy.hologuiapi.handlers.GUIPageLoadHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.StationaryPlayerGUIPage;

public class GUIPage
{
	private HashMap<UUID, GUIPageLoadHandler> pageLoadHandlers;
	private HashMap<UUID, GUIPageCloseHandler> pageCloseHandlers;
	
	private HashMap<String, GUIComponent> guiComponents;
		
	private HoloGUIPlugin holoGUIPlugin;
	private String id;
	private String configFilename;
	private ItemStack openItem;
	private String itemName;
	private boolean openOnLogin;
	private String showPermission;
	private String hidePermission;
	private boolean closeOnPlayerMove;
	private boolean closeOnPlayerItemSwitch;
	
	public GUIPage(HoloGUIPlugin holoGUIPlugin, HashMap<String, GUIComponent> guiComponents, String id, String configFilename, 
			ItemStack openItem, String itemName, boolean openOnLogin, String seePermission, String hidePermission, boolean closeOnPlayerMove, 
			boolean closeOnPlayerItemSwitch)
	{
		this.holoGUIPlugin = holoGUIPlugin;
		this.guiComponents = guiComponents;
		this.id = id;
		this.configFilename = configFilename;
		this.openItem = openItem;
		this.itemName = itemName;
		this.openOnLogin = openOnLogin;
		this.showPermission = seePermission;
		this.hidePermission = hidePermission;
		this.closeOnPlayerMove = closeOnPlayerMove;
		this.closeOnPlayerItemSwitch = closeOnPlayerItemSwitch;
		
		pageLoadHandlers = new HashMap<UUID, GUIPageLoadHandler>();
		pageCloseHandlers = new HashMap<UUID, GUIPageCloseHandler>();
	}
	
	public GUIPage(String id)
	{
		this.id = id;
		this.configFilename = id + ".yml";
	}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return holoGUIPlugin;
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
			components.put(component.getId(), playerGUIComponent);
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
			components.put(component.id, playerGUIComponent);
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
	
	public String getConfigFilename()
	{
		return configFilename;
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
}