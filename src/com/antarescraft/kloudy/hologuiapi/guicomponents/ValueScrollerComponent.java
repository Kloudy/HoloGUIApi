package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.handlers.ScrollHandler;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.AbstractScrollValue;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

public class ValueScrollerComponent extends ClickableGUIComponent
{
	private HashMap<UUID, ScrollHandler> scrollHandlers;
	
	private Sound onscrollSound;
	private float onscrollSoundVolume;
	private AbstractScrollValue<?, ?> componentValue;
	
	private HashMap<UUID, AbstractScrollValue<?, ?>> playerScrollValues;
	
	public ValueScrollerComponent(GUIComponentProperties properties, ClickableGUIComponentProperties clickableProperties,
			Sound onscrollSound, float onscrollSoundVolume, AbstractScrollValue<?, ?> componentValue) 
	{
		super(properties, clickableProperties);
		
		scrollHandlers = new HashMap<UUID, ScrollHandler>();
		
		this.onscrollSound = onscrollSound;
		this.onscrollSoundVolume = onscrollSoundVolume;
		this.componentValue = componentValue;
		
		playerScrollValues = new HashMap<UUID, AbstractScrollValue<?, ?>>();
	}
	
	@Override
	public ValueScrollerComponent clone()
	{
		return new ValueScrollerComponent(cloneProperties(), cloneClickableProperties(), onscrollSound, onscrollSoundVolume, componentValue.clone());
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
}