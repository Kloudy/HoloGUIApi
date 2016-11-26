package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;

public class ButtonComponent extends ClickableGUIComponent
{
	private String icon;
	private String[][] lines;
	private int currentFrame;
	private int frames;
	private boolean mini;
	private int width;
	private int height;
	
	public ButtonComponent(GUIComponentProperties properties, ClickableGUIComponentProperties clickableProperties,
			String[][] lines, String icon,  boolean mini, int width, int height)
	{
		this(properties, clickableProperties, lines, icon, mini, width, height, 0);
	}
	
	public ButtonComponent(GUIComponentProperties properties, ClickableGUIComponentProperties clickableProperties,
			String[][] lines, String icon,  boolean mini, int width, int height, int currentFrame)
	{
		super(properties, clickableProperties);
		
		this.lines = lines;
		this.icon = icon;
		this.mini = mini;
		this.width = width;
		this.height = height;
		this.currentFrame = currentFrame;
	}
	
	@Override
	public ButtonComponent clone()
	{
		String[][] linesCopy = new String[lines.length][lines[0].length];
		for(int i = 0; i < lines.length; i++)
		{
			for(int j = 0; j < lines[i].length; j++)
			{
				linesCopy[i][j] = lines[i][j];
			}
		}
		
		return new ButtonComponent(cloneProperties(), cloneClickableProperties(), linesCopy, icon, mini, width, height);
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
		return width;
	}
	
	public int getHeight()
	{
		return height;
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