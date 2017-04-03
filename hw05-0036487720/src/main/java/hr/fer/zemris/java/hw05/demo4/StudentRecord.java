package hr.fer.zemris.java.hw05.demo4;

/**
 * Class <code>StudentRecord</code> is used as for storing all of the
 * information about one student.
 * 
 * @author Matteo Miloš
 *
 */
public class StudentRecord {

	/**
	 * Student's jmbag.
	 */
	private String jmbag;
	/**
	 * Student's last name,
	 */
	private String lastName;

	/**
	 * Student's first name.
	 */
	private String firstName;

	/**
	 * Student's score on midterm exam.
	 */
	private double numOfPointsMI;

	/**
	 * Student's score on final exam.
	 */
	private double numOfPointsZI;

	/**
	 * Student's score on laboratory assignments
	 */
	private double numOfPointsLabAssignments;
	/**
	 * Student's final grade
	 */
	private int finalGrade;

	/**
	 * Public constructor that receives all information about the student in
	 * form of an {@linkplain String} array. Informations are properly assigned
	 * to instance variables of this class.
	 * 
	 * @param splitted
	 *            String array that contains information about the student.
	 */
	public StudentRecord(String[] splitted) {
		this.jmbag = splitted[0];
		this.lastName = splitted[1];
		this.firstName = splitted[2];
		this.numOfPointsMI = Double.parseDouble(splitted[3]);
		this.numOfPointsZI = Double.parseDouble(splitted[4]);
		this.numOfPointsLabAssignments = Double.parseDouble(splitted[5]);
		this.finalGrade = Integer.parseInt(splitted[6]);
	}

	/**
	 * Public getter for student's jmbag
	 * 
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Public getter for student's last name
	 * 
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Public getter for student's first name
	 * 
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Public getter for student's number of points on midterm exam.
	 * 
	 * @return student's number of points on midterm exam
	 */
	public double getNumOfPointsMI() {
		return numOfPointsMI;
	}

	/**
	 * Public getter for student's number of points on final exam.
	 * 
	 * @return student's number of points on final exam
	 */
	public double getNumOfPointsZI() {
		return numOfPointsZI;
	}

	/**
	 * Public getter for student's number of points on laboratory assignments.
	 * 
	 * @return student's number of points on laboratory assignments
	 */
	public double getNumOfPointsLabAssignments() {
		return numOfPointsLabAssignments;
	}

	/**
	 * Public getter for student's final grade
	 * 
	 * @return student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Method that calculates sum of student's points from midterm, final exam
	 * and laboratory assignments
	 * 
	 * @return sum of student's points
	 */
	protected double getSumOfAllPoints() {
		return numOfPointsLabAssignments + numOfPointsMI + numOfPointsZI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%-10s %8s %-15s %-10.2f %d", jmbag, firstName, lastName, getSumOfAllPoints(), finalGrade);
	}

}
