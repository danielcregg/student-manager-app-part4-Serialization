package ie.atu.studentmanagerpackage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * The Main class holds the main method.
 */
public class Main extends Application {

	StudentManager sm = new StudentManager(); // Used for managing students

	@Override
	public void start(Stage primaryStage) {

		/* Preparing the Scenes */
		// Create gridpane node to use as root node of scene and to arrnage child nodes
		// logically
		GridPane gridPane1 = new GridPane();
		// Create child nodes
		// Create Text node for top of scene 1
		Text txtHeader = new Text("Please select an option below:");
		// Create button and TextField for Loading DB
		Button btnLoadStudentList = new Button("Load Students from File");
		TextField tfLoadStudentFilePath = new TextField();
		tfLoadStudentFilePath.setPromptText("Path to Student File");
		// Add Student Button and text fields
		Button btnAddStudent = new Button("Add Student");
		TextField tfStudentID = new TextField();
		tfStudentID.setPromptText("ID Number");
		TextField tfStudentFirstName = new TextField();
		tfStudentFirstName.setPromptText("First Name");
		TextField tfStudentSurname = new TextField();
		tfStudentSurname.setPromptText("Surname");
		TextField tfStudentAge = new TextField();
		tfStudentAge.setPromptText("Year of Study");
		// Delete Student
		Button btnDelStudent = new Button("Delete Student");
		TextField tfStudentDel = new TextField();
		tfStudentDel.setPromptText("Student No.");
		// Show total number of students
		Button btnShowTotal = new Button("Show Total Students");
		// Show total number of students
		Button btnShowStudentList = new Button("Show Student List");
		// Save students to file
		Button btnSaveStudentList = new Button("Save Student List");
		TextField tfSaveStudentFilePath = new TextField();
		tfSaveStudentFilePath.setPromptText("Path to Student File");
		// Add Quit button
		Button btnQuit = new Button("Quit");
		// Create TextArea node for bottom of scene 1 to display output
		TextArea taMyOutput = new TextArea();

		// Adding and arranging all the nodes in the grid - add(node, column, row)
		gridPane1.add(txtHeader, 0, 0);
		gridPane1.add(btnLoadStudentList, 0, 1);
		gridPane1.add(tfLoadStudentFilePath, 1, 1);
		gridPane1.add(btnAddStudent, 0, 2);
		gridPane1.add(tfStudentID, 1, 2);
		gridPane1.add(tfStudentFirstName, 2, 2);
		gridPane1.add(tfStudentAge, 3, 2);
		gridPane1.add(btnDelStudent, 0, 3);
		gridPane1.add(tfStudentDel, 1, 3);
		gridPane1.add(btnShowTotal, 0, 4);
		gridPane1.add(btnShowStudentList, 0, 5);
		gridPane1.add(btnSaveStudentList, 0, 6);
		gridPane1.add(tfSaveStudentFilePath, 1, 6);
		gridPane1.add(btnQuit, 0, 7);
		gridPane1.add(taMyOutput, 0, 8, 5, 1);

		// Adding events to buttons
		// Load Students DB button
		btnLoadStudentList.setOnAction(e -> {

			if (tfLoadStudentFilePath.getText().trim().equals("")) { // If text field is empty
				taMyOutput.setText("Please enter path to Student file.\n");
			} else {
				// File studentCSVFile = new File(".\\resources\\students.csv");
				// sm.loadStudentsFromCSVFile(studentCSVFile);
				// sm.saveStudentManagerObjectToFile(studentObjectsFile);
				sm = sm.readStudentManagerObjectFromFile(tfLoadStudentFilePath.getText());
				if (sm == null) {
					taMyOutput.setText("ERROR: DB path " + tfLoadStudentFilePath.getText() + " does not exist\n");
					taMyOutput.appendText("Please check DB path and try again");
					tfLoadStudentFilePath.clear();
				} else {
					taMyOutput.setText("DB loaded successfully from " + tfLoadStudentFilePath.getText());
					// taMyOutput.appendText("\nTotal Students loaded: " +
					// Integer.toString(sm.findTotalStudents()) + "\n");
					// taMyOutput.appendText("\n" + sm.listAllStudnets());
					tfLoadStudentFilePath.clear();
				}
			}

		});

		// Add Student button action
		btnAddStudent.setOnAction(e -> {

			// If any of the Student fields are empty print prompt message
			if (tfStudentID.getText().trim().equals("") || tfStudentFirstName.getText().trim().equals("")
					|| tfStudentAge.getText().trim().equals("")) {
				taMyOutput.setText("Please enter ALL Student details\n");
			} else {
				// Create new Student with information in text fields
				try {
					this.sm.addStudentToList(tfStudentID.getText(), tfStudentFirstName.getText(), Integer.parseInt(tfStudentAge.getText()));
					// Print success message
					taMyOutput.setText(tfStudentID.getText().trim().equals("") + " has been added to the student list");

					// Clear input fields
					tfStudentID.clear();
					tfStudentFirstName.clear();
					tfStudentAge.clear();
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					taMyOutput.setText("Please enter a number for Student Year of Study");
				}

			}

		});

		// Delete Student button action
		btnDelStudent.setOnAction(e -> {

			if (tfStudentDel.getText().trim().equals("")) { // If text field is empty
				taMyOutput.setText("Please enter the Student Number you want to delete");
			} else {
				boolean success;
				success = sm.removeStudentFromList(tfStudentDel.getText());
				if (success != false) {
					taMyOutput.setText(" has been removed from the student list!");
					tfStudentDel.clear();
				} else {
					taMyOutput.setText("Student " + tfStudentDel.getText() + " not found\n");
					taMyOutput.appendText("No student deleted!");
					tfStudentDel.clear();
				}
			}
		});

		// Show total number of students
		btnShowTotal.setOnAction(e -> {
			int totalStudents = 0;
			// Find total Students
			totalStudents = sm.getStudentList().size();
			taMyOutput.setText("Current Total Students: " + Integer.toString(totalStudents));
		});

		// Show list of students
		btnShowStudentList.setOnAction(e -> {
			taMyOutput.setText(sm.listAllStudnets());
		});

		btnSaveStudentList.setOnAction(e -> {

			if (tfSaveStudentFilePath.getText().trim().equals("")) { // If text field is empty
				taMyOutput.setText("Please enter path to Student List.\n");
			} else {
				try {
					sm.writeStudentManagerObjectToFile(tfSaveStudentFilePath.getText());
					taMyOutput.setText("Student list saved!");
					tfSaveStudentFilePath.clear();
				} catch (Exception exception) {
					System.out.print("[Error] Cannont save DB. Cause: ");
					exception.printStackTrace();
					taMyOutput.setText("ERROR: Failed to save Students DB!");
				}
			}

		});

		// Quit button action
		btnQuit.setOnAction(e -> Platform.exit());

		// Create scene and add the root node i.e. the gridpane
		Scene scene1 = new Scene(gridPane1, 600, 450);
		// Preparing the Stage (i.e. the container of any JavaFX application)
		// Set Stage Title
		primaryStage.setTitle("Student Manager Application");
		// Setting the scene to Stage
		primaryStage.setScene(scene1);
		// Displaying the stage
		primaryStage.show();

	}// End Start Method

