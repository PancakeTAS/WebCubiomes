package com.minecrafttas.webcubiomes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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

	private ProgressFile progressFile;
	private AtomicLong progress;
	private Map<Runnable, Long> listeners;
	
	private WebCubiomes() {
		this.progress = new AtomicLong();
		this.listeners = new HashMap<>();
	}
	
	/**
	 * Load cubiomes progress file and trigger listeners
	 * @param progressFile Cubiomes Progress File
	 */
	public void loadProgressFile(ProgressFile progressFile) {
		this.progressFile = progressFile;
		this.progress.set(progressFile == null ? -1 : progressFile.progress());
		for (var entry : new HashMap<>(this.listeners).entrySet())
			if (System.currentTimeMillis() - entry.getValue() > 300000)
				this.listeners.remove(entry.getKey());
			else
				entry.getKey().run();
	}
	
	/**
	 * Register a listener
	 * @param r Listener
	 */
	public void registerListener(Runnable r) {
		this.listeners.put(r, System.currentTimeMillis());
		r.run();
	}
	
	/**
	 * Return the progress file
	 * @return Progress File
	 */
	public ProgressFile getProgressFile() {
		return this.progressFile;
	}

	/**
	 * Return the current progress
	 * @return Current Progress
	 */
	public AtomicLong getProgress() {
		return this.progress;
	}
	
}
