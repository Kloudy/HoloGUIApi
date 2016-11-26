package com.antarescraft.kloudy.hologuiapi.guicomponents;

import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;

public class GUIComponentProperties
{
	private HoloGUIPlugin holoGUIPlugin;
	private String id;
	private String pageId;
	private ComponentPosition position;
	private String label;
	private double labelDistance;
	private boolean alwaysShowLabel;
	private boolean hidden;
	
	public GUIComponentProperties(HoloGUIPlugin holoGUIPlugin, String id,String pageId, ComponentPosition position, String label, 
			double labelDistance, boolean alwaysShowLabel, boolean hidden)
	{
		this.holoGUIPlugin = holoGUIPlugin;
		this.id = id;
		this.pageId = pageId;
		this.position = position;
		this.label = label;
		this.labelDistance = labelDistance;
		this.alwaysShowLabel = alwaysShowLabel;
		this.hidden = hidden;
	}
	
	@Override
	protected GUIComponentProperties clone()
	{
		return new GUIComponentProperties(holoGUIPlugin, id, pageId, position, label, labelDistance, alwaysShowLabel, hidden);
	}
	
	public GUIComponentProperties()
	{
		
	}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return holoGUIPlugin;
	}
	
	public void setHoloGUIPlugin(HoloGUIPlugin holoGUIPlugin)
	{
		this.holoGUIPlugin = holoGUIPlugin;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String pageId()
	{
		return pageId;
	}
	
	public void setPageId(String pageId)
	{
		this.pageId = pageId;
	}
	
	public ComponentPosition getPosition()
	{
		return position;
	}
	
	public void setComponentPosition(ComponentPosition position)
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
	
	public boolean alwaysShowLabel()
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