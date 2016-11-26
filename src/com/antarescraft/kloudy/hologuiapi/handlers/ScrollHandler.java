package com.antarescraft.kloudy.hologuiapi.handlers;

import com.antarescraft.kloudy.hologuiapi.scrollvalues.AbstractScrollValue;

/**
 * Used to define a callback function that executes when a value scroller component is scrolled
 */
public interface ScrollHandler
{
	public void onScroll(AbstractScrollValue<?, ?> value);
}