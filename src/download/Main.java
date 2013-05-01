package download;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PathContainer dirs = new PathContainer();
			
		DownloadFiles downloader = new DownloadFiles(dirs.getModDateMapUri());
		
		downloadLinA(downloader, dirs);
		
		downloader.saveModDateConfig(dirs.getModDateMapUri());
			
	}

	private static void downloadLinA(DownloadFiles downloader, PathContainer dirs) {
		LinkExtractor extractor = new LinkExtractor();
		
		String linaPräsFolder = dirs.getLinAFolder() + "Präsenzübung/";
		for (int i = 0; i < extractor.getLinaPräsLinks().size(); i++) {
			downloader.download(extractor.getLinaPräsLinks().get(i),
					linaPräsFolder, "LinA-Präs-" + String.format("%02d", i + 1)
							+ ".pdf");
		}

		String linaHeimFolder = dirs.getLinAFolder() + "Heimübung/";
		for (int i = 0; i < extractor.getLinaHeimLinks().size(); i++) {
			downloader.download(extractor.getLinaHeimLinks().get(i),
					linaHeimFolder, "LinA-Heim-" + String.format("%02d", i + 1)
							+ ".pdf");
		}

	}

}
