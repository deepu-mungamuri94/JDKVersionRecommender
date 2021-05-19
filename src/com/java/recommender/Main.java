package com.java.recommender;

import java.util.List;
import java.util.Scanner;

import com.java.recommender.service.UtilityService;
import com.java.recommender.service.ZuluBundle;
import com.java.recommender.service.ZuluService;

public class Main {

	public static void main(String[] args) throws Exception {
		// Execution starts from here
		initiate();
	}
	
	private static void initiate() throws Exception {
		// 1. Input for file extensions.
		System.out.println("\nWe are supporing these file extensions for downloadable jdks: " + UtilityService.getSupportedFileExtensionsString());
		System.out.print("\nProvide required extension [ Press Enter otherwise ] : ");
		Scanner sc = new Scanner(System.in);
		String fileExtension = sc.nextLine();
		sc.close();
		
		// 2. Get host machines jdk version
		int[] hostJavaVersionElements = UtilityService.getHostJavaVersion();
		
		// 3. Get Zulu Bundles
		List<ZuluBundle> bundles = ZuluService.getInstance().getZuluBundles(fileExtension);
		
		// 4. Gives us the list of bundles available for update
		showUpdatableBundles(hostJavaVersionElements, bundles);
	}
	
	private static void showUpdatableBundles(int[] hostJavaVersionElements, List<ZuluBundle> bundles) {
		if(bundles == null || bundles.size() == 0) {
			System.out.println("\nNo updates found");
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