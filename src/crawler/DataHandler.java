package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataHandler {	
	private ArrayList<String[]> edges = null, enumeratedEdgeList = null;
	private String filePath = null;
	
	public DataHandler() {
		this.edges = new ArrayList<String[]>();
		this.filePath = "output.txt";
	}
	public DataHandler(String path){
		this.edges = new ArrayList<String[]>();
		this.filePath = path;
	}
	public void setEnumeratedList(ArrayList<String[]> edges){
		this.enumeratedEdgeList = edges;
	}
	public void setFilePath(String path){
		this.filePath = path;
	}
	public String getFilePath(){
		return this.filePath;
	}
	public String[] getLastEdge(){
		return this.edges.get(this.edges.size() - 1);
	}
	public ArrayList<String[]> getEdgeList(){
		return this.edges;
	}
	public void setEdgeList(ArrayList<String[]> edges){
		this.edges = edges;
	}
	
	
	protected String dataOutputAggregator(ArrayList<String[]> edges) throws NoDataException{
		final int PARENT = 0;
		final int CHILD = 1;
		if(edges == null || edges.size() < 1){
			throw new NoDataException();
		}
		else{
			StringBuilder output = new StringBuilder();
			for(String[] edge : edges){
				output.append(edge[PARENT]);
				output.append(',');
				output.append(edge[CHILD]);
				output.append("\n");
			}
			return output.toString();
		}
	}
	
	protected void parseEdgeListLine(String line) throws NoDataException{
		StringBuilder parent = new StringBuilder(), child = new StringBuilder();
		boolean isChild = false;
		for(int i = 0; i < line.length(); ++i){
			char c = line.charAt(i);
			if(c != ','){
				if(isChild){
					child.append(c);
				}
				else{
					parent.append(c);
				}
			}
			else{
				isChild = true;
			}
		}
		if(child.length() < 1){
			throw new NoDataException(NoDataException.MISSING_CHILD, "Current line: " + line);
		}
		else if(parent.length() < 1){
			throw new NoDataException(NoDataException.MISSING_PARENT, "Current line: " + line);
		}
		else{
			this.addEdge(parent.toString(), child.toString());
		}
	}
	
	public void readFromFile() throws FileNotFoundException{
		try {
			FileReader fileReader;
			if(this.filePath == null){
				fileReader = new FileReader("data.txt");
			}
			else{
				fileReader = new FileReader(this.filePath);
			}
			BufferedReader bufferReader = new BufferedReader(fileReader);
			String line = bufferReader.readLine();
			while(line != null){
				parseEdgeListLine(line);
				line = bufferReader.readLine();
			}
			bufferReader.close();
			fileReader.close();
		} 
		catch (FileNotFoundException e) {
			System.err.println("File not found on path: " + this.filePath);
			e.printStackTrace();
			throw e;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch(NoDataException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void writeToFile() throws NoDataException{
		try {
			FileWriter fileWriter1, fileWriter2;
			if(this.filePath == null){
				fileWriter1 = new FileWriter("data.txt");
				fileWriter2 = new FileWriter("enum_data.txt");
			}
			else{
				fileWriter1 = new FileWriter(this.filePath);
				fileWriter2 = new FileWriter("enum_" + this.filePath);
			}
			BufferedWriter outBuffer = new BufferedWriter(fileWriter1);
			outBuffer.write(this.dataOutputAggregator(this.edges));
			outBuffer.close();
			fileWriter1.close();
			outBuffer = new BufferedWriter(fileWriter2);
			outBuffer.write(this.dataOutputAggregator(this.enumeratedEdgeList));
			outBuffer.close();
			fileWriter2.close();
		} 
		catch (IOException e) {
			System.err.println("Failed to write file...");
			e.printStackTrace();
		}
		catch(NoDataException e){
			throw e;
		}
	}
	
	//Check if edge is already in dataset
	protected boolean checkEdge(String[] edgeToCheck){
		boolean found = false;
		for(String[] edge : this.edges){
			if(edge[0].equals(edgeToCheck[0])){
				if(edge[1].equals(edgeToCheck[1])){
					found = true;
				}
			}
		}
		return found;
	}
	public int searchEdgeUndirected(String[] edgeToCheck){
		int index = -1;
		for(int i = 0; i < this.edges.size(); ++i){
			if(this.edges.get(i)[0].equals(edgeToCheck[0])){
				if(this.edges.get(i)[1].equals(edgeToCheck[1])){
					index = i;
					return index;
				}
			}
			else if(this.edges.get(i)[1].equals(edgeToCheck[0])){
				if(this.edges.get(i)[0].equals(edgeToCheck[1])){
					index = i;
					return index;
				}
			}
		}
		return index;
	}
	protected int searchEdge(String[] edgeToFind){
		int index = -1;
		for(int i = 0; i < this.edges.size(); ++i){
			if(this.edges.get(i)[0].equals(edgeToFind[0])){
				if(this.edges.get(i)[1].equals(edgeToFind[1])){
					index = i;
					break;
				}
			}
		}
		return index;
	}
	
	public boolean removeEdge(String[] edge){
		int index = searchEdge(edge);
		if(index < 0){
			return false;
		}
		else{
			this.edges.remove(index);
			return true;
		}
	}
	
	//Add an edge to dataset
	public boolean addEdge(String parent, String child){
		String[] edge = { parent, child };
		if(!this.checkEdge(edge)){
			this.edges.add(edge);
			return true;
		}
		else{
			System.out.println("Edge " + parent + " -> " + child + " already in dataset - skipping.");
			return false;
		}
	}
	@Override
	public String toString(){
		StringBuilder outString = new StringBuilder();
		for(String[] edge : this.edges){
			outString.append(edge[0] + ", " + edge[1] + "\n");
		}
		return outString.toString();
		
	}
	
}
