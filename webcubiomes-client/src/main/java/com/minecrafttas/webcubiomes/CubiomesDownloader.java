package com.minecrafttas.webcubiomes;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CubiomesDownloader {
	private static final String VERSION = "3.2.1";
	private static final String CUBIOMES_VIEWER_URL = "https://maven.mgnet.work/main/com/github/cubitect/webcubiomes-viewer/" + VERSION + "/cubiomes.zip";
	
	/**
	 * Downloads cubiomes or nothing if already installed
	 * @throws Exception Filesystem Exception
	 */
	public static void download() throws Exception {
		// first check if already downloaded
		var cubiomes = new File(System.getenv("AppData"), "webcubiomes-viewer");
		if (cubiomes.exists())
			return;

		// then download and unzip
		var temp = Files.createTempFile("cubiomes-viewer", ".zip");
		Files.copy(new URL(CUBIOMES_VIEWER_URL).openStream(), temp, StandardCopyOption.REPLACE_EXISTING);
		ZipUtils.unzip(temp.toFile(), cubiomes);
	}
	
}
