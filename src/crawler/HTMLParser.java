package crawler;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
	private Document html;
	private String parent;
	public HTMLParser(Document html) {
		this.html = html;
	}
	public HTMLParser(Document html, String parent){
		this.html = html;
		this.parent = parent;
	}
	
	public Document getHtml(){
		return this.html;
	}
	public void setHtml(Document html){
		this.html = html;
	}
	
	public ArrayList<String> getFriends() throws NoDataException{
		if(this.html != null){
			//Too good to be true
			Elements elements = html.getElementsByClass("vcard");
			//System.out.println(elements.html());
			ArrayList<String> friends = new ArrayList<String>();
			if(elements.html().isEmpty()){
				throw new NoDataException(NoDataException.NO_FRIEND, "Parent is " + parent);
			}
			else{
				for(Element element : elements){
					if(!element.html().isEmpty()){
						friends.add(element.getElementsByAttributeValueMatching("href", "/user/").get(0).attr("href").substring(6));
					}
				}
				if(friends.size() < 1){
					throw new NoDataException(NoDataException.PARSE_FAIL, "ArrayList of friends is empty.");
				}
				else{
					return friends;
				}
			}
		}
		else{
			throw new NoDataException(NoDataException.MISSING_HTML);
		}
		
	}

}
