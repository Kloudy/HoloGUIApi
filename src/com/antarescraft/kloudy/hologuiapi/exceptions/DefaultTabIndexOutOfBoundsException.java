package com.antarescraft.kloudy.hologuiapi.exceptions;

/**
 * This exception gets thrown when the user configures a value for 'default-tab-index' 
 * that is outside the range of the defined tabs. 
 * 
 * Example:
 * 
 * 3 tabs defined, user attempts to set default-tab-index: 5
 */
public class DefaultTabIndexOutOfBoundsException extends HoloGUIException
{
	private static final long serialVersionUID = 1L;

	public DefaultTabIndexOutOfBoundsException(int defaultTabIndex, String tabsGuiPageId)
	{
		super(String.format("'default-tab-index' value: %d for TabsGUIPage '%s' is out of bounds. This value must >= 0 and < the number of tabs defined - 1.", 
				defaultTabIndex, tabsGuiPageId));
	}
}