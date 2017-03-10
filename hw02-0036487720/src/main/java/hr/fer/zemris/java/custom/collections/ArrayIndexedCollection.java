package hr.fer.zemris.java.custom.collections;

public class ArrayIndexedCollection extends Collection {

	private int size;
	private int capacity;
	private Object[] elements;

	private static final int DEFAULT_CAPACITY = 16;

	
	
	public static void main(String[] args) {
		ArrayIndexedCollection kolekcija = new ArrayIndexedCollection(5);
		System.out.println(kolekcija.capacity +" " + kolekcija.size);
		kolekcija.add(15);
		kolekcija.add(12);

		kolekcija.add(19);
		kolekcija.add(18);
		kolekcija.add(17);
		kolekcija.add(155);
		kolekcija.add(12);
		System.out.println(kolekcija.capacity +" " + kolekcija.size);
		kolekcija.insert(73, 2);

		System.out.println(kolekcija.capacity +" " + kolekcija.size);
		kolekcija.insert(73, 0);
		kolekcija.insert(73, 1);
		System.out.println(kolekcija.capacity +" " + kolekcija.size);
		for (Object broj : kolekcija.elements) {
			System.out.println(broj);
		}

		kolekcija.remove(3);
		for (Object broj : kolekcija.elements) {
			System.out.println(broj);
		}
	}
	
	
	
	protected ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	protected ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
	}

	protected ArrayIndexedCollection(Collection collection) {		
		
		this();
		this.addAll(collection);
		
	}
	
	protected ArrayIndexedCollection(Collection collection, int initialCapacity){
		this(initialCapacity);
		this.addAll(collection);
	}
	
	@Override
	public void add(Object value){
		if(value==null){
			throw new IllegalArgumentException();
		}
		if(size==capacity){
			Object[] help = elements;
			elements = new Object[capacity*2];
			capacity=capacity*2;
			System.arraycopy(help, 0, elements, 0, help.length);
		}
		elements[size++]=value;
	}

	public Object get(int index){
		if(index<0||index>size-1){
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	@Override
	public void clear(){
		for (int i = 0; i < size; i++) {
			elements[i]=null;
		}
		size=0;
	}
	
	public void insert(Object value, int position){
		if(value==null){
			throw new IllegalArgumentException();
		}
		if(position<0 || position>size){
			throw new IndexOutOfBoundsException();
		}
		if(size==capacity){
			Object[] help = elements;
			elements = new Object[capacity*2];
			capacity=capacity*2;
			System.arraycopy(help, 0, elements, 0, help.length);
		}
		for(int i = size; i>position; i--){
			elements[i]=elements[i-1];
		}
		elements[position]=value;
		size++;
	}
	
	public int indexOf(Object value){
		for(int i=0; i<size; i++){
			if(elements[i]==value) return i;
		}
		return -1;
	}
	
	public void remove(int index){
		if(index<0||index>size-1){
			throw new IndexOutOfBoundsException();
		}
		for(int i=index; i<size-1; i++){
			elements[i]=elements[i+1];
		}
		elements[--size]=null;
	}

	@Override
	public boolean remove(Object value){
		if(this.contains(value)){
			remove(indexOf(value));
			return true;
		}
		return false;
	}

	@Override	
	public int size(){
		return size;
	}
	
	@Override
	public boolean contains(Object value){
		if(indexOf(value)<0) return false;
		return true;
	}
	
	@Override	
	public Object[] toArray(){
		Object[] array = new Object[this.size];
		for(int i=0; i<size; i++){
			array[i] = this.elements[i];
		}
		return array;
	}
	
	
	@Override
	public void forEach(Processor processor){
		for(int i=0; i<this.size; i++){
			processor.process(this.elements[i]);
		}
	}
}
