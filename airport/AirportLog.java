package airport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AirportLog {

		private static AirportLog instance = new AirportLog();
		public String logName = "Airport_Log";
		protected String currentDir = System.getProperty("user.dir");
		private static File logFile;
		
		private AirportLog(){
			if (instance != null){
				throw new IllegalStateException("Cannot instantiate a second instance of log as it uses singleton");
			}
			this.createLogFile();
		}

		public static AirportLog getInstance(){
			if(instance == null) {
				instance = new AirportLog();
			}
			return instance;
		}

		public void createLogFile(){
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
			AirportLog.logFile = new File(logFolder.getName(),logName);
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

		public static void main(String[] args) {
			
			AirportLog.log("This is a message");
		}
}
