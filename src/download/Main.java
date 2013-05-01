package download;

import java.io.*;
import java.util.*;

public class Main {

	
	private static String root = null;
	private static String downloadRoot = null;
	private static String linAFolder = null;
	private static String modDateMapUri = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
				
			
			
			setRootDir();
			setRelativePath();
			DownloadFiles downloader = new DownloadFiles(modDateMapUri);
			downloadLinA(downloader);
			downloader.saveModDateConfig(modDateMapUri);
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private static void setRootDir() throws IOException {
		File rootDirCfg = new File("RootDir.cfg");

		if (rootDirCfg.exists()) {
			Scanner scanner = new Scanner(rootDirCfg);
			String rootPath = null;
			if (scanner.hasNextLine()) {
				rootPath = scanner.nextLine();
			} else {
				throw new IOException("The \"RootDir.cfg\" is set up wrong!\nThe file is empty");
			}
			if (new File(rootPath).exists()) {
				root = rootPath;
			} else {
				throw new IOException(
						"The \"RootDir.cfg\" is set up wrong!\nThe Directory must exist");
			}
		} else {
			rootDirCfg.createNewFile();
			PrintStream error = new PrintStream(rootDirCfg);
			error.println("This file must contain just the rootdirectory for the downloads");
			error.close();
			throw new IOException("The \"RootDir.cfg\" is set up wrong!");
		}

	}

	private static void setRelativePath() {
		modDateMapUri = root + "modDateMap.cvs";
		downloadRoot = root + "download/";
		linAFolder = downloadRoot + "LinA/";
	}

	private static void downloadLinA(DownloadFiles downloader) {
		LinkExtractor extractor = new LinkExtractor();
		String linaPräsFolder = linAFolder + "Präsenzübung/";

		for (int i = 0; i < extractor.getLinaPräsLinks().size(); i++) {
			downloader.download(extractor.getLinaPräsLinks().get(i),
					linaPräsFolder, "LinA-Präs-" + String.format("%02d", i + 1)
							+ ".pdf");
		}

		String linaHeimFolder = linAFolder + "Heimübung/";
		for (int i = 0; i < extractor.getLinaHeimLinks().size(); i++) {
			downloader.download(extractor.getLinaHeimLinks().get(i),
					linaHeimFolder, "LinA-Heim-" + String.format("%02d", i + 1)
							+ ".pdf");
		}

	}

}
