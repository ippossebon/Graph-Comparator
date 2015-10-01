package model;

public class IntegerHolder {
	/* Class created in order to allow passing int by reference. */
	private int value;
	
	public IntegerHolder(int i){
		this.setValue(i);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public void increment(){
		this.value++;
	}
}
