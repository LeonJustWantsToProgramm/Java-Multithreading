package threads;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigCon {

	static Path path = Paths.get("Main");
	static Path directoryPath = path.toAbsolutePath();
	
	public String getMode() throws IOException {
		
		String Mode = null;
				
		Properties config = new Properties();
		
		FileInputStream file;
		
		String path = directoryPath.getParent().toString() + "/config.properties";
		
		Main.logger.debug("trying to get the Mode in the config file in path: " + path);
		
		file = new FileInputStream(path);
		
		config.load(file);
		
		file.close();
		
		Mode = config.getProperty("mode");
		
		return Mode;
	}
	
	
	public String getThreadCount() throws IOException {
		
		String threadCount = null;
		
		Properties config = new Properties();
		
		FileInputStream file;
		
		String path = directoryPath.getParent().toString() + "/config.properties";
		
		Main.logger.debug("trying to get the Threadcount in the config file in path: " + path);
		
		file = new FileInputStream(path);
		
		config.load(file);
		
		file.close();
		
		threadCount = config.getProperty("thread.count");
		
		return threadCount;
	}
	
}
