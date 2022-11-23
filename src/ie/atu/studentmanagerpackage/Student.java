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
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

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

	// Create second constructor which takes arraylist as input
	public StudentManager(List<Student> studentList) {
		this.studentList = studentList;
	}

	// Getter
	public List<Student> getStudentList() {
		return this.studentList;
	}

	// Setter
	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	// Add student to list
	public boolean addStudentToList(String studentId, String name, int age) {
		// Check student details are vaild and if student is NOT already on list
		if (Student.isValid(studentId, name, age) && !isOnList(studentId)) {
			// Create student object with valid details and add student to the list
			Student newStudent = new Student(studentId, name, age);
			return this.studentList.add(newStudent);
		}
		// If student details are invalid or if student is already on list return false
		System.out.println("Student with ID " + studentId + " could not be added to list!");
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

	// Returns true if student on list
	public boolean isOnList(String studentId) {
		return studentList.contains(findStudentObjectByID(studentId));
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
		// If the inputted Student ID and new name are valid...
		if (Student.studentIdIsValid(studentId) && Student.firstNameIsValid(newName)) {
			// Search for student object with on the list with the inputted ID
			Student studentToUpdate = findStudentObjectByID(studentId);
			// If the student object is found i.e. NOT equal to null...
			if (studentToUpdate != null) {
				// Save the current students name
				String currentName = studentToUpdate.getFirstName();
				// Check if the currentName is equal to the newName
				if (currentName.equals(newName)) {
					System.out.println("Student name is already " + newName + "! No update made to name.");
				} else {
					// else if the currentName is NOT equal to the newName, update the name
					studentToUpdate.setFirstName(newName);
					System.out.println("Student name changed from " + currentName + " to " + newName + "!");
					return true;
				}
			}
		}
		// If student ID is invalid or new name is invalid
		System.out.println("Student name NOT updated!");
		return false;
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

	// Read student details from file
	public void readStudentDataFromCSVFile(String pathToStudentCSVFile) {
		File studentCSVFile = null;
		FileReader studentCSVFileReader = null;
		BufferedReader bufferedStudentCSVFileReader = null;
		String bufferData = null; // Used to store lines of data we read from the buffer

		// Create a file reader
		try {
			studentCSVFile = new File(pathToStudentCSVFile);
			studentCSVFileReader = new FileReader(studentCSVFile);
			// Add a buffer to the file reader
			bufferedStudentCSVFileReader = new BufferedReader(studentCSVFileReader);
			// Read first line of file and discard it. It contains column headers.
			bufferedStudentCSVFileReader.readLine();

			while ((bufferData = bufferedStudentCSVFileReader.readLine()) != null) {
				// System.out.println(bufferData);
				String[] studentFieldValues = bufferData.split(",");
				// System.out.println(Arrays.toString(studentFieldValues));
				String studentId = studentFieldValues[0];
				String firstName = studentFieldValues[1];
				int age = Integer.parseInt(studentFieldValues[2]);
				this.addStudentToList(studentId, firstName, age); // Add student to the studentList
			}
			System.out.println("Student data read from CSV file located at " + pathToStudentCSVFile);
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
				// Flushes buffer, which transfers buffer data to the file, then closes buffer.
				bufferedStudentCSVFileReader.close();
				// Close the file reader stream
				studentCSVFileReader.close();
			} catch (IOException IOExc) {
				System.err.println("ERROR: Could not close the buffer file reader!");
				IOExc.printStackTrace();
			} // End catch
		} // End finally
	} // End read method

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

	// Method to serialize the Student Manager Object
	public void writeStudentManagerObjectToFile(String pathToFile) {

		File studentManagerObjectByteFile = null;
		FileOutputStream fosToStudentManagerObjectByteFile = null;
		ObjectOutputStream oosToStudentManagerObjectByteFile = null;

		try {
			studentManagerObjectByteFile = new File(pathToFile);
			fosToStudentManagerObjectByteFile = new FileOutputStream(studentManagerObjectByteFile);
			oosToStudentManagerObjectByteFile = new ObjectOutputStream(fosToStudentManagerObjectByteFile);
			oosToStudentManagerObjectByteFile.writeObject(this);
		} catch (NullPointerException npExc) {
			npExc.printStackTrace();
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
				oosToStudentManagerObjectByteFile.close();
				// Close FileOutputStream
				fosToStudentManagerObjectByteFile.close();
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
		File studentManagerObjectByteFile = null;
		FileInputStream fisFromStudentManagerObjectByteFile = null;
		ObjectInputStream oisFromStudentManagerObjectByteFile = null;
		StudentManager studentManagerObjectReadIn = null; // Create empty StudentManager object to store read in object

		try {
			studentManagerObjectByteFile = new File(pathToFile);
			fisFromStudentManagerObjectByteFile = new FileInputStream(studentManagerObjectByteFile);
			oisFromStudentManagerObjectByteFile = new ObjectInputStream(fisFromStudentManagerObjectByteFile);
			studentManagerObjectReadIn = (StudentManager) oisFromStudentManagerObjectByteFile.readObject();
		} catch (NullPointerException npExc) {
			npExc.printStackTrace();
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (SecurityException secExc) {
			secExc.printStackTrace();
		} catch (StreamCorruptedException scExc) {
			scExc.printStackTrace();
		} catch (InvalidClassException icExc) {
			icExc.printStackTrace();
		} catch (OptionalDataException odExc) {
			odExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} catch (ClassNotFoundException cnfExc) {
			cnfExc.printStackTrace();
		} finally {
			try {
				// Close ObjectOutputStream
				oisFromStudentManagerObjectByteFile.close();
				// Close FileOutputStream
				fisFromStudentManagerObjectByteFile.close();
			} catch (NullPointerException npExc) {
				System.out.println("ERROR: Could not close the ObjectOutputStream or FileOutputStream!");
				npExc.printStackTrace();
			} catch (IOException ioExc) {
				ioExc.printStackTrace();
			} // End catch
		} // End finally
		return studentManagerObjectReadIn; // Returns null if no object is read in.
	}

} // End Class
