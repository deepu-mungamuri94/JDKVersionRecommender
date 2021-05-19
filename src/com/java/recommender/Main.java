package com.java.recommender;

import java.util.List;

import com.java.recommender.service.UtilityService;
import com.java.recommender.service.ZuluBundle;
import com.java.recommender.service.ZuluService;

public class Main {

	public static void main(String[] args) throws Exception {
		// Execution starts from here
		initiate();
	}
	
	private static void initiate() throws Exception {
		// 1. Prompt user for file extensions.
		String fileExtension = UtilityService.validateAndGetFileExtension();
		
		// 2. Get host machines jdk version
		int[] hostJavaVersionElements = UtilityService.getHostJavaVersion();
		
		// 3. Get Zulu Bundles
		List<ZuluBundle> bundles = ZuluService.getInstance().getZuluBundles(fileExtension);
		
		// 4. List bundles available for update
		showUpdatableBundles(hostJavaVersionElements, bundles);
	}
	
	/**
	 * This will print all the new jdk updates available from zulu
	 * @param hostJavaVersionElements - Host System jdk version
	 * @param bundles - Zulu bundles from API
	 */
	private static void showUpdatableBundles(int[] hostJavaVersionElements, List<ZuluBundle> bundles) {
		if(bundles == null || bundles.size() == 0) {
			System.out.println("\n\n[ DONE ] No updates found\n");
			return;
		}
		
		// Check each zulu bundle is an update/not with hosts jdk version
		System.out.println("\n\nUpdates available: ");
		System.out.println("--------------------------------------------------------------------------------------------------");
		System.out.printf("%-15s | %s\n", "JDK Version", "Download URL");
		System.out.println("--------------------------------------------------------------------------------------------------");
		for(ZuluBundle bundle : bundles) {
			if(bundle.isnewVersion(hostJavaVersionElements)) {
				System.out.printf("%-15s | %s\n", bundle.getJDKVersionAsReadable(), bundle.getURL());
			}
		}
		System.out.println("--------------------------------------------------------------------------------------------------");
	}
}