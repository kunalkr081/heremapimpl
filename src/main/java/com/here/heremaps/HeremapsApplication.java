package com.here.heremaps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.here.heremaps.application.EatDrink;
import com.here.heremaps.application.ElectronicCharingVehicle;
import com.here.heremaps.application.ParkingFacility;
import com.here.heremaps.restClient.RestClient;

@SpringBootApplication
public class HeremapsApplication {
	
	private static final Logger log = LoggerFactory.getLogger(HeremapsApplication.class);
	
	private static RestClient restClient;
	
	private static EatDrink eatDrink;
	
	private static ParkingFacility parkingFacility;
	
	private static ElectronicCharingVehicle evParking;

	private static final String baseUriPath = "places.ls.hereapi.com/places/v1/discover/explore?";
	private static final String at = "52.5159%2C13.3777";
	
	private static final String completeUriPath = baseUriPath+"at="+at;

	public static void main(String[] args) {
		SpringApplication.run(HeremapsApplication.class, args);
		
		log.info("starting thread");
		
		Thread onStreet = new Thread(()->{
			String response = restClient.queryRestEndpoints(eatDrink, completeUriPath, true);
			log.info("oeat drink response {}",response);
		});
		
		Thread offStreet = new Thread(()->{
			String response = restClient.queryRestEndpoints(parkingFacility, completeUriPath, true);
			log.info("parking facility response {}",response);
		});
		
		Thread evCharging = new Thread(()->{
			String response = restClient.queryRestEndpoints(evParking, completeUriPath, true);
			log.info("ev charging response {}",response);
		});
		
		onStreet.start();
		offStreet.start();
		evCharging.start();
		
	}

	@Autowired
	public void setRestClient(RestClient restClient){
		HeremapsApplication.restClient = restClient;
	}
	
	@Autowired
	public void setOnSteetClient(EatDrink eatDrink) {
		HeremapsApplication.eatDrink = eatDrink;
	}
	
	@Autowired
	public void setOffnSteetClient(ParkingFacility parkingFacility) {
		HeremapsApplication.parkingFacility = parkingFacility;
	}
	
	@Autowired
	public void setEvClient(ElectronicCharingVehicle evParking) {
		HeremapsApplication.evParking = evParking;
	}
	
}
