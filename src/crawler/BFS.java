package crawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.jsoup.nodes.Document;

public class BFS {

	private LinkedList<String> parents;
	private String currentParent;
	private DataHandler handler;
	private int nodeCount = 1;
	private int SAMPLE_SIZE = 2000;
	private ArrayList<String[]> enumeratedList = new ArrayList<String[]>();
	public BFS(String parent, DataHandler handler) {
		this.parents = new LinkedList<String>();
		this.currentParent = parent;
		this.handler = handler;
		String[] firstEdge = { "1" , parent };
		this.enumeratedList.add(firstEdge);
		++this.nodeCount;
	}
	public int getQueueSize(){
		System.out.println(this.parents.size());
		return this.parents.size();
	}
	public void setSampleSize(int size){
		this.SAMPLE_SIZE = size;
	}
	public ArrayList<String[]> getEnumeratedList(){
		return this.enumeratedList;
	}
	public String peekNextChild(){
		return this.parents.peek();
	}
	public String getNextChild(){
		return this.parents.poll();
	}
	public int getNodeCount(){
		return this.nodeCount;
	}
	protected int searchEnumList(String node){
		int index = -1;
		for(int i = 0; i < this.enumeratedList.size(); ++i) if(this.enumeratedList.get(i)[1].equals(node)) return i;
		return index;
	}
	public void addNewChild(String child){
		this.parents.offer(child);
	}
	public String getCurrentParent(){
		return this.currentParent;
	}
	public void setCurrentParent(String parent){
		this.currentParent = parent;
	}
	public ArrayList<String[]> shuffle(ArrayList<String[]> friends){
		ArrayList<String[]> shuffled = new ArrayList<String[]>();
		while(friends.size() > 0){
			Random rand = new Random();
			int index = rand.nextInt(friends.size());
			shuffled.add(friends.get(index));
			friends.remove(index);
		}
		if(shuffled.size() > SAMPLE_SIZE) for(int i = shuffled.size(); i < SAMPLE_SIZE && i < shuffled.size(); ++i) shuffled.remove(i); 
		return shuffled;
	}
	public ArrayList<String[]> findEdges(){
		String node = this.currentParent;
		URLHandler handler = new URLHandler("http://www.last.fm");
		String subDomain;
		HTMLParser parser = new HTMLParser();
		ArrayList<String[]> edgeList = new ArrayList<String[]>();
		int pageCounter = 1;
		ArrayList<String> firstPage = new ArrayList<String>();
		do{
			subDomain = "/user/" + node + "/friends/?page=" + pageCounter;
			Document html = handler.getHtml(subDomain);
			System.out.println(subDomain);
			if(html == null && pageCounter == 1) return null;
			else if(html == null) return edgeList;
			parser.setHtml(html);
			try {
				ArrayList<String> friends = parser.getFriends();
				if(pageCounter > 1){
					int howManyEqual = 0;
					int max;
					if(firstPage.size() < friends.size()){
						max = firstPage.size();
					}
					else{
						max = friends.size();
					}
					for(int i = 0; i < max; ++i){
						if(friends.get(i).equals(firstPage.get(i))){
							++howManyEqual;
						}
					}
					if(howManyEqual == firstPage.size()){
						//this.setCurrentParent(this.getNextChild());
						ArrayList<String[]> shuffled = shuffle(edgeList);
						for(String[] edge : shuffled) {
							this.addNewChild(edge[1]);
							//System.out.println(edge[0] + edge[1]);
							if(searchEnumList(edge[1]) == -1){
								String[] enumEdge = { String.valueOf(this.nodeCount), edge[1] };
								this.enumeratedList.add(enumEdge);
								++this.nodeCount;
							}
						}
						return shuffled;
					}
					else{
						for(String friend : friends){
							String[] edge = { node, friend };
							if(this.handler.searchEdgeUndirected(edge) == -1){
								System.out.println("Adding " + edge[0] + " -> " + edge[1]);
								edgeList.add(edge);
							}
						}
						++pageCounter;
					}
				}
				else{				
					for(String friend : friends){
						String[] edge = { node, friend };
						firstPage.add(friend);
						if(this.handler.searchEdgeUndirected(edge) == -1){
							System.out.println("Adding " + edge[0] + " -> " + edge[1]);
							edgeList.add(edge);
						}
					}
					++pageCounter;
					try {
						Thread.sleep(2000);
					} 
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} 
			catch (NoDataException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				this.setCurrentParent(this.getNextChild());
				return edgeList;
			}
			catch(NullPointerException e){
				System.err.println(e.getMessage());
				this.setCurrentParent(this.getNextChild());
				return edgeList;
			}
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true);
	}
}
