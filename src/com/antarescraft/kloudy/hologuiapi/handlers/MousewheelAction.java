package com.antarescraft.kloudy.hologuiapi.handlers;

public class MousewheelAction
{
	private int previousSlot;
	private int currentSlot;
	
	public MousewheelAction(int previousSlot, int currentSlot)
	{
		this.previousSlot = previousSlot;
		this.currentSlot = currentSlot;
	}
	
	public int getPreviousSlot()
	{
		return previousSlot;
	}
	
	public int getCurrentSlot()
	{
		return currentSlot;
	}
	
	/**
	 * Gets the direction that the player scrolled the mousewheel.
	 * @return MousewheelDirection enum indicated the direction of the mousewheel scroll.
	 */
	public MousewheelScrollDirection getScrollDirection()
	{
		// If the item selected is 2 slots of where it previously was, it's considered a scroll.
		
		int diff = currentSlot - previousSlot;
		
		// Scrolled up.
		if((diff <= 2 && diff > 0) || 
				(currentSlot == 0 && previousSlot == 8) ||
				(currentSlot == 1 && previousSlot == 0))
		{
			return MousewheelScrollDirection.UP;
		}
		
		// Scrolled down.
		else if((diff >= -2 && diff < 0) ||
				(currentSlot == 8 && previousSlot == 0) ||
				(currentSlot == 7 && previousSlot == 8))
		{
			return MousewheelScrollDirection.DOWN;
		}
		
		return MousewheelScrollDirection.NONE;
	}
	
	public enum MousewheelScrollDirection
	{
		NONE,
		UP,
		DOWN
	}
}