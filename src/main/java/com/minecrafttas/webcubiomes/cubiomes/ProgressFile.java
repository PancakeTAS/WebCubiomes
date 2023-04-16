package com.minecrafttas.webcubiomes.cubiomes;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

public record ProgressFile(String[] initialProgressFile, String mc, boolean is48Bit, long progress, List<Condition> conditions, List<Seed> seeds) {

	public String[] updateProgressFile(long progress) {
		var newProgressFile = new ArrayList<String>();
		for (String l : this.initialProgressFile) {
			if (l.startsWith("#Progress:"))
				l = "#Progress: " + progress;
			
			if (l.contains(":"))
				newProgressFile.add(l);
		}
		
		for (Seed s : this.seeds)
			newProgressFile.add(Long.toString(s.seed()));
		
		return newProgressFile.toArray(String[]::new);
	}

	public static ProgressFile parseProgressFile(String[] progressFile) {
		String mc = null;
		boolean is48Bit = false;
		long progress = -1L;
		List<Condition> conditions = new ArrayList<>();
		List<Seed> seeds = new ArrayList<>();
		for (String l : progressFile) {
			if (l.startsWith("#MC:"))
				mc = l.split("\\:")[1].trim();
			else if (l.startsWith("#Search:"))
				is48Bit = Integer.parseInt(l.split("\\:")[1].trim()) == 1;
			else if (l.startsWith("#Progress:"))
				progress = Long.parseUnsignedLong(l.split("\\:")[1].trim());
			else if (l.startsWith("#Cond:"))
				conditions.add(Condition.parseCondition(HexFormat.of().parseHex(l.split("\\:")[1].trim())));
			else if (!l.contains(":"))
				seeds.add(new Seed(Long.parseLong(l.trim())));
		}
		return new ProgressFile(progressFile, mc, is48Bit, progress, conditions, seeds);
	}
	
}
