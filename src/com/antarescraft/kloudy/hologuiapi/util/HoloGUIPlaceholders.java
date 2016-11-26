package com.antarescraft.kloudy.hologuiapi.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.PlayerData;
import com.antarescraft.kloudy.hologuiapi.StationaryGUIDisplayContainer;
import com.antarescraft.kloudy.hologuiapi.guicomponents.IValueHolder;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIComponent;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPage;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.PlayerGUIPageModel;
import com.antarescraft.kloudy.hologuiapi.playerguicomponents.StationaryPlayerGUIPage;

public class HoloGUIPlaceholders
{
	public static String setModelPlaceholders(HoloGUIPlugin holoGUIPlugin, PlayerGUIPageModel model, String input)
	{
		if(model == null) return input;

		String str = new String(input);
		//matches format: $model.myFunction(...);
		//match succeeds if the function contains any number of parameters
		//parameters must take the form of a string literal ex:'my string literal', a placeholder ex: '%some-placeholder%', or the player variable ex: $player
		//string literals and placeholders must be surrounded in single quotes
		//the player variable are not surrounded in single quotes. The match will fail if it is
		//the input string must end with a semi-colon to denote the end of the function placeholder syntax
		//any amount of white space can be present between arguments and the comma
		//HoloGUI placeholders and PlaceholderAPI placeholders get evaluated before model placeholders do. This means any placeholders contained as arguments will be evaled before the method is called
		//example successful match: $model.myFunction($player, 'johnny appleseed', '%apple-count%');
		Pattern methodSignaturePattern = Pattern.compile("\\$model\\.\\w+\\(.*?\\);");//"\\$model\\.\\w+\\( *((\\$player|'.+?') *(,(?! *\\))|(?= *\\))) *)* *\\);"
		Matcher methodSignatureMatcher = methodSignaturePattern.matcher(str);
		
		while (methodSignatureMatcher.find())//loop through all possible matches
		{			
			String methodSignature = methodSignatureMatcher.group();//the method signature as a whole
			
			Pattern functionNamePattern = Pattern.compile("\\w+");
			Matcher functionMatcher = functionNamePattern.matcher(methodSignature);
			
			try
			{
				functionMatcher.find();
				functionMatcher.find();
			}catch(Exception e){return input;}
			
			String functionName = functionMatcher.group();//function name is always the second capture group in the method signature

			Pattern argsPattern = Pattern.compile("(?<!\\\\)'.+?(?<!\\\\)'|\\$player");
			Matcher argsMatcher = argsPattern.matcher(methodSignature);
			ArrayList<Object> args = new ArrayList<Object>();
			while(argsMatcher.find())//match all the args in the function call
			{
				String arg = argsMatcher.group().replace("'", "");

				if(arg.equals("$player"))
				{
					args.add(model.getPlayer());
				}
				else
				{
					args.add(arg);
				}
			}
			
			//Search via reflection for the method in the model with a matching method signature
			Method[] commands = model.getClass().getMethods();
			for(Method method : commands)
			{
				if(method.getName().equals(functionName))
				{
					Parameter[] params = method.getParameters();
					
					if(params.length == args.size())
					{
						for(int i = 0; i < params.length; i++)
						{				
							if(!params[i].getType().isAssignableFrom(args.get(i).getClass()))
							{
								continue;
							}
						}
												
						try 
						{
							//if the method returns a string then treat it as a placeholder and replace the method signature with the result of the function call
							if(!method.getReturnType().equals(Void.TYPE))
							{
								StringBuilder sb = new StringBuilder(str);
								int matchStart = methodSignatureMatcher.start();
								int matchEnd = methodSignatureMatcher.end();
								
								Object r = (method.invoke(model, args.toArray()));
								String result = (r != null) ? r.toString() : "null";//invoke the method in the model
								sb.replace(matchStart, matchEnd, result);//replace the method signature with the result of the function call
								str = sb.toString();
								
								methodSignatureMatcher = methodSignaturePattern.matcher(str);
							}
							else//don't replace the method signature in the string, just execute the function
							{
								method.invoke(model, args.toArray());
							}
							
						}catch(Exception e){if(HoloGUIApi.debugMode)e.printStackTrace();}
					}
				}
			}
		}
		return str;
	}
	
	public static String setHoloGUIPlaceholders(HoloGUIPlugin holoGUIPlugin, String input, Player player)
	{
		String str = new String(input);
		
		PlayerGUIPage playerGUIContainer = PlayerData.getPlayerData(player).getPlayerGUIPage();
		if(playerGUIContainer != null)
		{
			for(PlayerGUIComponent playerGUIComponent : playerGUIContainer.getPlayerGUIComponents())
			{
				if(playerGUIComponent instanceof IValueHolder)
				{
					IValueHolder valueHolder = (IValueHolder)playerGUIComponent;
					
					str = str.replace("%" + playerGUIComponent.getGUIComponent().getId() + "_value%", valueHolder.getValue());
				}
			}
		}
		
		for(StationaryGUIDisplayContainer stationaryDisplay : holoGUIPlugin.getHoloGUI().getStationaryDisplays())
		{
			if(stationaryDisplay.isDisplayingToPlayer(player))
			{
				StationaryPlayerGUIPage stationaryContainer =  stationaryDisplay.getPlayerGUIContainersTable().get(player.getUniqueId());
				if(stationaryContainer != null)
				{
					for(PlayerGUIComponent playerGUIComponent : stationaryContainer.getPlayerGUIComponents())
					{
						if(playerGUIComponent instanceof IValueHolder)
						{
							IValueHolder valueHolder = (IValueHolder)playerGUIComponent;
							
							str = str.replace("%" + playerGUIComponent.getGUIComponent().getId() + "_value%", valueHolder.getValue());
						}
					}
				}
			}
		}
		
		return str;
	}
}