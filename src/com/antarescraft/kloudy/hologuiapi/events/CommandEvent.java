package com.antarescraft.kloudy.hologuiapi.events;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.util.ConfigManager;
import com.antarescraft.kloudy.plugincore.command.CommandHandler;
import com.antarescraft.kloudy.plugincore.command.CommandParser;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;

public class CommandEvent implements CommandExecutor
{
	public static String baseCommand = "hg";
	
	private HoloGUIApi holoGUI;
	
	public CommandEvent(HoloGUIApi holoGUI)
	{
		this.holoGUI = holoGUI;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return CommandParser.parseCommand(holoGUI, this, "hg", cmd.getName(), sender, args);
	}
	
	@CommandHandler(description = "Reloads the config file values", 
			mustBePlayer = false,  permission = "hg.admin", subcommands = "reloadconfig")
	public void reloadConfig(CommandSender sender, String[] args)
	{
		holoGUI.destroyAllGUIContainers();
		ConfigManager.getInstance().loadConfigValues(sender, holoGUI);
	}
	
	@CommandHandler(description = "Displays a list of defined GUI pages", 
			mustBePlayer = false, permission = "hg.see", subcommands = "guipagelist [page]")
	public void guiPageList(CommandSender sender, String[] args)
	{
		ArrayList<String> guiPageTitles = new ArrayList<String>();
		for(String guiPageTitle : holoGUI.getGUIPages().keySet())
		{
			guiPageTitles.add(guiPageTitle);
		}
		
		if(args.length == 2) MessageManager.pageList(sender, guiPageTitles, "GUI Pages", args[1], 10);//page number specified
		else MessageManager.pageList(sender, guiPageTitles, "GUI Pages");
	}
	
	@CommandHandler(description = "Displays a list of defined static GUI displays",
			mustBePlayer = false, permission = "hg.see", subcommands = "staticguidisplaylist [page]")
	public void staticGUIPageDisplays(CommandSender sender, String[] args)
	{
		ArrayList<String> staticDisplayStrings = new ArrayList<String>();
		for(StationaryGUIDisplayContainer stationaryDisplay : holoGUI.getStationaryDisplays())
		{
			String staticDisplayString = "Id: " + stationaryDisplay.getId() + ", World: " + stationaryDisplay.getLocation().getWorld().getName() +", Location: x: " + stationaryDisplay.getLocation().getBlockX() + 
					", y: " + stationaryDisplay.getLocation().getBlockY() + ", z: " + stationaryDisplay.getLocation().getBlockZ();
			
			staticDisplayStrings.add(staticDisplayString);
		}
		
		if(args.length == 2) MessageManager.pageList(sender, staticDisplayStrings, "Static GUI Displays", args[1], 10);//page number specified
		else MessageManager.pageList(sender, staticDisplayStrings, "Static GUI Displays");
	}
	
	@CommandHandler(description = "Teleports the player to the specified static GUI display's location",
			mustBePlayer = true, permission = "hg.teleport", subcommands = "tpstaticgui <static-gui-display-id>")
	public void tpStaticGUIDisplay(CommandSender sender, String[] args)
	{
		StationaryGUIDisplayContainer stationaryDisplay = holoGUI.getStationaryGUIDisplay(args[1]);
		if(stationaryDisplay != null)
		{
			Player player = (Player)sender;
			Location teleportLocation = stationaryDisplay.getLocation().clone();
			teleportLocation.setY(teleportLocation.getY() + 0.5);
			player.teleport(teleportLocation);
			
			MessageManager.success(sender, "You have been teleported to static GUI display " + ChatColor.AQUA + args[1]);
		}
		else
		{
			MessageManager.error(sender, "No static GUI display exists with that id. Did you spell it correctly?");
		}
	}
	
