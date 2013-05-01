package download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author gamer01
 * 	Provides functions to get actual direct download links for the actual sheets
 */
public class LinkExtractor {
	final String linaHostUrlString = "http://www2.math.uni-paderborn.de/";
	final String linaFileUrlString = "people/eike-lau/lineare-algebra-fuer-informatiker.html";
	Elements allLinaLinks;

	public LinkExtractor() {
		getAllLinaLinks();
	}

	/**
	 * Initializes the allLinaLinks variable with all Links on the LinA homepage
	 */
	private void getAllLinaLinks() {
		try {
			Document doc = Jsoup.connect(linaHostUrlString+linaFileUrlString).get();
			allLinaLinks = doc.select("a[href]");
		} catch (IOException e) {
			System.out.println("your are offline!");
		}
	}

	/**
	 * @return a linkedList of URLs linking to the LinaPräsenzSheets
	 */
	public LinkedList<URL> getLinaPräsLinks() {
		LinkedList<URL> links = new LinkedList<URL>();
		for (Element link : allLinaLinks) {
			String linkUrl = link.attr("href");
			if (Pattern.matches("fileadmin/.*/Pr%C3%A4senzaufgaben/.*\\.pdf", linkUrl))
				try {
					links.add(new URL(linaHostUrlString+linkUrl));
				} catch (MalformedURLException e) {
					System.out.println("LinaPräsURL is wrong formated");
				}
		}
		return links;
	}
	
	/**
	 * @return a linkedList of URLs linking to the LinaHomeSheets
	 */
	public LinkedList<URL> getLinaHeimLinks() {
		LinkedList<URL> links = new LinkedList<URL>();
		for (Element link : allLinaLinks) {
			String linkUrl = link.attr("href");
			if (Pattern.matches("fileadmin/.*/Hausaufgaben/.*\\.pdf", linkUrl))
				try {
					links.add(new URL(linaHostUrlString+linkUrl));
				} catch (MalformedURLException e) {
					System.out.println("LinaHeimURL is wrong formated");
				}
		}
		return links;
	}
}
