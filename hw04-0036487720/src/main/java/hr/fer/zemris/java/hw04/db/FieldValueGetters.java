package hr.fer.zemris.java.hw04.db;

/**
 * Class <code>FieldValueGettrs</code> gives us static methods for fetching
 * instances of the interface {@linkplain IFieldValueGetter}, which represent
 * one field of instance of the {@linkplain StudentRecord}.
 * 
 * @author Matteo Miloš
 *
 */
public class FieldValueGetters {

	/**
	 * Field getter that returns first name of the student record.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();
	/**
	 * Field getter that returns last name of the student record.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();
	/**
	 * Field getter that returns jmbag of the student record.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();

}