	public static void main(String[] args) {
		launch(args);
	}

//	// Main method containing print statement.
//	public static void main(String[] args) {
//
////		// Create menu object
////		Menu menuObject = new Menu();
////		// Run menu start method
////		menuObject.start();
//		boolean addStatus;
//
//		// Add studnets from csv file
//		StudentManager stuManObj = new StudentManager();
////		File studentCSVFile = new File(".\\resources\\students.csv");
//		File studentManagerByteFile = new File(".\\resources\\students.ser");
////		stuManObj.loadStudentsFromCSVFile(studentCSVFile);
//		stuManObj = stuManObj.loadStudentManagerObjectFromFile(studentManagerByteFile);
//		System.out.println(stuManObj.findTotalStudents());	
//		
//		// Add one more student manually and write out all students to csv file
//		Student studentObj1 = new Student("G00123461", "Jim", "Jones", 1);
//		stuManObj.addStudent(studentObj1);
//		System.out.println(stuManObj.findTotalStudents());
////		stuManObj.saveStudentsToCSVFile(studentCSVFile);
//		stuManObj.saveStudentManagerObjectToFile(studentManagerByteFile);
//
////		Student studentObject1 = new Student("G00123458", "Alf", "Stewart", 3);
////		Student studentObject2 = new Student("G00123459", "Sally", "Fletcher", 4);
////		Student studentObject3 = new Student("G00123459", "Sally", "Fletcher", 4);
////
////		addStatus = stuManObj.addStudent(studentObject1);
////		System.out.println(addStatus);
////		addStatus = stuManObj.addStudent(studentObject2);
////		System.out.println(addStatus);
////		addStatus = stuManObj.addStudent(studentObject3);
////		System.out.println(addStatus);
//
//		// boolean removeStatus = stuManObj.removeStudent(studentObject1);
//
//		// System.out.println(removeStatus);
//
//		System.out.println("The End");
//
//	} // End main method

} // End Main Class
