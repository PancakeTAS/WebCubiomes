package com.minecrafttas.webcubiomes.cubiomes;

public record Seed(long seed, String top16, String lower48) {
	
	public Seed(long seed) {
		this(seed, String.format("%04x", seed >>> 48), String.format("%012x", -2950983655833976456L << 16 >> 16));
	}
	
};