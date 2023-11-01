package nz.hmp.tither.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;

import nz.hmp.tither.configs.LoggerWrapper;

public class DateUtils {
	
	protected static final transient Logger logger = 
			LoggerWrapper.getLogger();
	
	public static final String EMPTY_DATE = "00000000";

	public static Date incrementDate(Date now, Integer incrementDays){
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, incrementDays);
		Date earlier = cal.getTime();
		
		return earlier;
	}
	
	public static Date incrementDateMin(Date now, Integer incrementMin){
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, incrementMin);
		Date earlier = cal.getTime();
		
		return earlier;
	}
	
	public static Date changeDtInitTime(Date date, String time) throws ParseException{
		
		if (date != null){
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			date = format.parse(dateFormat.format(date) + " " + time);
		}
		
		return date;
	}
	
	public static Date truncarHorario(Date date, String formato){
		
		Date data = null;
		
		if(date != null){
			
			DateFormat dateFormat = new SimpleDateFormat(formato);
			String dataString = dateFormat.format(date);
			
			try {
				data = dateFormat.parse(dataString);
			} catch (ParseException e) {
			}
			
		}
		
		return data;
	}
	
	public static String getFormattedDate(String dateStr) 
			throws ParseException{
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String formatted = "";
		if (dateStr != null 
				&& !dateStr.isEmpty()){
			Date date = format.parse(dateStr);
			format = new SimpleDateFormat("dd/MM/yyyy");
			formatted = format.format(date);
		}
		 
		return formatted;
	}
	
	public static String getFormattedDate(Date date){
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String formatted = "";
		if (date != null){
			formatted = format.format(date);
		}
		 
		return formatted;
	}
	
	public static String getFormattedDateTime(
			LocalDateTime date, String formatStr) {
		String formatted = "";
		if (date != null){
			formatted = date.format(
				DateTimeFormatter
					.ofPattern(formatStr));
		}
		
		return formatted;
	}
	
	public static LocalDate getDateFromStr(
			String dateStr) {
		LocalDate date = null;
		
		if (dateStr != null
				&& !dateStr.trim().isEmpty()
				&& !dateStr.trim().equalsIgnoreCase(EMPTY_DATE)){
			date = LocalDate
					.parse(dateStr, 
							DateTimeFormatter
								.ofPattern("ddMMyyyy"));
		}
		
		return date;
					
	}
	
	public static Date convertToDateViaInstant(
			LocalDate dateToConvert) {
	    return dateToConvert != null ? 
	    		Date.from(dateToConvert
	    				.atStartOfDay()
	    				.atZone(ZoneId.systemDefault())
    				.toInstant())
    			: null;
	}
	
	public static Date convertToDateViaInstant(
			LocalDateTime dateToConvert) {
	    return dateToConvert != null ?
	    		Date.from(dateToConvert
	    				.atZone(ZoneId.systemDefault())
    				.toInstant())
	    		: null;
	}
}
