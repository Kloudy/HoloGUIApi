package com.antarescraft.kloudy.hologuiapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.GifProcessor;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.PngJpgProcessor;
import com.antarescraft.kloudy.hologuiapi.util.ConfigManager;

import net.md_5.bungee.api.ChatColor;

/**
 * Represents an external plugin using the HoloGUIAPI to create GUI pages
 */
public abstract class HoloGUIPlugin extends JavaPlugin
{	
	public static String PATH_TO_YAMLS;
	public static String PATH_TO_IMAGES;
	
	private HashMap<String, GUIPage> guiPages = new HashMap<String, GUIPage>();
	private ArrayList<String> resourceYamlFilenames = new ArrayList<String>(); // Array of resource yaml filenames within the jar
	private ArrayList<String> resourceImageFilenames = new ArrayList<String>(); // Array of resource image filenames within the jar
	private boolean guiPagesLoaded;
	private String minSupportedApiVersion = "1.0";
	
	public HoloGUIPlugin()
	{
		PATH_TO_YAMLS = "plugins/" + getName() + "/gui configuration files";
		PATH_TO_IMAGES = "plugins/" + getName() + "/images";
		
		guiPagesLoaded = false;
				
 		initFileStructure();
 		
		
		// Pull out all resource files from the jar and save them to the correct plugin data folder.
		CodeSource source = this.getClass().getProtectionDomain().getCodeSource();
		if(source != null) 
		{
		    URL jar = source.getLocation();
		    try
		    {
		    	ZipInputStream zip = new ZipInputStream( jar.openStream());
			    ZipEntry entry = null;
			    
				while((entry = zip.getNextEntry()) != null)
				{
				    String entryName = entry.getName();
				    
				    // Yaml files
				    if(entryName.startsWith(HoloGUIApi.PATH_TO_YAMLS) &&  entryName.endsWith(".yml") )
				    {
				    	String[] pathTokens = entryName.split("/");
				        resourceYamlFilenames.add(pathTokens[pathTokens.length-1]);
				    }
				    
				    // Image files
				    if(entryName.startsWith(HoloGUIApi.PATH_TO_RESOURCE_IMAGES) && 
				    		(entryName.endsWith(".png") || entryName.endsWith(".jpg") || entryName.endsWith(".gif")))
				    {
				    	String[] pathTokens = entryName.split("/");
				    	resourceImageFilenames.add(pathTokens[pathTokens.length-1]);
				    }
				}
				
				copyResourceConfigs();
				copyResourceImages();
				
			} catch (IOException e) 
		    {
				e.printStackTrace();
			}
		 }
	}
	
	/**
	 * Set the minimum version of HoloGUIApi that the plugin can run on
	 */
	public void setMinSupportedApiVersion(String version)
	{
		this.minSupportedApiVersion = version;
	}
	
