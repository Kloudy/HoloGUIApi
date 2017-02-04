package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.TextBoxComponentProperties;
import com.antarescraft.kloudy.hologuiapi.handlers.TextBoxUpdateHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIPlaceholders;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

import me.clip.placeholderapi.PlaceholderAPI;


public class TextBoxComponent extends ClickableGUIComponent implements ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
	private TextBoxComponentProperties properties;
	
	private HashMap<UUID, String> playerTextBoxValues = new HashMap<UUID, String>();
	private HashMap<UUID, TextBoxUpdateHandler> textboxUpdateHandlers = new HashMap<UUID, TextBoxUpdateHandler>();
	
	private TextBoxComponent(){}
	
	@Override
	public TextBoxComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, TextBoxComponent.class, plugin);
		}
		catch(Exception e){}
		
		return null;
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
		return properties.defaultLine;
	}
	
	public void setDefaultLine(String defaultLine)
	{
		properties.defaultLine = defaultLine;
	}
	
	public boolean evaluatePlaceholders()
	{
		return properties.evaluatePlaceholders;
	}
	
	public void setEvaluatePlaceholders(boolean evaluatePlaceholders)
	{
		properties.evaluatePlaceholders = evaluatePlaceholders;
	}
	
	public void setPlayerTextBoxValue(Player player, String value)
	{
		playerTextBoxValues.put(player.getUniqueId(), value);
	}
	
	public String getPlayerTextBoxValue(Player player)
	{
		String value = properties.defaultLine;
		String v = playerTextBoxValues.get(player.getUniqueId());
		if(v != null)
		{
			value = v;
		}
		
		if(properties.evaluatePlaceholders)
		{
			value = HoloGUIPlaceholders.setHoloGUIPlaceholders(plugin, value, player);
			
			if(HoloGUIApi.hasPlaceholderAPI)
			{
				value = PlaceholderAPI.setPlaceholders(player, value);
			}
			
			PlayerData playerData = PlayerData.getPlayerData(player);
			if(playerData != null) value = HoloGUIPlaceholders.setModelPlaceholders(plugin, playerData.getPlayerGUIPageModel(), value);
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

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
	}
	
	@Override
	public TextBoxComponentProperties getProperties()
	{
		return properties;
	}
}