package com.antarescraft.kloudy.hologuiapi.guicomponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponentproperties.LabelComponentProperties;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUITextComponent;
import com.antarescraft.kloudy.hologuiapi.util.HoloGUIPlaceholders;
import com.antarescraft.kloudy.plugincore.config.ConfigObject;
import com.antarescraft.kloudy.plugincore.config.PassthroughParams;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigElement;
import com.antarescraft.kloudy.plugincore.config.annotations.ConfigProperty;

import me.clip.placeholderapi.PlaceholderAPI;

/*
 * Represents text on a GUI
 */
public class LabelComponent extends GUIComponent implements ConfigObject
{		
	@ConfigElement
	@ConfigProperty(key = "")
	private LabelComponentProperties properties;
	
	private HashSet<Integer> scrollingLines = new HashSet<Integer>();
	
	private String formatCode = "";
	
	private LabelComponent(){}
	
	private void parseLineScroll()
	{
		scrollingLines.clear();
				
		String[] linesArray = new String[properties.getLines().size()];
		for(int i = 0; i < properties.getLines().size(); i++)
		{
			String str = properties.getLines().get(i);
			str = str.replaceAll("§", "&");
						
			if(str.startsWith("%scroll%"))
			{
				str = str.replace("%scroll%", "");
				scrollingLines.add(i);
			}
			
			linesArray[i] = str;
		}
		
		properties.setLines(new ArrayList<String>(Arrays.asList(linesArray)));
	}
	
	public void setLines(ArrayList<String> lines)
	{
		properties.setLines(lines);
		
		parseLineScroll();
	}

	@Override
	public PlayerGUITextComponent initPlayerGUIComponent(Player player) 
	{	
		return new PlayerGUITextComponent(player, this, updateComponentLines(player));
	}
	
	@Override
	public void updateIncrement()
	{
		String[] linesArray = new String[properties.getLines().size()];
		String currentFormatting = "";
		for(int i = 0; i < properties.getLines().size(); i++)
		{
			String str = properties.getLines().get(i);
			
			if(scrollingLines.contains(i))
			{
				if(str.matches("&[a|b|c|d|e|f|0|1|2|3|4|5|6|7|8|9|k|l|m|n|o|r].*"))
				{
					//shift the two format characters
					str = shiftString(str);
					
					if(str.startsWith("r"))//hit a reset character, reset currentFormatting
					{
						currentFormatting = "";
					}
					else
					{
						currentFormatting = "&" + str.charAt(0);
					}
					
					str = shiftString(str);
					
					if(str.matches("&[a|b|c|d|e|f|0|1|2|3|4|5|6|7|8|9|k|l|m|n|o|r].*"))//there could be two formatting codes in a row
					{
						str = shiftString(str);
						
						if(str.startsWith("r"))//hit a reset character, reset currentFormatting
						{
							currentFormatting = "";
						}
						else
						{
							currentFormatting  += "&" + str.charAt(0);
						}
						
						formatCode = currentFormatting;
						
						str = shiftString(str);
					}
				}
				
				if(str.matches("%.+%.*"))//line starts with a placeholder. Shift the entire placeholder
				{
					str = shiftString(str);//shift the beginning '%'
					char c = str.charAt(0);
					while(c != '%')
					{
						str = shiftString(str);
						c = str.charAt(0);
					}
					str = shiftString(str);//shifting ending '%'
				}
				else//line doens't start with a placeholder, shift the character
				{
					str = shiftString(str);
				}
			}	
			
			linesArray[i] = str;
		}
		
		properties.setLines(new ArrayList<String>(Arrays.asList(linesArray)));
	}
	
	@Override
	public String[] updateComponentLines(Player player)
	{
		String[] componentLines = new String[properties.getLines().size()];
		for(int i = 0; i < properties.getLines().size(); i++)
		{
			String str = properties.getLines().get(i);
			
			str = HoloGUIPlaceholders.setHoloGUIPlaceholders(plugin, str, player);
			if(HoloGUIApi.hasPlaceholderAPI)
			{	
				str = PlaceholderAPI.setPlaceholders(player, formatCode + str);
			}
			
			PlayerData playerData = PlayerData.getPlayerData(player);
			if(playerData != null) str = HoloGUIPlaceholders.setModelPlaceholders(plugin, playerData.getPlayerGUIPageModel(), str);
			
			componentLines[i] = str;
		}
		
		return componentLines;
	}
	
	/**
	 * shifts a string to the left and wraps the first character to the end
	 */
	private String shiftString(String input)
	{
		String str = input;
		char firstCharacter = str.charAt(0);
		str = str.substring(1);
		str += Character.toString(firstCharacter);
		
		return str;
	}
	
	@Override
	public double getLineHeight()
	{
		return (1 / properties.getDistance()) * 0.21;
	}

	@Override
	public void configParseComplete(PassthroughParams params)
	{
		super.configParseComplete(params);
		
		parseLineScroll();
	}
	
	@Override
	public LabelComponentProperties getProperties()
	{
		return properties;
	}
}