module WebCubiomes {
	requires java.desktop;
	requires java.net.http;

	opens com.minecrafttas.webcubiomes.main;
	exports com.minecrafttas.webcubiomes;
}