package com.antarescraft.kloudy.hologuiapi.exceptions;

/**
 * Thrown when a gui page id is passed in that doesn't exist
 */
public class GUIPageNotFoundException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage()
	{
		return "No GUI page exists with that id";
	}
}