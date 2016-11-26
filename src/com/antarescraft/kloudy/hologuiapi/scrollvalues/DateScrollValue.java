package com.antarescraft.kloudy.hologuiapi.scrollvalues;

import java.time.Duration;
import java.util.Calendar;

import com.antarescraft.kloudy.plugincore.time.TimeFormat;

/**
 * Represents a Duration ComponentValue in the form "yyyy/mm/dd"
 */
public class DateScrollValue extends AbstractScrollValue<Calendar, Duration>
{
	private boolean wrap;
	
	public DateScrollValue(Calendar value, Duration step, Calendar minValue, Calendar maxValue, boolean wrap)
	{
		super(value, step, minValue, maxValue);
	}
	
	@Override
	public DateScrollValue clone()
	{
		return new DateScrollValue((Calendar)value.clone(), step.plus(Duration.ZERO), (Calendar)minValue.clone(), (Calendar)maxValue.clone(), wrap);
	}

	@Override
	public void increment()
	{
		 long milliseconds = value.getTimeInMillis();
		 long stepMilliseconds = step.toMillis();
		 
		 value.setTimeInMillis(milliseconds + stepMilliseconds);
		 if(maxValue != null && value.compareTo(maxValue) > 0)
		 {
			 if(wrap)
			 {
				 value = (Calendar)minValue.clone();
			 }
			 else
			 {
				 value = (Calendar)maxValue.clone();
			 }
		 }
	}

	@Override
	public void decrement() 
	{
		long milliseconds = value.getTimeInMillis();
		long stepMilliseconds = step.toMillis();
		 
		 value.setTimeInMillis(milliseconds - stepMilliseconds);
		 if(minValue != null && value.compareTo(minValue) < 0)
		 {
			 if(wrap)
			 {
				 value = (Calendar)maxValue.clone();
			 }
			 else
			 {
				 value = (Calendar)minValue.clone();
			 }
		 }
	}

	@Override
	public String toString()
	{
		return TimeFormat.getDateFormat(value);
	}
}