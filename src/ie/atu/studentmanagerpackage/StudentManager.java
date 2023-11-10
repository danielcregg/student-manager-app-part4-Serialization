package ie.atu.studentmanagerpackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManager implements Serializable {

	/*
	 * serialVersionUID is used to ensure that the same class is being used when
	 * deserializing an object
	 */
	public static final long serialVersionUID = 2L;

	// Instance Variables
	private List<Student> studentList;

	// Constructor
	public StudentManager() {
		this.studentList = new ArrayList<>();
	}

	// Constructor (Paramiterised) - takes student list as input
	public StudentManager(List<Student> studentList) {
		this.studentList = studentList;
	}

	// Getter
	public List<Student> getStudentList() {
		return this.studentList;
	}

	public void setStudentList(List<Student> studentList) {
		if (studentList == null || studentList.isEmpty()) {
			throw new IllegalArgumentException("Student list cannot be null or empty");
		}
		this.studentList = studentList;
	}

	// Add student to list
	public boolean addStudentToList(String studentId, String name, int age) {
		try {
			// Check student details are valid
			Student.validate(studentId, name, age);
			// Check if student is already on student list
			if (!studentList.contains(findStudentObjectByID(studentId))) {
				// Create student object with valid details and add student to the list
				Student newStudent = new Student(studentId, name, age);
				return this.studentList.add(newStudent);
			}
		} catch (IllegalArgumentException e) {
			// If student details are invalid or if student is already on list print an error message
			System.out.println("Student with ID " + studentId + " could not be added to list: " + e.getMessage());
		}
		return false;
	}

	// Remove student from list given studendID
	public boolean removeStudentFromList(String studentId) {
		return studentList.remove(findStudentObjectByID(studentId));
	}

	// Find student object by ID. Returns null if student is not found.
	public Student findStudentObjectByID(String studentId) {
		// Search all student objects in the student list
		for (Student studentObject : studentList) {
			// Compare IDs to ID passed in
			if (studentId.equals(studentObject.getStudentId())) {
				// If a match is found return the student object
				System.out.println("Student object with ID = " + studentId + " was found on list!");
				return studentObject;
			}
		}
		// If no match is found return null
		System.out.println("Student object with ID = " + studentId + " was NOT found on list!");
		return null;
	}

	public void findStudentsByName(String firstName) {
		System.out.println("Here are all students with first name " + firstName + ":");
		// Search all student objects in the student list
		for (Student studentObject : studentList) {
			// Compare IDs to ID passed in
			if (firstName.equals(studentObject.getFirstName())) {
				// If a match is found print the student details to console
				System.out.println(studentObject.toString());
			}
		}
	}

	public void findStudentsByAge(int age) {
		System.out.println("Here are all students of age " + age + ":");
		// Search all student objects in the student list
		for (Student studentObject : studentList) {
			// Compare IDs to ID passed in
			if (age == studentObject.getAge()) {
				// If a match is found print the student details to console
				System.out.println(studentObject.toString());
			}
		}
	}

	public List<Student> getStudentsByAge(int age) {
		// Use Java 8 Streams to filter the list of students by age
		return studentList.stream()
			.filter(student -> student.getAge() == age)
			.collect(Collectors.toList());
	}

	public void findStudentsByFirstNameAndAge(String firstName, int age) {
		System.out.println("Here are all students with first name " + firstName + " and age " + age + ":");
		// Search all student objects in the student list
		for (Student studentObject : studentList) {
			// Compare IDs to ID passed in
			if (studentObject.getFirstName().equals(firstName) && studentObject.getAge() == age) {
				// If a match is found print the student details to console
				System.out.println(studentObject.toString());
			}
		}
	}

	public Student findStudentsByAgeRange(int minAge, int maxAge) {
		if (minAge > maxAge) {
			System.err.println("Invalid input: Minimum age can not be greater than maximum age!");
			return null;
		} else {
			System.out.println("Here are all students between the ages of " + minAge + " and " + maxAge + ":");
			// Search all student objects in the student list
			for (Student studentObject : studentList) {
				// Compare IDs to ID passed in
				if (studentObject.getAge() >= minAge && studentObject.getAge() <= maxAge) {
					// If a match is found print the student details to console
					System.out.println(studentObject.toString());
				}
			}
			// If no match is found return null
			return null;
		}
	}

	// Update student name
	public boolean updateStudentName(String studentId, String newName) {
		// Validate the student ID and new name using the static methods in the Student
		// class
		if (!Student.studentIdIsValid(studentId) || !Student.firstNameIsValid(newName)) {
			// If either the student ID or the new name is invalid, throw an
			// IllegalArgumentException
			throw new IllegalArgumentException("Invalid student ID or name");
		}

		// If both the student ID and the new name are valid, find the student with the
		// given ID
		Student studentToUpdate = findStudentObjectByID(studentId);

		// If a student with the given ID is not found...
		if (studentToUpdate == null) {
			// Print a message and return false
			System.out.println("Student not found");
			return false;
		}

		// Save the current name of the student
		String currentName = studentToUpdate.getFirstName();

		// If the current name is the same as the new name...
		if (currentName.equals(newName)) {
			// Print a message and return false
			System.out.println("Student name is already " + newName + "! No update made to name.");
			return false;
		}

		// If the current name is different from the new name, update the name
		studentToUpdate.setFirstName(newName);
		// Print a message indicating the name has been updated
		System.out.println("Student name changed from " + currentName + " to " + newName + "!");
		// Return true indicating the name was successfully updated
		return true;
	}

	// Show total number of Students in List
	public void printTotalNumberOfStudents() {
		System.out.println(this.studentList.size());
	}

	// Print student list to console
	public void printStudentList() {
		System.out.println("ID,NAME,AGE");
		System.out.println("========================");
		for (Student studentObject : studentList) {
			System.out.println(studentObject.toString());
		}
		System.out.println("========================");
	}

	// Write student details to file
	public void writeStudentDataToCSVFile(String pathToStudentCSVFile) {
		File studentCSVFile = null;
		FileWriter studentFileWriterStream = null;
		BufferedWriter bufferedstudentFileWriterStream = null;
		try {
			// Create a buffered file writer which can write one line at a time
			studentCSVFile = new File(pathToStudentCSVFile);
			studentFileWriterStream = new FileWriter(studentCSVFile);
			bufferedstudentFileWriterStream = new BufferedWriter(studentFileWriterStream);

			// Write column headers to CSV file
			bufferedstudentFileWriterStream.write("ID,Firstname,Age" + "\n");

			// Write out student data from studentList to buffer and flush it to CSV file
			for (Student studentObject : studentList) {
				bufferedstudentFileWriterStream.write(studentObject.getStudentId() + "," + studentObject.getFirstName()
						+ "," + studentObject.getAge() + "\n");
				bufferedstudentFileWriterStream.flush(); // Flush buffer and transfer buffer data to the file
			}
			System.out.println("Student data written to CSV file located at " + pathToStudentCSVFile);
		} catch (NullPointerException npExc) {
			System.err.println("ERROR: Students NOT saved to file!");
			npExc.printStackTrace();
		} catch (FileNotFoundException fnfExc) {
			System.err.println("ERROR: Students NOT saved to file!");
			fnfExc.printStackTrace();
		} catch (IOException IOExc) {
			System.err.println("ERROR: Students NOT saved to file!");
			IOExc.printStackTrace();
		} finally {
			try {
				// Close buffer
				bufferedstudentFileWriterStream.close();
				// Close file writer
				studentFileWriterStream.close();
			} catch (NullPointerException npExc) {
				System.out.println("ERROR: Could not close the ObjectOutputStream or FileOutputStream!");
				npExc.printStackTrace();
			} catch (IOException ioExc) {
				ioExc.printStackTrace();
			} // End catchEnd catch
		} // End finally
	} // End Save method

	public void readStudentDataFromCSVFile(String pathToStudentCSVFile) {
		// Use try-with-resources to void the need to close the streams in a finally
		try (BufferedReader bufferedStudentCSVFileReader = new BufferedReader(new FileReader(pathToStudentCSVFile))) {
			// Read first line of file and discard it. It contains column headers.
			bufferedStudentCSVFileReader.readLine();
			String bufferData; // Variable to store each line of data read from file
			// Read each line of data from file and add it to the studentList
			while ((bufferData = bufferedStudentCSVFileReader.readLine()) != null) {
				String[] studentFieldValues = bufferData.split(",");
				String studentId = studentFieldValues[0];
				String firstName = studentFieldValues[1];
				int age = Integer.parseInt(studentFieldValues[2]);
				this.addStudentToList(studentId, firstName, age); // Add student to the studentList
			}
			System.out.println("Student data read from CSV file located at " + pathToStudentCSVFile);
		} catch (IOException e) {
			System.err.println(
					"ERROR: An error occurred while reading the student data from the file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Method to serialize the Student Manager Object
	public void writeStudentManagerObjectToFile(String pathToFile) {
		File studentManagerObjectFile = new File(pathToFile);
		FileOutputStream fileOutputStreamToStudentManagerObjectFile = null;
		ObjectOutputStream objectOutputStreamToStudentManagerObjectFile = null;

		try {
			// Create file output stream from file object
			fileOutputStreamToStudentManagerObjectFile = new FileOutputStream(studentManagerObjectFile);
			// Create object output stream from file output stream
			objectOutputStreamToStudentManagerObjectFile = new ObjectOutputStream(
					fileOutputStreamToStudentManagerObjectFile);
			// Write object to file
			objectOutputStreamToStudentManagerObjectFile.writeObject(this);
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (SecurityException secExc) {
			secExc.printStackTrace();
		} catch (InvalidClassException icExc) {
			icExc.printStackTrace();
		} catch (NotSerializableException nsExc) {
			nsExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} finally {
			try {
				// Close ObjectOutputStream
				objectOutputStreamToStudentManagerObjectFile.close();
				// Close FileOutputStream
				fileOutputStreamToStudentManagerObjectFile.close();
			} catch (NullPointerException npExc) {
				System.out.println("ERROR: Could not close the ObjectOutputStream or FileOutputStream!");
				npExc.printStackTrace();
			} catch (IOException ioExc) {
				ioExc.printStackTrace();
			} // End catch
		} // End finally
	}

	// Method to de-serialize the Student Manager Object
	public StudentManager readStudentManagerObjectFromFile(String pathToFile) {
		StudentManager studentManagerObject = null;

		// Use try-with-resources to void the need to close the streams in a finally
		// block.
		try (FileInputStream fileInputStreamFromStudentManagerObjectFile = new FileInputStream(pathToFile);
				ObjectInputStream objectInputStreamfromStudentManagerObjectFile = new ObjectInputStream(
						fileInputStreamFromStudentManagerObjectFile)) {
			// Read in object from file
			studentManagerObject = (StudentManager) objectInputStreamfromStudentManagerObjectFile.readObject();
		} catch (Exception e) {
			System.out.println("ERROR: An error occurred while reading the StudentManager object from file!");
			e.printStackTrace();
		}
		// Returns null if no object is read in or an exception occurs.
		return studentManagerObject;
	}

} // End Class
