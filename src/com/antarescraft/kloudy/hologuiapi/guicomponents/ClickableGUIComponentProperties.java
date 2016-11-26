package com.antarescraft.kloudy.hologuiapi.guicomponents;

import org.bukkit.Sound;

public class ClickableGUIComponentProperties 
{
	private String onclick;
	private boolean executeCommandAsConsole;
	private Sound onclickSound;
	private float onclickSoundVolume;
	private double labelZoomDistance;
	private String clickPermission;
	private String noPermissionMessage;
	
	public ClickableGUIComponentProperties(String onclick, boolean executeCommandAsConsole, Sound onclickSound, 
			float onclickSoundVolume, double labelZoomDistance, String clickPermission, String noPermissionMessage)
	{
		this.onclick = onclick;
		this.executeCommandAsConsole = executeCommandAsConsole;
		this.onclickSound = onclickSound;
		this.onclickSoundVolume = onclickSoundVolume;
		this.labelZoomDistance = labelZoomDistance;
		this.clickPermission = clickPermission;
		this.noPermissionMessage = noPermissionMessage;
	}
	
	@Override
	protected ClickableGUIComponentProperties clone()
	{
		return new ClickableGUIComponentProperties(onclick, executeCommandAsConsole, onclickSound, onclickSoundVolume,
				labelZoomDistance, clickPermission, noPermissionMessage);
	}
	
	public String getOnclickCommand()
	{
		return onclick;
	}
	
	public void setOnClickCommand(String onclick)
	{
		this.onclick = onclick;
	}
	
	public boolean executeCommandAsConsole()
	{
		return executeCommandAsConsole;
	}
	
	public void setExecuteAsConsole(boolean executeAsConsole)
	{
		this.executeCommandAsConsole = executeAsConsole;
	}
	
	public Sound getOnclickSound()
	{
		return onclickSound;
	}
	
	public void setOnclickSound(Sound onclickSound)
	{
		this.onclickSound = onclickSound;
	}
	
	public float getOnclickSoundVolume()
	{
		return onclickSoundVolume;
	}
	
	public void setOnclickSoundVolume(float onclickSoundVolume)
	{
		this.onclickSoundVolume = onclickSoundVolume;
	}
	
	public double getLabelZoomDistance()
	{
		return labelZoomDistance;
	}
	
	public void setLabelZoomDistance(double labelZoomDistance)
	{
		this.labelZoomDistance = labelZoomDistance;
	}
	
	public String getClickPermission()
	{
		return clickPermission;
	}
	
	public void setClickPermission(String clickPermission)
	{
		this.clickPermission = clickPermission;
	}
	
	public String getNoPermissionMessage()
	{
		return noPermissionMessage;
	}
	
	public void setNoPermissionMessage(String noPermissionMessage)
	{
		this.noPermissionMessage = noPermissionMessage;
	}
}