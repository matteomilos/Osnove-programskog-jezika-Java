package hr.fer.zemris.java.custom.collections;

public class ObjectStack {

	private ArrayIndexedCollection adaptee = new ArrayIndexedCollection();
	
	
	private boolean isEmpty(){
		return adaptee.isEmpty();
	}
	
	public int size(){
		return adaptee.size();
	}
	
	public void push(Object value){
		
		if(value==null){
			throw new IllegalArgumentException();
		}
		
		adaptee.add(value);
	}
	
	public Object pop(){
		Object popped = peek();
		adaptee.remove(adaptee.size()-1);
		return popped;
	}
	
	public Object peek(){
		
		if(adaptee.isEmpty()){
			throw new EmptyStackException();
		}
		
		return adaptee.get(adaptee.size()-1);
	}
	
	void clear(){
		adaptee.clear();
	}
	
	
	
	
	
}
