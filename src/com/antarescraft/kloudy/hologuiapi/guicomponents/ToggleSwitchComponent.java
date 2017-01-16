package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.handlers.ToggleHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.StringConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

public class ToggleSwitchComponent extends ClickableGUIComponent implements ConfigObject
{
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "value")
	private boolean defaultState;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-on")
	private String onclickOn;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-off")
	private String onclickOff;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "default-toggle-on.png")
	@ConfigProperty(key = "icon-on")
	private String onIcon;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "default-toggle-off.png")
	@ConfigProperty(key = "icon-off")
	private String offIcon;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-onclick-as-console")
	private boolean executeOnClickOnAsConsole;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-onclick-off-as-console")
	private boolean executeOnClickOffAsConsole;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "on-value")
	private String onValue;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "")
	@ConfigProperty(key = "off-value")
	private String offValue;
	
	private String[][] onLines = null;
	private String[][] offLines = null;
	
	private HashMap<UUID, ToggleHandler> toggleHandlers = new HashMap<UUID, ToggleHandler>();
	private HashMap<UUID, Boolean> playerToggleSwitchStates = new HashMap<UUID, Boolean>();
	
	private ToggleSwitchComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
	@Override
	public ToggleSwitchComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, ToggleSwitchComponent.class, plugin);
		}
		catch(Exception e){}
		
		return null;
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

	@Override
	public void configParseComplete()
	{
		onLines = plugin.loadImage(onIcon, 13, 13, true);
		offLines = plugin.loadImage(offIcon, 13, 13, true);
	}
}