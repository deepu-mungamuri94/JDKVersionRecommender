package com.java.recommender.service;

public class ZuluBundle {
	
	private String url;
	private int[] jdkVersion;
	
	public ZuluBundle(String url, int[] jdkVersion) {
		this.url = url;
		this.jdkVersion = jdkVersion;
	}
	
	public String getURL() {
		return this.url;
	}
	
	/**
	 * Preparing readable JDKVersion that we have received from ZULU API
	 * @return String
	 */
	public String getJDKVersionAsReadable() {
		StringBuilder out = new StringBuilder();
		for(int i=0; i<jdkVersion.length; i++) {
			out.append(jdkVersion[i]);
			if(i != jdkVersion.length-1) {
				out.append(".");
			}
		}
		return out.toString();
	}
	
	/**
	 * This returns true: if systemJDKVersion is older than ZULU JDK version & false otherwise
	 * @param systemJDKVersion
	 * @return true/false
	 */
	public boolean isnewVersion(int[] systemJDKVersion) {
		if(systemJDKVersion == null || systemJDKVersion.length == 0) {
			return true;
		}
		
		if(this.jdkVersion == null || this.jdkVersion.length == 0) {
			return false;
		}
		
		int index = 0;
		while(index < this.jdkVersion.length && index < systemJDKVersion.length) {
			if(this.jdkVersion[index] < systemJDKVersion[index]) {	// 8.1  8.1
				return false;
			} else if(this.jdkVersion[index] > systemJDKVersion[index]) {
				return true;
			}
			index ++;
		}
		
		return false;
	}
}