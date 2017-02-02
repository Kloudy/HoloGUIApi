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
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		onclickSound = Utils.parseSound(onclickSoundString);
	}
}