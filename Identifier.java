package assignment2;

public class Identifier implements IdentifierInterface{
	
	String value;
	
	public Identifier(String s) {
		this.value = s;
	}
	
	public String getValue(){ return this.value;}
	
	
	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
	
	public boolean equals(Object o) {
		return this.value.equals(((Identifier) o).getValue());
	}
}
