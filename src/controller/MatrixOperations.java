package controller;

import java.util.ArrayList;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public final class MatrixOperations {
	
	public static double[][] transposeVector(double[] v){
		double[][] t = new double[v.length][1];
		
		for (int i = 0; i < v.length; i++){
			t[i][0] = v[i];
		}
		
		return t;
	}
	
	public static double[][] convertVectorToMatrix(double[] s){
		double[][] s_matrix = new double[1][s.length];
		for (int i = 0; i < s.length; i++){
			s_matrix[0][i] = s[i];
		}
		return s_matrix;
	}
	
	public static double convertMatrixToDouble(double[][] a){
		if (a.length == 1){
			return a[0][0];
		}
		return -9999999;
	}


	
	// First matrix is mxn, second is pxq, result is mxq
	public static double[][] matrixMultiplication(double[][] first, int m, int n, double[][] second, int p, int q){
	    double[][] result = new double[m][q];
	    double sum = 0;
		
	    for (int i = 0 ; i < m ; i++ )
        {
           for (int j = 0 ; j < q ; j++ )
           {   
              for (int k = 0 ; k < p ; k++ )
              {
                 sum = sum + first[i][k]*second[k][j];
              }

              result[i][j] = sum;
              sum = 0;
           }
        }
	    return result;
	}
	
	// Calculates the eigenvectors and eigenvalues for the matrix. Returns the leading eigenvector.
	public static double[] getLeadingEigenvector(double[][] modularity_matrix){
		Matrix matrix = new Matrix(modularity_matrix);
		EigenvalueDecomposition eigen = matrix.eig();
		double[][] eigenvectors = eigen.getV().getArray();
		double[] real_part = eigen.getRealEigenvalues();
		
		int index = getMaxEigenvalueIndex(real_part);
		
		double[] leading = findLeadingEigenvector(index, eigenvectors);
		return leading;
	}
	
	public static double[] findLeadingEigenvector(int index, double[][] eigenvectors){
		double[] leading = new double[eigenvectors.length];
		
		if (index == -1){
			System.out.println("Error: leading eigenvector not found.");
		}
		
		for (int i = 0; i < eigenvectors.length; i++){
			leading[i] = eigenvectors[index][i];
		}
		return leading;
	}
	
	public static int getMaxEigenvalueIndex(double[] real_eigenvalues){
		double max = -50000000;
		int index = -1;
		for (int i = 0; i < real_eigenvalues.length; i++){
			if (real_eigenvalues[i] > max){
				max = real_eigenvalues[i];
				index = i;
			}
		}
		return index;
	}
	
	public static double[] convertArrayListToVector(ArrayList<Integer> a){
		double[] v = new double[a.size()];
		
		for (int i = 0; i < a.size(); i++){
			v[i] = a.get(i);
		}
		return v;
	}
	
	public static boolean containsPositiveValue(double[] eigenvectors){
		for (int i = 0; i < eigenvectors.length; i++){
			if (eigenvectors[i] >= 0){
				return true;
			}
		}
		return false;
	}
	
	public static void printVector(double[] v){
		for (int i = 0; i < v.length; i++){
			System.out.print(v[i] + " ");
		}
	}
}
