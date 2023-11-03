package ie.atu.studentmanagerpackage;

import java.io.Serializable;

public class Student implements Serializable {

	// serialVersionUID is used for version control of a Serializable class to
	// verify that the sender and receiver of a serialized object have loaded.
	// If the receiver has loaded a class for the object that has a different
	// serialVersionUID than that of the corresponding sender's class, then
	// deserialization will result in an InvalidClassException.
	public static final long serialVersionUID = 1L;

	// Instance Variables
	private String studentId;
	private String firstName;
	private int age;

	// Constructor
	public Student(String studentId, String name, int age) {
		this.studentId = studentId;
		this.firstName = name;
		this.age = age;
	}

	// Getters and Setters
	public String getStudentId() {
		return studentId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public int getAge() {
		return this.age;
	}

	public void setStudentId(String studentId) {
		// Check if student ID is not valid	and throw exception if it is.
		if (!studentIdIsValid(studentId)) {
			throw new IllegalArgumentException("Invalid student ID");
		}
		this.studentId = studentId;
	}

	public void setFirstName(String firstName) {
		// Check if student ID is not valid	and throw exception if it is.
		if (!firstNameIsValid(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}
	
	public void setAge(int age) {
		// Check if student ID is not valid	and throw exception if it is.
		if (!ageIsValid(age)) {
			throw new IllegalArgumentException("Invalid age");
		}
		this.age = age;
	}

	// Check if student ID is valid
	public static boolean studentIdIsValid(String studentId) {
		// Check if student ID is valid
		if (studentId == null) {
			System.err.println("Student ID can not be null");
			return false;
		} else if (!(studentId.matches("G00\\d{6}"))) {
			System.err.println("Student ID " + studentId + " does not match the format G00123456");
			return false;
		} else {
			return true;
		}
	}

	// Check if student ID is valid
	public static boolean firstNameIsValid(String firstName) {
		if (firstName == null) {
			System.err.println("First name can not be null");
			return false;
		} else if (firstName.length() < 2) {
			System.err.println("First name must be at least 2 characters long");
			return false;
		} else if (!firstName.matches("[a-zA-Z]+")) {
			System.err.println("First name must conatin Upper and lover case letters only");
			return false;
		} else {
			return true;
		}
	}

	// Check if student ID is valid
	public static boolean ageIsValid(int age) {
		if (age < 16 || age > 130) {
			System.err.println("Student must be at least 16 years old and less than 130 years old");
			return false;
		} else {
			return true;
		}
	}

	// Check if student details are valid
	public static boolean isValid(String studentId, String firstName, int age) {
		return studentIdIsValid(studentId) && firstNameIsValid(firstName) && ageIsValid(age);
	}

	// Method to print students name and age
	@Override
	public String toString() {
		return this.studentId + "," + this.firstName + "," + this.age;
	}

} // End of Student class
