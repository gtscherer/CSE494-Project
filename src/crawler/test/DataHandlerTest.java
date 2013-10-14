package crawler.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import crawler.DataHandler;
import crawler.HTMLParser;
import crawler.NoDataException;
import crawler.URLHandler;

@RunWith(JUnit4.class)
public class DataHandlerTest {
	private final String[] TEST_DATUM_1 = { "1" , "2" };
	private final String[] TEST_DATUM_2 = { "1" , "3" };
	private final String[] TEST_DATUM_3 = { "2" , "3" };

	
	@Test
	public void IntegrationTest(){
		URLHandler urlHandler = new URLHandler("http://www.last.fm");
		final String PARENT = "gerglewerx";
		Document html = urlHandler.getHtml("/user/" + PARENT + "/friends");
		HTMLParser parser = new HTMLParser(html);
		ArrayList<String> friends;
		try {
			friends = parser.getFriends();
			DataHandler handler = new DataHandler("test_data4.txt");
			for(String friend : friends){
				handler.addEdge(PARENT, friend);
			}
//			System.out.println(handler.toString());
			ArrayList<String[]> edgeList = handler.getEdgeList();
			String[] first_pair = edgeList.get(0);
			String[] second_pair = edgeList.get(1);
			String[] third_pair = edgeList.get(2);
			final String FIRST_CHILD = "AdamLikesMusic";
			final String SECOND_CHILD = "HomoRainbow";
			final String THIRD_CHILD = "Kakafeeni";
			assertTrue(first_pair[0].equals(PARENT));
			assertTrue(first_pair[1].equals(FIRST_CHILD));
			assertTrue(second_pair[0].equals(PARENT));
			assertTrue(second_pair[1].equals(SECOND_CHILD));
			assertTrue(third_pair[0].equals(PARENT));
			assertTrue(third_pair[1].equals(THIRD_CHILD));
		} 
		catch (NoDataException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void TestSaveDataToPath() {
		ArrayList<String[]> TEST_LIST_1 = new ArrayList<String[]>();
		TEST_LIST_1.add(TEST_DATUM_1);
		TEST_LIST_1.add(TEST_DATUM_2);
		TEST_LIST_1.add(TEST_DATUM_3);

		DataHandler handler = new DataHandler("./test_data2.txt");
		handler.setEdgeList(TEST_LIST_1);
		try {
			handler.writeToFile();
		} catch (NoDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String[]> emptyList = new ArrayList<String[]>();
		handler.setEdgeList(emptyList);
		try {
			handler.readFromFile();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String[]> OUTPUT_LIST = handler.getEdgeList();
		
		try{
			String[] first_pair = OUTPUT_LIST.get(0);
			String[] second_pair = OUTPUT_LIST.get(1);
			String[] third_pair = OUTPUT_LIST.get(2);
			assertTrue(TEST_DATUM_1[0].equals(first_pair[0]));
			assertTrue(TEST_DATUM_1[1].equals(first_pair[1]));
			assertTrue(TEST_DATUM_2[0].equals(second_pair[0]));
			assertTrue(TEST_DATUM_2[1].equals(second_pair[1]));
			assertTrue(TEST_DATUM_3[0].equals(third_pair[0]));
			assertTrue(TEST_DATUM_3[1].equals(third_pair[1]));
		}
		catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void TestSaveData() {
		ArrayList<String[]> TEST_LIST_1 = new ArrayList<String[]>();
		TEST_LIST_1.add(TEST_DATUM_1);
		TEST_LIST_1.add(TEST_DATUM_2);
		TEST_LIST_1.add(TEST_DATUM_3);

		DataHandler handler = new DataHandler("test_data.txt");
		handler.setEdgeList(TEST_LIST_1);
		try {
			handler.writeToFile();
		} catch (NoDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String[]> emptyList = new ArrayList<String[]>();
		handler.setEdgeList(emptyList);
		try {
			handler.readFromFile();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String[]> OUTPUT_LIST = handler.getEdgeList();
		
		try{
			String[] first_pair = OUTPUT_LIST.get(0);
			String[] second_pair = OUTPUT_LIST.get(1);
			String[] third_pair = OUTPUT_LIST.get(2);
			assertTrue(TEST_DATUM_1[0].equals(first_pair[0]));
			assertTrue(TEST_DATUM_1[1].equals(first_pair[1]));
			assertTrue(TEST_DATUM_2[0].equals(second_pair[0]));
			assertTrue(TEST_DATUM_2[1].equals(second_pair[1]));
			assertTrue(TEST_DATUM_3[0].equals(third_pair[0]));
			assertTrue(TEST_DATUM_3[1].equals(third_pair[1]));
		}
		catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void TestAddEdge() {
		ArrayList<String[]> TEST_LIST_1 = new ArrayList<String[]>();
		TEST_LIST_1.add(TEST_DATUM_1);
		TEST_LIST_1.add(TEST_DATUM_2);
		TEST_LIST_1.add(TEST_DATUM_3);

		DataHandler handler = new DataHandler("test_data3.txt");
		handler.setEdgeList(TEST_LIST_1);
		
		final String TEST_PARENT = "3";
		final String TEST_CHILD = "2";
		handler.addEdge(TEST_PARENT, TEST_CHILD);
		ArrayList<String[]> OUTPUT_LIST = handler.getEdgeList();
		try{
			String[] first_pair = OUTPUT_LIST.get(0);
			String[] second_pair = OUTPUT_LIST.get(1);
			String[] third_pair = OUTPUT_LIST.get(2);
			String[] fourth_pair = OUTPUT_LIST.get(3);
			assertTrue(TEST_DATUM_1[0].equals(first_pair[0]));
			assertTrue(TEST_DATUM_1[1].equals(first_pair[1]));
			assertTrue(TEST_DATUM_2[0].equals(second_pair[0]));
			assertTrue(TEST_DATUM_2[1].equals(second_pair[1]));
			assertTrue(TEST_DATUM_3[0].equals(third_pair[0]));
			assertTrue(TEST_DATUM_3[1].equals(third_pair[1]));
			assertTrue(TEST_PARENT.equals(fourth_pair[0]));
			assertTrue(TEST_CHILD.equals(fourth_pair[1]));
			assertFalse(handler.addEdge(TEST_PARENT, TEST_CHILD));
			assertTrue(handler.removeEdge(fourth_pair));
		}
		catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}
