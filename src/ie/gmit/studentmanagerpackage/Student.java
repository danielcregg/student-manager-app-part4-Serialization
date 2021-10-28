package ie.gmit.studentmanagerpackage;

public class Student {

	// Instance Variables
	private String studentId;
	private String fisrtName;
	private String surname;
	private int yearOfStudy;

	// Constructor1
	public Student(String studentId) {
		this.studentId = studentId;
	}
	
	// Constructor2
	public Student(String studentId, String fisrtName, String surname) {
		this.studentId = studentId;
		this.fisrtName = fisrtName;
		this.surname = surname;
	}

	// Constructor3
	public Student(String studentId, String fisrtName, String surname, int yearOfStudy) {
		this.studentId = studentId;
		this.fisrtName = fisrtName;
		this.surname = surname;
		this.yearOfStudy = yearOfStudy;
	}

	// Getters and Setters
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getFisrtName() {
		return fisrtName;
	}

	public void setFisrtName(String fisrtName) {
		this.fisrtName = fisrtName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

}
