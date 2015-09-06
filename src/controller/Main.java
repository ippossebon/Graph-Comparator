package controller;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Graph;
import view.ApplicationFrame;

public class Main {

	public static void main(String args[]){
		JSONGraphParser parser = new JSONGraphParser();
		ApplicationFrame frame;
		
		parser.execute();
		System.out.println("Number of graphs: " + parser.getGraphs().size());
		
		for (Graph g : parser.getGraphs()){
			g.createAdjacencyMatrix();
		}
		ArrayList<String> graphs1 = new ArrayList<String>();
		ArrayList<String> directions1 = new ArrayList<String>();
		ArrayList<String> graphs2 = new ArrayList<String>();
		ArrayList<String> directions2 = new ArrayList<String>();
		ArrayList<Double> simple_comparison_results = new ArrayList<Double>();
		
		Comparator comparator = new Comparator();
		JTable directed_networks_table = new JTable();
		JTable undirected_networks_table = new JTable();
		DefaultTableModel dft_directed = new DefaultTableModel();
		DefaultTableModel dft_undirected = new DefaultTableModel();
		
		/***** Directed networks ******/
		/* Simple comparison */
		double degree = 0;
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			directions1.add("Yes");
			graphs2.add(parser.getPeople().get(i+1).getName());
			directions2.add("Yes");
			degree = comparator.myCompare(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			simple_comparison_results.add(degree);
		}	
		dft_directed.addColumn("Graph 1 ID", graphs1.toArray());
		dft_directed.addColumn("Directed", directions1.toArray());
		dft_directed.addColumn("Graph 2 ID", graphs2.toArray());
		dft_directed.addColumn("Directed", directions2.toArray());
		dft_directed.addColumn("Simple comparison", simple_comparison_results.toArray());
		
		/* Two steps comparison */
		ArrayList<Double> two_steps_comparison_results = new ArrayList<Double>();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			degree = comparator.twoStepsComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			two_steps_comparison_results.add(degree);
		}
		dft_directed.addColumn("Two steps comparison", two_steps_comparison_results.toArray());
		
		/* Graph distance comparison */
		ArrayList<Double> graph_distance_results = new ArrayList<Double>();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			degree = comparator.graphDistance(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			graph_distance_results.add(degree);
		}
		dft_directed.addColumn("Graph distance", graph_distance_results.toArray());
		
		/* Vertex ranking comparison */
		ArrayList<Double> vertex_ranking_results = new ArrayList<Double>();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			try{
				degree = comparator.vertexRankingComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i).getDirected_graph());
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i);
			}
			
			vertex_ranking_results.add(degree);
		}
		dft_directed.addColumn("Vertex ranking", vertex_ranking_results.toArray());
		directed_networks_table.setModel(dft_directed);
		
		/***** Undirected networks ******/
		
		 /* Simple comparison */
		graphs1.clear();
		graphs2.clear();
		directions1.clear();
		directions2.clear();
		ArrayList<Double> simple_comparison_results2 = new ArrayList<Double>();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			directions1.add("No");
			graphs2.add(parser.getPeople().get(i+1).getName());
			directions2.add("No");
			degree = comparator.myCompare(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i).getUndirected_graph());
			simple_comparison_results2.add(degree);
		}	
		dft_undirected.addColumn("Graph 1 ID", graphs1.toArray());
		dft_undirected.addColumn("Directed", directions1.toArray());
		dft_undirected.addColumn("Graph 2 ID", graphs2.toArray());
		dft_undirected.addColumn("Directed", directions2.toArray());
		dft_undirected.addColumn("Simple comparison", simple_comparison_results2.toArray());
		
		/* Two steps comparison */
		ArrayList<Double> two_steps_comparison_results2 = new ArrayList<Double>();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			degree = comparator.twoStepsComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			two_steps_comparison_results2.add(degree);
		}
		dft_undirected.addColumn("Two steps comparison", two_steps_comparison_results2.toArray());
		
		/* Graph distance comparison */
		ArrayList<Double> graph_distance_results2 = new ArrayList<Double>();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			degree = comparator.graphDistance(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			graph_distance_results2.add(degree);
		}
		dft_undirected.addColumn("Graph distance", graph_distance_results2.toArray());
		
		/* Vertex ranking comparison */
		ArrayList<Double> vertex_ranking_results2 = new ArrayList<Double>();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			try{
				degree = comparator.vertexRankingComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i);
			}
			
			vertex_ranking_results2.add(degree);
		}
		dft_undirected.addColumn("Vertex ranking", vertex_ranking_results2.toArray());
		
		undirected_networks_table.setModel(dft_undirected);
		
		frame = new ApplicationFrame(directed_networks_table, undirected_networks_table);
		frame.setVisible(true);
		
	}
}
