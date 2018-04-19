package darun.csvloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
	
	private static Properties config = null;
	
	public static Properties getConfig() {
        FileInputStream fis = null;
        if(config == null){
        	config = new Properties();
        	try {
	            fis = new FileInputStream("config.properties");
	            config.load(fis);
	            fis.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
		return config;
	}
}
