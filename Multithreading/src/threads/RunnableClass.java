package threads;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class RunnableClass implements Runnable {

	private Thread thread;
	private String threadName;
	static ConfigCon config = new ConfigCon();
		
	RunnableClass(String name) {
		threadName = name;
		Main.logger.debug("Creating " + threadName);
	}

	
	public void run() {
		Main.logger.debug("Running " + threadName);
		
		try {
			for (int i = 0; i < 4; i++) {
				System.out.println(threadName + " is writing: " + i);
				Thread.sleep(50);
			}	
		} catch (InterruptedException ie) {
			Main.logger.error(threadName + " interrupted");
		}
		System.out.println(threadName + " existing");
		
		try {
			Main.logger.warn("trying to create a new file...");
			File myfile = new File(threadName + "-test.txt");
			if (myfile.createNewFile()) {
				Main.logger.info(threadName + ": File created: " + myfile.getName());
			} else if(!myfile.createNewFile()) {
				Main.logger.warn("trying to delete existing files...");
				myfile.delete();
				Main.logger.info(threadName + ": File " + myfile.getName() + " was deleted. ");
				myfile.createNewFile();
				Main.logger.info(threadName + ": File created: " + myfile.getName());
			}
			try {
				Main.logger.warn("trying to write the files...");
				BufferedWriter myWriter = new BufferedWriter(new FileWriter(myfile.getName()));
				File exampleFile = new File("exampleText.txt");
				Scanner sc = new Scanner(exampleFile);
				while (sc.hasNextLine()) {
					myWriter.write(sc.nextLine());
					myWriter.newLine();
				}
				sc.close();
				Main.logger.info("Successfully wrote to the file " + myfile.getName());
			} catch (FileNotFoundException Fe) {
				Main.logger.error("Error: " + Fe.getMessage());
			}
		} catch (IOException Ioe) {
			Main.logger.error("Error: " + Ioe.getMessage());
		}
	}
	
	
	public void start() {
		System.out.println("Starting " + threadName);
		if(thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
}