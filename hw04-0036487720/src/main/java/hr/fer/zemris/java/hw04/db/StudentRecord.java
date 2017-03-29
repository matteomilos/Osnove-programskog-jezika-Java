package hr.fer.zemris.java.hw04.db;

/**
 * Class <code>StudentRecord</code> represents record of one student.
 * 
 * @author Matteo Miloš
 *
 */
public class StudentRecord {

	/**
	 * Students jmbag
	 */
	private String jmbag;
	/**
	 * Students last name
	 */
	private String lastName;
	/**
	 * Students first name
	 */
	private String firstName;
	/**
	 * Students final grade
	 */
	private int finalGrade;

	/**
	 * Public constructor that gets students JMBAG, last name, first name and
	 * final grade.
	 * 
	 * @param jmbag
	 *            Students jmbag
	 * @param lastName
	 *            Students last name
	 * @param firstName
	 *            Students first name
	 * @param finalGrade
	 *            Students final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for students JMBAG
	 * 
	 * @return students JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for students last name
	 * 
	 * @return last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for students first name
	 * 
	 * @return first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for students final grade
	 * 
	 * @return students final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return lastName + " " + firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

}
