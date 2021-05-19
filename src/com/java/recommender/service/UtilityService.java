package com.java.recommender.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UtilityService {
	
	private static final Map<String, String> supportedOS = new HashMap<String, String>() {
		private static final long serialVersionUID = -6825043133626072379L;
		{
			put("MacOS X", "macos");
			put("Windows", "windows");
			put("Solaris", "solaris");
			put("QNX", "qnx");
			put("Linux", "linux");
			put("Linux, GLib", "linux");
			put("Linux, Musl", "linux_musl");
			put("Musl", "linux_musl");
		}
	};
	
	private static final String[] supportedArchs = {"x86", "arm", "mips", "ppc", "sparcv9"};
	
	private static final String[] supportedFileExtensions = {"cab","deb", "rpm", "msi", "dmg", "zip", "tar.gz"};

	public static int[] getHostJavaVersion() {
		String javaVersion = System.getProperty("java.version");
		if(javaVersion == null || javaVersion.isEmpty()) {
			System.out.println("No java available on the machine");
			return null;
		}
		
		System.out.println("\nJava version identified on the machine: [ " + javaVersion + " ]");
		String[] javaVersionElements = javaVersion.split("[._]");
		
		int [] javaVersionNumbers = Arrays.asList(javaVersionElements).stream().mapToInt(Integer::parseInt).toArray();
		if(javaVersionNumbers[0] == 1) {
			for(int i=0; i<javaVersionNumbers.length-1; i++) {
				javaVersionNumbers[i] = javaVersionNumbers[i+1];
			}
			javaVersionNumbers[javaVersionNumbers.length-1] = 0;
		}
		
		return javaVersionNumbers;
	}
	
	public static String getOSQueryString() {
		String osName = System.getProperty("os.name");
		System.out.println("\nOS identified as: [ " + osName + " ]");
	
		for(Map.Entry<String, String> keyValue : supportedOS.entrySet()) {
			String key = keyValue.getKey();
			if(key.replaceAll("\\s", "").toLowerCase().equals(osName.replaceAll("\\s", "").toLowerCase())) {
				return keyValue.getValue();
			}
		}
		
		System.out.println("\n[ WARNING ] OS("+osName+") isn't supported. OS filter disabled & fetching for all available OS's");
		
		return null;
	}
	
	public static String getOSArch() {
		String osArch = System.getProperty("os.arch");
		System.out.println("\nOS Arch identified as : [ " + osArch + " ]");
		for(String arch : supportedArchs) {
			if(osArch.toLowerCase().contains(arch.toLowerCase())) {
				return arch;
			}
		}
		
		System.out.println("\n[ WARNING ] OS.Arch("+osArch+") isn't supported. Arch's filter disabled & fetching for all Arch's.\n");
		return null;
	}
	
	public static String getSupportedFileExtensionsString() {
		StringBuilder out = new StringBuilder("[ ");
		for(String ext : supportedFileExtensions) {
			out.append(ext).append(", ");
		}
		out = new StringBuilder(out.substring(0, out.length()-1)).append(" ]");
		return out.toString();
	}
}