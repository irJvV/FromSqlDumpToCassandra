package com.example.cassandra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClassTransFormer {
	
	public static String dateFormatTester(){
		// test String on format String date
		return null;
	}
	
	public static Date fromStringToDate(String str, FileInfoBean FIB){
		Date date = null;
		try {
			date = new SimpleDateFormat(FIB.getDateFormat(), Locale.ENGLISH).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Integer fromStringToInteger(String str){
		return Integer.parseInt(str);
	}
	

}
