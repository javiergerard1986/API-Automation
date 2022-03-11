package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	
	private static PropertiesLoader instance;
	
	private PropertiesLoader() {
	}
	
	public static PropertiesLoader getInstance(){
		if(instance == null) {
			instance = new PropertiesLoader();
		}
		return instance;
	}
	
	public String getGlobalValue(String key) {
		Properties properties = new Properties();
		
		try {
			InputStream fis = PropertiesLoader.class.getResourceAsStream("global.properties");
			properties.load(fis);
		}catch(FileNotFoundException ex) {
			System.out.println("File not found: " + ex.getMessage());
		}catch(IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		return properties.getProperty(key);
	}
	
}
