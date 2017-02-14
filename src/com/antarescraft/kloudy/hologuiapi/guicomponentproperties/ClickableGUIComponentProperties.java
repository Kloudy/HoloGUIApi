package com.antarescraft.kloudy.hologuiapi.guicomponentproperties;

import org.bukkit.Sound;

import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.BooleanConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.DoubleConfigProperty;
import com.antarescraft.kloudy.plugincore.config.annotations.OptionalConfigProperty;
import com.antarescraft.kloudy.plugincore.utils.Utils;

/**
 * Collection all of the properties that all clickable gui components share
 */
public abstract class ClickableGUIComponentProperties extends GUIComponentProperties implements ConfigObject
{
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick")
	protected String onclick;
	
	@OptionalConfigProperty
	@BooleanConfigProperty(defaultValue = false)
	@ConfigProperty(key = "execute-command-as-console")
	protected boolean executeCommandAsConsole;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "onclick-sound")
	protected String onclickSoundString;
	
	@OptionalConfigProperty
	@DoubleConfigProperty(defaultValue = 0.5, maxValue = 1, minValue = 0)
	@ConfigProperty(key = "onclick-sound-volume")
	protected double onclickSoundVolume;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "label-zoom-distance")
	protected double labelZoomDistance = -1;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "click-permission")
	protected String clickPermission;
	
	@OptionalConfigProperty
	@ConfigProperty(key = "no-permission-message")
	protected String noPermissionMessage;

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
	}
	
	public String getOnclick()
	{
		return onclick;
	}
	
	public void setOnclick(String onclick)
	{
		this.onclick = onclick;
	}
	
	public boolean getExecuteCommandAsConsole()
	{
		 return executeCommandAsConsole;
	}
	
	public void setExecuteCommandAsConsole(boolean executeCommandAsConsole)
	{
		this.executeCommandAsConsole = executeCommandAsConsole;
	}
	
	public Sound getOnclickSound()
	{
		return Utils.parseSound(onclickSoundString);
	}
	
	public void setOnclickSound(Sound sound)
	{
		this.onclickSoundString = sound.toString();
	}
	
	public float getOnclickSoundVolume()
	{
		return (float)onclickSoundVolume;
	}
	
	public void setOnClickSoundVolume(float onclickSoundVolume)
	{
		this.onclickSoundVolume = (double)onclickSoundVolume;
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