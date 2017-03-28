package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TextBoxComponent;

import net.md_5.bungee.api.ChatColor;

public class PlayerGUITextBoxComponent extends PlayerGUIValueBoxComponent implements ValueHolder
{
	private String line;
	private String prevLine;
	private boolean lineVisible;
	
	public PlayerGUITextBoxComponent(Player player, TextBoxComponent guiComponent)
	{
		super(player, guiComponent);
		
		line = getTextBoxComponent().getPlayerTextBoxValue(player);
		prevLine = line;
		
		lineVisible = true;
		
		lines = new String[4];
		lines[0] = this.line;
		lines[1] = "▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇";
		lines[2] = "▇§l                                §r                                ▇";
		lines[3] = "▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇";
		
		componentEntityIds = new int[lines.length];
		armorstandLocations = new Location[componentEntityIds.length];
	}
	
	public TextBoxComponent getTextBoxComponent()
	{
		return (TextBoxComponent)guiComponent;
	}

	@Override
	public String getValue()
	{
		return line;
	}
	
	public void startEditing()
	{
		PlayerData playerData = PlayerData.getPlayerData(player);
		
		if(playerData.isSneaking())//sneaking, clear text
		{
			getTextBoxComponent().setPlayerTextBoxValue(player, "");
			
			String message = ChatColor.BOLD + "===============HoloGUI Text Box===============\n";
			message += " \n";
			message += ChatColor.GOLD + "" + ChatColor.ITALIC + "Cleared the text in the textbox\n" + ChatColor.RESET;
			message += " \n";
			message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
			
			player.sendMessage(message);
		}
		else
		{
			PlayerGUITextBoxComponent textBox = playerData.getTextBoxEditor();
			
			if(textBox == null)
			{
				select();
				
				playerData.setPlayerTextBoxEditor(this);
				
				String message = ChatColor.BOLD + "===============HoloGUI Text Box===============\n";
				message += " \n";
				message += ChatColor.GOLD + "" + ChatColor.ITALIC + "Text box current value: \n" + ChatColor.RESET;
				message += " \n";
				message += getValue() + "\n";
				message += " \n";
				message += ChatColor.GREEN + "" + ChatColor.ITALIC + "Type a new value for this text box in chat and press 'Enter'.\n";
				message += ChatColor.GREEN + "" + ChatColor.ITALIC + "Click the text box again to cancel without setting a value.\n";
				message += "Crouch (shift) and click text box to clear the text.\n";
				message += " \n";
				message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
				
				player.sendMessage(message);
			}
			else
			{
				if(this.equals(textBox))
				{
					cancelEditing();
				}
				else
				{
					String message = ChatColor.BOLD + "===============HoloGUI Text Box===============\n";
					message += " \n";
					message += ChatColor.RED + "" + ChatColor.ITALIC + "You are already editing another text box!\n";
					message += " \n";
					message += ChatColor.GOLD + "" + ChatColor.ITALIC + "Type into chat the value you would like the textbox to have to \nfinish editing the current text box.\n";
					message += "Click the current text box you are editing to cancel without\n";
					message += "setting a value.\n";
					message += "Crouch (shift) and click text box to clear the text.\n";
					message += " \n";
					message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
					
					player.sendMessage(message);
				}
			}
		}
	}
	
	public void cancelEditing()
	{
		unselect();
		
		PlayerData.getPlayerData(player).setPlayerTextBoxEditor(null);
		
		String message = ChatColor.BOLD + "===============HoloGUI Text Box===============\n";
		message += " \n";
		message += ChatColor.GREEN + "" + ChatColor.ITALIC + "You have canceled editing the text box.\n";
		message += " \n";
		message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
		
		player.sendMessage(message);
	}
	
	public void stopEditing(String newValue)
	{
		unselect();
		getTextBoxComponent().setPlayerTextBoxValue(player, newValue);
		
		String message = ChatColor.BOLD + "===============HoloGUI Text Box===============\n";
		message += " \n";
		message += ChatColor.GOLD + "" + ChatColor.ITALIC + "Text box value set to: \n" + ChatColor.RESET;
		message += " \n";
		message += newValue + "\n";
		message += " \n";
		message += ChatColor.WHITE + "" + ChatColor.BOLD + "=============================================";
		player.sendMessage(message);
		
		PlayerData.getPlayerData(player).setPlayerTextBoxEditor(null);
	}

	@Override
	public void updateComponentLines() 
	{
		updateLabelText();
		
		line = getTextBoxComponent().getPlayerTextBoxValue(player);
		
		if(prevLine != null && !prevLine.equals(line))//don't update the line if it hasn't changed from the previous frame
		{
			String displayLine = line;
			if(displayLine.length() > 30)
			{
				displayLine = displayLine.substring(0, 31) + "...";
			}
			
			if(!lineVisible && !displayLine.equals(""))//line was invisible, but now has content so make visible
			{	
				lineVisible = true;
				HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, componentEntityIds[0], lineVisible);
			}
			else if(lineVisible && displayLine.equals(""))
			{
				lineVisible = false;
				HoloGUIApi.packetManager.updateEntityCustomNameVisible(player, componentEntityIds[0], lineVisible);
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
		
		double distance = guiComponent.getConfig().getDistance();
		Location lookOrigin = player.getLocation();
		if(stationary) 
		{
			lookOrigin = lookLocation;
			distance = 15;
		}
		
		armorstandLocations[0] = calculateArmorStandLocation(2, lookOrigin, vect, distance, guiComponent.getLineHeight(), 
				guiComponent.getConfig().getPosition().getY(), guiComponent.getConfig().getPosition().getX());
		
		if(stationary)
		{
			armorstandLocations[0].setX(armorstandLocations[0].getX() + (vect.getX() * -15));
			armorstandLocations[0].setZ(armorstandLocations[0].getZ() + (vect.getZ() * -15));
		}
		
		String displayLine = new String(line);
		if(displayLine.length() > 30)
		{
			displayLine = displayLine.substring(0, 31) + "...";
		}
		
		if(displayLine.equals("")) lineVisible = false;
		
		componentEntityIds[0] = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, armorstandLocations[0], displayLine, lineVisible);
		
		renderLabel(lookOrigin, vect, stationary);

		for(int i = 1; i < lines.length; i++)
		{
			armorstandLocations[i] = calculateArmorStandLocation(i, lookOrigin, vect, distance, guiComponent.getLineHeight(), 
					guiComponent.getConfig().getPosition().getY(), guiComponent.getConfig().getPosition().getX());
			
			if(stationary)
			{
				armorstandLocations[i].setX(armorstandLocations[i].getX() + (vect.getX() * -15));
				armorstandLocations[i].setZ(armorstandLocations[i].getZ() + (vect.getZ() * -15));
			}
			
			componentEntityIds[i] = HoloGUIApi.packetManager.spawnEntity(EntityType.ARMOR_STAND, player, armorstandLocations[i] , lines[i], true);
		}
	}

	@Override
	public boolean equals(Object object)
	{
		if(object instanceof PlayerGUITextBoxComponent)
		{
			PlayerGUITextBoxComponent textBox = (PlayerGUITextBoxComponent)object;
			return textBox.getGUIComponent().getConfig().getId().equals(this.getGUIComponent().getConfig().getId());
		}
		
		return false;
	}
}