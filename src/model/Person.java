package model;


public class Person {
	private String name;
	private Graph directed_graph;
	private Graph undirected_graph;
	
	public Person(String name){
		this.setDirected_graph(new Graph());
		this.setUndirected_graph(new Graph());
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Graph getDirected_graph() {
		return this.directed_graph;
	}


	public void setDirected_graph(Graph directed_graph) {
		this.directed_graph = directed_graph;
	}


	public Graph getUndirected_graph() {
		return this.undirected_graph;
	}


	public void setUndirected_graph(Graph undirected_graph) {
		this.undirected_graph = undirected_graph;
	}
}
