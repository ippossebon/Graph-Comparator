package clustering;

import java.util.ArrayList;

import model.Group;
import model.IntegerHolder;
import utilities.GroupOperations;
import utilities.MatrixOperations;

public final class ClusteringController {
	private static double[][] modularity_matrix;
	public static double[][] similarity_matrix;
	private static int[][] correlation_matrix;
	public static double[] s;
	public static double r;
	public static Group root;
	public static int network_number_edges;
	public static double[] leading_eigenvector;
	public static ArrayList<Integer> indices;
	public static int numb_items;
	
	public ClusteringController(double[][] smatrix, double r_value, int n_items){
		similarity_matrix = smatrix;
		r = r_value;
		numb_items = n_items;
		setModularity_matrix(new double[numb_items][numb_items]);
		setCorrelation_matrix(new int[numb_items][numb_items]);
		ClusteringController.s = new double[numb_items];
		ClusteringController.indices = new ArrayList<Integer>();
		createIndicesArray();
	}	
	
	private void createIndicesArray(){
		for (int i = 0; i < numb_items; i++){
			indices.add(i);
		}
	}
	
	private void test(){
		int[][] m = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0},
				{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0},
				{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
				{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
				{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
				{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0}};
		setCorrelation_matrix(m);
	}
	
	public void run(){
		createCorrelationMatrix();
		//test();
		//network_number_edges = 32;
		
		createModularityMatrix();
		leading_eigenvector = MatrixOperations.getLeadingEigenvector(getModularity_matrix());
		root = new Group(getCorrelation_matrix(), getModularity_matrix(), null, s, network_number_edges);
		
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		ArrayList<Integer> indices_group1 = new ArrayList<Integer>();
		ArrayList<Integer> indices_group2 = new ArrayList<Integer>();
		
		GroupOperations.divideInTwo(s, leading_eigenvector, indices, s1, s2, indices_group1, indices_group2);
		
		IntegerHolder numb_edges1 = new IntegerHolder(0);
		IntegerHolder numb_edges2 = new IntegerHolder(0);
		
		int[][] correlation_matrix_group1 = GroupOperations.calculateGroupCorrelationMatrix(indices_group1, numb_edges1, similarity_matrix, r);
		int[][] correlation_matrix_group2 = GroupOperations.calculateGroupCorrelationMatrix(indices_group2, numb_edges2, similarity_matrix, r);
		
		double deltaQ1 = GroupOperations.calculateModularityContribution(indices_group1, getCorrelation_matrix(), numb_edges1.getValue(), getModularity_matrix());
		double deltaQ2 = GroupOperations.calculateModularityContribution(indices_group2, getCorrelation_matrix(), numb_edges2.getValue(), getModularity_matrix());
		
		double[][] mm1 = GroupOperations.calculateGeneralizedModularityMatrix(indices_group1, correlation_matrix_group1, getModularity_matrix());
		double[][] mm2 = GroupOperations.calculateGeneralizedModularityMatrix(indices_group2, correlation_matrix_group2, getModularity_matrix());
		double[] leading_eigenvector1 = MatrixOperations.getLeadingEigenvector(mm1);
		double[] leading_eigenvector2 = MatrixOperations.getLeadingEigenvector(mm2);
		
		boolean done = true;
		if (deltaQ1 > 0 && MatrixOperations.containsPositiveValue(leading_eigenvector1)){	
			double[] sv = MatrixOperations.convertArrayListToVector(s1);
			Group g = new Group(correlation_matrix_group1, mm1, indices_group1, sv, numb_edges1.getValue());	
			root.addChild(g);
			updateS(indices_group1);
			g.applyClusteringAlgorithm();
			done = false;
		}
		if (deltaQ2 > 0 && MatrixOperations.containsPositiveValue(leading_eigenvector2)){
			double[] sv = MatrixOperations.convertArrayListToVector(s2);
			Group g = new Group(correlation_matrix_group2, mm2, indices_group2, sv, numb_edges2.getValue());	
			root.addChild(g);
			updateS(indices_group2);
			g.applyClusteringAlgorithm();
			done = false;
		}
		if (done){
			MatrixOperations.printVector(s);
		}
	}
	
	/* - Two graphs are considered to be similar if their similarity degree is > r. 
	 * - Select a correlation as being an edge if the correlation value is > r. */
	private void createCorrelationMatrix(){	
		for (int i = 0; i < getCorrelation_matrix().length; i++){
			for (int j = 0; j < getCorrelation_matrix().length; j++){
				if (similarity_matrix[i][j] >= r){
					getCorrelation_matrix()[i][j] = 1;
					network_number_edges++; 
				}
				else{
					getCorrelation_matrix()[i][j] = 0;
				}
			}
		}
	}
	
	private void createModularityMatrix(){
		try {
			for (int i = 0; i < getModularity_matrix().length; i++){
				for (int j = 0; j < getModularity_matrix().length; j++){
					getModularity_matrix()[i][j] = getCorrelation_matrix()[i][j] - ((double)(calculateDegree(i) * calculateDegree(j))/network_number_edges);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Returns the degree of a given node. That is, the number of graphs to each this one is considered to be similar.*/
	private int calculateDegree(int index){
		int count = 0;
		for (int i = 0; i < getCorrelation_matrix().length; i++){
			if (getCorrelation_matrix()[index][i] >= r){
				count++;
			}
		}
		return count;
	}
	
	public static void updateS(ArrayList<Integer> indices){
		for (Integer i : indices){
			s[i] = (double) s[i] /2;
		}
	}

	public static double[][] getModularity_matrix() {
		return modularity_matrix;
	}

	public static void setModularity_matrix(double[][] modularity_matrix) {
		ClusteringController.modularity_matrix = modularity_matrix;
	}

	public static int[][] getCorrelation_matrix() {
		return correlation_matrix;
	}

	public static void setCorrelation_matrix(int[][] correlation_matrix) {
		ClusteringController.correlation_matrix = correlation_matrix;
	}
}
