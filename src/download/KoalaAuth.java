package download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class KoalaAuth {
	protected URL koalaHost;
	private Properties koalaAuthProp;
	private int koalaAuthCreationAttempts = 0;

	public KoalaAuth() {
		setKoalaURL();
	}

	private void setKoalaURL() {
		loadKoalaProp();
		try {
			koalaHost = new URL("http://koala.uni-paderborn.de/");
		} catch (MalformedURLException e) {
			System.out.println("This should never happen!");
		}
	}

	private void loadKoalaProp() {
		File koalaConf = new File("koala.auth");
		koalaAuthProp = new Properties();

		try {
			if (koalaConf.exists()) {
				InputStream in = new FileInputStream(koalaConf);
				koalaAuthProp.load(in);
				in.close();

			} else {
				koalaAuthCreationAttempts++;
				createKoalaConf();
			}
		} catch (IOException e) {
			System.out
					.println("The \"koala.auth\" is corrupted or it can't be read, because of other reasons."
							+ "\nPlease "
							+ (koalaConf.delete() ? "" : "delete and ")
							+ "restart!");
			System.exit(-1);
		}
	}

	private void createKoalaConf() {
		if (koalaAuthCreationAttempts <= 3) {
			Scanner in = new Scanner(System.in);

			File koalaAuthFile = new File("koala.auth");
			try {
				OutputStream out = new FileOutputStream(koalaAuthFile);
				System.out.print("Koala username: ");
				String koalaUsername = in.nextLine();
				koalaAuthProp.setProperty("Username", koalaUsername);

				System.out.print("Koala password: ");
				String koalaPassword = in.nextLine();
				koalaAuthProp.setProperty("Password", koalaPassword);
				
				if(!koalaLoginCorrect()){
					throw new IllegalArgumentException("Username or Password are not correct, please try again\n");
				}
					
				koalaAuthProp.store(out, "");

				out.close();
			} catch (IllegalArgumentException e){
				koalaAuthCreationAttempts++;
				System.out.println(e.getMessage());
				createKoalaConf();				
			} catch (FileNotFoundException e1) {
				System.out
						.println("\"koala.auth\" could not be created. Please check your Directory!");
			} catch (IOException e) {
				System.out
						.println("\"koala.auth\" is corrupted, it can not be written. Please check your Directory!");
			}

			setKoalaURL();
		} else {
			System.exit(-1);
		}
	}
	
	private static boolean koalaLoginCorrect(){
		//TODO 
		return true;
	}

	protected String getKoalaUsername() {			
		return koalaAuthProp.getProperty("Username");
	}

	protected String getKoalaPassword() {
		return koalaAuthProp.getProperty("Password");
	}

}