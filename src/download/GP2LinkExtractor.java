package download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GP2LinkExtractor extends KoalaAuth {

	private static final String GP2Vorlesung = "https://koala.uni-paderborn.de/semester/SS13/348325067931596/units/3460438/";

	public GP2LinkExtractor() {
		super();
	}

	private Elements allPageLinks(URL pageUrl) {
		Elements links = null;
		try {
			Document doc = Jsoup.connect(pageUrl.toString()).get();
			links = doc.select("a[href]");
		} catch (IOException e) {
			System.out.println("your are offline!");
		}
		return links;
	}

	public HashSet<URL> getVorlesungsLinks() {
		Elements allLinks = allPageLinks(new URL(GP2Vorlesung));
		HashSet<URL> links = new HashSet<URL>();
		for (Element link : allLinks) {
			String linkUrl = link.attr("href");
			if (Pattern.matches("fileadmin/.*/Pr%C3%A4senzaufgaben/.*\\.pdf",
					linkUrl))
				try {
					links.add(new URL(linaHostUrlString + linkUrl));
				} catch (MalformedURLException e) {
					System.out.println("LinaPräsURL is wrong formated");
				}
		}
		return links;
	}

	public URL getLectureZip() {
		Elements allLinks = allPageLinks(new URL(GP2Vorlesung));
		URL link;
		String linkUrl = link.attr("href");
		if (Pattern.matches(".*lecture.zip", linkUrl)){
			try {
				link(new URL(linaHostUrlString + linkUrl));
			} catch (MalformedURLException e) {
				System.out.println("LinaPräsURL is wrong formated");
			}
		}
		return link;
	}

	public HashSet<URL> getHeimLinks() {

	}

	public HashSet<URL> getPaesLinks() {

	}
}
