package com.minecrafttas.webcubiomes.cubiomes;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * Database for saving statistics
 */
public class StatisticsDatabase {

	private long seedsChecked;
	private long seedsFound;
	private int[] jobsReturned;
	private Date startingTime;
	private long timeSearched;
	
	public StatisticsDatabase() {
		this.jobsReturned = new int[7];
		this.startingTime = Date.from(Instant.now());
	}
	
	/**
	 * Update stats after job was returned
	 * @param seedsChecked Seeds checked
	 * @param seedsFound Seeds found
	 */
	public void jobReturned(long seedsChecked, long seedsFound) {
		this.jobsReturned[Instant.now().atZone(ZoneId.systemDefault()).get(ChronoField.DAY_OF_WEEK) - 1]++;
		this.seedsChecked += seedsChecked;
		this.seedsFound += seedsFound;
	}
	
	/**
	 * Parse statistics database from string
	 * @param data Statistics database
	 * @return Parsed statistics database
	 */
	public static StatisticsDatabase parseDatabase(String data) {
		var db = new StatisticsDatabase();
		var frags = data.split("\\:");
		db.seedsChecked = Long.parseUnsignedLong(frags[0]);
		db.seedsFound = Long.parseUnsignedLong(frags[1]);
		db.timeSearched = Long.parseUnsignedLong(frags[2]);
		for (int i = 3; i < 10; i++)
			db.jobsReturned[i-3] = Integer.parseInt(frags[i]);
		return db;
	}
	
	@Override
	public String toString() {
		var data = "";
		data += Long.toUnsignedString(this.seedsChecked) + ":";
		data += Long.toUnsignedString(this.seedsFound) + ":";
		data += Long.toUnsignedString(Duration.between(this.startingTime.toInstant(), Instant.now()).getSeconds() + this.timeSearched) + ":";
		for (int i : this.jobsReturned)
			data += i + ":";
		return data;
	}
	
	/*
	 * Query methods 
	 */

	public long getSeedsChecked() {
		return this.seedsChecked * 65535;
	}
	
	public long getSeedsFound() {
		return this.seedsFound;
	}
	
	public int getActiveClients() {
		return this.jobsReturned[Instant.now().atZone(ZoneId.systemDefault()).get(ChronoField.DAY_OF_WEEK) - 1];
	}
	
	public String getTimeWorkedOn() {
		var dur = Duration.between(this.startingTime.toInstant(), Instant.now()).plusSeconds(this.timeSearched);
		return String.format("%02d:%02d:%02d", dur.toHours(), dur.toMinutesPart(), dur.toSecondsPart());
	}
	
}
