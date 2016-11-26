package com.antarescraft.kloudy.hologuiapi.scrollvalues;

import java.util.List;

/**
 * Represents a list of strings that can be scrolled through in a value scroller
 */
public class ListScrollValue extends AbstractScrollValue<List<String>, Integer>
{
	private int index;
	
	public ListScrollValue(List<String> value) 
	{
		super(value, 1, null, null);

		if(value == null) throw new NullPointerException();
				
		index = 0;
	}
	
	@Override
	public ListScrollValue clone()
	{
		return new ListScrollValue(value);
	}

	@Override
	public void increment() 
	{
		index += step;
		if(index >= value.size()) index = 0;
	}

	@Override
	public void decrement()
	{
		index -= step;
		if(index < 0) index = value.size() - 1;
	}

	@Override
	public String toString() 
	{
		return value.get(index);
	}
}