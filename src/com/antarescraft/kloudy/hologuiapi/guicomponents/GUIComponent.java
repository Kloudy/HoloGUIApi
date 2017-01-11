package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.ConfigElementKey;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;

public abstract class GUIComponent
{
	protected HoloGUIPlugin holoGUIPlugin;
	
	@ConfigElementKey
	protected String id;
	
	@ConfigElement
	@ConfigProperty(key = "position")
	protected ComponentPosition position;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "label")
	protected String label;
	
	//TODO: Figure out what to do with this as the default value is different based on type
	@OptionalConfigProperty
	@ConfigProperty(key = "label-distance")
	protected double labelDistance;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "always-show-label")
	protected boolean alwaysShowLabel;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "hidden")
	protected boolean hidden;
	
	public GUIComponent(GUIComponentProperties properties)
	{
		this.holoGUIPlugin = properties.getHoloGUIPlugin();
		this.id = properties.getId();
		this.position = properties.getPosition();
		this.label = properties.getLabel();
		this.labelDistance = properties.getLabelDistance();
		this.alwaysShowLabel = properties.alwaysShowLabel();
		this.hidden = properties.isHidden();
	}
	
	public abstract PlayerGUIComponent initPlayerGUIComponent(Player player);
	public abstract void updateIncrement();//updates the guicomponent's next incremental state
	public abstract String[] updateComponentLines(Player player);
	public abstract double getDisplayDistance();
	public abstract double getLineHeight();
	public abstract GUIComponent clone();
	
	public GUIComponentProperties cloneProperties()
	{
		return new GUIComponentProperties(holoGUIPlugin, id, pageId, position, label, labelDistance, alwaysShowLabel, hidden);
	}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return holoGUIPlugin;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public ComponentPosition getPosition()
	{
		return position;
	}
	
	public void setPosition(ComponentPosition position)
	{
		this.position = position;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public double getLabelDistance()
	{
		return labelDistance;
	}
	
	public void setLabelDistance(double labelDistance)
	{
		this.labelDistance = labelDistance;
	}
	
	public boolean getAlwaysShowLabel()
	{
		return alwaysShowLabel;
	}
	
	public void setAlwaysShowLabel(boolean alwaysShowLabel)
	{
		this.alwaysShowLabel = alwaysShowLabel;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
	
	public void setIsHidden(boolean hidden)
	{
		this.hidden = hidden;
	}
}