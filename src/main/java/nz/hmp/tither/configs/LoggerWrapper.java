package nz.hmp.tither.configs;

import org.slf4j.Logger;

public class LoggerWrapper {
	
	private static final StringBuilder sb = new StringBuilder();

	public static Logger getLogger() {
		String logName = Thread.currentThread().getStackTrace()[2].getClassName();
		return org.slf4j.LoggerFactory.getLogger(logName);
	}
	
	public static void log(String message){
		sb.append(message + "\n");
	}
	
	public static void clear(){
		sb.delete(0, sb.length());
	}
	
	public static String getLog(){
		return sb.toString();
	}
	
}
