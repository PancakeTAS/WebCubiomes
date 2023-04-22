package com.minecrafttas.webcubiomes.main;

import com.minecrafttas.webcubiomes.CubiomesDownloader;
import com.minecrafttas.webcubiomes.JobFetcher;

/**
 * Main class of application
 */
public class Main {

	public static void main(String[] args) throws Exception {
		// download cubiomes
		CubiomesDownloader.download();
		
		// launch job fetcher
		var jobFetcher = new JobFetcher();
		jobFetcher.start();
	}
	
}
