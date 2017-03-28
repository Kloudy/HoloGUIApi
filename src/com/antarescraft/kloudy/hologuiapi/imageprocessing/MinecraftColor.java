package com.antarescraft.kloudy.hologuiapi.imageprocessing;

import java.io.Serializable;

public enum MinecraftColor implements Serializable
{
	BLACK("00", "00", "00", "§0"),
	DARK_BLUE("00", "00", "AA", "§1"),
	DARK_GREEN("00", "AA", "00", "§2"),
	DARK_AQUA("00", "AA", "AA", "§3"),
	DARK_RED("AA", "00", "00", "§4"),
	DARK_PURPLE("AA", "00", "AA", "§5"),
	GOLD("FF", "AA", "00", "§6"),
	GRAY("AA", "AA", "AA", "§7"),
	DARK_GRAY("55", "55", "55", "§8"),
	BLUE("55", "55", "FF", "§9"),
	GREEN("55", "FF", "55", "§a"),
	AQUA("55", "FF", "FF", "§b"),
	RED("FF", "55", "55", "§c"),
	LIGHT_PURPLE("FF", "55", "FF", "§d"),
	YELLOW("FF", "FF", "55", "§e"),
	WHITE("FF", "FF", "FF", "§f"),
	TRANSPARENT("00", "00", "00", "§l", (byte)0);
	
	private String red;
	private String green;
	private String blue;
	private String symbol;
	private byte alpha;
	
	MinecraftColor(String red, String green, String blue, String symbol)
	{
		this(red, green, blue, symbol, (byte)1);
	}
	
	MinecraftColor(String red, String green, String blue, String symbol, byte alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.symbol = symbol;
		this.alpha = alpha;
	}
	
	public int red()
	{
		return Integer.parseInt(red, 16);
	}
	
	public int green()
	{
		return Integer.parseInt(green, 16);
	}
	
	public int blue()
	{
		return Integer.parseInt(blue, 16);
	}
	
	public String symbol()
	{
		return symbol;
	}
	
	public byte alpha()
	{
		return alpha;
	}
}