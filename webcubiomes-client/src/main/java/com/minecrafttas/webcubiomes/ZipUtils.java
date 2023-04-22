package com.minecrafttas.webcubiomes;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Unzipping Utils
 */
public class ZipUtils {

	/**
	 * Unzips a zip
	 * @param zipFile Zip file
	 * @param destDir Zip destination
	 * @throws Exception Filesystem exception
	 */
	public static void unzip(File zipFile, File destDir) throws Exception {
		var zis = new ZipInputStream(new FileInputStream(zipFile));
		
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
		    var f = new File(destDir, entry.getName());
		    if (entry.isDirectory())
		    	continue;

		    f.getParentFile().mkdirs();
		    Files.copy(zis, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		
		zis.closeEntry();
		zis.close();
	}
	
}
