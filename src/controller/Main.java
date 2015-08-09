package controller;

import model.Graph;



public class Main {

	public static void main(String args[]){
		JSONGraphParser parser = new JSONGraphParser();
		
		parser.execute();
		System.out.println("Number of graphs: " + parser.getGraphs().size());
		
		for (Graph g : parser.getGraphs()){
			//System.out.println("-------------");
			//parser.getGraphs().get(i).printInfo();
			g.createAdjacencyMatrix();
			//parser.getGraphs().get(i).printAdjancencyMatrix();
			//System.out.println("-------------");
		}
		
		
		// Comparision
		Comparator comparator = new Comparator();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			System.out.println("Comparision between " + parser.getPeople().get(i).getName() + " and " + parser.getPeople().get(i+1).getName());
			comparator.compare(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			System.out.println("-----------------");
		}
		
		// TEST data/xam
		System.out.println("Comparision between DIRECTED AND UNDIRECTED GRAPHS: " + parser.getPeople().get(12).getName());
		comparator.compare(parser.getPeople().get(12).getDirected_graph(), parser.getPeople().get(12).getUndirected_graph());
		System.out.println("-----------------");
		
	}
}
