package crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Crawler {

	public static void resume(DataHandler handler, String name) throws NoDataException, InterruptedException{
		System.out.println("Starting crawl with " + name);	
		BFS crawler = new BFS(name, handler);
		boolean firstRun = true;
		do{
			ArrayList<String[]> edges = crawler.findEdges();
			if(edges != null){			
				System.out.println("Found " + edges.size() + " friends of " + name);
				for(String[] edge : edges) handler.addEdge(edge[0], edge[1]);
			}
			else{
				System.out.println("Edges are null");
			}
			name = crawler.getNextChild();
			crawler.setCurrentParent(name);
			//System.out.println(handler.toString());

			if((!(crawler.getQueueSize() > 0) /*|| crawler.getNodeCount() > 100*/) && !firstRun){
				handler.setEnumeratedList(crawler.getEnumeratedList());
				break;
			}
			Thread.sleep(2000);
			System.out.println();

			System.out.println(name);
			if(firstRun) firstRun = false;
			
		}
		while(crawler.peekNextChild() != null);
		try{
			handler.writeToFile();
		}
		catch (NoDataException e){
			throw e;
		}
	}
	
	public static void main(String[] args){
		String input;
		InputStreamReader inReader = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(inReader);
		do{
			System.out.print("Welcome to Gregg's webcrawler. Shall I pick up where I left off? (y/n): ");
			try {
				input = buffer.readLine();
				if(input.equals("y") || input.equals("Y")){
					System.out.println("If there is a file, please enter the path. Otherwise type \"n.\"");
					input = buffer.readLine();
					DataHandler handler;
					boolean fail = false;
					if(input.equals("n")){
						handler = new DataHandler();
					}
					else{
						handler = new DataHandler(input);
						do{
							try{
								handler.readFromFile();
								break;
							}
							catch(FileNotFoundException e) {
								System.out.println("Enter a correct filename or \"n\" to cancel:");
								input = buffer.readLine();
								if(!input.equals("n")){
									handler.setFilePath(input);
								}
								else{
									fail = true;
									break;
								}
							}
						}
						while(true);
					}
					if(!fail){
						resume(handler, handler.getLastEdge()[1]);	
					}
					//method to continue
					break;
				}
				else if(input.equals("n") || input.equals("N")){
					System.out.println("Please enter a filename or \"n\" for default.");
					input = buffer.readLine();
					DataHandler handler;
					if(input.equals("n")){
						handler = new DataHandler();
					}
					else{
						handler = new DataHandler(input);
					}
					do{
						try{
			
							System.out.println("Please enter a name to begin searching:");
							input = buffer.readLine();
							resume(handler, input);
							break;
						}
						catch(NoDataException e) {
							System.out.println("Enter a correct name:");
							System.err.println(e.getMessage());
							input = buffer.readLine();
							handler.setFilePath(input);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					while(true);
					break;
				}
				else{
					System.out.println("Enter \"y\" or \"n\"");
				}
			} 
			catch (NoDataException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			catch (IOException e){
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		} while(true);
	}

}
