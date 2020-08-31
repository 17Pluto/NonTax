package com.xcmis.framework.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	public static String getDateTimeStr1(){
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTimeStr1 = sf.format(date);
		return dateTimeStr1;
	}


	public static String getDateTimeStr2(){
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTimeStr1 = sf.format(date);
		return dateTimeStr1;
	}

	public static String getCurrentYear(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return sdf.format(date);
	}

}