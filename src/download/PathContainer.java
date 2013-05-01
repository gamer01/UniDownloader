package download;

import java.io.*;
import java.util.Scanner;

public class PathContainer {
	private String root;
	private int rootConfigAttempts = 0;

	public PathContainer() {
		setRootDir();
	}

	private void setRootDir() {
		File rootDirCfg = new File("RootDir.cfg");

		try {
			if (rootDirCfg.exists()) {

				Scanner scanner = new Scanner(rootDirCfg);
				String rootPath = null;
				if (scanner.hasNextLine()) {
					rootPath = scanner.nextLine();
				} else {
					throw new IOException("The \"RootDir.cfg\" is empty");
				}

				File rootDir = new File(rootPath);

				if (rootDir.exists()) {
					root = rootDir.getAbsolutePath()+"/";
				} else {
					throw new IOException(
							"The \"RootDir.cfg\" is set up wrong!\nThe Directory must exist and have the right format!");
				}
			} else {
				throw new IOException("The \"RootDir.cfg\" doesn't exist!");
			}
		} catch (IOException e) {
			rootConfigAttempts++;
			createRootDirFile(e);
			
		}

	}

	private void createRootDirFile(Exception e) {
		if(rootConfigAttempts<=3){
			System.out.println(e.getMessage());
			Scanner in = new Scanner(System.in);
			
			File rootDirCfg = new File("RootDir.cfg");
			try {
				PrintStream out = new PrintStream(rootDirCfg);
				System.out.print("Please enter your download root directory in absolute path: ");
				String newRootDir = in.nextLine();
				
				out.println(newRootDir);
				out.close();
			} catch (FileNotFoundException e1) {
				System.out.println("\"RootDir.cfg\" could not be created. Please check your Directory!");
			}
			
			setRootDir();
		} else {
			System.exit(-1);
		}
	}

	private String getDownloadRoot() {
		return root + "download/";
	}

	public String getLinAFolder() {
		return getDownloadRoot() + "LinA/";
	}

	public String getDuAFolder() {
		return getDownloadRoot() + "DuA/";
	}

	public String getGP2Folder() {
		return getDownloadRoot() + "GP2/";
	}

	public String getGTIFolder() {
		return getDownloadRoot() + "GTI/";
	}

	public String getModDateMapUri() {
		return root + "modDateMap.cvs";
	}

}
