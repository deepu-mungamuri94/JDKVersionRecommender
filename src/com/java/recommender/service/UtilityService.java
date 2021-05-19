package com.java.recommender.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UtilityService {
	
	public static final Map<String, String> supportedOS = new HashMap<String, String>() {
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
	
	public static final String[] supportedArchs = {"x86", "arm", "mips", "ppc", "sparcv9"};
	
	public static final String[] supportedFileExtensions = {"cab","deb", "rpm", "msi", "dmg", "zip", "tar.gz"};

	/**
	 * Returns current host systems jdk version as int array with versions as elements.
	 * @return int[]
	 */
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
		
		// Console purpose
		System.out.println("\n\nOS Info: ");
		System.out.println("\t(*) Supported : " + getArrayAsString(supportedOS.keySet().toArray(new String[supportedOS.size()])));
		System.out.println("\t(*) Identified : [ " + osName + " ]");
	
		for(Map.Entry<String, String> keyValue : supportedOS.entrySet()) {
			String key = keyValue.getKey();
			//if(key.replaceAll("\\s", "").toLowerCase().equals(osName.replaceAll("\\s", "").toLowerCase())) {
			if(osName.replaceAll("\\s", "").toLowerCase().contains(key.replaceAll("\\s", "").toLowerCase())) {
				return keyValue.getValue();
			}
		}
		
		System.out.println("\n[ WARNING ] OS("+osName+") isn't supported. OS filter disabled & fetching for all available OS's");
		
		return null;
	}
	
	public static String getOSArch() {
		String osArch = System.getProperty("os.arch");
		
		// Console purpose
		System.out.println("\n\nOS Architecture Info: ");
		System.out.println("\t(*) Supported : " + getArrayAsString(supportedArchs));
		System.out.println("\t(*) Identified : [ " + osArch + " ]");
		
		for(String arch : supportedArchs) {
			if(osArch.toLowerCase().contains(arch.toLowerCase())) {
				return arch;
			}
		}
		
		System.out.println("\n[ WARNING ] OS.Arch("+osArch+") isn't supported. Arch's filter disabled & fetching for all Arch's.\n");
		return null;
	}
	
	public static String getArrayAsString(String ...strings) {
		StringBuilder out = new StringBuilder("[ ");
		for(String ext : strings) {
			out.append(ext).append(", ");
		}
		out = new StringBuilder(out.substring(0, out.lastIndexOf(","))).append(" ]");
		return out.toString();
	}
	
	public static boolean isValidFileExtension(String fileExtension) {
		if(fileExtension == null || fileExtension.isEmpty()) {
			return true;
		}
		for(String extension : supportedFileExtensions) {
			if(extension.equals(fileExtension)) {
				return true;
			}
		}
		
		System.out.println("\n\n[ ERROR ] Invalid file extension provided: " + fileExtension);
		return false;
	}
	
	public static String validateAndGetFileExtension() throws Exception {
		System.out.println("\nWe are supporing these file extensions for downloadable jdks: " + getArrayAsString(supportedFileExtensions));
		String fileExtension = null;
		boolean isValidFileExtension = false;
		
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("\nProvide required extension [ Press Enter otherwise ] : ");
			fileExtension = sc.nextLine();
			
			// Validate fileExtension
			isValidFileExtension = isValidFileExtension(fileExtension);
		} while(!isValidFileExtension);
		sc.close();
		
		return fileExtension;
	}
}