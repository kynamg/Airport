package airport;

//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

public class AirportLog {
	
	private final static Logger logger = Logger.getLogger(AirportLog.class.getName());
	private static FileHandler fh;
	private static SimpleFormatter formatter;

	private static AirportLog instance = new AirportLog();
//	public String logName = "Airport_Log";
//	protected String currentDir = System.getProperty("user.dir");
//		private static File logFile;
		
		private AirportLog() {
			if (instance != null){
				throw new IllegalStateException("Cannot instantiate a second instance of log as it uses singleton");
			}
			setUp();
		}

		public static AirportLog getInstance(){
			if(instance == null) {
				instance = new AirportLog();
			}
			return instance;
		}
		
		public static void setUp() {
			logger.setLevel(Level.INFO);
			//Get the current date and time
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		   	Calendar c = Calendar.getInstance();
			try {
				AirportLog.fh = new FileHandler("Airport_Log"+ '-' +  df.format(c.getTime()) + ".log");
				System.out.println("INFO: new log file created");
			} catch (IOException e) {
				System.err.println("ERROR: Cannot create log file");
				e.printStackTrace();
				System.exit(1);
			}
			//create a formatter
			formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.addHandler(fh);	
		}
		
		private synchronized static Logger getLogger(){
		    if(logger == null){
		    	new AirportLog();
		    }
		    return logger;
		}
		
		public synchronized static void log(Level level, String msg){
			System.out.println("I got here 1");
		    getLogger().log(level, msg);
		    System.out.println(msg);
		}
		
		
/*
		public void createLogsFolder(){
		
			File logFolder = new File(currentDir + '/' + "logs");
			
			if(!logFolder.exists()){ 
				System.out.println("INFO: logs directory created in " + currentDir);
				logFolder.mkdir();
			}

			//Get the current date and time
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		   	Calendar c = Calendar.getInstance();
		   	
		   	//Create the name of the file from the path and current time
			logName =  logName + '-' +  df.format(c.getTime()) + ".log";
			
			
			//AirportLog.logFile = new File(logFolder.getName(),logName);
			try{
				if(logFile.createNewFile()){
					System.out.println("INFO: new log file created");	
				}
			}
			catch(IOException e){
				System.err.println("ERROR: Cannot create log file");
				System.exit(1);
			}
		}

		
		public static void log(String msg){
			try{
				FileWriter output = new FileWriter(AirportLog.logFile, true);
				output.write(msg.toCharArray());
				output.close();
			}catch(IOException e){
				System.err.println("ERROR: Could not write to log file");
			}
		}
		
		
		public static void test(){
			logger.setLevel(Level.INFO);
	        logger.severe("Info Log");
	        logger.warning("Info Log");
	        logger.info("Info Log");
	        logger.finest("Really not important");
		}

		public static void main(String[] args) throws IOException {
			AirportLog.setUp();
			AirportLog.test();
			//AirportLog.log("This is a message");
		} */
}
