package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTable table_simple_comparison;
	private static JTable table_2steps_comparison;
	private static JTable table_graph_distance;
	private static JTable table_vertex_ranking;
	private static JTable table_vector_similarity;
	private static JTable table_levenshtein;
	private JPanel contentPane;

	public ApplicationFrame(JTable table_simple, JTable table_2, JTable table_distance, JTable table_ranking, JTable table_vector, JTable table_lev) {
		setBounds(100, 100, 847, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMyAlgorithm = new JLabel("Simple comparison:");
		lblMyAlgorithm.setBounds(5, 21, 187, 16);
		contentPane.add(lblMyAlgorithm);
		table_simple_comparison = table_simple;
		JScrollPane scroll_simple_comparison = new JScrollPane(table_simple_comparison);
		scroll_simple_comparison.setBounds(5, 49, 400, 100);
		scroll_simple_comparison.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_simple_comparison);
		
		JLabel lblNewLabel = new JLabel("Two steps comparison:");
		lblNewLabel.setBounds(5, 161, 163, 16);
		contentPane.add(lblNewLabel);
		table_2steps_comparison = table_2;
		JScrollPane scroll_2steps_comparison = new JScrollPane(table_2steps_comparison);
		scroll_2steps_comparison.setBounds(5, 180, 400, 100);
		scroll_2steps_comparison.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_2steps_comparison);
		
		JLabel lblGraphDistance = new JLabel("Graph distance:");
		lblGraphDistance.setBounds(5, 285, 152, 16);
		contentPane.add(lblGraphDistance);
		table_graph_distance = table_distance;
		JScrollPane scroll_graph_distance = new JScrollPane(table_graph_distance);
		scroll_graph_distance.setBounds(5, 302, 400, 100);
		scroll_graph_distance.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_graph_distance);
		
		JLabel lblVertexRanking = new JLabel("Vertex ranking:");
		lblVertexRanking.setBounds(436, 21, 114, 16);
		contentPane.add(lblVertexRanking);
		table_vertex_ranking = table_ranking;
		JScrollPane scroll_vertex_ranking = new JScrollPane(table_vertex_ranking);
		scroll_vertex_ranking.setBounds(436, 49, 400, 100);
		scroll_vertex_ranking.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_vertex_ranking);
		
		JLabel lblVertexedgeVectorSimilarity = new JLabel("Vertex/edge vector similarity:");
		lblVertexedgeVectorSimilarity.setBounds(436, 161, 217, 16);
		contentPane.add(lblVertexedgeVectorSimilarity);
		table_vector_similarity = table_vector;
		JScrollPane scroll_vector_similarity = new JScrollPane(table_vector_similarity);
		scroll_vector_similarity.setBounds(436, 180, 400, 100);
		scroll_vector_similarity.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_vector_similarity);
		
		JLabel lblLevenshteinDistance = new JLabel("Levenshtein distance:");
		lblLevenshteinDistance.setBounds(436, 285, 187, 16);
		contentPane.add(lblLevenshteinDistance);
		table_levenshtein = table_lev;
		JScrollPane scroll_levenshtein = new JScrollPane(table_levenshtein);
		scroll_levenshtein.setBounds(436, 302, 400, 100);
		scroll_levenshtein.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_levenshtein);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	}
	
	public JTable getTableSimpleComparison(){
		return table_simple_comparison;
	}
	
	public void setTableSimpleComparison(JTable t){
		table_simple_comparison = t;
	}
	
	public JTable getTable2StepsComparison(){
		return table_2steps_comparison;
	}
	
	public JTable getTableGraphDistance(){
		return table_graph_distance;
	}
	
	public JTable getTableVertexRanking(){
		return table_vertex_ranking;
	}
	
	
	public JTable getTableVectorSimilarity(){
		return table_vector_similarity;
	}
	
	public JTable getTableLevenshtein(){
		return table_levenshtein;
	}
	
}
