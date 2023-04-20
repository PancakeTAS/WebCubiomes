package com.minecrafttas.webcubiomes.cubiomes;

import java.util.Random;

public class SeedDatabase {

	private long[] progress;
	private Random random;
	
	public SeedDatabase(long[] progress) {
		this.progress = progress;
		this.random = new Random();
	}
	
	public long getProgressSector() {
		int i = this.random.nextInt(256);
		return (long) i << 40 | (long) this.progress[i];
	}
	
	public void updateProgressSector(long val) {
		int index = (int) (val >> 40 & 0xFF);
		long prog = val & 0xFFFFFFFFFFL;
		if (this.progress[index] < prog)
			this.progress[index] = prog;
	}
	
	public static SeedDatabase parseString(String data) {
		var db = new SeedDatabase(new long[256]);
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
