package airport;

//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

public class AirportLog {
    //private static AirportLog instance; //create instance when class is initlaised
    private final static Logger logger = Logger.getLogger(AirportLog.class.getName());
    private static FileHandler fh;
    private static SimpleFormatter formatter;
    
    //private constructor is only accessible within the class
    private AirportLog() {
        if (instance != null){
            throw new IllegalStateException("Cannot instantiate a second instance of log as it uses singleton");
        }
        setUp();
    }
    //public get method is accessible everywhere in program
   /*public synchronized static AirportLog getInstance(){
        if(instance == null)
        	synchronized(AirportLog.class) {
        		if(instance == null)
        			instance = new AirportLog();	
        	}
    	return instance;
    }*/
    
    public static void setUp() {
    	logger.setLevel(Level.INFO); //sets logging level to INFO
        //Get the current date and time
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        //try to create airport log file name with date and time included, catch if error occurs
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
        
        /*private synchronized static Logger getLogger(){
            //if logger has not been created, create new AirportLog instance
            if(logger == null){
            	synchronized(AirportLog.class) {
            		if(logger == null)
            			logger = new AirportLog();	
            	}
            }
            return logger;
        }*/
        
    public static synchronized void log(Level level, String msg){
    	//getInstance();
    	logger.log(level, msg);
        System.out.println(msg);
        }
}

