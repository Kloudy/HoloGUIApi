package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.events.HoloGUIClickEvent;
import com.antarescraft.kloudy.hologuiapi.handlers.ClickHandler;
import com.antarescraft.kloudy.hologuiapi.handlers.HoverHandler;
import com.antarescraft.kloudy.hologuiapi.handlers.HoverOutHandler;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIPlaceholders;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

import me.clip.placeholderapi.PlaceholderAPI;

public abstract class ClickableGUIComponent extends GUIComponent
{
	private HashMap<UUID, ClickHandler> clickHandlers;
	private HashMap<UUID, HoverHandler> hoverHandlers;
	private HashMap<UUID, HoverOutHandler> hoverOutHandlers;
	
	protected String onclick;
	protected boolean executeCommandAsConsole;
	protected Sound onclickSound;
	protected float onclickSoundVolume;
	protected double labelZoomDistance;
	protected String clickPermission;
	protected String noPermissionMessage;
	
	public ClickableGUIComponent(GUIComponentProperties properties, ClickableGUIComponentProperties clickableProperties)
	{
		super(properties);
		
		this.onclick = clickableProperties.getOnclickCommand();
		this.executeCommandAsConsole = clickableProperties.executeCommandAsConsole();
		this.onclickSound = clickableProperties.getOnclickSound();
		this.onclickSoundVolume = clickableProperties.getOnclickSoundVolume();
		this.labelZoomDistance = clickableProperties.getLabelZoomDistance();
		this.clickPermission = clickableProperties.getClickPermission();
		this.noPermissionMessage = clickableProperties.getNoPermissionMessage();
		
		clickHandlers = new HashMap<UUID, ClickHandler>();
		hoverHandlers = new HashMap<UUID, HoverHandler>();
		hoverOutHandlers = new HashMap<UUID, HoverOutHandler>();
	}
	
	public ClickableGUIComponentProperties cloneClickableProperties()
	{
		return new ClickableGUIComponentProperties(onclick, executeCommandAsConsole, onclickSound, onclickSoundVolume,
				labelZoomDistance, clickPermission, noPermissionMessage);
	}
	
	public abstract double getZoomedInLineHeight();
	public abstract double zoomDistance();
	
	//Bounding rects need to be a little different to be accurate in 1.8 vs 1.9
	public abstract AABB.Vec3D getMinBoundingRectPoint18(Point3D origin);
	public abstract AABB.Vec3D getMaxBoundingRectPoint18(Point3D origin);
	public abstract AABB.Vec3D getMinBoundingRectPoint19(Point3D origin);
	public abstract AABB.Vec3D getMaxBoundingRectPoint19(Point3D origin);
	
	public String getOnClick()
	{
		return onclick;
	}
	
	public void setOnClick(String onclick)
	{
		this.onclick = onclick;
	}
	
	public Sound getOnclickSound()
	{
		return onclickSound;
	}
	
	public void setOnClickSound(Sound onclickSound)
	{
		this.onclickSound = onclickSound;
	}
	
	public float getOnclickSoundVolume()
	{
		return onclickSoundVolume;
	}
	
	public void setOnclickSoundVolume(float onclickSoundVolume)
	{
		this.onclickSoundVolume = onclickSoundVolume;
	}
	
	public double getLabelZoomDistance()
	{
		return labelZoomDistance;
	}
	
	public void setLabelZoomDistance(double labelZoomDistance)
	{
		this.labelZoomDistance = labelZoomDistance;
	}
	
	public String getClickPermission()
	{
		return clickPermission;
	}
	
	public void setClickPermission(String clickPermission)
	{
		this.clickPermission = clickPermission;
	}
	
	public String getNoPermissionMessage()
	{
		return noPermissionMessage;
	}
	
	public void setNoPermissionMesssage(String noPermissionMessage)
	{
		this.noPermissionMessage = noPermissionMessage;
	}
	
	public void executeOnclick(Player player)
	{
		executeOnclick(player, null, onclick, executeCommandAsConsole);
	}
	
