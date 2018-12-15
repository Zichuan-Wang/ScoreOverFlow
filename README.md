# Schedule++

Schedule++ is a multi-platform desktop software that automates the scheduling of public facility usages (e.g., university classrooms). Administrators can input a list of events (e.g. class sessions) with their respective time and location requirements. The system will assign a room and time slot for each of them while optimizing for these constraints. Individual users can also view available rooms and book venues for private events on this platform. Additional features include specifying the equipments (e.g. projector) available in a classroom and prioritizing certain user groups (e.g. professors) for room booking. The software can also potentially add a voice control function for accessibility concerns. The system will be a smart and integrated tool for universities and other facility providers.

## Implemented Features

### Search for rooms

On the search panel, select the requirements and preferences, including desired room capacity, equipments (e.g. projectors), time slots, building/location preferences and desired room name. A list of rooms that match the preference will be shown.

### Book rooms

For the list of search results on the search panel, there is a button after each room item. Click the one that you want to book the room.

### View reservations

On the user panel, a list of all booked rooms will be shown.

### Override reservations

Higher priority users can override reservations made by other users. The users whose resevations are overriden will receive email notifications about the change.

### Cancel reservations

For the list of booked rooms on the user panel, there is a button after each reservation item. Click the one that you want to cancel the reservation.

### Batch Uploads

Certain user groups are allowed to upload CSV files that contain reservation information and make many reservations all at once.

### Manage rooms and facilities

Admin can add/edit/delete rooms or facilities at a special panel. 

## Dependencies
Java 8, Maven (JPA, Hibernate, JDBC, JUnit, Mockito, PMD), MySQL

## How to run

1) Install MySQL and create database based on ```db_schema/scoreoverflowdb.sql```.

2) Install Maven and build the dependencies using ```mvn clean```

3) To run the client, select ```MainWindow.java``` in Eclipse and run as Java Application.

4) To run the tests, select ```src/test``` in Eclipse and run as JUnit Test.

## Pre-Commit

You can configure this repository to set up pre-commit testing. Run ```scripts/git-hooks/hook-setup.sh``` to incorporate pre-commit. Before each commit, the static code analyzer/style checker PMD is automatically run. The unit test suites are also run by Maven. If there are static bugs or failed unit tests, the system would not allowed the commit go through.
