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
		ArrayList<String> graphs2 = new ArrayList<String>();
		
		Comparator comparator = new Comparator();
		JTable directed_networks_table = new JTable();
		JTable undirected_networks_table = new JTable();
		DefaultTableModel dft_directed = new DefaultTableModel();
		DefaultTableModel dft_undirected = new DefaultTableModel();
		
		/***** Directed networks ******/
		double degree = 0;
		ArrayList<Double> simple_comparison_results = new ArrayList<Double>();
		ArrayList<Double> two_steps_comparison_results = new ArrayList<Double>();
		ArrayList<Double> graph_distance_results = new ArrayList<Double>();
		ArrayList<Double> vertex_ranking_results = new ArrayList<Double>();
		ArrayList<Double> vector_similarity_results = new ArrayList<Double>();
		
		for (int i = 0; i < (int)parser.getPeople().size()-1; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			graphs2.add(parser.getPeople().get(i+1).getName());
			
			degree = comparator.myCompare(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			simple_comparison_results.add(degree);
			
			degree = comparator.twoStepsComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			two_steps_comparison_results.add(degree);
			
			degree = comparator.graphDistance(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			graph_distance_results.add(degree);
			
			try{
				degree = comparator.vertexRankingComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i).getDirected_graph());
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i);
			}
			vertex_ranking_results.add(degree);
			
			degree = comparator.vectorSimilarity(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			vector_similarity_results.add(degree);
		}	
		dft_directed.addColumn("Graph 1 ID", graphs1.toArray());
		dft_directed.addColumn("Graph 2 ID", graphs2.toArray());
		dft_directed.addColumn("Simple comparison", simple_comparison_results.toArray());
		dft_directed.addColumn("Two steps comparison", two_steps_comparison_results.toArray());
		dft_directed.addColumn("Graph distance", graph_distance_results.toArray());
		dft_directed.addColumn("Vertex ranking", vertex_ranking_results.toArray());
		dft_directed.addColumn("Vector similarity", vector_similarity_results.toArray());
		directed_networks_table.setModel(dft_directed);
		
		/***** Undirected networks ******/
		graphs1.clear();
		graphs2.clear();
		ArrayList<Double> simple_comparison_results2 = new ArrayList<Double>();
		ArrayList<Double> two_steps_comparison_results2 = new ArrayList<Double>();
		ArrayList<Double> graph_distance_results2 = new ArrayList<Double>();
		ArrayList<Double> vertex_ranking_results2 = new ArrayList<Double>();
		ArrayList<Double> vector_similarity_results2 = new ArrayList<Double>();
		
		for (int i = 0; i < (int)parser.getPeople().size()-1; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			graphs2.add(parser.getPeople().get(i+1).getName());
			
			degree = comparator.myCompare(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i).getUndirected_graph());
			simple_comparison_results2.add(degree);
			
			degree = comparator.twoStepsComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			two_steps_comparison_results2.add(degree);
			
			degree = comparator.graphDistance(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			graph_distance_results2.add(degree);
			
			try{
				degree = comparator.vertexRankingComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i);
			}
			vertex_ranking_results2.add(degree);
			
			degree = comparator.vectorSimilarity(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			vector_similarity_results2.add(degree);
		}	
		dft_undirected.addColumn("Graph 1 ID", graphs1.toArray());
		dft_undirected.addColumn("Graph 2 ID", graphs2.toArray());
		dft_undirected.addColumn("Simple comparison", simple_comparison_results2.toArray());
		dft_undirected.addColumn("Two steps comparison", two_steps_comparison_results2.toArray());
		dft_undirected.addColumn("Graph distance", graph_distance_results2.toArray());
		dft_undirected.addColumn("Vertex ranking", vertex_ranking_results2.toArray());
		dft_undirected.addColumn("Vector similarity", vector_similarity_results2.toArray());
		
		undirected_networks_table.setModel(dft_undirected);
		
		/* Big networks */
		parser.executeBigNetworks();
		JTable big_networks_table = new JTable();
		DefaultTableModel dft = new DefaultTableModel();
		
		for (Graph g : parser.getNetworks()){
			g.createAdjacencyMatrix();
		}
		
		ArrayList<String> nets1 = new ArrayList<String>();
		ArrayList<String> nets2 = new ArrayList<String>();
		ArrayList<Double> results_simple_compare = new ArrayList<Double>();
		ArrayList<Double> results_two_steps = new ArrayList<Double>();
		ArrayList<Double> results_graph_distance = new ArrayList<Double>();
		ArrayList<Double> results_vertex_ranking = new ArrayList<Double>();
		ArrayList<Double> results_vector_similarity = new ArrayList<Double>();
		
		for (int i = 0; i < (int)parser.getNetworks().size() -1; i++){
			nets1.add(parser.getNetworks().get(i).getId());
			nets2.add(parser.getNetworks().get(i+1).getId());
			
			degree = comparator.myCompare(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_simple_compare.add(degree);
			
			degree = comparator.twoStepsComparison(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_two_steps.add(degree);
			
			degree = comparator.graphDistance(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_graph_distance.add(degree);
			
			degree = comparator.vertexRankingComparison(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_vertex_ranking.add(degree);
			
			degree = comparator.vectorSimilarity(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_vector_similarity.add(degree);
		}	
		dft.addColumn("Network 1", nets1.toArray());
		dft.addColumn("Network 2", nets2.toArray());
		dft.addColumn("Simple comparison", results_simple_compare.toArray());
		dft.addColumn("Two steps comparison", results_two_steps.toArray());
		dft.addColumn("Graph distance", results_graph_distance.toArray());
		dft.addColumn("Vertex ranking", results_vertex_ranking.toArray());
		dft.addColumn("Vector similarity", results_vector_similarity.toArray());
		big_networks_table.setModel(dft);
		
		frame = new ApplicationFrame(directed_networks_table, undirected_networks_table, big_networks_table);
		frame.setVisible(true);
		
	}
}
