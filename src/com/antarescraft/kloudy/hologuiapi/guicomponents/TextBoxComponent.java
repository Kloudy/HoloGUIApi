package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.handlers.TextBoxUpdateHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIPlaceholders;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

import me.clip.placeholderapi.PlaceholderAPI;


public class TextBoxComponent extends ClickableGUIComponent
{
	private HashMap<UUID, TextBoxUpdateHandler> textboxUpdateHandlers;
	
	private String defaultLine;
	private boolean evaluatePlaceholders;
	
	private HashMap<UUID, String> playerTextBoxValues;
	
	public TextBoxComponent(GUIComponentProperties properties, ClickableGUIComponentProperties clickableProperties,
			String defaultLine, boolean evaluatePlaceholders) 
	{
		super(properties, clickableProperties);
		
		textboxUpdateHandlers = new HashMap<UUID, TextBoxUpdateHandler>();
		
		this.defaultLine = defaultLine;
		this.evaluatePlaceholders = evaluatePlaceholders;
		
		playerTextBoxValues = new HashMap<UUID, String>();
	}
	
	@Override
	public TextBoxComponent clone()
	{
		return new TextBoxComponent(cloneProperties(), cloneClickableProperties(), defaultLine, evaluatePlaceholders);
	}
	
	public void registerTextBoxUpdateHandler(Player player, TextBoxUpdateHandler textboxUpdateHandler)
	{
		textboxUpdateHandlers.put(player.getUniqueId(), textboxUpdateHandler);
	}
	
	public void triggerTextBoxUpdateHandler(Player player, String value)
	{
		TextBoxUpdateHandler textboxUpdateHandler = textboxUpdateHandlers.get(player.getUniqueId());
		if(textboxUpdateHandler != null) textboxUpdateHandler.onUpdate(value);
	}
	
	public void removeTextBoxUpdateHandler(Player player)
	{
		textboxUpdateHandlers.remove(player.getUniqueId());
	}
	
	public String getDefaultValue()
	{
		return defaultLine;
	}
	
	public void setDefaultLine(String defaultLine)
	{
		this.defaultLine = defaultLine;
	}
	
	public boolean evaluatePlaceholders()
	{
		return evaluatePlaceholders;
	}
	
	public void setEvaluatePlaceholders(boolean evaluatePlaceholders)
	{
		this.evaluatePlaceholders = evaluatePlaceholders;
	}
	
	public void setPlayerTextBoxValue(Player player, String value)
	{
		playerTextBoxValues.put(player.getUniqueId(), value);
	}
	
	public String getPlayerTextBoxValue(Player player)
	{
		String value = defaultLine;
		String v = playerTextBoxValues.get(player.getUniqueId());
		if(v != null)
		{
			value = v;
		}
		
		if(evaluatePlaceholders)
		{
			value = HoloGUIPlaceholders.setHoloGUIPlaceholders(holoGUIPlugin, value, player);
			value = PlaceholderAPI.setPlaceholders(player, value);
			
			PlayerData playerData = PlayerData.getPlayerData(player);
			if(playerData != null) value = HoloGUIPlaceholders.setModelPlaceholders(holoGUIPlugin, playerData.getPlayerGUIPageModel(), value);
		}
		
		return value;
	}

	@Override
	public double zoomDistance()
	{
		return 2;
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-1.75, origin.y - 2, origin.z-1.75));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+1.875, origin.y + 1, origin.z+1.875));
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-1, origin.y+0.2, origin.z-1));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+1, origin.y + 1, origin.z+1));
	}
	
	@Override
	public double getDisplayDistance()
	{
		return 10;
	}
	
	@Override
	public double getLineHeight()
	{
		return 0.023;
	}
	
	@Override
	public double getZoomedInLineHeight()
	{
		return 0.018;
	}

	@Override
	public PlayerGUITextBoxComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUITextBoxComponent(player, this);
	}

	@Override
	public void updateIncrement() 
	{

	}

	@Override
	public String[] updateComponentLines(Player player) 
	{
		return new String[]{playerTextBoxValues.get(player.getUniqueId())};
	}
}