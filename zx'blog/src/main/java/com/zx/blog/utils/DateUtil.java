package com.zx.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static final String date1 = "yyyy-MM-dd HH:mm:ss";
	
	
	public static String getDate1(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(date1);
		return sdf.format(date);
	}

}
