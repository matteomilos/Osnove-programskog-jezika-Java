package hr.fer.zemris.java.hw04.db;

/**
 * Interface represents strategy which is used for fetching one of the fields of
 * the one instance of {@linkplain StudentRecord}.
 * 
 * @author Matteo Miloš
 *
 */
public interface IFieldValueGetter {

	/**
	 * Method used for fetching one of the fields. Each field is defined in
	 * implementation of the class {@linkplain FieldValueGetters}.
	 * 
	 * @param record
	 *            student record
	 * @return one field of the student record
	 */
	public String get(StudentRecord record);
}
