package com.here.heremaps.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class RestUtils {
	
	public static URI buildUriFromString(String basePath,boolean isSSl,String uriPath) throws URISyntaxException {
		
		String uriObject = basePath;
		//http & https
		if(isSSl)
			uriObject = "https://"+uriObject;
		else
			uriObject = "http://"+uriObject;
		
		return new URI(uriObject+uriPath);
		
	}

}
