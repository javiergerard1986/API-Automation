package logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Logger {

	private static Logger instance;
	private PrintStream printStream;
	
	private Logger() {
		try {
			this.printStream = new PrintStream(new FileOutputStream("Log.txt"));
		} catch(FileNotFoundException ex) {
			System.out.println("Not able to create the logger!!!: " + ex.getMessage());
		}
	}
	
	public static Logger getInstance() {
		if(instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	public PrintStream getPrintStream() {
		return this.printStream;
	}
		
}
