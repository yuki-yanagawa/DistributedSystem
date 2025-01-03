package myteam.distributednet2.privateclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PrivatePropertiesReader {
	private static final String PATH = "properties/privateclient.properties";
	private static Properties prop;
	static {
		Path p = Paths.get(PATH);
		try(FileInputStream fis = new FileInputStream(p.toFile())){
			prop = new Properties();
			prop.load(fis);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public static Object getValue(Object key) {
		return prop.get(key);
	}

	public static <T> T getValueSttingDataType(Object key) {
		return (T)prop.get(key);
	}
}
