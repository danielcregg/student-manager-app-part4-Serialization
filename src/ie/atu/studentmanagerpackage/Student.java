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
        // Validate student ID directly in the setter
        if (!studentIdIsValid(studentId)) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        this.studentId = studentId;
    }

    public void setFirstName(String firstName) {
        // Validate first name directly in the setter
        if (!firstNameIsValid(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }
        this.firstName = firstName;
    }

    public void setAge(int age) {
        // Validate age directly in the setter
        if (!ageIsValid(age)) {
            throw new IllegalArgumentException("Invalid age");
        }
        this.age = age;
    }

	// Check if student ID is valid
	public static boolean studentIdIsValid(String studentId) {
		if (studentId == null) {
			throw new IllegalArgumentException("Student ID cannot be null");
		}
		if (!studentId.matches("G00\\d{6}")) {
			throw new IllegalArgumentException("Student ID " + studentId + " does not match the format G00123456");
		}
		return true;
	}

	// Check if first name is valid
	public static boolean firstNameIsValid(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("First name cannot be null");
		}
		if (firstName.length() < 2) {
			throw new IllegalArgumentException("First name must be at least 2 characters long");
		}
		if (!firstName.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("First name must contain upper and lower case letters only");
		}
		return true;
	}

	// Check if age is valid
	public static boolean ageIsValid(int age) {
		if (age < 16 || age > 130) {
			throw new IllegalArgumentException("Student must be at least 16 years old and less than 130 years old");
		}
		return true;
	}

	// Check if student details are valid
	public static void validate(String studentId, String firstName, int age) {
		studentIdIsValid(studentId);
		firstNameIsValid(firstName);
		ageIsValid(age);
	}

	// Method to print students name and age
	@Override
	public String toString() {
		return "Student ID: " + this.studentId + ", Name: " + this.firstName + ", Age: " + this.age;
	}

} // End of Student class
