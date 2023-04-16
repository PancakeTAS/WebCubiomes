package com.minecrafttas.webcubiomes.cubiomes;

public enum Filter {
    SELECT("Unknown"),
    QH_IDEAL("Quad-hut (ideal)"),
    QH_CLASSIC("Quad-hut (classic)"),
    QH_NORMAL("Quad-hut (normal)"),
    QH_BARELY("Quad-hut (barely)"),
    QM_95("Quad-ocean-monument (>95%)"),
    QM_90("Quad-ocean-monument (>90%)"),
    BIOME("Biomes 1:1"),
    BIOME_4_RIVER("Biomes 1:4 RIVER"),
    BIOME_16("Biomes 1:16"),
    BIOME_64("Biomes 1:64"),
    BIOME_256("Biomes 1:256"),
    BIOME_256_OTEMP("Biomes 1:256 O.TEMP"),
    TEMPS("Temperature categories"),
    SLIME("Slime chunk"),
    SPAWN("Spawn"),
    STRONGHOLD("Stronghold"),
    DESERT("Desert temple"),
    JUNGLE("Jungle temple"),
    HUT("Swamp hut"),
    IGLOO("Igloo"),
    MONUMENT("Ocean monument"),
    VILLAGE("Village"),
    OUTPOST("Pillager outpost"),
    MANSION("Woodland mansion"),
    TREASURE("Buried treasure"),
    RUINS("Ocean ruins"),
    SHIPWRECK("Shipwreck"),
    PORTAL("Ruined portal (overworld)"),
    FORTRESS("Nether fortress"),
    BASTION("Bastion remnant"),
    ENDCITY("End city"),
    BIOME_NETHER_1("Nether biomes 1:1"),
    BIOME_NETHER_4("Nether biomes 1:4"),
    BIOME_NETHER_16("Nether biomes 1:16"),
    BIOME_NETHER_64("Nether biomes 1:64"),
    BIOME_NETHER_256("Nether biomes 1:256"),
    BIOME_END_1("End biomes 1:1"),
    BIOME_END_4("End biomes 1:4"),
    BIOME_END_16("End biomes 1:16"),
    BIOME_END_64("End biomes 1:64"),
    PORTALN("Ruined portal (nether)"),
    GATEWAY("End gateway"),
    MINESHAFT("Abandoned mineshaft"),
    SPIRAL_1("Spiral iterator 1:1"),
    SPIRAL_16("Spiral iterator 1:16"),
    SPIRAL_64("Spiral iterator 1:64"),
    SPIRAL_256("Spiral iterator 1:256"),
    SPIRAL_512("Spiral iterator 1:512"),
    SPIRAL_1024("Spiral iterator 1:1024"),
    BIOME_4("Biomes 1:4"),
    SCALE_TO_NETHER("Coordinate factor x/8"),
    SCALE_TO_OVERWORLD("Coordinate factor x*8"),
    F_LOGIC_OR("OR logic gate"),
    F_SPIRAL_4("Spiral iterator 1:4"),
    F_FIRST_STRONGHOLD("First stronghold"),
    F_CLIMATE_NOISE("Climate parameters 1:4"),
    F_ANCIENT_CITY("Ancient city"),
    F_LOGIC_NOT("NOT logic gate"),
    F_BIOME_CENTER("Locate biome center 1:4"),
    F_BIOME_CENTER_256("Locate biome center 1:256"),
    F_CLIMATE_MINMAX("Locate climate extreme 1:4"),
    F_HEIGHT("Surface height"),
    F_LUA("Lua"),
    FILTER_MAX("Unknown");
	
	private final String name;
	
	private Filter(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
