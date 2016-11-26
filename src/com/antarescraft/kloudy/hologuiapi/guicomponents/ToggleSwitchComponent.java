package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.handlers.ToggleHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

public class ToggleSwitchComponent extends ClickableGUIComponent
{
	private HashMap<UUID, ToggleHandler> toggleHandlers;
	
	private boolean defaultState;
	private String onclickOn;
	private String onclickOff;
	private String onIcon;
	private String offIcon;
	private boolean executeOnClickOnAsConsole;
	private boolean executeOnClickOffAsConsole;
	private String[][] onLines;
	private String[][] offLines;
	private String onValue;
	private String offValue;
	
	private HashMap<UUID, Boolean> playerToggleSwitchStates;
	
	public ToggleSwitchComponent(GUIComponentProperties properties, ClickableGUIComponentProperties clickableProperties,
			String[][] onLines, String[][] offLines, boolean defaultState, String onclickOn, String onclickOff, String onIcon, String offIcon, 
			boolean executeOnClickOnAsConsole, boolean executeOnClickOffAsConsole, String onValue, String offValue)
	{
		super(properties, clickableProperties);
		
		toggleHandlers = new HashMap<UUID, ToggleHandler>();
		
		this.onLines = onLines;
		this.offLines = offLines;
		this.defaultState = defaultState;
		this.onclickOn = onclickOn;
		this.onclickOff = onclickOff;
		this.onIcon = onIcon;
		this.offIcon = offIcon;
		this.executeOnClickOnAsConsole = executeOnClickOnAsConsole;
		this.executeOnClickOffAsConsole = executeOnClickOffAsConsole;
		this.onValue = onValue;
		this.offValue = offValue;
		
		playerToggleSwitchStates = new HashMap<UUID, Boolean>();
	}
	
	@Override
	public ToggleSwitchComponent clone()
	{
		String[][] onLinesCopy = new String[onLines.length][onLines[0].length];
		for(int i = 0; i < onLines.length; i++)
		{
			for(int j = 0; j < onLines[i].length; j++)
			{
				onLinesCopy[i][j] = onLines[i][j];
			}
		}
		
		String[][] offLinesCopy = new String[offLines.length][offLines[0].length];
		for(int i = 0; i < offLines.length; i++)
		{
			for(int j = 0; j < offLines[i].length; j++)
			{
				offLinesCopy[i][j] = offLines[i][j];
			}
		}
		return new ToggleSwitchComponent(cloneProperties(), cloneClickableProperties(), onLinesCopy, offLinesCopy, defaultState,
				onclickOn, onclickOff, onIcon, offIcon, executeOnClickOnAsConsole, executeOnClickOffAsConsole, onValue, offValue);
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
		setPlayerToggleSwitchState(player, !getPlayerToggleSwitchState(player));
	}
	
	public boolean getPlayerToggleSwitchState(Player player)
	{
		boolean state = defaultState;
		if(playerToggleSwitchStates.containsKey(player.getUniqueId()))
		{
			state = playerToggleSwitchStates.get(player.getUniqueId());
		}
		
		return state;
	}
	
	public boolean getDefaultState()
	{
		return defaultState;
	}
	
	public void setDefaultState(boolean defaultState)
	{
		this.defaultState = defaultState;
	}
	
	public String getOnclickOn()
	{
		return onclickOn;
	}
	
	public void setOnClickOn(String onclickOn)
	{
		this.onclickOn = onclickOn;
	}
	
	public String getOnclickOff()
	{
		return onclickOff;
	}
	
	public void setOnclickOff(String onclickOff)
	{
		this.onclickOff = onclickOff;
	}
	
	public String getOnIcon()
	{
		return onIcon;
	}
	
	public void setOnIcon(String onIcon)
	{
		this.onIcon = onIcon;
	}
	
	public String getOffIcon()
	{
		return offIcon;
	}
	
	public void setOffIcon(String offIcon)
	{
		this.offIcon = offIcon;
	}
	
	public boolean getExecuteOnClickOnAsConsole()
	{
		return executeOnClickOnAsConsole;
	}
	
	public void setExecuteOnClickOnAsConsole(boolean executeOnClickOnAsConsole)
	{
		this.executeOnClickOnAsConsole = executeOnClickOnAsConsole;
	}
	
	public boolean getExecuteOnClickOffAsConsole()
	{
		return executeOnClickOffAsConsole;
	}
	
	public void setExecuteOnClickOffAsConsole(boolean executeOnClickOffAsConsole)
	{
		this.executeOnClickOffAsConsole = executeOnClickOffAsConsole;
	}
	
	public String getOnValue()
	{
		return onValue;
	}
	
	public void setOnValue(String onValue)
	{
		this.onValue = onValue;
	}
	
	public String getOffValue()
	{
		return offValue;
	}
	
	public void setOffValue(String offValue)
	{
		this.offValue = offValue;
	}

	@Override
	public double getZoomedInLineHeight() 
	{
		return 0.0145;
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
	public double getDisplayDistance()
	{
		return 15;
	}
	
	@Override
	public double getLineHeight()
	{
		return 0.014;
	}
}