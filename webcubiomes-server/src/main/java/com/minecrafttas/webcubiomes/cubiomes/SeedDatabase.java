package com.minecrafttas.webcubiomes.cubiomes;

import java.util.Random;

/**
 * Database for reserving checked seeds
 */
public class SeedDatabase {

	private long[] progress; // progress for 256 different structure-seed-bit 0-7
	private Random random;
	
	public SeedDatabase() {
		this.progress = new long[256];
		this.random = new Random();
	}
	
	/**
	 * Get progress of random sector
	 * @return Progress of random sector
	 */
	public long getProgressSector() {
		int i = this.random.nextInt(256);
		return (long) i << 40 | (long) this.progress[i];
	}
	
	/**
	 * Updates progress sector based on seed checked
	 * @param val Seed last checked by client
	 */
	public void updateProgressSector(long val) {
		int index = (int) (val >> 40 & 0xFF);
		long prog = val & 0xFFFFFFFFFFL;
		if (this.progress[index] < prog)
			this.progress[index] = prog;
	}
	
	/**
	 * Parse seed database from string
	 * @param data Seed database
	 * @return Parsed seed database
	 */
	public static SeedDatabase parseDatabase(String data) {
		var db = new SeedDatabase();
		var frag = data.split("\\:");
		for (int i = 0; i < 256; i++)
			db.progress[i] = Long.parseUnsignedLong(frag[i]);
		return db;
	}
	
	@Override
	public String toString() {
		var data = "";
		for (long l : this.progress)
			data += Long.toUnsignedString(l) + ":";
		return data;
	}
	
}
