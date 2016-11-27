 package com.antarescraft.kloudy.hologuiapi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.HoloGUIPlugin;
import com.antarescraft.kloudy.hologuiapi.events.GUIPagesLoadedEvent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ButtonComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ClickableGUIComponentProperties;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ComponentPosition;
import com.antarescraft.kloudy.hologuiapi.guicomponents.EntityButtonComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.EntityComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponentProperties;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ImageComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ItemButtonComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ItemComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.LabelComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.TextBoxComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ToggleSwitchComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.ValueScrollerComponent;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.GifProcessor;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.PngJpgProcessor;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.AbstractScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.DateScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.DoubleScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.DurationScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.IntegerScrollValue;
import com.antarescraft.kloudy.hologuiapi.scrollvalues.ListScrollValue;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDateFormatException;
import com.antarescraft.kloudy.plugincore.exceptions.InvalidDurationFormatException;
import com.antarescraft.kloudy.plugincore.messaging.MessageManager;
import com.antarescraft.kloudy.plugincore.time.TimeFormat;

public class ConfigManager 
{
	private String X = "x";
	private String Y = "y";
	private String Z = "z";
	private String ITEM_ID = "item-id";
	private String OPEN_WITH_ITEM_ID = "open-with-item-id";
	private String OPEN_WITH_ITEM_NAME = "open-with-item-name";
	private String OPEN_ON_LOGIN = "open-on-login";
	private String RENDER_DISTANCE = "render-distance";
	private String LABEL = "label";
	private String LABEL_DISTANCE = "label-distance";
	private String HIDDEN = "hidden";
	private String LABEL_ZOOM_DISTANCE = "label-zoom-distance";
	private String COMPONENTS = "components";
	private String TYPE = "type";
	private String POSITION = "position";
	private String ICON = "icon";
	private String ONCLICK = "onclick";
	private String MINI = "mini";
	private String TEXT = "text";
	private String IMAGE_SRC = "image-src";
	private String WIDTH = "width";
	private String HEIGHT = "height";
	private String ROTATION = "rotation";
	private String SHOW_PERMISSION = "show-permission";
	private String HIDE_PERMISSION = "hide-permission";
	private String SYMMETRICAL = "symmetrical";
	private String ALWAYS_SHOW_LABEL = "always-show-label";
	private String ONCLICK_SOUND = "onclick-sound";
	private String ONCLICK_SOUND_VOLUME = "onclick-sound-volume";
	private String ENTITY_TYPE = "entity-type";
	private String YAW = "yaw";
	private String EXECUTE_COMMAND_AS_CONSOLE = "execute-command-as-console";
	private String CLOSE_ON_PLAYER_MOVE = "close-on-player-move";
	private String CLOSE_ON_PLAYER_ITEM_SWITCH = "close-on-player-item-switch";
	private String VALUE = "value";
	private String ICON_ON = "icon-on";
	private String ICON_OFF = "icon-off";
	private String ONCLICK_ON = "onclick-on";
	private String ONCLICK_OFF = "onclick-off";
	private String EXECUTE_ONCLICK_ON_AS_CONSOLE = "execute-onclick-on-as-console";
	private String EXECUTE_ONCLICK_OFF_AS_CONSOLE = "execute-onclick-off-as-console";
	private String ON_VALUE = "on-value";
	private String OFF_VALUE = "off-value";
	private String DEFAULT_TEXT = "default-text";
	private String EVALUATE_PLACEHOLDERS = "evaluate-placeholders";
	private String DEFAULT_VALUE = "default-value";
	private String STEP = "step";
	private String ONSCROLL_SOUND = "onscroll-sound";
	private String ONSCROLL_SOUND_VOLUME = "onscroll-sound-volume";
	private String VALUE_TYPE = "value-type";
	private String MIN_VALUE = "min-value";
	private String MAX_VALUE = "max-value";
	private String WRAP_VALUE = "wrap-value";
	private String DECIMAL_FORMAT = "decimal-format";
	private String CLICK_PERMISSION = "click-permission";
	private String NO_PERMISSION_MESSAGE = "no-permission-message";
	private String CLICKABLE = "clickable";
	
