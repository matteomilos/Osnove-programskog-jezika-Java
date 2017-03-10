package hr.fer.zemris.java.custom.collections;

public class Collection {

	protected Collection() {
		super();
	}

	public boolean isEmpty() {
		if (this.size() == 0)
			return true;
		return false;
	}

	public int size() {
		return 0;
	}

	public void add(Object value) {

	}

	public boolean contains(Object value) {
		return false;
	}

	public boolean remove(Object value) {
		return false;
	}

	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	public void forEach(Processor processor) {

	}

	public void addAll(Collection other) {

		Processor localProcessor = new Processor() {
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		};
		other.forEach(localProcessor);
	}

	public void clear() {

	}

}
