package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.handlers.ScrollHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.AbstractScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.DoubleScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.IntegerScrollValue;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.config.StringConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

public class ValueScrollerComponent extends ClickableGUIComponent implements ConfigObject
{
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
	
	@ConfigProperty(key = "default-value")
	private String defaultValue;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "step")
	private String step;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "min-value")
	private String minValueString;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "max-value")
	private String maxValueString;
	
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
		return onscrollSound;
	}
	
	public void setOnscrollSound(Sound onscrollSound)
	{
		this.onscrollSound = onscrollSound;
	}
	
	public float getOnscrollSoundVolume()
	{
		return onscrollSoundVolume;
	}
	
	public void setOnscrollSoundVolume(float onscrollSoundVolume)
	{
		this.onscrollSoundVolume = onscrollSoundVolume;
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
	public void updateIncrement()
	{

	}

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
	public void configParseComplete(ConfigurationSection section)
	{
		if(valueType.equals("decimal"))
		{
			double defaultValue = section.getDouble("default-value", 0);
			double step = section.getDouble("step", 1);
			String decimalFormat = section.getString("decimal-format", "#.#");
			
			if(minValue != null) 
			if(section.isSet(MAX_VALUE))maxValue = section.getDouble(MAX_VALUE, 0);
			
			componentValue = new DoubleScrollValue(defaultValue, step, minValue, maxValue, decimalFormat, wrap);
		}
		else if(valueType.equalsIgnoreCase("integer"))
		{
			
		}
		else if(valueType.equalsIgnoreCase("duration"))
		{
			
		}
		else if(valueType.equalsIgnoreCase("date"))
		{
			
		}
		else if(valueType.equalsIgnoreCase("list"))
		{
			
		}
	}
}