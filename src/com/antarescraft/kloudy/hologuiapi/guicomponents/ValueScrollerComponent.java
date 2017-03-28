package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.config.ValueScrollerComponentConfig;
import com.antarescraft.kloudy.hologuiapi.handlers.ScrollHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.AbstractScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.DateScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.DoubleScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.DurationScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.IntegerScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.ListScrollValue;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDateFormatException;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDurationFormatException;
import com.antarescraft.kloudy.plugincore.time.TimeFormat;

public class ValueScrollerComponent extends ClickableGUIComponent
{
	ValueScrollerComponentConfig config;
	
	private AbstractScrollValue<?, ?> componentValue;
	
	private HashMap<UUID, ScrollHandler> scrollHandlers = new HashMap<UUID, ScrollHandler>();
	private HashMap<UUID, AbstractScrollValue<?, ?>> playerScrollValues = new HashMap<UUID, AbstractScrollValue<?, ?>>();
	
	ValueScrollerComponent(HoloGUIPlugin plugin, ValueScrollerComponentConfig config)
	{
		super(plugin);
		
		this.config = config;
		
		if(config.valueType.equals("decimal"))
		{
			double defaultValue = 0;
			double step = 1.0;
			Double minValue = null;
			Double maxValue = null;
			
			try
			{
				defaultValue = Double.parseDouble(config.defaultValue);
			}
			catch(NumberFormatException e){}
			
			if(config.step != null)
			{
				try
				{
					step = Double.parseDouble(config.step);
				}
				catch(NumberFormatException e){}
			}
			
			if(config.minValue != null) 
			{
				try
				{
					minValue = Double.parseDouble(config.minValue);
				}
				catch(NumberFormatException e){}
			}
				
			if(config.maxValue != null)
			{
				try
				{
					maxValue = Double.parseDouble(config.maxValue);
				}
				catch(NumberFormatException e){}
			}
			
			componentValue = new DoubleScrollValue(defaultValue, step, minValue, maxValue, config.decimalFormat, config.wrap);
		}
		else if(config.valueType.equalsIgnoreCase("integer"))
		{
			int defaultValue = 0;
			int step = 1;
			Integer minValue = null;
			Integer maxValue = null;
			
			try
			{
				defaultValue = Integer.parseInt(config.defaultValue);
			}
			catch(NumberFormatException e){}
			
			if(config.step != null)
			{
				try
				{
					step = Integer.parseInt(config.step);
				}
				catch(NumberFormatException e){}
			}
			
			if(config.minValue != null) 
			{
				try
				{
					minValue = Integer.parseInt(config.minValue);
				}
				catch(NumberFormatException e){}
			}
				
			if(config.maxValue != null)
			{
				try
				{
					maxValue = Integer.parseInt(config.maxValue);
				}
				catch(NumberFormatException e){}
			}
			
			componentValue = new IntegerScrollValue(defaultValue, step, minValue, maxValue, config.wrap);
		}
		else if(config.valueType.equalsIgnoreCase("duration"))
		{
			Duration defaultValue = Duration.ZERO;
			Duration step = Duration.ZERO.plusSeconds(1);
			Duration minValue = null;
			Duration maxValue = null;
			
			try
			{
				defaultValue = TimeFormat.parseDurationFormat(config.defaultValue);
			}
			catch(InvalidDurationFormatException e){}
			
			if(config.step != null)
			{
				try
				{
					step = TimeFormat.parseDurationFormat(config.step);
				}
				catch(InvalidDurationFormatException e){}
			}
			
			if(config.minValue != null) 
			{
				try
				{
					minValue = TimeFormat.parseDurationFormat(config.minValue);
				}
				catch(InvalidDurationFormatException e){}
			}
				
			if(config.maxValue != null)
			{
				try
				{
					maxValue = TimeFormat.parseDurationFormat(config.maxValue);
				}
				catch(InvalidDurationFormatException e){}
			}
			
			componentValue = new DurationScrollValue(defaultValue, step, minValue, maxValue, config.wrap);
		}
		else if(config.valueType.equalsIgnoreCase("date"))
		{
			Calendar defaultValue = Calendar.getInstance();
			Duration step = Duration.ZERO.plusDays(1);
			Calendar minValue = null;
			Calendar maxValue = null;
			
			try
			{
				defaultValue = TimeFormat.parseDateFormat(config.defaultValue);
			}
			catch(InvalidDateFormatException e){}
			
			if(config.step != null)
			{
				try
				{
					step = TimeFormat.parseDurationFormat(config.step);
				}
				catch(InvalidDurationFormatException e){}
			}
			
			if(config.minValue != null) 
			{
				try
				{
					minValue = TimeFormat.parseDateFormat(config.minValue);
				}
				catch(InvalidDateFormatException e){}
			}
				
			if(config.maxValue != null)
			{
				try
				{
					maxValue = TimeFormat.parseDateFormat(config.maxValue);
				}
				catch(InvalidDateFormatException e){}
			}
			
			componentValue = new DateScrollValue(defaultValue, step, minValue, maxValue, config.wrap);
		}
		else if(config.valueType.equalsIgnoreCase("list"))
		{
			if(config.listItems != null)
			{
				componentValue = new ListScrollValue(config.listItems);
			}
			else 
			{
				componentValue = new ListScrollValue(new ArrayList<String>());
			}
		}
	}
	
	@Override
	public void removePlayerHandlers(Player player)
	{
		super.removePlayerHandlers(player);
		
		scrollHandlers.remove(player.getUniqueId());
	}
	
	public void setPlayerScrollValue(Player player, AbstractScrollValue<?, ?> value)
	{
		playerScrollValues.put(player.getUniqueId(), value);
	}
	
	public void removePlayerScrollValue(Player player)
	{
		playerScrollValues.remove(player.getUniqueId());
	}
	
	public void registerScrollHandler(Player player, ScrollHandler scrollHandler)
	{
		scrollHandlers.put(player.getUniqueId(), scrollHandler);
	}
	
	public void removeScrollHandler(Player player)
	{
		scrollHandlers.remove(player.getUniqueId());
	}
	
	public void triggerScrollHandler(Player player, AbstractScrollValue<?, ?> value)
	{
		ScrollHandler scrollHandler = scrollHandlers.get(player.getUniqueId());
		if(scrollHandler != null) scrollHandler.onScroll(value);
	}
	
	public AbstractScrollValue<?, ?> getPlayerScrollValue(Player player)
	{
		AbstractScrollValue<?, ?> value = componentValue;
		
		if(playerScrollValues.containsKey(player.getUniqueId()))
		{
			value = playerScrollValues.get(player.getUniqueId());
		}
		
		return value;
	}

	@Override
	public PlayerGUIValueScrollerComponent initPlayerGUIComponent(Player player) 
	{
		return new PlayerGUIValueScrollerComponent(player, this);
	}

	@Override
	public void updateIncrement(){}

	@Override
	public String[] updateComponentLines(Player player)
	{
		return new String[]{getPlayerScrollValue(player).toString()};
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
	public double getLineHeight()
	{
		return (1 / config.getDistance()) * 0.21;
	}
	
	@Override
	public double getZoomedInLineHeight()
	{
		return getLineHeight() + 0.0005;
	}
	
	@Override
	public ValueScrollerComponentConfig getConfig()
	{
		return config;
	}
}