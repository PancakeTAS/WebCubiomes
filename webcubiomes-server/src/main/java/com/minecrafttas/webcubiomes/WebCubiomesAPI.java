package com.minecrafttas.webcubiomes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Random;

import com.minecrafttas.webcubiomes.cubiomes.Seed;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/*
 * Web Cubiomes HTTP API for downloading jobs and uploading progress updates
 */
public class WebCubiomesAPI implements HttpHandler {

	private HttpServer httpServer;
	private Random random;
	private String seq;
	
	/**
	 * Initialize the web cubiomes api http server
	 * @throws IOException Networking exception
	 */
	public WebCubiomesAPI() throws IOException {
		this.random = new Random();
		this.httpServer = HttpServer.create(new InetSocketAddress(3689), 0);
		this.httpServer.createContext("/", this);
		this.httpServer.setExecutor(null);
		this.httpServer.start();
		this.updateSeq();
	}

	/**
	 * Updates the current file sequence
	 */
	public void updateSeq() {
		this.seq = Long.toUnsignedString(this.random.nextLong());
	}
	
	/**
	 * Handle incoming HTTP request
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		var headers = exchange.getRequestHeaders();
		var status = headers.getFirst("Status");
		var progressFile = WebCubiomes.getInstance().getProgressFile();
		var stream = exchange.getResponseBody();
		try {
			if (progressFile == null) { // no progress loaded
				exchange.sendResponseHeaders(500, 0);
			} else if ("Fetch".equals(status)) { // obtain and send job
				// obtain job and convert to body
				var lines = progressFile.obtainJobProgressFile();
				
				var job = "";
				for (String line : lines)
					job += line + "\n";
				byte[] out = job.getBytes();
				
				// send response
				exchange.getResponseHeaders().set("Seq", this.seq);
				exchange.sendResponseHeaders(200, out.length);
				stream.write(out);
				stream.close();
				
				// log stuff
				System.out.println("=== JOB FETCHED ===");
				System.out.println("Ip: " + exchange.getRemoteAddress().getHostString());
				System.out.println(job);
				System.out.println();
			} else if ("Update".equals(status) && this.seq.equalsIgnoreCase(headers.getFirst("Seq"))) { // update progress
				// parse progress
				var progress = Long.parseUnsignedLong(headers.getFirst("Progress"));
				var seeds = new String(exchange.getRequestBody().readNBytes(Integer.parseInt(exchange.getRequestHeaders().getFirst("Content-Length"))));
				
				// update progress and seeds
				var frags = seeds.split(":");
				for (var seed : frags)
					progressFile.seeds().add(new Seed(Long.parseLong(seed)));
				progressFile.progress().updateProgressSector(progress);
				
				exchange.sendResponseHeaders(200, 0);
				
				// log stuff
				System.out.println("=== JOB FINISHED ===");
				System.out.println("Ip: " + exchange.getRemoteAddress().getHostString());
				System.out.println("Progress: " + progress);
				System.out.println("Seeds found: " + frags.length);
				System.out.println();
			} else { // no valid request
				exchange.sendResponseHeaders(400, 0);
			}
		} catch (Exception e) {
			// something went wrong
			System.err.println("HTTP request generated exception");
			e.printStackTrace();
			exchange.sendResponseHeaders(500, 0);
		}
		stream.close();
	}
	
}
