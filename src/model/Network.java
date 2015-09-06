package model;

public class Network extends Graph{

	private String id;
	
	public Network(String id){
		this.setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
