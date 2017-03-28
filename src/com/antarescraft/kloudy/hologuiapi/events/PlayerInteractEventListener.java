package com.antarescraft.kloudy.hologuiapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ClickableGUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.StationaryPlayerGUIPage;

public class PlayerInteractEventListener implements Listener
{
	private HoloGUIApi holoGUI;
	
	public PlayerInteractEventListener(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		final Player player = event.getPlayer();
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			PlayerData playerData = PlayerData.getPlayerData(player);
			
			final PlayerGUIPage focusedPage = playerData.getPlayerFocusedPage();
			PlayerGUIPage playerGUIPage = playerData.getPlayerGUIPage();
						
			if((playerGUIPage != null || focusedPage != null))//player is already viewing a GUI
			{
				if(focusedPage != null && focusedPage.getGUIPage().getHoloGUIPlugin().guiPagesLoaded())
				{
					final PlayerGUIComponent playerGUIComponent = focusedPage.getFocusedComponent();
					
					if(playerGUIComponent != null)
					{
						//component is button
						if(playerGUIComponent.getGUIComponent() instanceof ClickableGUIComponent)
						{
							final boolean stationary = (playerGUIPage instanceof StationaryPlayerGUIPage);
							
							final ClickableGUIComponent clickableGUIComponent = (ClickableGUIComponent) playerGUIComponent.getGUIComponent();
							clickableGUIComponent.triggerClickHandler(player);
							
							playerGUIComponent.unfocusComponent(stationary);
							
							if(clickableGUIComponent.getConfig().onclickSound.unwrap() != null)
							{
								player.playSound(player.getLocation(), clickableGUIComponent.getConfig().onclickSound.unwrap(), (float)clickableGUIComponent.getConfig().onclickSoundVolume, 1);
							}

							//animate button press
							new BukkitRunnable(){
								@Override
								public void run()
								{
									if(clickableGUIComponent.getConfig().clickPermission != null && !player.hasPermission(clickableGUIComponent.getConfig().clickPermission))
									{
										if(clickableGUIComponent.getConfig().noPermissionMessage != null) player.sendMessage(clickableGUIComponent.getConfig().noPermissionMessage);
									}
									else
									{
										if(focusedPage instanceof StationaryPlayerGUIPage)
										{
											StationaryPlayerGUIPage stationaryPlayerGUIContainer = (StationaryPlayerGUIPage) focusedPage;
											clickableGUIComponent.executeOnclick(player, stationaryPlayerGUIContainer.getStationaryGUIDisplayPageId(), clickableGUIComponent.getConfig().onclick, stationary);
											
											if(playerGUIComponent instanceof PlayerGUIToggleSwitchComponent)
											{
												PlayerGUIToggleSwitchComponent playerToggleSwitch = (PlayerGUIToggleSwitchComponent)playerGUIComponent;
												
												ToggleSwitchComponent toggleSwitch = playerToggleSwitch.getToggleSwitchComponent();
												toggleSwitch.swapPlayerToggleSwitchState(player);
												toggleSwitch.triggerToggleHandler(player);
											}
											else if(playerGUIComponent instanceof PlayerGUITextBoxComponent)
											{
												PlayerGUITextBoxComponent textBox = (PlayerGUITextBoxComponent)playerGUIComponent;
												textBox.startEditing();
											}
											else if(playerGUIComponent instanceof PlayerGUIValueScrollerComponent)
											{
												PlayerGUIValueScrollerComponent valueScroller = (PlayerGUIValueScrollerComponent)playerGUIComponent;
												valueScroller.startEditing();
											}
										}
										else
										{
											clickableGUIComponent.executeOnclick(player, null, clickableGUIComponent.getConfig().onclick, stationary);
											
											if(playerGUIComponent instanceof PlayerGUIToggleSwitchComponent)
											{
												PlayerGUIToggleSwitchComponent playerToggleSwitch = (PlayerGUIToggleSwitchComponent)playerGUIComponent;
												
												ToggleSwitchComponent toggleSwitch = playerToggleSwitch.getToggleSwitchComponent();
												toggleSwitch.swapPlayerToggleSwitchState(player);
												toggleSwitch.triggerToggleHandler(player);
											}
											else if(playerGUIComponent instanceof PlayerGUITextBoxComponent)
											{
												PlayerGUITextBoxComponent textBox = (PlayerGUITextBoxComponent)playerGUIComponent;
												textBox.startEditing();
											}
											else if(playerGUIComponent instanceof PlayerGUIValueScrollerComponent)
											{
												PlayerGUIValueScrollerComponent valueScroller = (PlayerGUIValueScrollerComponent)playerGUIComponent;
												valueScroller.startEditing();
											}
										}
									}
									
									if(playerGUIComponent.isFocused())
									{
										playerGUIComponent.focusComponent(stationary);
									}
								}
							}.runTaskLater(holoGUI, 3);
						}
					}
				}
			}
			else//player is not already viewing a gui
			{
				ItemStack item = event.getItem();
				
				for(HoloGUIPlugin holoGUIPlugin : holoGUI.getHookedHoloGUIPlugins())
				{
					for(GUIPage guiPage : holoGUIPlugin.getGUIPages().values())
					{
						if(guiPage.playerHasPermission(player))
						{
							//check to see if item matches the open item
							if(item != null && guiPage.getConfig().openItem.unwrap() != null && item.getType() == guiPage.getConfig().openItem.unwrap().getType() && 
									PlayerData.getPlayerData(player).getPlayerGUIPage() == null)
							{
								//check to see if the display name matches (if it exists)
								if((guiPage.getConfig().itemName != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null &&
										item.getItemMeta().getDisplayName().equals(guiPage.getConfig().itemName)) || guiPage.getConfig().itemName == null)
								{
									if(guiPage.getHoloGUIPlugin().guiPagesLoaded())
									{
										guiPage.renderComponentsForPlayer(player, player.getLocation().clone());
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
}