package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.exceptions.InvalidImageException;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.ButtonComponentProperties;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.ImageOptions;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.hologuiapi.util.AABB;
import com.antarescraft.kloudy.hologuiapi.util.Point3D;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

/*
 * Represents an image based button on a GUI
 */
public class ButtonComponent extends ClickableGUIComponent implements ConfigObject
{	
	@ConfigElement
	@ConfigProperty(key = "")
	private ButtonComponentProperties properties;
	
	private String[][] lines = null;
	private int currentFrame = 0;
	
	private ButtonComponent(){}
	
	@Override
	public PlayerGUITextComponent initPlayerGUIComponent(Player player)
	{
		return new PlayerGUITextComponent(player, this, updateComponentLines(player));
	}
	
	public void setLines(String[][] lines)
	{
		this.lines = lines;
		
		currentFrame = 0;
	}
	
	@Override
	public void updateIncrement()
	{
		currentFrame = (currentFrame + 1) % lines.length;
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
		if(properties.isMini())
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
		if(properties.isMini())
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
		if(properties.isMini())
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
		if(properties.isMini())
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x+0.875, origin.y + 0.3, origin.z+0.875));
		}
		else
		{
			return AABB.Vec3D.fromVector(new Vector(origin.x+1.7, origin.y + 0.3, origin.z+1.7));
		}
	}
	
	/*@Override
	public double getDisplayDistance()
	{
		return 15;
	}*/
	
	@Override
	public double getLineHeight()
	{
		return (1 / properties.getDistance()) * 0.21;
	}
	
	@Override
	public double getZoomedInLineHeight()
	{
		return getLineHeight() + 0.0005;
	}
	
	public int getWidth()
	{
		return (properties.isMini()) ? 9 : 18;
	}
	
	public int getHeight()
	{
		return getWidth();
	}

	public int getFrames()
	{
		return lines.length;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		ImageOptions options = new ImageOptions();
		options.width = getWidth();
		options.height = getHeight();
		options.symmetrical = properties.isSymmetrical();
		
		try 
		{
			lines = plugin.loadImage(properties.getIcon(), options);
		} 
		catch (InvalidImageException e) 
		{
			MessageManager.error(Bukkit.getConsoleSender(), "An error occured while attempting to load the image for GUI component: " + properties.getId());
		}
	}
	
	@Override
	public ButtonComponentProperties getProperties()
	{
		return properties;
	}
}