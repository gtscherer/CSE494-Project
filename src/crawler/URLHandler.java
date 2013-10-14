package crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;


public class URLHandler {
	private String domain;
	public URLHandler(String domain) {
		this.domain = domain;
	}
	
	public Document getHtml(String subDomain){
		while(true){
			try {
				HttpConnection http = (HttpConnection) Jsoup.connect(this.domain + subDomain);
				Document result = http.get();
				System.out.println("response code: " + http.response().statusCode());
				if(http.response().statusCode() == 404){
					return null;
				}
				return result;
			} 
			catch (IOException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
				e1.printStackTrace();
				try{
					Thread.sleep(2000);
				}
				catch(InterruptedException e){
					System.err.println(e.getMessage());
				}
			}
			
		}
	}
	
	
	
}
