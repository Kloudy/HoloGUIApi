package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ValueScrollerComponentProperties;
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
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDateFormatException;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDurationFormatException;
import com.antarescraft.kloudy.plugincore.time.TimeFormat;

public class ValueScrollerComponent extends ClickableGUIComponent implements ConfigObject
{
	@ConfigElement
	@ConfigProperty(key = "")
	ValueScrollerComponentProperties properties;
	
	private AbstractScrollValue<?, ?> componentValue;
	
	private HashMap<UUID, ScrollHandler> scrollHandlers = new HashMap<UUID, ScrollHandler>();
	private HashMap<UUID, AbstractScrollValue<?, ?>> playerScrollValues = new HashMap<UUID, AbstractScrollValue<?, ?>>();
	
	private ValueScrollerComponent(){}
	
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
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		if(properties.getValueType().equals("decimal"))
		{
			double defaultValue = 0;
			double step = 1.0;
			Double minValue = null;
			Double maxValue = null;
			
			try
			{
				defaultValue = Double.parseDouble(properties.getDefaultValue());
			}
			catch(NumberFormatException e){}
			
			if(properties.getStep() != null)
			{
				try
				{
					step = Double.parseDouble(properties.getStep());
				}
				catch(NumberFormatException e){}
			}
			
			if(properties.getMinValue() != null) 
			{
				try
				{
					minValue = Double.parseDouble(properties.getMinValue());
				}
				catch(NumberFormatException e){}
			}
				
			if(properties.getMaxValue() != null)
			{
				try
				{
					maxValue = Double.parseDouble(properties.getMaxValue());
				}
				catch(NumberFormatException e){}
			}
			
			componentValue = new DoubleScrollValue(defaultValue, step, minValue, maxValue, properties.getDecimalFormat(), properties.wrap());
		}
		else if(properties.getValueType().equalsIgnoreCase("integer"))
		{
			int defaultValue = 0;
			int step = 1;
			Integer minValue = null;
			Integer maxValue = null;
			
			try
			{
				defaultValue = Integer.parseInt(properties.getDefaultValue());
			}
			catch(NumberFormatException e){}
			
			if(properties.getStep() != null)
			{
				try
				{
					step = Integer.parseInt(properties.getStep());
				}
				catch(NumberFormatException e){}
			}
			
			if(properties.getMinValue() != null) 
			{
				try
				{
					minValue = Integer.parseInt(properties.getMinValue());
				}
				catch(NumberFormatException e){}
			}
				
			if(properties.getMaxValue() != null)
			{
				try
				{
					maxValue = Integer.parseInt(properties.getMaxValue());
				}
				catch(NumberFormatException e){}
			}
			
			componentValue = new IntegerScrollValue(defaultValue, step, minValue, maxValue, properties.wrap());
		}
		else if(properties.getValueType().equalsIgnoreCase("duration"))
		{
			Duration defaultValue = Duration.ZERO;
			Duration step = Duration.ZERO.plusSeconds(1);
			Duration minValue = null;
			Duration maxValue = null;
			
			try
			{
				defaultValue = TimeFormat.parseDurationFormat(properties.getDefaultValue());
			}
			catch(InvalidDurationFormatException e){}
			
			if(properties.getStep() != null)
			{
				try
				{
					step = TimeFormat.parseDurationFormat(properties.getStep());
				}
				catch(InvalidDurationFormatException e){}
			}
			
			if(properties.getMinValue() != null) 
			{
				try
				{
					minValue = TimeFormat.parseDurationFormat(properties.getMinValue());
				}
				catch(InvalidDurationFormatException e){}
			}
				
			if(properties.getMaxValue() != null)
			{
				try
				{
					maxValue = TimeFormat.parseDurationFormat(properties.getMaxValue());
				}
				catch(InvalidDurationFormatException e){}
			}
			
			componentValue = new DurationScrollValue(defaultValue, step, minValue, maxValue, properties.wrap());
		}
		else if(properties.getValueType().equalsIgnoreCase("date"))
		{
			Calendar defaultValue = Calendar.getInstance();
			Duration step = Duration.ZERO.plusDays(1);
			Calendar minValue = null;
			Calendar maxValue = null;
			
			try
			{
				defaultValue = TimeFormat.parseDateFormat(properties.getDefaultValue());
			}
			catch(InvalidDateFormatException e){}
			
			if(properties.getStep() != null)
			{
				try
				{
					step = TimeFormat.parseDurationFormat(properties.getStep());
				}
				catch(InvalidDurationFormatException e){}
			}
			
			if(properties.getMinValue() != null) 
			{
				try
				{
					minValue = TimeFormat.parseDateFormat(properties.getMinValue());
				}
				catch(InvalidDateFormatException e){}
			}
				
			if(properties.getMaxValue() != null)
			{
				try
				{
					maxValue = TimeFormat.parseDateFormat(properties.getMaxValue());
				}
				catch(InvalidDateFormatException e){}
			}
			
			componentValue = new DateScrollValue(defaultValue, step, minValue, maxValue, properties.wrap());
		}
		else if(properties.getValueType().equalsIgnoreCase("list"))
		{
			if(properties.getListItems() != null)
			{
				componentValue = new ListScrollValue(properties.getListItems());
			}
			else 
			{
				componentValue = new ListScrollValue(new ArrayList<String>());
			}
		}
	}
	
	@Override
	public ValueScrollerComponentProperties getProperties()
	{
		return properties;
	}
}