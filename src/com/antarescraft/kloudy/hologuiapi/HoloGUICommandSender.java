package com.antarescraft.kloudy.hologuiapi;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class HoloGUICommandSender
{
	private HoloGUIPlugin plugin;
	private CommandSender sender;
	
	public HoloGUICommandSender(HoloGUIPlugin plugin, CommandSender sender)
	{
		this.plugin = plugin;
		this.sender = sender;
	}
	
	public HoloGUIPlugin getHoloGUIPlugin()
	{
		return plugin;
	}
	
	public CommandSender getCommandSender()
	{
		return sender;
	}
	
	public void performCommand(String command)
	{
		Bukkit.getServer().dispatchCommand(sender, command);
	}
}