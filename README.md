# Doctor Appointment Management System 🏥

A console-based Doctor Appointment Management System developed using **Java, JDBC, and MySQL**. This project helps manage doctors, patients, appointments, and hospital administration.

## Features

### Admin
- Add new doctors
- View all doctors
- Manage doctor records
- View appointments
- Manage patients

### Patient
- Register patient details
- Book appointments
- View appointment information
- Check assigned doctor details

## Technologies Used

- Java
- JDBC
- MySQL
- Eclipse IDE
- Git & GitHub

## Database Tables

### Doctors Table
Contains doctor information:

| Column | Type |
|--------|------|
| doc_id | INT (PK) |
| doctor_name | VARCHAR |
| specialist | VARCHAR |

### Patients Table

| Column | Type |
|--------|------|
| patient_id | INT (PK) |
| patient_name | VARCHAR |
| age | INT |
| gender | VARCHAR |

### Appointments Table

| Column | Type |
|--------|------|
| appointment_id | INT (PK) |
| patient_id | FK |
| doctor_id | FK |
| appointment_date | DATE |

## Project Structure

```text
src/
│
├── Main.java
├── DoctorService.java
├── PatientService.java
├── AppointmentService.java
├── DatabaseConnection.java
└── Models/
