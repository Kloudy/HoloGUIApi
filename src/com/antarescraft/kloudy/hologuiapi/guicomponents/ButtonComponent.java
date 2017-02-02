package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ButtonComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

/*
 * Represents an image based button on a GUI
 */
public class ButtonComponent extends ClickableGUIComponent implements ConfigObject
{	
	@ConfigProperty(key = "<root>")
	private ButtonComponentProperties properties;
	
	private String[][] lines = null;
	private int currentFrame = 0;
	private int frames;
	
	private ButtonComponent(){}
	
	@Override
	public ButtonComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, ButtonComponent.class, plugin);
		} 
		catch (Exception e){}
		
		return null;
	}
	
	@Override
	public PlayerGUITextComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUITextComponent(player, this, updateComponentLines(player));
	}
	
	@Override
	public void updateIncrement()
	{
		currentFrame++;
		if(currentFrame >= frames)
		{
			currentFrame = 0;
		}
	}
	
	@Override
	public String[] updateComponentLines(Player player)
	{
		return lines[currentFrame];
	}
	
	@Override
	public double zoomDistance()
	{
		return 4;
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint18(Point3D origin)
	{
		if(properties.mini)
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x-0.75, origin.y - 3, origin.z-0.75));
		}
		else
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x-1.5, origin.y -  5, origin.z-1.5));
		}
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint18(Point3D origin)
	{
		if(properties.mini)
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x+0.875, origin.y + 0.6, origin.z+0.875));
		}
		else
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x+1.7, origin.y + 0.3, origin.z+1.7));
		}
	}
	
	@Override
	public AABB.Vec3D getMinBoundingRectPoint19(Point3D origin)
	{
		if(properties.mini)
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x-0.75, origin.y - 2, origin.z-0.75));
		}
		else
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x-1.5, origin.y - 3.3, origin.z-1.5));
		}
	}
	
	@Override
	public AABB.Vec3D getMaxBoundingRectPoint19(Point3D origin)
	{
		if(properties.mini)
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x+0.875, origin.y + 0.3, origin.z+0.875));
		}
		else
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x+1.7, origin.y + 0.3, origin.z+1.7));
		}
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
	public double getZoomedInLineHeight()
	{
		return 0.0145;
	}
	
	public String getIcon()
	{
		return properties.icon;
	}
	
	public int getWidth()
	{
		return (properties.mini) ? 9 : 18;
	}
	
	public int getHeight()
	{
		return getWidth();
	}
	
	public boolean isMini()
	{
		return properties.mini;
	}

	public int getFrames()
	{
		return frames;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		lines = plugin.loadImage(properties.icon, getWidth(), getHeight(), properties.symmetrical);
		
		frames = lines.length;
	}
	
	@Override
	public ButtonComponentProperties getProperties()
	{
		return properties;
	}
}