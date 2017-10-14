package com.antarescraft.kloudy.hologuiapi.exceptions;

/**
 * Exception thrown when a file not of type .jpg, .png, or .gif is attempted to be loaded.
 */
public class InvalidImageException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidImageException()
	{
		super("Image must be a .jpg, .png, or .gif");
	}
}