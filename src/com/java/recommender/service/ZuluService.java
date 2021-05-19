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

public class ZuluService {
	
	private static String zuluBundlesAPIURL = "https://api.azul.com/zulu/download/community/v1.0/bundles?bundle_type=jdk";
	
	private static ZuluService _instance = null;
	private ZuluService() {}
	
	// Singleton
	public synchronized static ZuluService getInstance() {
		if(_instance == null) {
			_instance = new ZuluService();
		}
		return _instance;
	}
	
	/**
	 * Fetches all Bundles available using zulu API by using query parameters(os,arch,fileExtension)
	 * @param fileExtension 
	 * @return List<ZuluBundle>
	 * @throws Exception
	 */
	public List<ZuluBundle> getZuluBundles(String fileExtension) throws Exception {
		
		StringBuilder url = new StringBuilder(zuluBundlesAPIURL);
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
		System.out.println("\nRequesting bundles from zulu: " + url);
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
		
		// 3. Return zulu Bundles prepared
		return prepareZuluBundlesFromResponse(responseContent.toString());
	}
	
	
	/**
	 * Prepares ZuluBundle models(data/pojo) list from zulu bundles API response
	 * @param response
	 * @return List<ZuluBundle>
	 * @throws JSONException
	 */
	private List<ZuluBundle> prepareZuluBundlesFromResponse(String response) throws JSONException {
		List<ZuluBundle> zuluBundles = new ArrayList<>();
		if(response == null || response.isEmpty()) {
			return zuluBundles;
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
			
			zuluBundles.add(new ZuluBundle(downloadURL, versionElements));
		}
		
		return zuluBundles;
	}

}
