package com.yabe.util;

import javax.servlet.http.HttpServletResponse;

public class ServletUtils {
	public static void noCache(HttpServletResponse httpResponse){
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
		httpResponse.setDateHeader("Expires", 0);
	}
}
