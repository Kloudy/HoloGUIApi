package com.antarescraft.kloudy.hologuiapi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponents.ClickableGUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.StationaryPlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIApiConfig;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

public class PlayerGUIUpdateTask extends BukkitRunnable
{	
	private static PlayerGUIUpdateTask instance;
	
	/**
	 * Loops every 2 ticks to see if a player is looking at a GUIComponent and update GUI lines
	 */
	
	private HoloGUIApi holoGUI;
	private boolean running;
	private Hashtable<UUID, Location> playerPrevLocations;
		
	private PlayerGUIUpdateTask(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
		
		running = false;
				
		playerPrevLocations = new Hashtable<UUID, Location>();
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	public static PlayerGUIUpdateTask getInstance(HoloGUIApi holoGUI)
	{
		if(instance == null)
		{
			instance = new PlayerGUIUpdateTask(holoGUI);
		}
		
		return instance;
	}

	public void start(int tickrate)
	{
		runTaskTimer(holoGUI, 0, tickrate);
		
		running = true;
	}

	@Override
	public void run()
	{
		boolean foundIntersection = false;
		
		Hashtable<UUID, Location> playerTempPrevLocations = new Hashtable<UUID, Location>(playerPrevLocations);	
		
		try
		{
			//update all the guiComponents incremental state
			for(HoloGUIPlugin holoGUIPlugin : holoGUI.getHookedHoloGUIPlugins())
			{
				if(holoGUIPlugin.guiPagesLoaded())
				{
					for(GUIPage guiPage : holoGUIPlugin.getGUIPages().values())
					{
						guiPage.updateIncrement();
					}
				}
			}			

			ArrayList<PlayerGUIPage> playerGUIPages = new ArrayList<PlayerGUIPage>();
			
			for(PlayerData playerData : PlayerData.getAllPlayerData())
			{
				PlayerGUIPage playerGUIPage = playerData.getPlayerGUIPage();
				if(playerGUIPage != null)
				{
					playerGUIPage.updateIncrement();
					playerGUIPages.add(playerGUIPage);
					
					// Trigger the gui page update callbacks
					playerGUIPage.getGUIPage().triggerGUIPageUpdateHandler(playerGUIPage.getPlayer());
				}
			}
			
			for(StationaryGUIDisplayContainer stationaryDisplay : holoGUI.getStationaryDisplays())
			{
				playerGUIPages.addAll(stationaryDisplay.getPlayerGUIContainers());
				
				//update stationary component lines
				for(PlayerGUIPage playerGUIContainer : stationaryDisplay.getPlayerGUIContainers())
				{
					for(PlayerGUIComponent playerGUIComponent : playerGUIContainer.getPlayerGUIComponents())
					{
						playerGUIComponent.updateComponentLines();
					}
				}
			}
						
			ArrayList<Player> playersToRemove = new ArrayList<Player>();
			
			for(PlayerGUIPage playerGUIPage : playerGUIPages)
			{
				Player player = playerGUIPage.getPlayer();
				boolean updateLocation = updateGUILocation(player);
				boolean stationary = false;
				
				if(playerGUIPage instanceof StationaryPlayerGUIPage)
				{
					stationary = true;
					Location containerLoc = playerGUIPage.getLookLocation();
					Location playerLoc = player.getLocation();
					
					double xi = containerLoc.getX() - playerLoc.getX();
					double yi = containerLoc.getY() - playerLoc.getY();
					double zi = containerLoc.getZ() - playerLoc.getZ();
					Vector vect = new Vector(xi, yi , zi).normalize();
					
					double xf = containerLoc.getX();
					double yf = containerLoc.getY();
					double zf = containerLoc.getZ();
					
					Location lookLocation = new Location(containerLoc.getWorld(), xf, yf, zf);
					lookLocation.setDirection(vect);
					
					playerGUIPage.setLookLocation(lookLocation);
				}
				else
				{
					if(updateLocation)
					{
						if(playerGUIPage.getGUIPage().getCloseOnPlayerMove())
						{
							playerGUIPage.destroy();
							playersToRemove.add(playerGUIPage.getPlayer());
							playerTempPrevLocations.remove(playerGUIPage.getPlayer().getUniqueId());
							
							continue;
						}
						else
						{
							playerGUIPage.setLookLocation(player.getLocation().clone());
						}
					}
				}

				//iterating over a new array list because the hover handlers could cause the list to get updated.
				//This avoid a ConcurrentModificationException on the collection
				for(PlayerGUIComponent playerGUIComponent : new ArrayList<PlayerGUIComponent>(playerGUIPage.getPlayerGUIComponents()))
				{
					if(playerGUIComponent.isHidden())
					{
						continue;
					}
					
					if(updateLocation)//moves the GUI with the player
					{
						playerGUIComponent.updateLocation(playerGUIPage.getLookLocation(), stationary);
					}

					if(!foundIntersection && playerGUIComponent.getGUIComponent() instanceof ClickableGUIComponent)
					{
						Vector dir = playerGUIComponent.getPlayer().getLocation().getDirection().multiply(HoloGUIApiConfig.stationaryDisplayRenderDistance());
						Point3D point = new Point3D(playerGUIComponent.getLocation());
						
						AABB.Vec3D min = null;
						AABB.Vec3D max = null;
						
						ClickableGUIComponent clickableGUIComponent = (ClickableGUIComponent)playerGUIComponent.getGUIComponent();
						
						if(Bukkit.getVersion().contains("1.8"))
						{
							min = clickableGUIComponent.getMinBoundingRectPoint18(point);
							max = clickableGUIComponent.getMaxBoundingRectPoint18(point);
						}
						else
						{
							min = clickableGUIComponent.getMinBoundingRectPoint19(point);
							max = clickableGUIComponent.getMaxBoundingRectPoint19(point);
						}
						
						AABB.Vec3D origin = AABB.Vec3D.fromLocation(player.getLocation());
						AABB.Vec3D direction = AABB.Vec3D.fromVector(dir);
						AABB.Ray3D ray = new AABB.Ray3D(origin, direction);
						
						AABB boundingBox = new AABB(min, max);
												
						if(boundingBox.intersectsRay(ray, 0f, (float)HoloGUIApiConfig.stationaryDisplayRenderDistance()) != null && !playerGUIComponent.isFocused() && playerGUIPage.getFocusedComponent() == null)//check to see if player is looking at the component
						{
							playerGUIComponent.setIsFocused(true);
							playerGUIComponent.focusComponent(stationary);
							foundIntersection = true;
							PlayerData.getPlayerData(player).setPlayerFocusedPage(playerGUIPage);
							
							//trigger hover handler for component if it exists
							clickableGUIComponent.triggerHoverHandler(player);
						}
						else if(boundingBox.intersectsRay(ray, 0f, (float)HoloGUIApiConfig.stationaryDisplayRenderDistance()) == null && playerGUIComponent.isFocused())
						{
							playerGUIComponent.unfocusComponent(stationary);
							playerGUIComponent.setIsFocused(false);
							PlayerData.getPlayerData(player).setPlayerFocusedPage(null);
							
							//trigger hover out handler for component if it exists
							clickableGUIComponent.triggerHoverOutHandler(player);
						}
					}
					
					if(!stationary)//stationary components have already been updated
					{
						playerGUIComponent.updateComponentLines();
					}
				}
				playerTempPrevLocations.put(playerGUIPage.getPlayer().getUniqueId(), playerGUIPage.getPlayer().getLocation().clone());
			}
			
			playerPrevLocations = new Hashtable<UUID, Location>(playerTempPrevLocations);
			
			for(Player player : playersToRemove)
			{
				PlayerData.getPlayerData(player).setPlayerGUIPage(null);
			}
			
			playersToRemove.clear();
		}
		catch(Exception e){e.printStackTrace();this.cancel();}		
	}
	
	private boolean updateGUILocation(Player player)
	{
		boolean updateGUILocation = false;
		
		Location prevLocation = playerPrevLocations.get(player.getUniqueId());
		if(prevLocation != null)
		{
			Location location = player.getLocation();
			if(Math.abs(location.getX() - prevLocation.getX()) > 0.04 || Math.abs(location.getY() - prevLocation.getY()) > 0.04 || Math.abs(location.getZ() - prevLocation.getZ()) > 0.04)
			{
				updateGUILocation = true;
			}
		}
		
		return updateGUILocation;
	}
}