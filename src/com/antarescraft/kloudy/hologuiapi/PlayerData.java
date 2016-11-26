package com.antarescraft.kloudy.hologuiapi;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPageModel;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIValueScrollerComponent;

public class PlayerData 
{
	private static HashMap<UUID, PlayerData> playerData = new HashMap<UUID, PlayerData>();
	
	private Player player;
	private PlayerGUIPage playerGUIPage;
	private PlayerGUIPageModel model;
	private PlayerGUIPageModel prevModel;
	private PlayerGUIPage playerPreviousGUIContainer;
	private PlayerGUIPage playerFocusedContainer;
	private PlayerGUITextBoxComponent textBoxEditor;
	private PlayerGUIValueScrollerComponent valueScrollerEditor;
	private boolean isSneaking;
	
	private PlayerData(Player player)
	{
		this.player = player;
		
		isSneaking = false;
	}
	
	public static PlayerData getPlayerData(Player player)
	{
		PlayerData data = playerData.get(player.getUniqueId());
		if(data == null)
		{
			data = new PlayerData(player);
			playerData.put(player.getUniqueId(), data);
		}
		
		return data;
	}
	
	public static void removePlayerData(Player player)
	{
		playerData.remove(player.getUniqueId());
	}
	
	public static Collection<PlayerData> getAllPlayerData()
	{
		return playerData.values();
	}
	
	public static void removeAllPlayerData()
	{
		playerData.clear();
	}
	
	public UUID getUniqueId()
	{
		return player.getUniqueId();
	}
	
	public PlayerGUIPage getPlayerGUIPage()
	{
		return playerGUIPage;
	}
	
	public void setPlayerGUIPage(PlayerGUIPage playerGUIPage)
	{
		this.playerGUIPage = playerGUIPage;
	}
	
	public PlayerGUIPageModel getPlayerGUIPageModel()
	{
		return model;
	}
	
	public PlayerGUIPageModel getPrevPlayerGUIPageModel()
	{
		return prevModel;
	}
	
	public void setPlayerGUIPageModel(PlayerGUIPageModel model)
	{
		prevModel = this.model;
		this.model = model;
	}
	
	public PlayerGUIPage getPlayerPreviousGUIContainer()
	{
		return playerPreviousGUIContainer;
	}
	
	public void setPlayerPreviousGUIContainer(PlayerGUIPage playerPreviousGUIContainer)
	{
		this.playerPreviousGUIContainer = playerPreviousGUIContainer;
	}
	
	public PlayerGUIPage getPlayerFocusedPage()
	{
		return playerFocusedContainer;
	}
	
	public void setPlayerFocusedPage(PlayerGUIPage playerFocusedContainer)
	{
		this.playerFocusedContainer = playerFocusedContainer;
	}
	
	public PlayerGUITextBoxComponent getTextBoxEditor()
	{
		return textBoxEditor;
	}
	
	public void setPlayerTextBoxEditor(PlayerGUITextBoxComponent textBoxEditor)
	{
		this.textBoxEditor = textBoxEditor;
	}
	
	public void setPlayerValueScrollerEditor(PlayerGUIValueScrollerComponent valueScrollerEditor)
	{
		this.valueScrollerEditor = valueScrollerEditor;
	}
	
	public PlayerGUIValueScrollerComponent getPlayerValueScrollerEditor()
	{
		return valueScrollerEditor;
	}
	
	public boolean isSneaking()
	{
		return isSneaking;
	}
	
	public void setPlayerSneaking(boolean isSneaking)
	{
		this.isSneaking = isSneaking;
	}
}