	public void executeOnclick(Player player, String stationaryDisplayId)
	{
		executeOnclick(player, stationaryDisplayId, onclick, executeCommandAsConsole);
	}
	
	public void registerClickHandler(Player player, ClickHandler clickHandler)
	{
		clickHandlers.put(player.getUniqueId(), clickHandler);
	}
	
	public void removeClickHandler(Player player)
	{
		clickHandlers.remove(player.getUniqueId());
	}
	
	public void triggerClickHandler(Player player)
	{
		ClickHandler clickHandler = clickHandlers.get(player.getUniqueId());
		if(clickHandler != null) clickHandler.onClick();
	}
	
	public void registerHoverHandler(Player player, HoverHandler hoverHandler)
	{
		hoverHandlers.put(player.getUniqueId(), hoverHandler);
	}
	
	public void removeHoverHandler(Player player)
	{
		hoverHandlers.remove(player.getUniqueId());
	}
	
	public void triggerHoverHandler(Player player)
	{
		HoverHandler hoverHandler = hoverHandlers.get(player.getUniqueId());
		if(hoverHandler != null) hoverHandler.onHover();
	}
	
	public void registerHoverOutHandler(Player player, HoverOutHandler hoverOutHandler)
	{
		hoverOutHandlers.put(player.getUniqueId(), hoverOutHandler);
	}
	
	public void removeHoverOutHandler(Player player)
	{
		hoverOutHandlers.remove(player.getUniqueId());
	}
	
	public void triggerHoverOutHandler(Player player)
	{
		HoverOutHandler hoverOutHandler = hoverOutHandlers.get(player.getUniqueId());
		if(hoverOutHandler != null) hoverOutHandler.onHoverOut();
	}

	public void executeOnclick(Player player, String stationaryDisplayId, String command, boolean executeCommandAsConsole)
	{
		HoloGUIClickEvent holoGUIClickEvent = new HoloGUIClickEvent(holoGUIPlugin, this, player);
		Bukkit.getServer().getPluginManager().callEvent(holoGUIClickEvent);
		
		if(command != null && !command.equals(""))
		{
			if(command.startsWith("server"))//bungeecord change server command
			{
				if(!executeCommandAsConsole)
				{
					ByteArrayOutputStream b = new ByteArrayOutputStream();
					DataOutputStream out = new DataOutputStream(b);
					 
					try 
					{
					    out.writeUTF("Connect");
					    out.writeUTF(command.split(" ")[1]); // Target Server
					    player.sendPluginMessage(holoGUIPlugin, "BungeeCord", b.toByteArray());
					} catch (Exception e) {}
				}
			}

			else if(command.matches("\\$model\\.\\w+\\(.*\\);"))//model function trigger
			{
				HoloGUIPlaceholders.setModelPlaceholders(holoGUIPlugin, PlayerData.getPlayerData(player).getPlayerGUIPageModel(), command);
			}
			else
			{
				String onclickSetPlaceholders = new String(command);
				if(HoloGUIApi.hasPlaceholderAPI)
				{
					onclickSetPlaceholders = HoloGUIPlaceholders.setHoloGUIPlaceholders(holoGUIPlugin, onclickSetPlaceholders, player);
					onclickSetPlaceholders = PlaceholderAPI.setPlaceholders(player, command);
					
					PlayerData playerData = PlayerData.getPlayerData(player);
					if(playerData != null) onclickSetPlaceholders = HoloGUIPlaceholders.setModelPlaceholders(holoGUIPlugin, playerData.getPlayerGUIPageModel(), onclickSetPlaceholders);
				}
				
				if(stationaryDisplayId != null)
				{
					if(onclickSetPlaceholders.startsWith("hg open") || onclickSetPlaceholders.startsWith("hg back"))
					{
						onclickSetPlaceholders += " " + stationaryDisplayId;
					}
				}
				
				if(executeCommandAsConsole)
				{
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), onclickSetPlaceholders);
				}
				else
				{
					Bukkit.getServer().dispatchCommand(player, onclickSetPlaceholders);
				}	
			}
		}
	}
}