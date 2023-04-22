package com.minecrafttas.webcubiomes;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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
				this.doRun();
			} catch (Exception e) {
				e.printStackTrace();
				try { Thread.sleep(5*1000); } catch (InterruptedException e1) { }
			}
		}
	}
	
	/**
	 * Fetches and runs a job
	 * @throws Exception Process exception
	 */
	private void doRun() throws Exception {
		// fetch job or wait
		var job = this.fetchJob();
		if (job == null) {
			Thread.sleep(30*1000);
			return;
		}
		
		// run job
		this.runJob(job);
	}
	
	/**
	 * Fetches a job from the server
	 * @return Progress file or null
	 * @throws Exception Networking exception
	 */
	private byte[] fetchJob() throws Exception {
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder().GET().uri(new URI(API_URL)).header("Status", "Fetch").build();

		var response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() != 200) {
			System.err.println("[JobFetcher] Server returned " + response.statusCode());
			return null;
		}

		return response.body().getBytes();
	}
	
	/**
	 * Runs a job
	 * @param job Progress file
	 * @throws Exception Process exception
	 */
	private void runJob(byte[] job) throws Exception {
		// write job file
		var file = new File(System.getenv("userprofile"), "job.txt");
		Files.write(file.toPath(), job, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		
		// launch process
		var pb = new ProcessBuilder(new File(System.getenv("AppData"), "webcubiomes-viewer\\cubiomes-viewer.exe").getAbsolutePath(), "-gui");
		pb.environment().put("QT_ASSUME_STDERR_HAS_CONSOLE", "1");
		pb.redirectErrorStream(true);
		var p = pb.start();
		
		// read input
		var reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		var line = "";
		while ((line = reader.readLine()) != null) {
			line = reader.readLine();
			System.out.println(line);
		}
		
		// exit job
		file.delete();
		System.out.println("job done");
	}
	
}
