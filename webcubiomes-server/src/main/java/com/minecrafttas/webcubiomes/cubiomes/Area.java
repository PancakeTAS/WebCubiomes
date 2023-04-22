package com.minecrafttas.webcubiomes.cubiomes;

/**
 * Area of Condition
 */
public record Area(int minX, int minZ, int maxX, int maxZ, int relative) {
	
	public Area(int minX, int minZ, int maxX, int maxZ) {
		this(minX, minZ, maxX, maxZ, 0);
	}

	@Override
	public String toString() {
		return this.relative != 0 ?
			String.format("[%02d]+(%d,%d),(%d,%d)", this.relative, this.minX, this.minZ, this.maxX, this.maxZ) : 
			String.format("\u00A0\u00A0\u00A0\u00A0\u00A0(%d,%d),(%d,%d)", this.minX, this.minZ, this.maxX, this.maxZ);
	}
	
}
