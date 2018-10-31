# Schedule++

Schedule++ is a multi-platform desktop software that automates the scheduling of public facility usages (e.g., university classrooms). Administrators can input a list of events (e.g. class sessions) with their respective time and location requirements. The system will assign a room and time slot for each of them while optimizing for these constraints. Individual users can also view available rooms and book venues for private events on this platform. Additional features include specifying the equipments (e.g. projector) available in a classroom and prioritizing certain user groups (e.g. professors) for room booking. The software can also potentially add a voice control function for accessibility concerns. The system will be a smart and integrated tool for universities and other facility providers.

## Implemented Features

### Search for rooms

On the search panel, select the requirements and preferences, including desired room capacity, equipments (e.g. projectors), time slots, building/location preferences and desired room name. A list of rooms that match the preference will be shown.

### Book rooms

For the list of search results on the search panel, there is a button after each room item. Click the one that you want to book the room.

### View reservations

On the user panel, a list of all booked rooms will be shown.

### Cancel reservations

For the list of booked rooms on the user panel, there is a button after each reservation item. Click the one that you want to cancel the reservation.

## Dependencies
Java 8, Mockito, Maven

## How to run

1) Install MySQL and create database based on the schema provided.

2) Install the tools/technologies in the dependencies section.

3) In Eclipse, select MainWindow.java and run as a Java Application.
