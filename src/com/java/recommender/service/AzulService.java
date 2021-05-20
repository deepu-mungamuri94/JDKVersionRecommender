package com.java.recommender.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AzulService {
	
	private static String azulBundlesAPIURL = "https://api.azul.com/zulu/download/community/v1.0/bundles?bundle_type=jdk";
	
	private static AzulService _instance = null;
	private AzulService() {}
	
	// Singleton
	public synchronized static AzulService getInstance() {
		if(_instance == null) {
			_instance = new AzulService();
		}
		return _instance;
	}
	
	/**
	 * Fetches all Bundles available using Azul API by using query parameters(os,arch,fileExtension)
	 * @param fileExtension 
	 * @return List<AzulBundle>
	 * @throws Exception
	 */
	public List<AzulBundle> getAzulBundles(String fileExtension) throws Exception {
		
		StringBuilder url = new StringBuilder(azulBundlesAPIURL);
		String os = UtilityService.getOSQueryString();
		String arch = UtilityService.getOSArch();
		if(os != null) {
			url.append("&os=").append(os);
		}
		if(arch != null) {
			url.append("&arch=").append(arch);
		}
		if(fileExtension != null && !fileExtension.isEmpty()) {
			url.append("&ext=").append(fileExtension);
		}
		
		// 1. Initiate Connection
		System.out.println("\nRequesting bundles from Azul: " + url);
		URL urlObj = new URL(url.toString());
		HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
		connection.setRequestMethod("GET");
		
		// 2. Get response from API and prepare content
		StringBuilder responseContent = new StringBuilder();
		try(BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line = null;
			
			while((line = responseReader.readLine()) != null) {
				responseContent.append(line);
			}
		}
		
		// 3. Return Azul Bundles prepared
		return prepareAzulBundlesFromResponse(responseContent.toString());
	}
	
	
	/**
	 * Prepares AzulBundle models(data/pojo) list from Azul bundles API response
	 * @param response
	 * @return List<AzulBundle>
	 * @throws JSONException
	 */
	private List<AzulBundle> prepareAzulBundlesFromResponse(String response) throws JSONException {
		List<AzulBundle> azulBundles = new ArrayList<>();
		if(response == null || response.isEmpty()) {
			return azulBundles;
		}
		
		JSONArray responseJSONArray = new JSONArray(response);
		for(int i=0; i<responseJSONArray.length(); i++) {
			JSONObject javaVersionDetalis = responseJSONArray.getJSONObject(i);
			JSONArray jdkVersionArrayElements = javaVersionDetalis.getJSONArray("jdk_version");
			String downloadURL = javaVersionDetalis.getString("url");
			
			// Prepare version number[major,minor,fixes..] as array elements
			int[] versionElements = new int[jdkVersionArrayElements.length()];
			for(int j=0; j<jdkVersionArrayElements.length(); j++) {
				versionElements[j] = jdkVersionArrayElements.getInt(j);
			}
			
			azulBundles.add(new AzulBundle(downloadURL, versionElements));
		}
		
		return azulBundles;
	}

}
