package com.minecrafttas.webcubiomes;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Job Fetcher thread
 */
public class JobFetcher extends Thread {
	private final String API_URL = "http://localhost:3689/";
	
	/**
	 * Initialize job fetcher thread
	 */
	public JobFetcher() {
		super("Job Fetcher Thread");
//		this.setDaemon(true);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(50);
				this.fetchJob();
			} catch (Exception e) {
				e.printStackTrace();
				try { Thread.sleep(5*1000); } catch (InterruptedException e1) { }
			}
		}
	}
	
	/**
	 * Fetches a job from the server
	 * @throws Exception Network or process exception
	 */
	private void fetchJob() throws Exception {
		System.out.println("[JobFetcher] Fetching job...");
		
		// create http client
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder().GET().uri(new URI(API_URL)).header("Status", "Fetch").build();

		// send request
		var response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() != 200) {
			System.out.println("[JobFetcher] Server returned " + response.statusCode());
			Thread.sleep(30*1000);
			return;
		}

		this.runJob(response.body().replaceFirst("%THREADS%", WebCubiomes.THREADS + "").getBytes(), response.headers().firstValue("Seq").get());
	}
	
	/**
	 * Runs a job
	 * @param job Progress file
	 * @param seq Progress file sequence
	 * @throws Exception Network or process exception
	 */
	private void runJob(byte[] job, String seq) throws Exception {
		System.out.println("[JobFetcher] Executing job...");
		
		// write job file
		var file = new File(System.getenv("userprofile"), "job.txt");
		Files.write(file.toPath(), job, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		
		// launch process
		var pb = new ProcessBuilder(new File(System.getenv("AppData"), "webcubiomes-viewer\\cubiomes-viewer.exe").getAbsolutePath(), "-gui", "-platform", "offscreen");
		pb.environment().put("QT_ASSUME_STDERR_HAS_CONSOLE", "1");
		pb.redirectErrorStream(true);
		var p = pb.start();
		
		// prepare output
		var seeds = new ArrayList<Long>();
		var progress = -1L;
		
		// read input
		var reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		var line = "";
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("SEED: "))
				seeds.add(Long.parseLong(line.split("\\:")[1].trim()));
			else if (line.startsWith("PROGRESS: "))
				progress = Long.parseUnsignedLong(line.split("\\:")[1].trim());
			System.out.println("[JobFetcher#Process] " + line);
		}
		
		// wait until app is closed
		p.waitFor();
		file.delete();

		if (progress == -1) {
			System.out.println("[JobFetcher] Job exited incorrectly");
			return;
		}
		
		// finish job and update server
		this.updateServer(seeds, progress, seq);
	}
	
	/**
	 * Update server
	 * @param seeds List of founds seeds
	 * @param progress Progress made
	 * @param seq Progress file sequence
	 * @throws Exception Networking exception
	 */
	private void updateServer(List<Long> seeds, long progress, String seq) throws Exception {
		System.out.println("[JobFetcher] Sending job data to server...");
		
		var data = seeds.stream().map(l -> l.toString()).collect(Collectors.joining(":"));
		
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(data)).uri(new URI(API_URL)).header("Status", "Update").header("Seq", seq).header("Progress", Long.toUnsignedString(progress)).build();

		var response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() != 200) {
			System.out.println("[JobFetcher] Server returned " + response.statusCode());
			return;
		}

		System.out.println("[JobFetcher] Job successfully executed");
	}
	
}