	private static ConfigManager instance;
			
	private ConfigManager(){}
	
	public class Image
	{
		private String imageName;
		private int width;
		private int height;
		
		public Image(String imageName, int width, int height)
		{
			this.imageName = imageName;
			this.width = width;
			this.height = height;
		}
		
		public String getName()
		{
			return imageName;
		}
		
		public int getWidth()
		{
			return width;
		}
		
		public int getHeight()
		{
			return height;
		}
	}
	
	public static ConfigManager getInstance()
	{
		if(instance == null)
		{
			instance = new ConfigManager();
		}
		
		return instance;
	}
	
	@SuppressWarnings("deprecation")
	public HashMap<String, YamlConfiguration> loadYamlConfigurations(HoloGUIPlugin holoGUIPlugin)
	{
		HashMap<String, YamlConfiguration> yamls = new HashMap<String, YamlConfiguration>();
		
		for(String yamlFile : holoGUIPlugin.getYamlFilenames())
		{
			try
			{
				YamlConfiguration yaml = new YamlConfiguration();
				yaml.load(new FileInputStream(String.format("plugins/%s/gui configuration files/%s",
						holoGUIPlugin.getName(), yamlFile)));
				
				yamls.put(yamlFile, yaml);
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		return yamls;
	}
	
	/**
	 * Resource images should be stored in 'resources/images' in the plugin jar
	 * 
	 * Loads and processes the specified imageName. Returns the image as a String[][]. Returns null if the file couldn't be found.
	 */
	private String[][] loadImage(HoloGUIPlugin holoGUIPlugin, String imageName, int width, int height, boolean symmetrical)
	{
		String[][] iconLines = null;
		
		try
		{
			InputStream inputStream = holoGUIPlugin.getResource(HoloGUIApi.PATH_TO_IMAGES + "/" + imageName);

			if(imageName.contains(".gif"))
			{
				iconLines = GifProcessor.processGif(imageName, inputStream, width, height, symmetrical);
			}
			else if(imageName.contains(".jpg") || imageName.contains(".png"))
			{
				iconLines = PngJpgProcessor.processImage(imageName, inputStream, width, height, symmetrical);
			}
			inputStream.close();
		}
		catch(Exception e){}
		
		return iconLines;
	}

	/*
	 * Loads values in config.yml
	 */
	public void loadConfigValues(final CommandSender sender, final HoloGUIApi holoGUI)
	{	
		MessageManager.info(sender, "[HoloGUI] Loading config values...");
		
		FileConfiguration config = holoGUI.getConfig();
		ConfigurationSection root = config.getRoot();
		
		holoGUI.reloadConfig();
		
		HoloGUIApi.stationaryDisplayRenderDistance = root.getDouble(RENDER_DISTANCE, 25);
		if(HoloGUIApi.stationaryDisplayRenderDistance > 50) HoloGUIApi.stationaryDisplayRenderDistance = 50;
		else if(HoloGUIApi.stationaryDisplayRenderDistance < 0) HoloGUIApi.stationaryDisplayRenderDistance = 10;
	}
	
	public void writePropertyToConfigFile(String fileName, String path, Object value)
	{
		try
		{
			File configFile = new File(fileName);
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(configFile);
			yaml.set(path, value);
			yaml.save(configFile);
			
		}catch(Exception e){MessageManager.error(Bukkit.getConsoleSender(), "[HoloGUI] Error saving values to " + fileName + ". Does the file still exist?");}
	}
	
	/**
	 * Aysnc method that loads the gui containers from config for the given HoloGUIPlugin
	 * @param sender
	 * @param holoGUIPlugin
	 */
	public void loadGUIContainers(final CommandSender sender, final HoloGUIPlugin holoGUIPlugin)
	{
		holoGUIPlugin.setGUIPagesLoaded(false);
		
		final HashMap<String, GUIPage> guiPages = new HashMap<String, GUIPage>();
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				HashMap<String, YamlConfiguration> yamlFiles = loadYamlConfigurations(holoGUIPlugin);
				
				String[] yamls = new String[yamlFiles.size()];
				yamlFiles.keySet().toArray(yamls);

				for(String filename : yamls)
				{
					YamlConfiguration guiConfig = yamlFiles.get(filename);
					String[] keys = new String[1];
					ConfigurationSection root = guiConfig.getRoot();
					root.getKeys(false).toArray(keys);
					
					if(keys[0] != null)
					{
						String key = keys[0];//id of the guiContainer
						
						ConfigurationSection guiContainerSection = root.getConfigurationSection(key);
						String item = guiContainerSection.getString(OPEN_WITH_ITEM_ID);
						String itemName = guiContainerSection.getString(OPEN_WITH_ITEM_NAME);
						
						ItemStack openItem = null;
						try
						{
							openItem = new ItemStack(Material.valueOf(item.toUpperCase()), 1);
						}catch(Exception e){}
						
						boolean openOnLogin = guiContainerSection.getBoolean(OPEN_ON_LOGIN, false);
						String showPermission = guiContainerSection.getString(SHOW_PERMISSION);
						if(showPermission != null && showPermission.equals("")) showPermission = null;
						String hidePermission = guiContainerSection.getString(HIDE_PERMISSION);
						if(hidePermission != null && hidePermission.equals(""))hidePermission = null;
						
						boolean closeOnPlayerMove = guiContainerSection.getBoolean(CLOSE_ON_PLAYER_MOVE);
						boolean closeOnPlayerItemSwitch = guiContainerSection.getBoolean(CLOSE_ON_PLAYER_ITEM_SWITCH);

						ConfigurationSection componentsSection = guiContainerSection.getConfigurationSection(COMPONENTS);
						if(componentsSection != null)
						{
							GUIPage guiPage = new GUIPage(holoGUIPlugin, loadComponents(holoGUIPlugin, componentsSection, key), key,filename, openItem, 
									itemName, openOnLogin, showPermission, hidePermission, closeOnPlayerMove, closeOnPlayerItemSwitch);
							
							guiPages.put(key, guiPage);
						}
					}
				}
				
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						holoGUIPlugin.setGUIPages(guiPages);
						holoGUIPlugin.setGUIPagesLoaded(true);
						
						GUIPagesLoadedEvent guiPagesLoadedEvent = new GUIPagesLoadedEvent(holoGUIPlugin);
						Bukkit.getPluginManager().callEvent(guiPagesLoadedEvent);
						
						MessageManager.success(sender, "[" + holoGUIPlugin.getName() + "] GUI pages loaded");
					}
				}.runTask(holoGUIPlugin);
			}
		}.runTaskAsynchronously(holoGUIPlugin);
	}
	
	private HashMap<String, GUIComponent> loadComponents(HoloGUIPlugin holoGUIPlugin, ConfigurationSection componentsSection, String parentId)
	{
		HashMap<String, GUIComponent> guiComponents = new HashMap<String, GUIComponent>();
		
		for(String componentId : componentsSection.getKeys(false))
		{
			ConfigurationSection guiComponentSection = componentsSection.getConfigurationSection(componentId); 
			ConfigurationSection positionSection = guiComponentSection.getConfigurationSection(POSITION);
			String type = guiComponentSection.getString(TYPE);
			String componentLabel = guiComponentSection.getString(LABEL);
			
			if(type == null)
			{
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "The 'type' of GUI component: " + ChatColor.AQUA + componentId + 
						ChatColor.RED + " is missing!");
				
				continue;
			}
			if(positionSection == null)
			{
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "The position of GUI component: " + ChatColor.AQUA + componentId + 
						ChatColor.RED + " is missing!");
				
				continue;
			}
			
			double x = positionSection.getDouble(X, 0);
			double y = positionSection.getDouble(Y, 0);
			boolean alwaysShowLabel = guiComponentSection.getBoolean(ALWAYS_SHOW_LABEL, false);
			boolean hidden = guiComponentSection.getBoolean(HIDDEN, false);
			
			if(holoGUIPlugin.getName().equals("HoloGUI"))
			{
				if(x < -1) x = -1;
				if(x > 1) x = 1;
				if(y < -1) y = 1;
				if(y > 1) y = 1;
			}

			x *= -1;
			if(type.equals("item")) y+= 0.1;//give item components a little bit more height (makes it look a little better)
			ComponentPosition position = new ComponentPosition(x, y);
			
			//map component type to label distance to get default value 
			LabelDistances defaultComponentTypeLabelDistance = null;
			try
			{
				defaultComponentTypeLabelDistance = LabelDistances.valueOf(type.replace("-", "_").toUpperCase());
			}
			catch(IllegalArgumentException e){}
			
			double defaultLabelDistance = (defaultComponentTypeLabelDistance != null) ? defaultComponentTypeLabelDistance.getLabelDistance() : 15;
			double labelDistance = guiComponentSection.getDouble(LABEL_DISTANCE, defaultLabelDistance);
			if(labelDistance < 5) labelDistance = 5;
			if(labelDistance > 20) labelDistance = 20;
			
			GUIComponentProperties properties = new GUIComponentProperties(holoGUIPlugin, componentId, parentId, position, componentLabel, labelDistance,
					alwaysShowLabel, hidden);
						
			String onclick = guiComponentSection.getString(ONCLICK);

			//map component type to label zoom distance to get default value
			LabelZoomDistances defaultComponentTypeLabelZoomDistance = null;
			try
			{
				defaultComponentTypeLabelZoomDistance = LabelZoomDistances.valueOf(type.replace("-", "_").toUpperCase());
			}
			catch(IllegalArgumentException e){}
			
			double defaultLabelZoomDistance = (defaultComponentTypeLabelZoomDistance != null) ? defaultComponentTypeLabelZoomDistance.getZoomDistance() : 2;
			double labelZoomDistance = guiComponentSection.getDouble(LABEL_ZOOM_DISTANCE, defaultLabelZoomDistance);
			if(labelZoomDistance < 0) labelZoomDistance = 0;
			else if(labelZoomDistance > 6) labelZoomDistance = 6;
			
			boolean executeCommandAsConsole = guiComponentSection.getBoolean(EXECUTE_COMMAND_AS_CONSOLE, false);
			String sound = guiComponentSection.getString(ONCLICK_SOUND, null);
			float onclickSoundVolume = (float)guiComponentSection.getDouble(ONCLICK_SOUND_VOLUME, 0.5);
			if(onclickSoundVolume > 1) onclickSoundVolume = 1;
			else if(onclickSoundVolume < 0) onclickSoundVolume = 0;
			
			Sound onclickSound = parseSound(sound);
			
			String clickPermission = guiComponentSection.getString(CLICK_PERMISSION);
			String noPermissionMessage = guiComponentSection.getString(NO_PERMISSION_MESSAGE);
			if(noPermissionMessage != null)
			{
				noPermissionMessage = noPermissionMessage.replace("&", "§");
			}
			
			ClickableGUIComponentProperties clickableProperties = new ClickableGUIComponentProperties(onclick, executeCommandAsConsole, onclickSound, onclickSoundVolume, labelZoomDistance,
					clickPermission, noPermissionMessage);
			
			if(type.equalsIgnoreCase("button"))
			{
				String icon = guiComponentSection.getString(ICON);
				boolean symmetrical = guiComponentSection.getBoolean(SYMMETRICAL, false);
				boolean mini = guiComponentSection.getBoolean(MINI, false);
								
				if(icon != null)
				{
					int imgSize = 18;
					if(mini) imgSize = 9;
					
					String[][] imagePixels = loadImage(holoGUIPlugin, icon, imgSize, imgSize, symmetrical);
					
					if(imagePixels != null)
					{
						ButtonComponent button = new ButtonComponent(properties, clickableProperties, imagePixels, icon, mini, imgSize, imgSize);
						guiComponents.put(componentId, button);
					}
				}
			}
			else if(type.equalsIgnoreCase("label"))
			{
				ArrayList<String> text = (ArrayList<String>) guiComponentSection.getStringList(TEXT);
				String[] lines = new String[text.size()];
				for(int i = 0; i < lines.length; i++)
				{
					lines[i] = text.get(i);
				}
				
				LabelComponent labelComponent = new LabelComponent(properties, lines);
				guiComponents.put(componentId, labelComponent);
			}
			else if(type.equalsIgnoreCase("image"))
			{	
				int width = guiComponentSection.getInt(WIDTH, 18);
				int height = guiComponentSection.getInt(HEIGHT, 18);
				boolean symmetrical = guiComponentSection.getBoolean(SYMMETRICAL, false);
				
				String imageSrc = guiComponentSection.getString(IMAGE_SRC);
				
				if(imageSrc != null)
				{
					String[][] imageLines = loadImage(holoGUIPlugin, imageSrc, width, height, symmetrical);
					
					if(imageLines != null)
					{
						ImageComponent imageComponent = new ImageComponent(properties, imageLines, imageSrc, width, height);
						guiComponents.put(componentId, imageComponent);
					}
				}
			}
			else if(type.equalsIgnoreCase("item"))
			{
				String itemId = guiComponentSection.getString(ITEM_ID);
				if(itemId != null)
				{
					String[] tokens = itemId.split(":");
					
					itemId = tokens[0];
					short itemData = 0;
					try
					{
						if(tokens.length == 2)
						{
							itemData = Short.parseShort(tokens[1]);
						}
					}catch(Exception e){}
					
					try
					{
						Material itemMaterial = Material.valueOf(itemId.toUpperCase());
						ItemStack item = new ItemStack(itemMaterial, 1, itemData);
						
						ConfigurationSection rotationSection = guiComponentSection.getConfigurationSection(ROTATION);
						
						double xRotation = rotationSection.getDouble(X, 0);
						double yRotation = rotationSection.getDouble(Y, 0);
						double zRotation = rotationSection.getDouble(Z, 0);
						
						if(itemMaterial.isBlock())
						{
							xRotation += 75;
							yRotation += 45;
						}
					
						Vector rotation = new Vector(xRotation, yRotation, zRotation);
						
						boolean clickable = guiComponentSection.getBoolean(CLICKABLE, false);
						
						if(clickable)
						{
							ItemButtonComponent itemButtonComponent = new ItemButtonComponent(properties, clickableProperties, item, rotation);
							guiComponents.put(componentId, itemButtonComponent);
						}
						else
						{
							ItemComponent itemComponent = new ItemComponent(properties, item, rotation);
							guiComponents.put(componentId, itemComponent);
						}
					}
					catch(Exception e){}
				}
			}
			else if(type.equalsIgnoreCase("entity"))
			{	
				String entityTypeStr = guiComponentSection.getString(ENTITY_TYPE);
				float yaw = (float)guiComponentSection.getDouble(YAW, 0);
				
				if(entityTypeStr != null)
				{
					EntityType entityType = null;
					try
					{
						entityType = EntityType.valueOf(entityTypeStr);
					}
					catch(Exception e){}
					
					if(entityType != null)
					{
						boolean clickable = guiComponentSection.getBoolean(CLICKABLE, false);
						
						if(clickable)
						{
							EntityButtonComponent entityComponent = new EntityButtonComponent(properties, clickableProperties, entityType, yaw);
							guiComponents.put(componentId, entityComponent);
						}
						else
						{
							EntityComponent entityComponent = new EntityComponent(properties, entityType, yaw);
							guiComponents.put(componentId, entityComponent);	
						}
					}
				}
			}
			else if(type.equals("toggle-switch"))
			{	
				boolean value = guiComponentSection.getBoolean(VALUE, false);
				String onclickOn = guiComponentSection.getString(ONCLICK_ON, null);
				String onclickOff = guiComponentSection.getString(ONCLICK_OFF, null);
				String iconOn = guiComponentSection.getString(ICON_ON, "default-toggle-on.png");
				String iconOff = guiComponentSection.getString(ICON_OFF, "default-toggle-off.png");
				boolean executeOnclickOnAsConsole = guiComponentSection.getBoolean(EXECUTE_ONCLICK_ON_AS_CONSOLE, false);
				boolean executeOnclickOffAsConsole = guiComponentSection.getBoolean(EXECUTE_ONCLICK_OFF_AS_CONSOLE, false);
				String onValue = guiComponentSection.getString(ON_VALUE);
				String offValue = guiComponentSection.getString(OFF_VALUE);

				String[][] onLines = loadImage(holoGUIPlugin, iconOn, 13, 13, true);
				String[][] offLines = loadImage(holoGUIPlugin, iconOff, 13, 13, true);
				
				if(onLines != null && offLines != null)
				{
					ToggleSwitchComponent toggleSwitch = new ToggleSwitchComponent(properties, clickableProperties, onLines, offLines, value, onclickOn, onclickOff, iconOn, 
							iconOff, executeOnclickOnAsConsole, executeOnclickOffAsConsole, onValue, offValue);
					
					guiComponents.put(componentId, toggleSwitch);
				}
			}
			else if(type.equals("text-box"))
			{
				boolean evaluatePlaceholders = guiComponentSection.getBoolean(EVALUATE_PLACEHOLDERS, false);
				
				String defaultValue = guiComponentSection.getString(DEFAULT_TEXT, "");
				TextBoxComponent textBoxComponent = new TextBoxComponent(properties, clickableProperties, defaultValue, evaluatePlaceholders);
				
				guiComponents.put(componentId, textBoxComponent);
			}
			else if(type.equals("value-scroller"))
			{
				String valueType = guiComponentSection.getString(VALUE_TYPE);
				if(valueType != null)
				{	
					String onscrollSoundText = guiComponentSection.getString(ONSCROLL_SOUND, null);
					Sound onscrollSound = parseSound(onscrollSoundText);
					float onscrollSoundVolume = (float)guiComponentSection.getDouble(ONSCROLL_SOUND_VOLUME, 0.5);
					if(onscrollSoundVolume > 1) onscrollSoundVolume = 1;
					else if(onscrollSoundVolume < 0) onscrollSoundVolume = 0;
					
					boolean wrap = guiComponentSection.getBoolean(WRAP_VALUE, false);
					
					AbstractScrollValue<?, ?> componentValue = null;
					
					if(valueType.equalsIgnoreCase("decimal"))
					{
						double defaultValue = guiComponentSection.getDouble(DEFAULT_VALUE, 0);
						double step = guiComponentSection.getDouble(STEP, 1);
						String decimalFormat = guiComponentSection.getString(DECIMAL_FORMAT, "#.#");
						
						Double minValue = null;
						Double maxValue = null;
						if(guiComponentSection.isSet(MIN_VALUE))minValue = guiComponentSection.getDouble(MIN_VALUE, 0);
						if(guiComponentSection.isSet(MAX_VALUE))maxValue = guiComponentSection.getDouble(MAX_VALUE, 0);
						
						componentValue = new DoubleScrollValue(defaultValue, step, minValue, maxValue, decimalFormat, wrap);
					}
					else if(valueType.equalsIgnoreCase("integer"))
					{
						int defaultValue = guiComponentSection.getInt(DEFAULT_VALUE);
						int step = guiComponentSection.getInt(STEP, 1);
						
						Integer minValue = null;
						Integer maxValue = null;
						
						if(guiComponentSection.isSet(MIN_VALUE))minValue = guiComponentSection.getInt(MIN_VALUE, 0);
						if(guiComponentSection.isSet(MAX_VALUE))maxValue = guiComponentSection.getInt(MAX_VALUE, 0);
						
						componentValue = new IntegerScrollValue(defaultValue, step, minValue, maxValue, wrap);
					}
					else if(valueType.equalsIgnoreCase("duration"))
					{
						Duration defaultValue = null;
						try
						{
							if(guiComponentSection.getString(DEFAULT_VALUE) != null)defaultValue = TimeFormat.parseDurationFormat(guiComponentSection.getString(DEFAULT_VALUE));
						} 
						catch (InvalidDurationFormatException e) 
						{
							defaultValue = TimeFormat.getMinDuration();
						}
						Duration step = null;
						try
						{
							if(guiComponentSection.getString(STEP) != null)step = TimeFormat.parseDurationFormat(guiComponentSection.getString(STEP, "00:00:01"));
						} 
						catch (InvalidDurationFormatException e) 
						{
							step = TimeFormat.getMinDuration().plusSeconds(1);
						}
						Duration minValue = null;
						try
						{
							if(guiComponentSection.getString(MIN_VALUE) != null)minValue = TimeFormat.parseDurationFormat(guiComponentSection.getString(MIN_VALUE, "00:00:00"));
						}
						catch (InvalidDurationFormatException e)
						{
							minValue = TimeFormat.getMinDuration();
						}
						Duration maxValue = null;
						try 
						{
							if(guiComponentSection.getString(MAX_VALUE) != null)maxValue = TimeFormat.parseDurationFormat(guiComponentSection.getString(MAX_VALUE, "99:59:59"));
						} catch (InvalidDurationFormatException e)
						{
							maxValue = TimeFormat.getMaxDuration();
						}
						componentValue = new DurationScrollValue(defaultValue, step, minValue, maxValue, wrap);
					}
					else if(valueType.equalsIgnoreCase("date"))
					{
						Calendar defaultValue = null;
						String dateString = guiComponentSection.getString(DEFAULT_VALUE);
						if(dateString != null)
						{
							try
							{
								defaultValue = TimeFormat.parseDateFormat(dateString);
							}
							catch(InvalidDateFormatException e)
							{
								defaultValue = Calendar.getInstance();
							}
						}
						
						Duration step;
						String stepString = guiComponentSection.getString(STEP);
						if(stepString != null)
						{
							try
							{
								step = TimeFormat.parseDurationFormat(stepString);
							}
							catch(InvalidDurationFormatException e)
							{
								step = TimeFormat.getMinDuration().plusDays(1);
							}
						}
						else
						{
							step = TimeFormat.getMinDuration().plusDays(1);
						}
						
						Calendar minValue = null;
						String minValueString = guiComponentSection.getString(MIN_VALUE);
						if(minValueString != null)
						{
							try
							{
								minValue = TimeFormat.parseDateFormat(minValueString);
							}
							catch(InvalidDateFormatException e)
							{
								minValue =Calendar.getInstance();
							}
						}
						
						Calendar maxValue = null;
						String maxValueString = guiComponentSection.getString(MAX_VALUE);
						if(maxValueString != null)
						{
							try
							{
								maxValue = TimeFormat.parseDateFormat(maxValueString);
							}
							catch(Exception e)
							{
								maxValue = Calendar.getInstance();
							}
						}

						componentValue = new DateScrollValue(defaultValue, step, minValue, maxValue, wrap);
					}
					else if(valueType.equals("list"))
					{
						List<String> list = guiComponentSection.getStringList(DEFAULT_VALUE);
						
						componentValue = new ListScrollValue(list);
					}
					
					ValueScrollerComponent valueScroller = new ValueScrollerComponent(properties, clickableProperties, onscrollSound, onscrollSoundVolume, componentValue);
					
					guiComponents.put(componentId, valueScroller);
				}
			}
		}

		return guiComponents;
	}
	
	public static Sound parseSound(String soundStr)
	{
		Sound sound = null;
		try
		{
			sound = Sound.valueOf(soundStr);
		}
		catch(Exception e){}
		
		return sound;
	}
	
	/*
	 * Enum of default label distances by component type
	 */
	private enum LabelDistances
	{
		BUTTON(15),
		IMAGE(15),
		LABEL(10),
		ITEM(6),
		ENTITY(8),
		TOGGLE_SWITCH(15),
		TEXT_BOX(10),
		VALUE_SCROLLER(10);
		
		private double distance;
		LabelDistances(double distance)
		{
			this.distance = distance;
		}
		
		public double getLabelDistance()
		{
			return distance;
		}
	}
	
	/*
	 * Enum of default label zoom distances by component type
	 */
	private enum LabelZoomDistances
	{
		BUTTON(4),
		IMAGE(0),
		LABEL(0),
		ITEM(1.3),
		ENTITY(1.3),
		TOGGLE_SWITCH(4),
		TEXT_BOX(2),
		VALUE_SCROLLER(2);
		
		private double zoomDistance;
		LabelZoomDistances(double zoomDistance)
		{
			this.zoomDistance = zoomDistance;
		}
		
		public double getZoomDistance()
		{
			return zoomDistance;
		}
	}
}