package crawler.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import crawler.HTMLParser;
import crawler.NoDataException;
import crawler.URLHandler;

@RunWith(JUnit4.class)
public class HTMLParserTest {
	@Test
	public void IntegrationTest(){
		URLHandler handler = new URLHandler("http://www.last.fm");
		Document html = handler.getHtml("/user/gerglewerx/friends");
		HTMLParser parser = new HTMLParser(html);
		ArrayList<String> friends;
		try {
			friends = parser.getFriends();
			assertSame("Friends should have 3 members: ", friends.size(), 3);
			System.out.println("Friends found: " + friends.size());
			System.out.print("Found: ");
			for(String friend : friends){
				System.out.print(friend + ", ");
			}
		} 
		catch (NoDataException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void Test404(){
		URLHandler handler = new URLHandler("http://www.last.fm");
		Document html = handler.getHtml("/user/bullshiit/friends");
		assertTrue(handler.getResponseCode() == 404);
		assertTrue(html == null);
	}
//	@Test
//	public void testGetFriendsFile(){
//		FileReader sampleHtml;
//		try {
//			sampleHtml = new FileReader("/Users/gtscherer/Documents/workspace3/Last.fm Crawler/sample.html");
//			BufferedReader buffer = new BufferedReader(sampleHtml);
//			StringBuilder buildTestString = new StringBuilder();
//			String line = buffer.readLine();
//			while(line != null){
//				buildTestString.append(line);
//				line = buffer.readLine();
//			}
//			buffer.close();
//			sampleHtml.close();
//			Document html = new Document("");
//			//System.out.println(buildTestString.toString());
//			html.html(buildTestString.toString());
//			HTMLParser parser = new HTMLParser(html);
//			ArrayList<String> friends = parser.getFriends();
//			assertSame("Friends should have 3 members: ", friends.size(), 3);
//			for(String friend : friends){
//				System.out.println(friend);
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			System.err.println(e.getMessage());
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.err.println(e.getMessage());
//		} catch (NoDataException e) {
//			e.printStackTrace();
//			System.err.println(e.getMessage());
//		}
//		
//	}

}
