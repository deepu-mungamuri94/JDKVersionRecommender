package com.java.recommender;

import java.util.List;

import com.java.recommender.service.UtilityService;
import com.java.recommender.service.AzulBundle;
import com.java.recommender.service.AzulService;

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
		
		// 3. Get Azul Bundles
		List<AzulBundle> bundles = AzulService.getInstance().getAzulBundles(fileExtension);
		
		// 4. List bundles available for update
		showUpdatableBundles(hostJavaVersionElements, bundles);
	}
	
	/**
	 * This will print all the new jdk updates available from Azul
	 * @param hostJavaVersionElements - Host System jdk version
	 * @param bundles - Azul bundles from API
	 */
	private static void showUpdatableBundles(int[] hostJavaVersionElements, List<AzulBundle> bundles) {
		if(bundles == null || bundles.size() == 0) {
			System.out.println("\n\n[ DONE ] No updates found\n");
			return;
		}
		
		// Check each Azul bundle is an update/not with hosts jdk version
		System.out.println("\n\nUpdates available: ");
		System.out.println("--------------------------------------------------------------------------------------------------");
		System.out.printf("%-15s | %s\n", "JDK Version", "Download URL");
		System.out.println("--------------------------------------------------------------------------------------------------");
		for(AzulBundle bundle : bundles) {
			if(bundle.isnewVersion(hostJavaVersionElements)) {
				System.out.printf("%-15s | %s\n", bundle.getJDKVersionAsReadable(), bundle.getURL());
			}
		}
		System.out.println("--------------------------------------------------------------------------------------------------");
	}
}