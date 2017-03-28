package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.handlers.MousewheelAction;
import com.antarescraft.kloudy.hologuiapi.handlers.MousewheelAction.MousewheelScrollDirection;
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

		MousewheelAction action = new MousewheelAction(event.getPreviousSlot(), event.getNewSlot());
				
		PlayerGUIValueScrollerComponent valueScroller = playerData.getPlayerValueScrollerEditor();
		if(valueScroller != null)
		{
			ValueScrollerComponent valueScrollerComponent = valueScroller.getValueScrollerComponent();
			AbstractScrollValue<?, ?> componentValue = valueScrollerComponent.getPlayerScrollValue(player);
			
			//if((newSlot == prevSlot+1 || newSlot == prevSlot + 2) || (newSlot == 0 && prevSlot == 8))//scroll up
			if(action.getScrollDirection() == MousewheelScrollDirection.LEFT)
			{
				 componentValue.decrement();
				 
				 Sound onscrollSound = valueScroller.getValueScrollerComponent().getConfig().onscrollSound.unwrap();
				 float onscrollSoundVolume = (float)valueScroller.getValueScrollerComponent().getConfig().onscrollSoundVolume;
				 if(valueScroller.getValueScrollerComponent().getConfig().onscrollSound.unwrap() != null)
				 {
					 player.playSound(player.getLocation(), onscrollSound, onscrollSoundVolume, 1);
				 }
				 
				 valueScrollerComponent.triggerScrollHandler(player, componentValue);		 
			}
			//else if((newSlot == prevSlot-1 || newSlot == prevSlot-2) || (newSlot == 8 && prevSlot == 0))//scroll down
			else if(action.getScrollDirection() == MousewheelScrollDirection.RIGHT)
			{
				componentValue.increment();
				
				 Sound onscrollSound = valueScroller.getValueScrollerComponent().getConfig().onscrollSound.unwrap();
				 float onscrollSoundVolume = (float)valueScroller.getValueScrollerComponent().getConfig().onscrollSoundVolume;
				 if(valueScroller.getValueScrollerComponent().getConfig().onscrollSound.unwrap() != null)
				 {
					 player.playSound(player.getLocation(), onscrollSound, onscrollSoundVolume, 1);
				 }
				 
				 valueScrollerComponent.triggerScrollHandler(player, componentValue);
			}
			
			valueScroller.getValueScrollerComponent().setPlayerScrollValue(player, componentValue);
		}
		
		PlayerGUIPage playerGuiPage = PlayerData.getPlayerData(player).getPlayerGUIPage();
		if(playerGuiPage != null)
		{
			GUIPage guiPage = playerGuiPage.getGUIPage();
			
			 // Trigger the mousewheel handler callback.
			guiPage.triggerMouseWheelScrollHandler(player, action);
			
			if(guiPage.getConfig().closeOnPlayerItemSwitch)
			{
				playerGuiPage.destroy();
				playerData.setPlayerGUIPage(null);
				playerData.setPlayerPreviousGUIPage(null);
			}
		}
	}
}