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
	
	// Map of player specific gui components only seen on this player gui page.
	// GUI components in this map are not contained in the global gui page that generated this player gui page.
	private HashMap<String, GUIComponent> guiComponents; 
		
	public PlayerGUIPage(Player player, HashMap<String, PlayerGUIComponent> components, Location lookLocation, GUIPage guiPage)
	{
		this.player = player;
		this.components = components;
		this.lookLocation = lookLocation;
		this.guiPage = guiPage;
		
		guiComponents = new HashMap<String, GUIComponent>();
	}
	
	/**
	 * @return The Player associated with this PlayerGUIPage.
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * The anchor point Location is the last Location the player was when they stopped moving.
	 * @return The current anchor point location for this PlayerGUIPage.
	 */
	public Location getLookLocation()
	{
		return lookLocation;
	}
	
	/**
	 * Sets the current look location anchor point for this PlayerGUIPage.
	 * The anchor point Location is the last Location the player was when they stopped moving.
	 * @param lookLocation The anchor point location.
	 */
	public void setLookLocation(Location lookLocation)
	{
		this.lookLocation = lookLocation;
	}
	
	/**
	 * @return The underlying GUIPage being displayed to the player through this PlayerGUIPage.
	 */
	public GUIPage getGUIPage()
	{
		return guiPage;
	}
	
	/**
	 * Updates the incremental state of all of the PlayerGUIComponent objects in this PlayerGUIPage.
	 */
	public void updateIncrement()
	{
		for(GUIComponent guiComponent : guiComponents.values())
		{
			guiComponent.updateIncrement();
		}
	}
	
	/**
	 * @param guiComponent The GUIComponent in question.
	 * @return true if the GUIComponent is currently not visible for the player.
	 * 			Also returns true of the guiComponent does not exist in this PlayerGUIPage.
	 */
	public boolean isHidden(GUIComponent guiComponent)
	{
		return isHidden(guiComponent.getProperties().getId());
	}
	
	/**
	 * @param guiComponentId The id of the GUIComponent in question.
	 * @return true if the GUIComponent is currently visible for the player, otherwise false. 
	 * 			Also returns true if no GUIComponent exists for the given guiComponentId.
	 */
	public boolean isHidden(String guiComponentId)
	{
		PlayerGUIComponent component = components.get(guiComponentId);
		
		return (component == null || (component != null && component.isHidden()));
	}
	
	/**
	 * Renders the GUIComponent for the player.
	 * @param guiComponent The GUIComponent to be rendered for the player.
	 */
	public void renderComponent(GUIComponent guiComponent)
	{
		PlayerGUIComponent existingComponent = components.get(guiComponent.getProperties().getId());
		if(existingComponent != null) 
		{
			removeComponent(guiComponent.getProperties().getId());
		}
		 		 
		PlayerGUIComponent playerGUIComponent = guiComponent.initPlayerGUIComponent(player);
		playerGUIComponent.setHidden(false);
		 
		playerGUIComponent.spawnEntities(lookLocation, (this instanceof StationaryPlayerGUIPage));
		 
		components.put(guiComponent.getProperties().getId(), playerGUIComponent);
		 
		// Don't add to guiComponents if the same guicomponent already exists in the gui page. This would cause it to get updated twice
		if(guiPage.componentExists(guiComponent.getProperties().getId()) && guiPage.getComponent(guiComponent.getProperties().getId()).equals(guiComponent))
		{
			return;
		}
		 
		guiComponents.put(guiComponent.getProperties().getId(), guiComponent);
	}
	
	/**
	 * Removes the GUIComponent from the player's GUI.
	 * @param componentId the id of the GUIComponent being hidden on the player's GUI.
	 */
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
	
	/**
	 * Destroys this PlayerGUIPage.
	 */
	public void destroy()
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		guiPage.triggerPageCloseHandler(player);
		
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
	
	/**
	 * @return A collection of all the PlayerGUIComponents in this PlayerGUIPage.
	 */
	public Collection<PlayerGUIComponent> getPlayerGUIComponents()
	{
		return components.values();
	}
	
	/**
	 * Renders all of the GUIComponents onto the player's GUI.
	 */
	public void renderComponents()
	{
		for(PlayerGUIComponent playerGUIComponent : components.values())
		{
			if(!playerGUIComponent.getGUIComponent().getProperties().isHidden())
			{
				playerGUIComponent.spawnEntities(lookLocation, false);
			}
		}
	}
	
	/**
	 * @return The GUIComponent that the player is currently focused upon. 
	 * 			Returns null if the player is not focused on any component.
	 */
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