package com.ef.utils;

import com.ef.model.AppConstants;

public class StringUtils {
	public static String removeQuotes(String string) {
		return string.replaceAll(AppConstants.REGEX_QUOTES_REPLACE, "");
	}
}
