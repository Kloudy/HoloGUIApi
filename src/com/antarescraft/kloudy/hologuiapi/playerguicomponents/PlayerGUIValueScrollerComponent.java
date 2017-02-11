package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ValueScrollerComponent;

import net.md_5.bungee.api.ChatColor;

public class PlayerGUIValueScrollerComponent extends PlayerGUIValueBoxComponent implements ValueHolder
{
	private String prevLine;
	private String line;
	
	public PlayerGUIValueScrollerComponent(Player player, GUIComponent guiComponent)
	{
		super(player, guiComponent);

		line = (getValueScrollerComponent().getPlayerScrollValue(player) != null) ? getValueScrollerComponent().getPlayerScrollValue(player).toString() : "";
		prevLine = line;

		lines = new String[4];
		lines[0] = line;
		lines[1] = "▇▇▇▇▇▇▇▇▇▇▇▇▇";
		lines[2] = "▇§a§l ▲        §r          §c§l▼§r ▇";
		lines[3] = "▇▇▇▇▇▇▇▇▇▇▇▇▇";
		
		componentEntityIds = new int[lines.length];
		armorstandLocations = new Location[componentEntityIds.length];
	}
	
	public ValueScrollerComponent getValueScrollerComponent()
	{
		return (ValueScrollerComponent)guiComponent;
	}
	
	public void startEditing()
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		PlayerGUIValueScrollerComponent  valueScroller = playerData.getPlayerValueScrollerEditor();
		
		if(valueScroller == null)
		{
			select();
			playerData.setPlayerValueScrollerEditor(this);
			
			String message = ChatColor.BOLD + "=============HoloGUI Value Scroller=============\n";
			message += " \n";
			message += ChatColor.GOLD + "" + ChatColor.ITALIC + "Value scroller current value: \n" + ChatColor.RESET;
			message += " \n";
			message += getValue() + "\n";
			message += " \n";
			message += ChatColor.GREEN + "" + ChatColor.ITALIC + "Use your mousewheel to scroll up or down to change value.\n";
			message += ChatColor.GREEN + "" + ChatColor.ITALIC + "Click the value scroller again to stop editing.\n";
			message += " \n";
			message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
			
			player.sendMessage(message);
		}
		else
		{
			if(this.equals(valueScroller))
			{
				stopEditing();
			}
			else
			{
				String message = ChatColor.BOLD + "=============HoloGUI Value Scroller=============\n";
				message += " \n";
				message += ChatColor.RED + "" + ChatColor.ITALIC + "You are already editing another value scroller!\n";
				message += " \n";
				message += ChatColor.GOLD + "" + ChatColor.ITALIC + "Use your mousewheel to scroll up or down to change value.\n";
				message += "Click the current value scroller you are editing to finish\n";
				message += "editing.\n";
				message += " \n";
				message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
				
				player.sendMessage(message);
			}
			
		}
	}
	public void stopEditing()
	{
		unselect();
		
		String message = ChatColor.BOLD + "=============HoloGUI Value Scroller=============\n";
		message += " \n";
		message += ChatColor.GOLD + "" + ChatColor.ITALIC + "Value scroller set to: \n" + ChatColor.RESET;
		message += " \n";
		message += line + "\n";
		message += " \n";
		message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
		player.sendMessage(message);
		
		PlayerData.getPlayerData(player).setPlayerValueScrollerEditor(null);
	}

	@Override
	public String getValue()
	{
		return getValueScrollerComponent().getPlayerScrollValue(player).toString();
	}

	@Override
	public void updateComponentLines()
	{
		updateLabelText();
		
		line = getValueScrollerComponent().getPlayerScrollValue(player).toString();
				
		if(prevLine != null && !prevLine.equals(line))//don't update the line if it hasn't changed from the previous frame
		{
			String displayLine = line;
			if(displayLine.length() > 10)
			{
				displayLine = displayLine.substring(0, 11) + "...";
			}
						
			HoloGUIApi.packetManager.updateEntityText(player, componentEntityIds[0], displayLine);
		}
		
		lines[0] = line;
		prevLine = line;
	}

	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{
		Vector vect = lookLocation.getDirection().setY(0.25);
		vect = customNormalize(vect);
		
		double distance = guiComponent.getDisplayDistance();
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		armorstandLocations[0] = calculateArmorStandLocation(2, lookOrigin, vect, distance, guiComponent.getLineHeight(), 
				guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
		
		if(stationary)
		{
			armorstandLocations[0].setX(armorstandLocations[0].getX() + (vect.getX() * -15));
			armorstandLocations[0].setZ(armorstandLocations[0].getZ() + (vect.getZ() * -15));
		}
				
		String displayLine = line;
		if(displayLine.length() > 10)
		{
			displayLine = displayLine.substring(0, 11) + "...";
		}
		
		componentEntityIds[0] = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, armorstandLocations[0], displayLine, true);
		
		renderLabel(lookOrigin, vect, stationary);

		for(int i = 1; i < lines.length; i++)
		{
			armorstandLocations[i] = calculateArmorStandLocation(i, lookOrigin, vect, distance, guiComponent.getLineHeight(), 
					guiComponent.getProperties().getPosition().getY(), guiComponent.getProperties().getPosition().getX());
			
			if(stationary)
			{
				armorstandLocations[i].setX(armorstandLocations[i].getX() + (vect.getX() * -15));
				armorstandLocations[i].setZ(armorstandLocations[i].getZ() + (vect.getZ() * -15));
			}
									
			componentEntityIds[i] = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, armorstandLocations[i], lines[i], true);
		}
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(object instanceof PlayerGUIValueScrollerComponent)
		{
			PlayerGUIValueScrollerComponent valueScroller = (PlayerGUIValueScrollerComponent)object;
			return valueScroller.getGUIComponent().getProperties().getId().equals(this.getGUIComponent().getProperties().getId());
		}
		
		return false;
	}
}