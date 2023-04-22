package com.minecrafttas.webcubiomes.cubiomes;

/**
 * Seed holder with top and lower bits separated
 */
public record Seed(long seed, String top16, String lower48) {
	
	public Seed(long seed) {
		this(seed, String.format("%04x", seed >>> 48), String.format("%012x", seed & 0x0000FFFFFFFFFFFFL));
	}
	
};