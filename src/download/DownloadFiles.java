package download;

import java.io.*;
import java.net.*;
import java.util.Properties;

public class DownloadFiles {

	private Properties modDateMap;

	public DownloadFiles(String modConfUri) {
		setModDateConfig(modConfUri);
	}

	private void setModDateConfig(String modConfUri) {
		File cfg = new File(modConfUri);
		modDateMap = new Properties();

		try {
			if (cfg.exists()) {
				InputStream in = new FileInputStream(cfg);
				modDateMap.load(in);
				in.close();
			} else {
				cfg.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveModDateConfig(String modConfUri) {
		File cfgFile = new File(modConfUri);
		OutputStream saveModDateMap;
		try {
			saveModDateMap = new PrintStream(cfgFile);
			modDateMap.store(saveModDateMap, "");
			saveModDateMap.close();
		} catch (IOException e) {
			System.out.println("The saving of ModificationDates Failed!");
		}
	}
	
	public void download(URL downloadLink, String pathname, String filename) {

		new File(pathname).mkdirs();
		downloadFile(downloadLink, pathname + filename);
	}

	private boolean fileIsNewer(String fileUri, Long newDate) {
		if (modDateMap.containsKey(fileUri)
				&& modDateMap.getProperty(fileUri).compareTo("" + newDate) == 0) {
			return false;
		} else
			return true;
	}

	private void writeModificationDate(String fileUri, Long lastModification) {
		modDateMap.setProperty(fileUri, "" + lastModification);
	}

	private void downloadFile(URL downloadLink, String uri) {
		try {
			final URLConnection conn = downloadLink.openConnection();

			if (!new File(uri).exists() || fileIsNewer(uri, conn.getLastModified())) {

				final InputStream is = new BufferedInputStream(
						conn.getInputStream());
				final OutputStream os = new BufferedOutputStream(
						new FileOutputStream(uri));
				byte[] chunk = new byte[1024];
				int chunkSize;
				while ((chunkSize = is.read(chunk)) != -1) {
					os.write(chunk, 0, chunkSize);
				}
				os.flush();
				os.close();
				is.close();
				writeModificationDate(uri, conn.getLastModified());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
