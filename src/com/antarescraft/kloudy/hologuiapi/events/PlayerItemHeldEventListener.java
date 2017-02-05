package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.AbstractScrollValue;

public class PlayerItemHeldEventListener implements Listener
{
	@EventHandler
	public void playerItemHeldEvent(PlayerItemHeldEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		int prevSlot = event.getPreviousSlot();
		int newSlot = event.getNewSlot();
				
		PlayerGUIValueScrollerComponent valueScroller = playerData.getPlayerValueScrollerEditor();
		if(valueScroller != null)
		{
			ValueScrollerComponent valueScrollerComponent = valueScroller.getValueScrollerComponent();
			AbstractScrollValue<?, ?> componentValue = valueScrollerComponent.getPlayerScrollValue(player);
			
			if((newSlot == prevSlot+1 || newSlot == prevSlot + 2) || (newSlot == 0 && prevSlot == 8))//scroll up
			{
				 componentValue.decrement();
				 
				 Sound onscrollSound = valueScroller.getValueScrollerComponent().getProperties().getOnscrollSound();
				 float onscrollSoundVolume = valueScroller.getValueScrollerComponent().getProperties().getOnscrollSoundVolume();
				 if(valueScroller.getValueScrollerComponent().getProperties().getOnscrollSound() != null)
				 {
					 player.playSound(player.getLocation(), onscrollSound, onscrollSoundVolume, 1);
				 }
				 
				 valueScrollerComponent.triggerScrollHandler(player, componentValue);
				 
				 HoloGUIValueScrollerUpdateEvent valueScrollerUpdateEvent = new HoloGUIValueScrollerUpdateEvent(valueScrollerComponent.getHoloGUIPlugin(), valueScrollerComponent, player);
				 Bukkit.getPluginManager().callEvent(valueScrollerUpdateEvent);				 
			}
			else if((newSlot == prevSlot-1 || newSlot == prevSlot-2) || (newSlot == 8 && prevSlot == 0))//scroll down
			{
				componentValue.increment();
				
				 Sound onscrollSound = valueScroller.getValueScrollerComponent().getProperties().getOnscrollSound();
				 float onscrollSoundVolume = valueScroller.getValueScrollerComponent().getProperties().getOnscrollSoundVolume();
				 if(valueScroller.getValueScrollerComponent().getProperties().getOnscrollSound() != null)
				 {
					 player.playSound(player.getLocation(), onscrollSound, onscrollSoundVolume, 1);
				 }
				 
				 valueScrollerComponent.triggerScrollHandler(player, componentValue);
			}
			
			valueScroller.getValueScrollerComponent().setPlayerScrollValue(player, componentValue);
		}
		
		PlayerGUIPage playerGUIContainer = PlayerData.getPlayerData(player).getPlayerGUIPage();
		if(playerGUIContainer != null)
		{
			if(playerGUIContainer.getGUIPage().getCloseOnPlayerItemSwitch())
			{
				playerGUIContainer.destroy();
				playerData.setPlayerGUIPage(null);
				playerData.setPlayerPreviousGUIContainer(null);
			}
		}
	}
}