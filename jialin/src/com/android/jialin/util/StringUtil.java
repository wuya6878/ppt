package com.android.jialin.util;


public class StringUtil {

	public StringUtil() {
	}

	/**
	 * @param obj
	 * @return
	 * @author jialin
	 */
	public static String nvl(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}
}
