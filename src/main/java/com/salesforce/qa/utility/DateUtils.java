package com.salesforce.qa.utility;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;


@SuppressWarnings("unused")
public class DateUtils {
	public static String getTimeStamp() {
		Date currDate = new Date();
		String date = currDate.toString().replaceAll(":", "_").replaceAll(" ", "_");
		return date;
	}
}


/*
	public static void main(String[] args) {
		Date currDate = new Date();
		String date = currDate.toString().replaceAll(":", "_").replaceAll(" ", "_");
		System.out.println(date);
		
		LocalDate localDate = LocalDate.now();
		System.out.println(localDate);
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		LocalAddress la = LocalAddress.ANY;
		System.out.println(la);
		int df = DateFormat.WEEK_OF_MONTH_FIELD;
		System.out.println(df);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd:MMMM:yyyy");
		String edited = localDate.format(format);
		System.out.println(edited);
		System.out.println(localDate.get(ChronoField.DAY_OF_MONTH));
		System.out.println(localDate.get(ChronoField.DAY_OF_WEEK));
		System.out.println(localDate.getEra());
		
	}
*/
