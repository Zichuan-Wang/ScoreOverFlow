# Schedule++

Schedule++ is a multi-platform desktop software that automates the scheduling of public facility usages (e.g., university classrooms). Administrators can input a list of events (e.g. class sessions) with their respective time and location requirements. The system will assign a room and time slot for each of them while optimizing for these constraints. Individual users can also view available rooms and book venues for private events on this platform. Additional features include specifying the equipments (e.g. projector) available in a classroom and prioritizing certain user groups (e.g. professors) for room booking. The software can also potentially add a voice control function for accessibility concerns. The system will be a smart and integrated tool for universities and other facility providers.

## How to Build

This Software is developed with Java 8. Please make sure you have the Jave Runtime Environment (JRE) 1.8+ installed on your machine. This is also a maven project. Prior to compiling, please install maven and run ```mvn clean``` to install the necessary dependencies. You can use Java Compiler (javac) to compile the source codes.

## How to Run

Before running the application, you should install MySQL to the machine and setup a production database. Please construct the database based on ```db_schema/scoreoverflowdb.sql```. Then substitute the relevant parameters in ```src/main/resources/META-INF/persistence.xml```. In particular, change the value of ```hibernate.connection.url``` to the database url and configure the ```hibernate.connection.username``` and ```hibernate.connection.password``` accordingly. To enable the emailing function, you should create a Gmail account and edit the address, username and password fields in ```src/main/resources/email.properties```.

The main entry point for the program is the ```MainWindow.java``` class. Please run java for the ```MainWindow.class``` binary file. Alternatively, you can use Eclipse IDE > Select MainWindow class > run as a Java Application.

## How to Test

This software implements a comprehensive test suite using JUnit and AssertJ. All the unit test files are included in the src/test/ directory. You can run an individual test by running the specific class as a JUnit Test Case in Eclipse. You can also run the entire test suite using the command ```mvn test```. We also incorporated Jacoco tests for instruction and branch coverage. In particular, the test will fail for a branch coverage of lower than 50%.


## Features

### Login/Logout

User can login to the application in the entry page with the correct username and password combination. This information is stored in the user table in the database. The password in database is hashed with salt.

### Search for rooms

On the search panel, select the requirements and preferences, including desired room capacity, facilities (e.g. projectors), time slots, building/location preferences and desired room name. A list of available rooms that match the preference will be shown.

### Book rooms

For the list of search results on the search panel, there is a button after each room item. Click the one that you want to book the room.

### View reservations

On the reservation panel, a list of all upcoming reservations will be shown.

### Cancel reservations

For the list of booked rooms on the user panel, there is a button after each reservation item. Click the one that you want to cancel the reservation.

### Override reservations

Higher priority users can override reservations made by other users. The users whose resevations are overriden will receive email notifications about the change.

### Batch Uploads

Certain user groups are allowed to upload CSV files that contain request information and make many reservations all at once. The application uses a greedy algorithm to book as many rooms as possible. The UI also provides a template CSV file for user download.

### Manage rooms and facilities

Admin can add/edit/delete rooms or facilities at a special panel. When a room is deleted, all users who have upcoming reservations in the room will be notified.

## Dependencies
Java 8, Maven (JPA, Hibernate, JDBC, JUnit, Mockito, PMD), MySQL

## Pre-Commit

You can configure this repository to set up pre-commit testing. Run ```scripts/git-hooks/hook-setup.sh``` to incorporate pre-commit. Before each commit, the static code analyzer/style checker PMD is automatically run. The unit test suites are also run by Maven. If there are static bugs or failed unit tests, the system would not allowed the commit go through.

