package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ToggleSwitchComponent;

public class PlayerGUIToggleSwitchComponent extends PlayerGUITextComponent implements ValueHolder
{
	//private boolean state;
	
	public PlayerGUIToggleSwitchComponent(Player player, ToggleSwitchComponent toggleComponent, String[] lines)
	{
		super(player, toggleComponent, lines);
	}
	
	@Override
	public String getValue()
	{
		if(getToggleSwitchComponent().getPlayerToggleSwitchState(player))
		{
			String onState = getToggleSwitchComponent().getConfig().getOnValue();
			onState = onState.replace("&", "§");
			
			return onState;
		}
		else
		{
			String offState = getToggleSwitchComponent().getConfig().getOffValue();
			offState = offState.replace("&", "§");
			
			 return offState;
		}
	}
	
	public ToggleSwitchComponent getToggleSwitchComponent()
	{
		return (ToggleSwitchComponent)guiComponent;
	}
	
	@Override
	public void updateComponentLines()
	{
		updateLabelText();
		
		String[] newLines = getToggleSwitchComponent().updateComponentLines(player);
		for(int i = 0; i < newLines.length; i++)
		{
			if(lines[i] != null && !lines[i].equals(newLines[i]))//don't update the line if it hasn't changed from the previous frame
			{
				if(newLines[i].equals("") && !hiddenLines.contains(i))
				{
					HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, componentEntityIds[i], false);
					hiddenLines.add(i);
				}
				else if(!newLines[i].equals("") && hiddenLines.contains(i))
				{
					HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, componentEntityIds[i], true);
					hiddenLines.remove(i);
				}
				HoloGUIApi.packetManager.updateEntityText(player, componentEntityIds[i], newLines[i]);
			}
		}
		
		lines = newLines;
	}
}