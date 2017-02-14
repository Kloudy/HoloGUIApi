package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.events.HoloGUIClickEvent;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ClickableGUIComponentProperties;
import com.antarescraft.kloudy.hologuiapi.handlers.ClickHandler;
import com.antarescraft.kloudy.hologuiapi.handlers.HoverHandler;
import com.antarescraft.kloudy.hologuiapi.handlers.HoverOutHandler;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIPlaceholders;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;

import me.clip.placeholderapi.PlaceholderAPI;

public abstract class ClickableGUIComponent extends GUIComponent
{
	private HashMap<UUID, ClickHandler> clickHandlers = new HashMap<UUID, ClickHandler>();
	private HashMap<UUID, HoverHandler> hoverHandlers = new HashMap<UUID, HoverHandler>();
	private HashMap<UUID, HoverOutHandler> hoverOutHandlers = new HashMap<UUID, HoverOutHandler>();
	
	public abstract ClickableGUIComponentProperties getProperties();
	public abstract double getZoomedInLineHeight();
	public abstract double zoomDistance();
	
	//Bounding rects need to be a little different to be accurate in 1.8 vs 1.9
	public abstract AABB.Vec3D getMinBoundingRectPoint18(Point3D origin);
	public abstract AABB.Vec3D getMaxBoundingRectPoint18(Point3D origin);
	public abstract AABB.Vec3D getMinBoundingRectPoint19(Point3D origin);
	public abstract AABB.Vec3D getMaxBoundingRectPoint19(Point3D origin);
	
	public void removePlayerHandlers(Player player)
	{
		clickHandlers.remove(player.getUniqueId());
		hoverHandlers.remove(player.getUniqueId());
		hoverOutHandlers.remove(player.getUniqueId());
	}
		
	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
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
		HoloGUIClickEvent holoGUIClickEvent = new HoloGUIClickEvent(plugin, this, player);
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
					    player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
					} catch (Exception e) {}
				}
			}

			else if(command.matches("\\$model\\.\\w+\\(.*\\);"))//model function trigger
			{
				String onclickSetPlaceholders = new String(command);
				onclickSetPlaceholders = HoloGUIPlaceholders.setHoloGUIPlaceholders(plugin, onclickSetPlaceholders, player);

				if(HoloGUIApi.hasPlaceholderAPI)
				{
					onclickSetPlaceholders = PlaceholderAPI.setPlaceholders(player, command);
				}
				
				HoloGUIPlaceholders.setModelPlaceholders(plugin, PlayerData.getPlayerData(player).getPlayerGUIPageModel(), onclickSetPlaceholders);
			}
			else
			{
				String onclickSetPlaceholders = new String(command);
				
				onclickSetPlaceholders = HoloGUIPlaceholders.setHoloGUIPlaceholders(plugin, onclickSetPlaceholders, player);

				if(HoloGUIApi.hasPlaceholderAPI)
				{
					onclickSetPlaceholders = PlaceholderAPI.setPlaceholders(player, command);
				}
				
				PlayerData playerData = PlayerData.getPlayerData(player);
				if(playerData != null) onclickSetPlaceholders = HoloGUIPlaceholders.setModelPlaceholders(plugin, playerData.getPlayerGUIPageModel(), onclickSetPlaceholders);
				
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