package com.minecrafttas.webcubiomes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.minecrafttas.webcubiomes.cubiomes.ProgressFile;

/**
 * The most unprofessional backend implemented within the frontend.
 * oh i'm supposed to write what this class is about here... uhh.. it's the backend!
 * @author Pancake
 */
public class WebCubiomes {
	
	// WebCubiomes Instance
	private static WebCubiomes instance;
	
	/**
	 * Returns the instance of WebCubiomes
	 * @return WebCubiomes Instance
	 */
	public static WebCubiomes getInstance() {
		if (WebCubiomes.instance == null)
			WebCubiomes.instance = new WebCubiomes();
		
		return WebCubiomes.instance;
	}

	private volatile ProgressFile progressFile;
	private List<Runnable> listeners;
	private WebCubiomesAPI api;
	private Timer statsTimer;
	
	private WebCubiomes() {
		this.listeners = new ArrayList<>();
		this.statsTimer = new Timer();
		this.rescheduleTimer();
		
		try {
			this.api = new WebCubiomesAPI();
		} catch (IOException e) {
			System.err.println("Could not initialize Web Cubiomes API");
			e.printStackTrace();
		}
	}
	
	/**
	 * Load cubiomes progress file and trigger listeners
	 * @param progressFile Cubiomes Progress File
	 */
	public void loadProgressFile(ProgressFile progressFile) {
		this.progressFile = progressFile;
		this.api.updateSeq();
		for (var r : this.listeners)
			r.run();
		
		if (this.listeners.size() > 15)
			this.listeners = this.listeners.subList(this.listeners.size()-15, this.listeners.size());
		
		if (progressFile != null) {
			System.out.println("=== PROGRESS LOADED ===");
			for (String line : progressFile.updateProgressFile())
				if (line.startsWith("#"))
					System.out.println(line);
		} else {
			System.out.println("=== PROGRESS UNLOADED ===");
		}
		System.out.println();
	}
	
	/**
	 * Register a listener
	 * @param r Listener
	 */
	public void registerListener(Runnable r) {
		this.listeners.add(r);
		r.run();
	}
	
	/**
	 * Return the progress file
	 * @return Progress File
	 */
	public ProgressFile getProgressFile() {
		return this.progressFile;
	}
	
	public void rescheduleTimer() {
		// Find time for next history check
		long time = 0L;
		if (this.progressFile != null) {
			var history = this.progressFile.statistics().getHistory();
			if (history.size() < 10)
				time = 1*60*1000L;
			else if (history.size() >= 10)
				time = 5*60*1000L;
			else if (history.size() >= 20)
				time = 60*60*1000L;
			else if (history.size() >= 30)
				time = 24*60*60*1000L;
		} else {
			time = 1000L;
		}
		// Run timer
		this.statsTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (progressFile != null) {
					var statistics = progressFile.statistics();
					statistics.getHistory().add(statistics.getSeedsChecked());
				}
				
				rescheduleTimer();
			}
		}, time);
	}

}
