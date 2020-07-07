package com.here.heremaps.restClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.here.heremaps.application.HereMapsImpl;
import com.here.heremaps.utils.RestUtils;
//beanFactor->|RestClient|A|B|C|
@Component 
@Scope("prototype")
public class RestClient {
	
	private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
	private RestTemplate restTemplate = null;
	private PoolingHttpClientConnectionManager syncConnectionManager;
	
	
	public void init() {
		restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(buildhttpClient()));
	}
	
	
	public synchronized String queryRestEndpoints(HereMapsImpl mapType,String basePath,boolean isSSl) {
		
		URI uriObject = null;
		
		String uriPath = mapType.returnUri();
		
		init();
		
		try {
			uriObject = RestUtils.buildUriFromString(basePath, isSSl, uriPath);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("rest api is {}",uriObject);
		//spring api 
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> requestEntity = new HttpEntity<String>("",headers);
		//rest api call with uriObject 
		ResponseEntity<String> responseEntity = restTemplate.exchange(uriObject, 
													HttpMethod.GET, requestEntity, String.class);
		
		if(responseEntity.getStatusCodeValue() >=400 || responseEntity.getStatusCodeValue() <= 500)
			logger.info("failed with status code {}",responseEntity.getStatusCodeValue() );
		
		logger.info("checking my response");
		logger.info("checking my response here {}",responseEntity.getBody());
		
		return responseEntity.getBody();
	}
	
	private CloseableHttpClient buildhttpClient() {
		CloseableHttpClient httpClient = null;
		httpClient = HttpClientBuilder.create().setConnectionManager(getSyncConnectionManager())
																		.build();
		return httpClient;
	}


	public PoolingHttpClientConnectionManager getSyncConnectionManager() {
		
		syncConnectionManager = new PoolingHttpClientConnectionManager();
		return syncConnectionManager;
	}


	
	
}