	/**
	 * Checks to see if the version of HoloGUIApi installed on the server is at least
	 * the minSupportedApiVersion for this plugin.
	 * 
	 * If the HoloGUIApi version is too old this method will unload the plugin
	 * and display an error message in the console alerting the server owner to 
	 * update HoloGUIApi.
	 */
	public void checkMinApiVersion()
	{
		if(minSupportedApiVersion == null) minSupportedApiVersion = "1.0";
		
		if(versionCompare(getHoloGUIApi().getDescription().getVersion(), minSupportedApiVersion) < 0)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + String.format("[%s]: HoloGUIApi-v%s is required to run the plugin. Please update HoloGUIApi with the latest version from Spigot.",
					getName(), minSupportedApiVersion));
		}
	}
	
	private int versionCompare(String v1, String v2) 
	{
		if(v1.equals(v2)) return 0;
		
	    String[] v1Tokens = v1.split("\\.");
	    String[] v2Tokens = v2.split("\\.");
	    
	    int length = v1Tokens.length;
	    if(v2Tokens.length > v1Tokens.length) length = v2Tokens.length;
	    
	    for(int i = 0; i < length; i++)
	    {
	    	if(i  >= v1Tokens.length || i >= v2Tokens.length )//reached the end, return the longer version number
	    	{
	    		return (v1Tokens.length > v2Tokens.length) ? 1 : -1;
	    	}
	    	
	    	int v1Value = Integer.parseInt(v1Tokens[i]);
	    	int v2Value = Integer.parseInt(v2Tokens[i]);
	    	
	    	if(v1Value > v2Value)
	    	{
	    		return 1;
	    	}
	    	else if(v1Value < v2Value)
	    	{
	    		return -1;
	    	}
	    }
	    
	    return 0;
	}
	
	/**
	 * @return ArrayList<String> of all yaml files contained in /resources/yamls in the plugin jar
	 */
	public ArrayList<String> getYamlFilenames()
	{
		return resourceYamlFilenames;
	}
	
	/**
	 * @return an instance of the HoloGUIApi class.
	 */
	public HoloGUIApi getHoloGUIApi()
	{
		return (HoloGUIApi) Bukkit.getServer().getPluginManager().getPlugin("HoloGUIApi");
	}
	
	/**
	 * @return A HashMap<String, GUIPage>, where the key is the gui page id and the value is the GUIPage object
	 */
	public HashMap<String, GUIPage> getGUIPages()
	{
		return guiPages;
	}
	
	/**
	 * 
	 * @param guiPageId
	 * @return GUIPage object with the given guiPageId. returns null if no gui page exists with the given guiPageId
	 */
	public GUIPage getGUIPage(String guiPageId)
	{
		return guiPages.get(guiPageId);
	}
	
	/**
	 * Returns the GUIComponent with the specified guiContainerId and guiComponentId.
	 * 
	 * @param guiPageId Id of the GUIPage containing the component
	 * @param guiComponentId Id of the GUIComponent
	 * @return The GUIComponent contained in the guiContainer with the specified id
	 */
	public GUIComponent getGUIComponent(String guiPageId, String guiComponentId)
	{
		GUIPage guiPage = getGUIPages().get(guiPageId);
		if(guiPage == null) return null;
		
		return guiPage.getComponent(guiComponentId);
	}
	
	public boolean guiPagesLoaded()
	{
		return guiPagesLoaded;
	}
	
	public void setGUIPagesLoaded(boolean guiPagesLoaded)
	{
		this.guiPagesLoaded = guiPagesLoaded;
	}
	
	public void addGUIPage(String guiContainerId, GUIPage guiContainer)
	{
		guiPages.put(guiContainerId, guiContainer);
	}
	
	public void setGUIPages(HashMap<String, GUIPage> guiContainers)
	{
		this.guiPages = guiContainers;
	}
	
	/**
	 * Causes HoloGUI to load the gui containers async from yaml for this HoloGUIPlugin.
	 * A HoloGUIPagesLoadedEvent is triggered after the config values are finished loading
	 */
	public void loadGUIPages()
	{
		loadGUIPages(null);
	}
	
	/**
	 * Causes HoloGUI to load the gui containers async from yaml for this HoloGUIPlugin.
	 * A HoloGUIPagesLoadedEvent is triggered after the config values are finished loading
	 * 
	 * @param callback callback function that executes when the guipages have finished loading
	 */
	public void loadGUIPages(final GUIPageLoadComplete callback)
	{
		guiPagesLoaded = false;
		guiPages.clear();
		
		final HoloGUIPlugin self = this;
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				// Load all the yamls in the plugin data folder.
				File yamlsFolder = new File(PATH_TO_YAMLS);
				for(File file : yamlsFolder.listFiles())
				{
					if(file.getName().endsWith(".yml"))
					{
						GUIPage guiPage = ConfigManager.loadGUIPage(self, file);
						
						guiPages.put(guiPage.getId(), guiPage);
					}
				}
				
				if(callback != null)
				{
					callback.onGUIPageLoadComplete();
				}
				
				guiPagesLoaded = true;
			}
		}.runTaskAsynchronously(this);
	}
	
	/**
	 * Copy resource images into the plugin image data folder
	 */
	public void copyResourceImages()
	{
		copyResourceImages(false);
	}
	
	/**
	 * @param overwriteExistingImages (true|false) if the plugin should override images files from plugin folder with the images from inside the jar
	 * Copies the resource images from the plugin jar to plugins/<holoGUI_plugin_name>/images folder
	 */
	public void copyResourceImages(boolean overwriteExistingImages)
	{
		for(String imageName : resourceImageFilenames)
		{
			try
			{
				InputStream inputStream = getResource("resources/images/" + imageName);
				File imagePath = new File(String.format("plugins/%s/images/%s", 
						getName(), imageName));
				
				if((!overwriteExistingImages && !imagePath.exists()) || overwriteExistingImages)
				{
					FileOutputStream output = new FileOutputStream(imagePath);
					output.write(IOUtils.toByteArray(inputStream));
					
					inputStream.close();
					output.close();
				}
			}
			catch(Exception e){e.printStackTrace();}
		}
	}

	/**
	 * * Copies the resource gui pages from the plugin jar to plugins/<holoGUI_plugin_name>/gui configuration files folder
	 */
	public void copyResourceConfigs()
	{
		copyResourceConfigs(false);
	}
	
	/**
	 * @param overwriteExistingYamls (true|false) if the plugin should override yaml files from plugin folder with the yamls from inside the jar
	 * Copies the resource gui pages from the plugin jar to plugins/<holoGUI_plugin_name>/gui configuration files folder
	 */
	public void copyResourceConfigs(boolean overwriteExistingYamls)
	{
		for(String yamlName : resourceYamlFilenames)
		{
			try
			{
				InputStream inputStream = getResource("resources/yamls/" + yamlName);
				File yamlPath = new File(String.format("plugins/%s/gui configuration files/%s", 
						getName(), yamlName));
				
				if((!overwriteExistingYamls && !yamlPath.exists()) || overwriteExistingYamls)
				{
					FileOutputStream output = new FileOutputStream(yamlPath);
					output.write(IOUtils.toByteArray(inputStream));
					
					inputStream.close();
					output.close();
				}
			}
			catch(Exception e){e.printStackTrace();}
		}
	}
	
	
	/**
	 * Resource images should be stored in 'resources/images' in the plugin jar
	 * 
	 * Loads and processes the specified imageName. Returns the image as a String[][]. Returns null if the file couldn't be found.
	 */
	public String[][] loadImageFromResource(String imageName, int width, int height, boolean symmetrical)
	{		
		InputStream inputStream = getResource(HoloGUIApi.PATH_TO_RESOURCE_IMAGES + "/" + imageName);
			
		return processImage(imageName, inputStream, width, height, symmetrical);
	}
	
	/**
	 * Processes the image given an InputStream to the image.
	 * @return String[][] representing the image.
	 */
	private String[][] processImage(String imageName, InputStream inputStream, int width, int height, boolean symmetrical)
	{
		String[][] imageLines = null;
		
		if(inputStream == null) return null;
		
		if(imageName.endsWith(".gif"))
		{
			imageLines = GifProcessor.processGif(imageName, inputStream, width, height, symmetrical);
		}
		else if(imageName.endsWith(".jpg") || imageName.endsWith(".png"))
		{
			imageLines = PngJpgProcessor.processImage(imageName, inputStream, width, height, symmetrical);
		}
		
		try 
		{
			inputStream.close();
		} 
		catch (Exception e) 
		{
			System.out.println("Error loading resource: " + HoloGUIApi.PATH_TO_RESOURCE_IMAGES + "/" + imageName);

			e.printStackTrace();
		}
		
		return imageLines;
	}
	
	/**
	 * Loads an image from the images folder in the plugin data folder.
	 * @param imageName The image filename to load from file.
	 * @return InputStream for the image.
	 */
	public String[][] loadImageFromFile(String imageName, int width, int height, boolean symmetrical)
	{
		String[][] imageLines = null;
		
		if(imageName.contains(".jpg") || imageName.contains(".png") || imageName.contains(".gif"))
		{
			try 
			{
				InputStream inputStream = new FileInputStream(PATH_TO_IMAGES + "/" + imageName);
				
				imageLines = processImage(imageName, inputStream, width, height, symmetrical);
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		return imageLines;
	}
	
	/**
	 * Creates the plugin's data folder and gui_page_configs folder
	 */
	private void initFileStructure()
	{
		try
		{
			File folder = new File(String.format("plugins/%s", getName()));
			if(!folder.exists())
			{
				folder.mkdir();
			}
			
			folder = new File(String.format("plugins/%s/gui configuration files", getName()));
			if(!folder.exists())
			{
				folder.mkdir();
			}
			
			folder = new File(String.format("plugins/%s/images", getName()));
			if(!folder.exists())
			{
				folder.mkdir();
			}
		}
		catch(Exception e){}		
	}
}