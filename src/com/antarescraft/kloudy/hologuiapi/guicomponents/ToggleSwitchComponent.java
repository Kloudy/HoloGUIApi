package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.exceptions.InvalidImageException;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ToggleSwitchComponentProperties;
import com.antarescraft.kloudy.hologuiapi.handlers.ToggleHandler;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.ImageOptions;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

public class ToggleSwitchComponent extends ClickableGUIComponent implements ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
	ToggleSwitchComponentProperties properties;
	
	private String[][] onLines = null;
	private String[][] offLines = null;
	
	private HashMap<UUID, ToggleHandler> toggleHandlers = new HashMap<UUID, ToggleHandler>();
	private HashMap<UUID, Boolean> playerToggleSwitchStates = new HashMap<UUID, Boolean>();
	
	private ToggleSwitchComponent(){}
	
	@Override
	public void removePlayerHandlers(Player player)
	{
		super.removePlayerHandlers(player);
		
		toggleHandlers.remove(player.getUniqueId());
	}
	
	public void registerToggleHandler(Player player, ToggleHandler toggleHandler)
	{
		toggleHandlers.put(player.getUniqueId(), toggleHandler);
	}
	
	public void removeToggleHandler(Player player)
	{
		toggleHandlers.remove(player.getUniqueId());
	}
	
	public void triggerToggleHandler(Player player)
	{
		ToggleHandler toggleHandler = toggleHandlers.get(player.getUniqueId());
		if(toggleHandler != null) toggleHandler.onToggle(getPlayerToggleSwitchState(player));
	}
	
	@Override
	public PlayerGUIComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUIToggleSwitchComponent(player, this, updateComponentLines(player));
	}

	@Override
	public void updateIncrement()
	{
		
	}

	@Override
	public String[] updateComponentLines(Player player)
	{
		if(this.getPlayerToggleSwitchState(player)) return onLines[0];
		else return offLines[0];
	}
	
	public void setPlayerToggleSwitchState(Player player, boolean state)
	{
		playerToggleSwitchStates.put(player.getUniqueId(), state);
	}
	
	public void swapPlayerToggleSwitchState(Player player)
	{
		swapPlayerToggleSwitchState(player, null);
	}
	
	public void swapPlayerToggleSwitchState(Player player, String stationaryDisplayId)
	{
		setPlayerToggleSwitchState(player, !getPlayerToggleSwitchState(player));
		
		boolean state = getPlayerToggleSwitchState(player);
		if(state)
		{
			executeOnclick(player, stationaryDisplayId, properties.getOnclickOn(), properties.executeOnclickOnAsConsole());
		}
		else
		{
			executeOnclick(player, stationaryDisplayId, properties.getOnclickOff(), properties.executeOnclickOffAsConsole());
		}
	}
	
	public boolean getPlayerToggleSwitchState(Player player)
	{
		boolean state = properties.getDefaultState();
		if(playerToggleSwitchStates.containsKey(player.getUniqueId()))
		{
			state = playerToggleSwitchStates.get(player.getUniqueId());
		}
		
		return state;
	}
	
	public void removePlayerToggleSwitchState(Player player)
	{
		playerToggleSwitchStates.remove(player.getUniqueId());
	}

	@Override
	public double getZoomedInLineHeight()
	{
		return getLineHeight() + 0.0005;
	}

	@Override
	public double zoomDistance()
	{
		return 4;
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-1.2, origin.y - 2.7, origin.z-1.2));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint18(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+1.2, origin.y + 1.2, origin.z+1.2));
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x-1.2, origin.y - 1.8, origin.z-1.2));
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint19(Point3D origin)
	{
		return AABB.Vec3D.fromVector(new Vector(origin.x+1.2, origin.y + 1.2, origin.z+1.2));
	}
	
	@Override
	public double getLineHeight()
	{
		return 0.014;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		ImageOptions options = new ImageOptions();
		options.width = 13;
		options.height = 13;
		options.symmetrical = true;
		
		try 
		{
			onLines = plugin.loadImage(properties.getOnIcon(), options);
			offLines = plugin.loadImage(properties.getOffIcon(), options);
		}
		catch (InvalidImageException e)
		{
			MessageManager.error(Bukkit.getConsoleSender(), "An error occured while attempting to load the image for GUI component: " + properties.getId());
		}
	}
	
	@Override
	public ToggleSwitchComponentProperties getProperties()
	{
		return properties;
	}
}