package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;

public class PlayerGUIPage
{
	protected Player player;
	protected GUIPage guiPage;
	protected Location lookLocation;
	protected HashMap<String, PlayerGUIComponent> components;
	
	//map of player specific gui components only seen on this player gui page.
	//gui components in this map are not contained in the global gui page that generated this player gui page.
	private HashMap<String, GUIComponent> guiComponents; 
		
	public PlayerGUIPage(Player player, HashMap<String, PlayerGUIComponent> components, Location lookLocation, GUIPage guiPage)
	{
		this.player = player;
		this.components = components;
		this.lookLocation = lookLocation;
		this.guiPage = guiPage;
		
		guiComponents = new HashMap<String, GUIComponent>();
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Location getLookLocation()
	{
		return lookLocation;
	}
	
	public void setLookLocation(Location lookLocation)
	{
		this.lookLocation = lookLocation;
	}
	
	public GUIPage getGUIPage()
	{
		return guiPage;
	}
	
	public void updateIncrement()
	{
		for(GUIComponent guiComponent : guiComponents.values())
		{
			guiComponent.updateIncrement();
		}
	}
	 
	 public void renderComponent(GUIComponent guiComponent)
	 {
		 PlayerGUIComponent existingComponent = components.get(guiComponent.getProperties().id);
		 if(existingComponent != null) 
		 {
			removeComponent(guiComponent.getProperties().id);
		 }
		 		 
		 PlayerGUIComponent playerGUIComponent = guiComponent.initPlayerGUIComponent(player);
		 playerGUIComponent.setHidden(false);
		 
		 playerGUIComponent.spawnEntities(lookLocation, (this instanceof StationaryPlayerGUIPage));
		 
		 components.put(guiComponent.getProperties().id, playerGUIComponent);
		 
		 //don't add to guiComponents if the same guicomponent already exists in the gui page. This would cause it to get updated twice
		 if(guiPage.componentExists(guiComponent.getProperties().id) && guiPage.getComponent(guiComponent.getProperties().id).equals(guiComponent))
		 {
			 return;
		 }
		 
		 guiComponents.put(guiComponent.getProperties().id, guiComponent);
	 }
	 
	 public void removeComponent(String componentId)
	 {
		 PlayerGUIComponent playerGUIComponent = components.get(componentId);
		 if(playerGUIComponent != null)
		 {
			 playerGUIComponent.setHidden(true);
			 
			 playerGUIComponent.destroyArmorStands();
		 }
		 
		 guiComponents.remove(componentId);
		 components.remove(componentId);
	 }
	
	public void destroy()
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		for(PlayerGUIComponent playerGUIComponent : components.values())
		{
			playerGUIComponent.destroyArmorStands();
		}
		
		PlayerGUIPage focusedPage = playerData.getPlayerFocusedPage();//remove the page as focused if it was focused
		if(focusedPage != null && focusedPage.equals(this))
		{
			playerData.setPlayerFocusedPage(null);
		}
		
		playerData.setPlayerTextBoxEditor(null);
		playerData.setPlayerValueScrollerEditor(null);
	}
	
	public Collection<PlayerGUIComponent> getPlayerGUIComponents()
	{
		return components.values();
	}
	
	public void renderComponents()
	{
		for(PlayerGUIComponent playerGUIComponent : components.values())
		{
			if(!playerGUIComponent.getGUIComponent().getProperties().hidden)
			{
				playerGUIComponent.spawnEntities(lookLocation, false);
			}
		}
	}
	
	public PlayerGUIComponent getFocusedComponent()
	{
		PlayerGUIComponent focusedComponent = null;
		for(PlayerGUIComponent playerGUIComponent : components.values())
		{
			if(playerGUIComponent.isFocused())
			{
				focusedComponent = playerGUIComponent;
				break;
			}
		}
		
		return focusedComponent;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof PlayerGUIPage)
		{
			PlayerGUIPage playerGUIPage = (PlayerGUIPage)obj;
			if(playerGUIPage.getGUIPage().getId().equals(guiPage.getId())) return true;
		}
		return false;
	}
}