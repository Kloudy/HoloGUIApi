package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.objectmapping.ObjectMapper;

public class ButtonComponent extends ClickableGUIComponent
{
	@ConfigProperty(key = "icon")
	private String icon;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "mini")
	private boolean mini;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "symmetrical")
	boolean symmetrical;
	
	private String[][] lines;
	private int currentFrame;
	private int frames;
	
	public ButtonComponent(HoloGUIPlugin plugin, String icon, boolean mini, boolean symmetrical)
	{		
		this.icon = icon;
		this.mini = mini;
		
		lines = plugin.loadImage(icon, getWidth(), getHeight(), symmetrical);
	}
	
	@Override
	public ButtonComponent clone()
	{
		try
		{
			return ObjectMapper.mapObject(this, ButtonComponent.class);
		} 
		catch (Exception e){}
		
		return null;
	}
	
	@Override
	public PlayerGUITextComponent initPlayerGUIComponent(Player player)
	{
		//lines = ResourceManager.getInstance().getImageLines(icon + ":" + width + "," + height);
		frames = lines.length;
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
		if(mini)
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
		if(mini)
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
		if(mini)
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
		if(mini)
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
		return icon;
	}
	
	public int getWidth()
	{
		return (mini) ? 9 : 18;
	}
	
	public int getHeight()
	{
		return (mini) ? 9 : 18;
	}
	
	public boolean isMini()
	{
		return mini;
	}

	public int getFrames()
	{
		return frames;
	}
}