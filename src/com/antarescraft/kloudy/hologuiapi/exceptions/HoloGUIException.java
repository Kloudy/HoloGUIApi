package com.antarescraft.kloudy.hologuiapi.exceptions;

public class HoloGUIException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public HoloGUIException(String message)
	{
		super("[HoloGUIApi]: " + message);
	}
}