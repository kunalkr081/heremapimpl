package com.here.heremaps.application;

import org.springframework.stereotype.Component;

@Component
public class ElectronicCharingVehicle implements HereMapsImpl {

	private static final String cat = "ev-charging-station";
	private static final String API_KEY = "LKtXL4_3oVTBKBmF3HuvNFSbMR-lDwQ9pLcQZEu-idw";

	@Override
	public String returnUri() {
		
		String uriPath = "&cat="+cat+"&apiKey="+API_KEY;
		
		return uriPath;
	}
	
}
