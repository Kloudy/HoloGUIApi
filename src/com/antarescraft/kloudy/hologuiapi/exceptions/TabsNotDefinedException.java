package com.antarescraft.kloudy.hologuiapi.exceptions;

public class TabsNotDefinedException extends HoloGUIException
{
	private static final long serialVersionUID = 1L;

	public TabsNotDefinedException()
	{
		super("No tabs have been defined.");
	}
}