package model;

import java.util.ArrayList;

public class Graph {
	private int adjacency_matrix[][];
	private int links[][];
	private String nodes[][];
	private int numb_links;
	private int numb_nodes;
	// links corresponds to the set of links of a graph, where [i][0] is the source and [i][1], target.
	// nodes corresponds to the set of nodes of a graph, where [i][0] = node name, [i][1] = node type, and i = node index.
	
	public Graph(){
		this.setLinks(new int[15][2]);
		this.setNodes(new String[15][2]);
		this.setNumb_links(0);
		this.setNumb_nodes(0);
	}

	public int[][] getAdjacency_matrix() {
		return this.adjacency_matrix;
	}

	public void setAdjacency_matrix(int adjacency_matrix[][]) {
		this.adjacency_matrix = adjacency_matrix;
	}
	
	private void initializeMatrix(){
		this.adjacency_matrix = new int[this.numb_nodes][this.numb_nodes];
		
		for (int i = 0; i < this.numb_nodes; i++){
			for (int j = 0; j < this.numb_nodes; j++){
				this.adjacency_matrix[i][j] = 0;
			}
		}
	}
	
	public void createAdjacencyMatrix(){
		initializeMatrix();
		
		for (int i = 0; i < this.numb_links; i++){
			int source = this.links[i][0];
			int target = this.links[i][1];
			
			this.adjacency_matrix[source][target] = 1;
		}
	}

	public void addNewLink(int source, int target){	
		this.links[this.numb_links][0] = source;
		this.links[this.numb_links][1] = target;
		this.numb_links++;
	}
	
	public void addNewNode(int index, String name, String type){
		this.numb_nodes++;
		this.nodes[index][0] = name;
		this.nodes[index][1] = type;
	}
	
	public int[][] getLinks() {
		return this.links;
	}

	public void setLinks(int links[][]) {
		this.links = links;
	}

	public String[][] getNodes() {
		return this.nodes;
	}

	public void setNodes(String nodes[][]) {
		this.nodes = nodes;
	}

	public int getNumb_links() {
		return this.numb_links;
	}

	public void setNumb_links(int numb_links) {
		this.numb_links = numb_links;
	}
	
	public int getNumb_nodes(){
		return this.numb_nodes;
	}
	
	public void setNumb_nodes(int n){
		this.numb_nodes = n;
	}


	public void printInfo(){
		System.out.println("Number of links: " + this.numb_links);
		System.out.println("Number of nodes: " + this.numb_nodes);
		
		System.out.println("Links: ");
		for (int i = 0; i < this.numb_links; i++){
			System.out.println("Source: " + this.links[i][0] + " Target: " + this.links[i][1]);
		}
		
		System.out.println("Nodes: ");
		for (int j = 0; j < this.numb_nodes; j++){
			System.out.println("Index: " + j + " Name: " + this.nodes[j][0] + " Type: " + this.nodes[j][1]);
		}
	}
	
	public void printAdjancencyMatrix(){
		for (int i = 0; i < this.numb_nodes; i++){
			for (int j = 0; j < this.numb_nodes; j++){
				System.out.print(this.adjacency_matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public ArrayList<Integer> getNodeConnections(int index){
		ArrayList<Integer> connections = new ArrayList<Integer>();
		if (this.numb_links == 0){
			return null;
		}
		for (int i = 0; i < this.numb_nodes; i++){
			try{
				if (this.adjacency_matrix[index][i] == 1){
					connections.add(i);
				}
			}
			catch(NullPointerException e){
				e.printStackTrace();
				if (this.adjacency_matrix == null){
					System.out.println("matrix vazia");
				}
			}
			
		}
		return connections;
	}
	
	public String getNameFromIndex(int index){
		try{
			return this.nodes[index][0];
		}
		catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
}
