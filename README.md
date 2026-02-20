# Student Manager App - Part 4: Serialization

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)

A Java console application that manages student records with object serialization and deserialization. This is Part 4 of a multi-part Student Manager series, building on CSV I/O by adding the ability to serialize entire `StudentManager` objects to binary `.ser` files using `ObjectOutputStream` and `ObjectInputStream`.

## Overview

This project extends the Student Manager application with Java object serialization. Both the `Student` and `StudentManager` classes implement `Serializable`, enabling the full object graph to be persisted to disk as a binary file and reconstructed later. The project also demonstrates `serialVersionUID` for version control of serialized classes, try-with-resources for cleaner stream management, and Java 8 Streams for filtering.

## Features

- **Object Serialization** -- Write the entire `StudentManager` object (including its student list) to a `.ser` binary file
- **Object Deserialization** -- Read a `StudentManager` object back from a `.ser` file
- **CSV File I/O** -- Read from and write to CSV files using buffered streams
- **Try-With-Resources** -- Cleaner resource management with automatic stream closing
- **Input Validation** -- Validates student ID (`G00XXXXXX`), name, and age with `IllegalArgumentException`
- **Search & Filter** -- Find students by name, age, combined criteria, or age range
- **Java 8 Streams** -- Filter student lists using the Stream API

## Prerequisites

- **Java JDK** 11 or higher
- A Java IDE (Eclipse, IntelliJ IDEA, VS Code) or command-line access

## Getting Started

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/danielcregg/student-manager-app-part4-Serialization.git
   cd student-manager-app-part4-Serialization
   ```

2. Open the project in your preferred Java IDE, or compile from the command line.

### Usage

**Compile and run from the command line:**

```bash
javac -d bin src/ie/atu/studentmanagerpackage/*.java
java -cp bin ie.atu.studentmanagerpackage.Main
```

The application reads student data from `resources/students.csv`, performs CRUD operations, serializes the `StudentManager` object to `resources/students.ser`, and then deserializes it back.

## Tech Stack

- **Language:** Java
- **Serialization:** `java.io.Serializable`, `ObjectOutputStream`, `ObjectInputStream`
- **I/O:** `java.io` (FileReader, FileWriter, BufferedReader, BufferedWriter)
- **Data Structures:** `java.util.ArrayList`, Java 8 Streams
- **Dev Environment:** VS Code Dev Containers

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
