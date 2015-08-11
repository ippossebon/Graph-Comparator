package model;

import java.util.ArrayList;

public class Node {

	private Node parent;
	private ArrayList<Node> children;
	private String key;
	private int index;
	private int parent_index;
	
	public Node(int parent_index, int index){
		this.setParent_index(parent_index);
		this.setChildren(new ArrayList<Node>());
		this.setIndex(index);
	}
	
	public Node(int index){
		this.setParent(null);
		this.setChildren(new ArrayList<Node>());
		this.setIndex(index);
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getParent_index() {
		return parent_index;
	}

	public void setParent_index(int parent_index) {
		this.parent_index = parent_index;
	}
}
