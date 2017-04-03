package hr.fer.zemris.java.hw05.demo4;

public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private double numOfPointsMI;
	private double numOfPointsZI;
	private double numOfPointsLabAssignments;
	private int finalGrade;

	public StudentRecord(String[] splitted) {
		this.jmbag = splitted[0];
		this.lastName = splitted[1];
		this.firstName = splitted[2];
		this.numOfPointsMI = Double.parseDouble(splitted[3]);
		this.numOfPointsZI = Double.parseDouble(splitted[4]);
		this.numOfPointsLabAssignments = Double.parseDouble(splitted[5]);
		this.finalGrade = Integer.parseInt(splitted[6]);
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public double getNumOfPointsMI() {
		return numOfPointsMI;
	}

	public double getNumOfPointsZI() {
		return numOfPointsZI;
	}

	public double getNumOfPointsLabAssignments() {
		return numOfPointsLabAssignments;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	public double getSumOfAllPoints() {
		return numOfPointsLabAssignments + numOfPointsMI + numOfPointsZI;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%-10s %8s %-15s %-10.2f %d", jmbag, firstName, lastName, getSumOfAllPoints(),
				finalGrade);
	}

}
