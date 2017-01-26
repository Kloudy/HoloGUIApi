package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import java.util.HashMap;

import org.bukkit.Sound;

import com.antarescraft.kloudy.plugincore.config.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.utils.Utils;

/**
 * Collection all of the properties that all clickable gui components share
 */
public abstract class ClickableGUIComponentProperties extends GUIComponentProperties implements ConfigObject
{
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick")
	public String onclick;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-command-as-console")
	public boolean executeCommandAsConsole;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-sound")
	public String onclickSoundString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0.5, maxValue = 1, minValue = 0)
	@ConfigProperty(key = "onclick-sound-volume")
	public double onclickSoundVolume;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "label-zoom-distance")
	public double labelZoomDistance = -1;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "click-permission")
	public String clickPermission;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "no-permission-message")
	public String noPermissionMessage;
	
	public Sound onclickSound = null;
	
	@Override
	public void configParseComplete(HashMap<String, Object> passthroughParams)
	{
		onclickSound = Utils.parseSound(onclickSoundString);
	}
	
	@Override
	public String toString()
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(super.toString())
		.append(String.format("onclick: %s,\n", onclick))
		.append(String.format("execute-command-as-console: %b,\n", executeCommandAsConsole))
		.append(String.format("onclick-sound: %s,\n", onclickSoundString))
		.append(String.format("onclick-sound-volume: %d,\n", onclickSoundVolume))
		.append(String.format("label-zoom-distance: %d,\n", labelZoomDistance))
		.append(String.format("click-permission: %s,\n", clickPermission))
		.append(String.format("no-permission-message: %s,\n", noPermissionMessage));
		
		return strBuilder.toString();
	}
	
	/*public ClickableGUIComponentProperties(String onclick, boolean executeCommandAsConsole, Sound onclickSound, 
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
		return (float)onclickSoundVolume;
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
	}*/
}