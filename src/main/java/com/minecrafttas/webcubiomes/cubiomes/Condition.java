package com.minecrafttas.webcubiomes.cubiomes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Cubiomes condition
 */
public record Condition(
	int id,
	Filter type,
	Area area
) {

	/**
	 * Parse condition from byte buffer
	 * @param buf Buffer
	 * @return Parsed condition
	 */
	public static Condition parseCondition(byte[] buf) {
		var data = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN);
		var type = Filter.values()[data.getShort(0)];
		data.position(0);
		var minX = data.getInt(4);
		var minZ = data.getInt(8);
		var maxX = data.getInt(12);
		var maxZ = data.getInt(16);
		var id = data.getInt(20);
		var relative = data.getInt(24);
		return new Condition(id, type, new Area(minX, minZ, maxX, maxZ, relative));
	}
	
}
