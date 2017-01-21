package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
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
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDateFormatException;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDurationFormatException;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;
import com.antarescraft.kloudy.plugincore.time.TimeFormat;

public class ValueScrollerComponent extends ClickableGUIComponent implements ConfigObject
{
	/*private static final double DEFAULT_LABEL_DISTANCE = 10;
	private static final double DEFAULT_LABEL_ZOOM_DISTANCE = 2;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "BLOCK_LAVA_POP")
	@ConfigProperty(key = "onscroll-sound")
	private String onscrollSoundString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0.5, maxValue = 0.0, minValue = 1.0)
	@ConfigProperty(key = "onscroll-sound-volume")
	private double onscrollSoundVolume;
	
	@StringConfigProperty(defaultValue = "", acceptedValues = { "decimal", "integer", "duration", "date", "list" }, acceptedValuesIgnoreCase = true)
	@ConfigProperty(key = "value-type")
	private String valueType;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "default-value")
	private String defaultValueString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "step")
	private String stepString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "min-value")
	private String minValueString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "max-value")
	private String maxValueString;
	
	@OptionalConfigProperty
	@StringConfigProperty(defaultValue = "#.#")
	@ConfigProperty(key = "decimal-format")
	private String decimalFormat;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "wrap")
	private boolean wrap;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "list-items")
	private ArrayList<String> listItems;*/
	
	@ConfigProperty(key = "<root>")
	ValueScrollerComponentProperties properties;
	
	private AbstractScrollValue<?, ?> componentValue;
	
	private HashMap<UUID, ScrollHandler> scrollHandlers = new HashMap<UUID, ScrollHandler>();
	private HashMap<UUID, AbstractScrollValue<?, ?>> playerScrollValues = new HashMap<UUID, AbstractScrollValue<?, ?>>();
	
	private ValueScrollerComponent(HoloGUIPlugin plugin)
	{
		super(plugin);
	}
	
	@Override
	public ValueScrollerComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, ValueScrollerComponent.class, plugin);
		}
		catch(Exception e){}
		
		return null;
	}
	
	public Sound getOnscrollSound()
	{
		try
		{
			return Sound.valueOf(properties.onscrollSoundString);
		}
		catch(Exception e){}
		
		return null;
	}
	
	public void setOnscrollSound(Sound onscrollSound)
	{
		properties.onscrollSoundString = onscrollSound.toString();
	}
	
	public float getOnscrollSoundVolume()
	{
		return (float) properties.onscrollSoundVolume;
	}
	
	public void setOnscrollSoundVolume(float onscrollSoundVolume)
	{
		properties.onscrollSoundVolume = onscrollSoundVolume;
	}
	
	public void setPlayerScrollValue(Player player, AbstractScrollValue<?, ?> value)
	{
		playerScrollValues.put(player.getUniqueId(), value);
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
	public void configParseComplete()
	{
		if(properties.valueType.equals("decimal"))
		{
			double defaultValue = 0;
			double step = 1.0;
			Double minValue = null;
			Double maxValue = null;
			
			try
			{
				defaultValue = Double.parseDouble(properties.defaultValueString);
			}
			catch(NumberFormatException e){}
			
			if(properties.stepString != null)
			{
				try
				{
					step = Double.parseDouble(properties.stepString);
				}
				catch(NumberFormatException e){}
			}
			
			if(properties.minValueString != null) 
			{
				try
				{
					minValue = Double.parseDouble(properties.minValueString);
				}
				catch(NumberFormatException e){}
			}
				
			if(properties.maxValueString != null)
			{
				try
				{
					maxValue = Double.parseDouble(properties.maxValueString);
				}
				catch(NumberFormatException e){}
			}
			
			componentValue = new DoubleScrollValue(defaultValue, step, minValue, maxValue, properties.decimalFormat, properties.wrap);
		}
		else if(properties.valueType.equalsIgnoreCase("integer"))
		{
			int defaultValue = 0;
			int step = 1;
			Integer minValue = null;
			Integer maxValue = null;
			
			try
			{
				defaultValue = Integer.parseInt(properties.defaultValueString);
			}
			catch(NumberFormatException e){}
			
			if(properties.stepString != null)
			{
				try
				{
					step = Integer.parseInt(properties.stepString);
				}
				catch(NumberFormatException e){}
			}
			
			if(properties.minValueString != null) 
			{
				try
				{
					minValue = Integer.parseInt(properties.minValueString);
				}
				catch(NumberFormatException e){}
			}
				
			if(properties.maxValueString != null)
			{
				try
				{
					maxValue = Integer.parseInt(properties.maxValueString);
				}
				catch(NumberFormatException e){}
			}
			
			componentValue = new IntegerScrollValue(defaultValue, step, minValue, maxValue, properties.wrap);
		}
		else if(properties.valueType.equalsIgnoreCase("duration"))
		{
			Duration defaultValue = Duration.ZERO;
			Duration step = Duration.ZERO.plusSeconds(1);
			Duration minValue = null;
			Duration maxValue = null;
			
			try
			{
				defaultValue = TimeFormat.parseDurationFormat(properties.defaultValueString);
			}
			catch(InvalidDurationFormatException e){}
			
			if(properties.stepString != null)
			{
				try
				{
					step = TimeFormat.parseDurationFormat(properties.stepString);
				}
				catch(InvalidDurationFormatException e){}
			}
			
			if(properties.minValueString != null) 
			{
				try
				{
					minValue = TimeFormat.parseDurationFormat(properties.minValueString);
				}
				catch(InvalidDurationFormatException e){}
			}
				
			if(properties.maxValueString != null)
			{
				try
				{
					maxValue = TimeFormat.parseDurationFormat(properties.maxValueString);
				}
				catch(InvalidDurationFormatException e){}
			}
			
			componentValue = new DurationScrollValue(defaultValue, step, minValue, maxValue, properties.wrap);
		}
		else if(properties.valueType.equalsIgnoreCase("date"))
		{
			Calendar defaultValue = Calendar.getInstance();
			Duration step = Duration.ZERO.plusDays(1);
			Calendar minValue = null;
			Calendar maxValue = null;
			
			try
			{
				defaultValue = TimeFormat.parseDateFormat(properties.defaultValueString);
			}
			catch(InvalidDateFormatException e){}
			
			if(properties.stepString != null)
			{
				try
				{
					step = TimeFormat.parseDurationFormat(properties.stepString);
				}
				catch(InvalidDurationFormatException e){}
			}
			
			if(properties.minValueString != null) 
			{
				try
				{
					minValue = TimeFormat.parseDateFormat(properties.minValueString);
				}
				catch(InvalidDateFormatException e){}
			}
				
			if(properties.maxValueString != null)
			{
				try
				{
					maxValue = TimeFormat.parseDateFormat(properties.maxValueString);
				}
				catch(InvalidDateFormatException e){}
			}
			
			componentValue = new DateScrollValue(defaultValue, step, minValue, maxValue, properties.wrap);
		}
		else if(properties.valueType.equalsIgnoreCase("list"))
		{
			if(properties.listItems != null)
			{
				componentValue = new ListScrollValue(properties.listItems);
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