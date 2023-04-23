package com.minecrafttas.webcubiomes.cubiomes;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

/**
 * Cubiomes progress file
 */
public record ProgressFile(String[] initialProgressFile, String mc, boolean is48Bit, SeedDatabase progress, StatisticsDatabase statistics, List<Condition> conditions, List<Seed> seeds) {

	/**
	 * Updates the progress file seed database and seeds
	 * @return New progress file
	 */
	public String[] updateProgressFile() {
		var newProgressFile = new ArrayList<String>();
		for (String l : this.initialProgressFile) {
			if (l.startsWith("#SeedDatabase:") || l.startsWith("#StatisticsDatabase:"))
				continue;
			
			if (l.startsWith("#Progress:"))
				l = "#Progress: 0\n#SeedDatabase:" + this.progress.toString() + "\n#StatisticsDatabase:" + this.statistics.toString();
			
			if (l.contains(":"))
				newProgressFile.add(l);
		}
		
		for (Seed s : this.seeds)
			newProgressFile.add(Long.toString(s.seed()));
		
		return newProgressFile.toArray(String[]::new);
	}

	/**
	 * Obtains a job progress file
	 * @return Job progress file
	 */
	public String[] obtainJobProgressFile() {
		var newProgressFile = new ArrayList<String>();
		for (String l : this.initialProgressFile) {
			if (l.startsWith("#SeedDatabase:") || l.startsWith("#StatisticsDatabase:"))
				continue;
			
			if (l.startsWith("#Progress:"))
				l = "#Progress: " + Long.toUnsignedString(this.progress.getProgressSector());
			
			if (l.startsWith("#Threads:"))
				l = "#Threads:  %THREADS%";
			
			if (l.contains(":"))
				newProgressFile.add(l);
		}
		
		return newProgressFile.toArray(String[]::new);
	}

	/**
	 * Parse progress file
	 * @param progressFile Progress file string
	 * @return Parsed Progress File
	 */
	public static ProgressFile parseProgressFile(String[] progressFile) {
		String mc = null;
		var is48Bit = false;
		var progress = new SeedDatabase();
		var stats = new StatisticsDatabase();
		var conditions = new ArrayList<Condition>();
		var seeds = new ArrayList<Seed>();
		for (String l : progressFile) {
			if (l.startsWith("#MC:"))
				mc = l.split("\\:")[1].trim();
			else if (l.startsWith("#Search:"))
				is48Bit = Integer.parseInt(l.split("\\:")[1].trim()) == 1;
			else if (l.startsWith("#SeedDatabase:"))
				progress = SeedDatabase.parseDatabase(l.split("\\:", 2)[1].trim());
			else if (l.startsWith("#StatisticsDatabase:"))
				stats = StatisticsDatabase.parseDatabase(l.split("\\:", 2)[1].trim());
			else if (l.startsWith("#Cond:"))
				conditions.add(Condition.parseCondition(HexFormat.of().parseHex(l.split("\\:")[1].trim())));
			else if (!l.contains(":"))
				seeds.add(new Seed(Long.parseLong(l.trim())));
		}
		return new ProgressFile(progressFile, mc, is48Bit, progress, stats, conditions, seeds);
	}
	
}
