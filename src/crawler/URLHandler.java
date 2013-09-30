package crawler;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;


public class URLHandler {
	private String domain;
	public URLHandler(String domain) {
		this.domain = domain;
	}
	
	public Document getHtml(String subDomain){
		try {
			HttpConnection http = (HttpConnection) Jsoup.connect(this.domain + subDomain);
			return http.get();
		} 
		catch (Exception e) {
			System.err.println("Failed to parse " + this.domain + subDomain);
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
