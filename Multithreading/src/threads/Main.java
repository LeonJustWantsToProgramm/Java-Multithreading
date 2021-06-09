package threads;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	static Logger logger = LogManager.getLogger(Main.class);
	
	static ConfigCon config = new ConfigCon();
	
	public static void main(String[] args) throws Exception {
		
		logger.debug("Application started");
		if (config.getMode().equalsIgnoreCase("1")) {
			logger.info("Mode is set to 1, nothing will be executed");
		} else if (config.getMode().equalsIgnoreCase("2")) {
			
			logger.info("config file has been read and mode was set to 2");
			if (Integer.valueOf(config.getThreadCount()) >= 2) {
				logger.info("got the number of Threads: " + config.getThreadCount());
				System.out.println("This code is only running with multiple threads:");
				for(int i = 1; i < (Integer.valueOf(config.getThreadCount()) + 1); i++) {
					RunnableClass R = new RunnableClass("Thread " + i);
					R.start();
				}
				
			} else if (config.getThreadCount().equalsIgnoreCase("1")) {
				logger.info("got one thread.");
				try {
					for (int i = 1; i < 6; i++) {
						File myfile = new File("Thread " + i + "-test.txt");
						logger.debug("trying to create a new file...");
						if (myfile.createNewFile()) {
							logger.info("File created: " + myfile.getName());
						} else if(!myfile.createNewFile()) {
//							@SuppressWarnings("resource")
//							Scanner sc = new Scanner(System.in);
//							System.out.println("File/s already exists. Do you want to delete the existing file/s?");
//							String arg = sc.nextLine();
							String arg = "yes";
							while (!arg.equalsIgnoreCase("yes") || !arg.equalsIgnoreCase("no")) {
								
								if (arg.equalsIgnoreCase("yes")) {
									logger.warn("trying to delete multiple files...");
									File multipleFiles = new File(".");
									for (File file : multipleFiles.listFiles()) {
										if (file.getName().endsWith("-test.txt")) {
											file.delete();
										}
									}
									logger.info("File/s was/were deleted. ");
									myfile.createNewFile();
									logger.info("File created: " + myfile.getName());
									break;
//								} else if (arg.equalsIgnoreCase("no")) {
//									System.out.println("File was not deleted.");
//									break;
//								} else {
//									System.out.println("wrong input. please write \"yes\" or \"no\":");
//									arg = sc.nextLine();
								}
							}
						}
						try {
							logger.warn("trying to write the files");
							BufferedWriter myWriter = new BufferedWriter(new FileWriter(myfile.getName()));
							File exampleFile = new File("exampleText.txt");
							Scanner sc = new Scanner(exampleFile);
							while (sc.hasNextLine()) {
								myWriter.write(sc.nextLine());
								myWriter.newLine();
							}
							sc.close();
							logger.info("Successfully wrote to the file " + myfile.getName());
						} catch (FileNotFoundException Fe) {
							logger.error(Fe.getMessage());
						}
					}
				} catch (IOException Ioe) {
					logger.error("An error occured while creating / deleting the files:" + Ioe.getMessage());
				}
			}
		}			
	}
}