	@CommandHandler(description = "Opens a GUI for the player",
			mustBePlayer = true, permission = "hg.see", subcommands = "open <gui-page-id> [static-gui-display-id]")
	public void openGUI(CommandSender sender, String[] args)
	{
		Player player = (Player)sender;
		
		GUIPage guiContainer = holoGUI.getGUIPages().get(args[1]);
		if(guiContainer != null)
		{
			if(guiContainer.playerHasPermission(player))
			{
				if(args.length == 2)//personal GUI
				{
					holoGUI.openGUIPage(holoGUI, player, args[1]);
				}
				else if(args.length == 3)//static GUI
				{
					holoGUI.openGUIPage(holoGUI, player, args[1], args[2], null);
				}
			}
			else
			{
				MessageManager.error(player, "You do not have permission to view this GUI");
			}
		}
		else
		{
			MessageManager.error(player, "GUI container id: "  + ChatColor.AQUA + "'" + args[1] + "'" + ChatColor.RED + " does not exist!");
		}
	}
	
	@CommandHandler(description = "Closes the GUI the player is currently looking at",
			mustBePlayer = true, permission = "hg.see", subcommands = "close")
	public void closeGUI(CommandSender sender, String[] args)
	{
		Player player = (Player)sender;
		PlayerGUIPage playerGUIContainer = PlayerData.getPlayerData(player).getPlayerGUIPage();
		if(playerGUIContainer != null)
		{
			holoGUI.closeGUIPage(player);
		}
		else
		{
			MessageManager.error(sender, "You don't have a GUI currently open");
		}
	}
	
	@CommandHandler(description = "Removes current GUI and displays previous GUI the player was looking at",
			mustBePlayer = true, permission = "hg.see", subcommands = "back [static-gui-display-id]")
	public void back(CommandSender sender, String[] args)
	{
		Player player = (Player)sender;
		
		if(args.length == 2)//stationary gui display id was specified
		{
			holoGUI.displayPreviousGUIPage(player, args[1]);
		}
		else
		{
			holoGUI.displayPreviousGUIPage(player);
		}
	}
	
	@CommandHandler(description = "Creates a new static GUI display with the given <static-gui-display-id> at the location the player is standing and displays GUI page <gui-page-id>",
			mustBePlayer = true, permission = "hg.static", subcommands = "newstaticgui <static-gui-display-id> <gui-page-id>")
	public void staticGUI(CommandSender sender, String[] args)
	{
		Player player = (Player)sender;
	
		if(holoGUI.getStationaryGUIDisplay(args[1]) == null)
		{
			GUIPage guiContainer = holoGUI.getGUIPages().get(args[2]);
			if(guiContainer != null)
			{
				Location displayLocation = player.getLocation().clone();
				displayLocation.setY(displayLocation.getY() + 0.5);
				StationaryGUIDisplayContainer stationaryDisplay = new StationaryGUIDisplayContainer(args[1], guiContainer, displayLocation);
				holoGUI.addStationaryDisplay(args[1], stationaryDisplay);
				
				stationaryDisplay.display(player);
				
				MessageManager.success(sender, "Successuly created stationary GUI " + ChatColor.AQUA + args[1]);
			}
			else
			{
				MessageManager.error(sender, "No GUI page exists with id " + ChatColor.AQUA + args[2] + ChatColor.RED + ". Did you spell it correctly?");
			}
		}
		else
		{
			MessageManager.error(sender, "A stationary display with id " + ChatColor.AQUA + args[1] + ChatColor.RED + " already exists. Pick another id.");
		}
	}
	
	@CommandHandler(description = "Deletes the specified static gui display",
			mustBePlayer = false, permission = "hg.static", subcommands = "deletestaticgui <static-gui-display-id>")
	public void deleteStaticGUI(CommandSender sender, String[] args)
	{
		StationaryGUIDisplayContainer stationaryDisplay = holoGUI.getStationaryGUIDisplay(args[1]);
		if(stationaryDisplay != null)
		{
			stationaryDisplay.destroyAll();
			stationaryDisplay.deleteConfigProperties();
			holoGUI.removeStationaryDisplay(args[1]);
			
			MessageManager.success(sender, "Successfully removed stationary display: " + ChatColor.AQUA + args[1]);
		}
		else
		{
			MessageManager.error(sender, "No stationary gui display exists with id: " + ChatColor.AQUA + args[1] + ChatColor.RED + ". Did you spell it correctly?");
		}
	}
}