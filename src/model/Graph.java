package model;

import java.util.ArrayList;

public class Graph {
	private int adjacency_matrix[][];
	private int edges[][];
	private String nodes[][];
	private int numb_edges;
	private int numb_nodes;
	// edges corresponds to the set of edges of a graph, where [i][0] is the source and [i][1], target.
	// nodes corresponds to the set of nodes of a graph, where [i][0] = node name, [i][1] = node type, and i = node index.
	
	public Graph(){
		this.setEdges(new int[15][2]);
		this.setNodes(new String[15][2]);
		this.setNumbEdges(0);
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
		
		for (int i = 0; i < this.numb_edges; i++){
			int source = this.edges[i][0];
			int target = this.edges[i][1];
			
			this.adjacency_matrix[source][target] = 1;
		}
	}

	public void addNewEdge(int source, int target){	
		this.edges[this.numb_edges][0] = source;
		this.edges[this.numb_edges][1] = target;
		this.numb_edges++;
	}
	
	public void addNewNode(int index, String name, String type){
		this.numb_nodes++;
		this.nodes[index][0] = name;
		this.nodes[index][1] = type;
	}
	
	public int[][] getLinks() {
		return this.edges;
	}

	public void setEdges(int links[][]) {
		this.edges = links;
	}

	public String[][] getNodes() {
		return this.nodes;
	}

	public void setNodes(String nodes[][]) {
		this.nodes = nodes;
	}

	public int getNumbEdges() {
		return this.numb_edges;
	}

	public void setNumbEdges(int numb_links) {
		this.numb_edges = numb_links;
	}
	
	public int getNumb_nodes(){
		return this.numb_nodes;
	}
	
	public void setNumb_nodes(int n){
		this.numb_nodes = n;
	}
	
	public int[][] getEdges(){
		return this.edges;
	}


	public void printInfo(){
		System.out.println("Number of links: " + this.numb_edges);
		System.out.println("Number of nodes: " + this.numb_nodes);
		
		System.out.println("Links: ");
		for (int i = 0; i < this.numb_edges; i++){
			System.out.println("Source: " + this.edges[i][0] + " Target: " + this.edges[i][1]);
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
		if (this.numb_edges == 0){
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
	
	public int getVertexIndexFromName(String name){
		for (int i = 0 ; i < this.numb_nodes; i++){
			if (this.nodes[i][0].equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	public void graphToDFSTree(){
		ArrayList<Integer> stack = new ArrayList<Integer>();
		int root = 0;
		int visited_nodes[] = new int[this.numb_nodes];
		stack.add(root);
		Node tree = new Node(root);
		
		/*
		while (root < this.numb_nodes){
			for (int i = 0; i < this.numb_nodes; i++){
				if (this.adjacency_matrix[root][i] == 1){
					// There is a connection. Let's consider it as a child.
					stack.add(i);
					Node node = new Node(root, i);
					tree.addChild(node);
					root = i;
				}
			}
			root++;
		}
		*/
		
	}
	
	public void testDFS(){
		int visited_nodes[] = new int[this.numb_nodes];
		
		for (int i = 0; i < this.numb_nodes; i++){
			System.out.println(this.getNameFromIndex(i));
			//this.dfs(i, visited_nodes);
		}
		
	}
	
	private void dfs(int node, int visited_nodes[]){
		visited_nodes[node] = 1;
		int i = 0;
		for (i = 0; i < this.numb_nodes; i++){
			if(this.adjacency_matrix[node][i] == 1 && (visited_nodes[i] == 0)){
				System.out.println(this.getNameFromIndex(i));
				dfs(i, visited_nodes);
			}
		}
	}
    
	public ArrayList<String> getNodeConnectionsNames(int index){
		ArrayList<Integer> connections = new ArrayList<Integer>();
		ArrayList<String> connections_names = new ArrayList<String>();
		connections = this.getNodeConnections(index);
		
		// Get all connections names.
		for (Integer c : connections){
			connections_names.add(this.getNameFromIndex(c));
		}
		return connections_names;
	}
	
	private int countConnections(int index){
		int count = 0;
		for (int i = 0; i < this.numb_nodes; i++){
			for (int j = 0; j < this.numb_nodes; j++){
				if (this.adjacency_matrix[i][j] == 1){
					count++;
				}
			}
		}
		return count;
	}
	
	public double[] calculatePageRank(){
		double[] previous_pagerank = new double[this.numb_nodes];
		double[] current_pagerank = new double[this.numb_nodes];
		
		// Initialize
		for (int i = 0; i < this.numb_nodes; i++){
			previous_pagerank[i] = (double)1/this.numb_nodes;
			current_pagerank[i] = (double)1/this.numb_nodes;
		}
		double lambda = 0.85; // Suggested value
		double sum;
		double count = 0;
		
		for (int iterations = 0; iterations < 10; iterations++){
			previous_pagerank = current_pagerank.clone();
			// Calculates the pagerank for each vertex
			for (int i = 0; i < this.numb_nodes; i++){
				sum = 0;
				count = 0;
				for (int j = 0; j < this.numb_nodes; j++){
					if (this.adjacency_matrix[j][i] == 1){
						sum += (double)previous_pagerank[j];
						count++;
					}
				}
				if (count == 0){
					current_pagerank[i] = (double)(1 - lambda)/this.numb_nodes;
				}
				else{
					current_pagerank[i] = (double)(1 - lambda)/this.numb_nodes + (double)(lambda * (sum/count));
				}
			}
			iterations++;
		}
		return current_pagerank;
	}
	
	public double[] calculateVectorPageRank(ArrayList<Integer> nodes){
		double[] previous_pagerank = new double[nodes.size()];
		double[] current_pagerank = new double[nodes.size()];
		
		// Initialize
		for (int i = 0; i < nodes.size(); i++){
			previous_pagerank[i] = (double)1/this.numb_nodes;
			current_pagerank[i] = (double)1/this.numb_nodes;
		}
		double lambda = 0.85; // Suggested value
		double sum;
		double count = 0;
		
		for (int iterations = 0; iterations < 10; iterations++){
			previous_pagerank = current_pagerank.clone();
			// Calculates the pagerank for each vertex
			for (Integer i : nodes){
				sum = 0;
				count = 0;
				for (int j = 0; j < this.numb_nodes; j++){
					if (this.adjacency_matrix[j][i] == 1){
						sum += (double)previous_pagerank[j];
						count++;
					}
				}
				if (count == 0){
					current_pagerank[i] = (double)(1 - lambda)/this.numb_nodes;
				}
				else{
					current_pagerank[i] = (double)(1 - lambda)/this.numb_nodes + (double)(lambda * (sum/count));
				}
			}
			iterations++;
		}
		return current_pagerank;
	}
	
	public int getNumberNodeOutlinks(int node){
		int count = 0;
		
		for (int i = 0; i < this.numb_edges; i++){
			if (this.edges[i][0] == node){
				count++;
			}
		}
		
		return count;
	}
	
	public int getNumberNodeInlinks(int node){
	int count = 0;
		
		for (int i = 0; i < this.numb_edges; i++){
			if (this.edges[i][1] == node){
				count++;
			}
		}
		
		return count;
	}
	
	// Considering undirected networks
	public boolean containsEdge(int source,int target){
		
		for (int i = 0; i < this.numb_edges; i++){
			if (this.edges[i][0] == source){
				if (this.edges[i][1] == target){
					return true;
				}
			}
		}
		return false;
	}
}

