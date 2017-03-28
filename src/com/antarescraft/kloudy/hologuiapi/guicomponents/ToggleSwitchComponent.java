package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.ToggleSwitchComponentConfig;
import com.antarescraft.kloudy.hologuiapi.handlers.ToggleHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

public class ToggleSwitchComponent extends ClickableGUIComponent
{
	ToggleSwitchComponentConfig config;
	
	private String[][] onLines = null;
	private String[][] offLines = null;
	
	private HashMap<UUID, ToggleHandler> toggleHandlers = new HashMap<UUID, ToggleHandler>();
	private HashMap<UUID, Boolean> playerToggleSwitchStates = new HashMap<UUID, Boolean>();
	
	ToggleSwitchComponent(HoloGUIPlugin plugin, ToggleSwitchComponentConfig config)
	{
		super(plugin);
		
		this.config = config;
		
		onLines = plugin.loadImage(config.onIcon, 13, 13, true);
		offLines = plugin.loadImage(config.offIcon, 13, 13, true);
	}
	
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
			executeOnclick(player, stationaryDisplayId, config.onclickOn, config.executeOnclickOnAsConsole);
		}
		else
		{
			executeOnclick(player, stationaryDisplayId, config.onclickOff, config.executeOnclickOffAsConsole);
		}
	}
	
	public boolean getPlayerToggleSwitchState(Player player)
	{
		boolean state = config.defaultState;
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
	public ToggleSwitchComponentConfig getConfig()
	{
		return config;
	}
}