package com.minecrafttas.webcubiomes.cubiomes;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

/**
 * Cubiomes progress file
 */
public record ProgressFile(String[] initialProgressFile, String mc, boolean is48Bit, SeedDatabase progress, List<Condition> conditions, List<Seed> seeds) {

	/**
	 * Updates the progress file seed database and seeds
	 * @return New progress file
	 */
	public String[] updateProgressFile() {
		var newProgressFile = new ArrayList<String>();
		for (String l : this.initialProgressFile) {
			if (l.startsWith("#Progress:"))
				l = "#Progress: 0\n#SeedDatabase:" + this.progress.toString();
			
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
			if (l.startsWith("#Progress:"))
				l = "#Progress: " + Long.toUnsignedString(this.progress.getProgressSector());
			
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
		boolean is48Bit = false;
		SeedDatabase progress = new SeedDatabase(new long[256]);
		List<Condition> conditions = new ArrayList<>();
		List<Seed> seeds = new ArrayList<>();
		for (String l : progressFile) {
			if (l.startsWith("#MC:"))
				mc = l.split("\\:")[1].trim();
			else if (l.startsWith("#Search:"))
				is48Bit = Integer.parseInt(l.split("\\:")[1].trim()) == 1;
			else if (l.startsWith("#SeedDatabase:"))
				progress = SeedDatabase.parseDatabase(l.split("\\:")[1].trim());
			else if (l.startsWith("#Cond:"))
				conditions.add(Condition.parseCondition(HexFormat.of().parseHex(l.split("\\:")[1].trim())));
			else if (!l.contains(":"))
				seeds.add(new Seed(Long.parseLong(l.trim())));
		}
		return new ProgressFile(progressFile, mc, is48Bit, progress, conditions, seeds);
	}
	
}
