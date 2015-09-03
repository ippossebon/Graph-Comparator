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
		ArrayList<Double> degrees = new ArrayList<Double>();
		
		Comparator comparator = new Comparator();
		
		/* Simple comparison */
		double degree = 0;
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			directions1.add("No");
			graphs2.add(parser.getPeople().get(i+1).getName());
			directions2.add("No");
			degree = comparator.myCompare(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i).getUndirected_graph());
			degrees.add(degree);
		}
		JTable simple_comparison_table = new JTable();
		
		DefaultTableModel dft = new DefaultTableModel();
		dft.addColumn("Graph 1 ID", graphs1.toArray());
		dft.addColumn("Directed", directions1.toArray());
		dft.addColumn("Graph 2 ID", graphs2.toArray());
		dft.addColumn("Directed", directions2.toArray());
		dft.addColumn("Similarity degree", degrees.toArray());
		
		simple_comparison_table.setModel(dft);
		JTable t3 = new JTable();
		JTable t5 = new JTable();
		JTable t6 = new JTable();
		
		/* Two steps comparison */
		degree = 0;
		graphs1.clear();
		graphs2.clear();
		directions1.clear();
		directions2.clear();
		degrees.clear();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			directions1.add("No");
			graphs2.add(parser.getPeople().get(i+1).getName());
			directions2.add("No");
			degree = comparator.twoStepsComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			degrees.add(degree);
		}
		
		JTable two_steps_table = new JTable();
		DefaultTableModel dft2 = new DefaultTableModel();
		dft2.addColumn("Graph 1 ID", graphs1.toArray());
		dft2.addColumn("Directed", directions1.toArray());
		dft2.addColumn("Graph 2 ID", graphs2.toArray());
		dft2.addColumn("Directed", directions2.toArray());
		dft2.addColumn("Similarity degree", degrees.toArray());
		two_steps_table.setModel(dft2);
		
		/* Graph distance comparison */
		degree = 0;
		graphs1.clear();
		graphs2.clear();
		directions1.clear();
		directions2.clear();
		degrees.clear();
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			directions1.add("No");
			graphs2.add(parser.getPeople().get(i+1).getName());
			directions2.add("No");
			degree = comparator.graphDistance(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			degrees.add(degree);
		}
		
		JTable graph_distance_table = new JTable();
		DefaultTableModel dft3 = new DefaultTableModel();
		dft3.addColumn("Graph 1 ID", graphs1.toArray());
		dft3.addColumn("Directed", directions1.toArray());
		dft3.addColumn("Graph 2 ID", graphs2.toArray());
		dft3.addColumn("Directed", directions2.toArray());
		dft3.addColumn("Similarity degree", degrees.toArray());
		graph_distance_table.setModel(dft3);
		
		/* Vertex ranking comparison */
		degrees.clear();
		directions1.clear();
		directions2.clear();
		
		for (int i = 0; i < (int)parser.getGraphs().size()/2; i++){
			directions1.add("Yes");
			directions2.add("Yes");
			
			try{
				degree = comparator.vertexRankingComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i);
			}
			
			degrees.add(degree);
		}
		
		JTable vertex_ranking_table = new JTable();
		DefaultTableModel dft4 = new DefaultTableModel();
		dft4.addColumn("Graph 1 ID", graphs1.toArray());
		dft4.addColumn("Directed", directions1.toArray());
		dft4.addColumn("Graph 2 ID", graphs2.toArray());
		dft4.addColumn("Directed", directions2.toArray());
		dft4.addColumn("Similarity degree", degrees.toArray());
		vertex_ranking_table.setModel(dft4);
		
		//parser.getPeople().get(0).getUndirected_graph().testDFS();
		
		frame = new ApplicationFrame(simple_comparison_table, two_steps_table, graph_distance_table, vertex_ranking_table, t5, t6);
		frame.setVisible(true);
	}
}
