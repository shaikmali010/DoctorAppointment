package DoctorAppointment;

import java.sql.*;

import java.util.Scanner;
import java.time.*;
import java.time.format.*;

public class AppointmentService {
	
	int id1 = 0;

	static void AppointTable(Connection con ) throws SQLException{
		 
		 String createTable1 = """
				 CREATE TABLE IF NOT EXISTS appointment (
				 appt_id INT PRIMARY KEY AUTO_INCREMENT,
				 
				 patient_name VARCHAR(50) NOT NULL,
				 patient_age INT,
				 
				 doc_id INT NOT NULL,
				 
				 date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
				 
				 appt_details VARCHAR(30) NOT NULL,
				 
				 appt_status VARCHAR(10) NOT NULL,
				 
				 FOREIGN KEY (doc_id ) REFERENCES doctors(doc_id) 
				 );
				 
		 		""";
				 
		 try(PreparedStatement psApp = con.prepareStatement(createTable1)) {
			 psApp.executeUpdate();
			 System.out.println("The Appointment table created successfully!\n");
		 }
	}
	
	
//	1. ADD APPOINTMENT-------------------------------------------------------------------------
	 static void BookAppointment(Connection con) throws SQLException{
		
		 Scanner sc = new Scanner(System.in);
		 
		 String addQuery = "INSERT INTO appointment(patient_name, patient_age, doc_id, appt_details, appt_status) VALUES(?, ?, ?, ?, ?)";
		 String getQuery = "SELECT doc_id, specialty FROM doctors";
		 
		 try(PreparedStatement psAdd = con.prepareStatement(addQuery)){
			 PreparedStatement psGet = con.prepareStatement(getQuery);
			 
			 ResultSet rs = psGet.executeQuery();
			 
			 
			 
			 System.out.print("Enter patient name:");
			 String name = sc.nextLine();
			 
			 System.out.print("Enter patient age:");
			 int age = sc.nextInt();
			 sc.nextLine();
			 
			 System.out.println();
			 
//			 SHOW AVAILABLE SERVICE 
			 while(rs.next()) {
				 System.out.println(rs.getInt("doc_id")+"."+rs.getString("specialty"));
			 }
			 
			 System.out.print("Choose the option for Appointment:");
			 int id = sc.nextInt();
			 sc.nextLine();
			 
			 psAdd.setString(1, name);
			 psAdd.setInt(2, age);
			 psAdd.setInt(3, id);
			 
//			 SHOW AVAILABLE DATE FOR APPOINTMENT 
			 LocalDateTime currentDate = LocalDateTime.now();

		        DateTimeFormatter formatter =
		                DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        System.out.println("Available Dates");
		        System.out.println("----------------");

		        for(int i = 1; i <= 3; i++) {

		            LocalDateTime appointmentDate =
		                    currentDate.plusDays(i - 1);

		            String formattedDate =
		                    appointmentDate.format(formatter);

		            System.out.println(i + ". " + formattedDate);
		        }

//		        SELECT THE A PERTICULAR DATE 
		        
		        System.out.print("\nSelect Appointment Date: ");

		        int choice = sc.nextInt();
		        sc.nextLine();

		        String finalDate = "";
		        if(choice >= 1 && choice <= 3) {

		            LocalDateTime selectedDate =
		                    currentDate.plusDays(choice - 1);

		            finalDate =
		                    selectedDate.format(formatter);


		        }else {

		            System.out.println("\nInvalid Selection!");
		        }
		        
		        System.out.println("Write the Available time in this formate(hh:mm am/pm):");
		        String time2 = sc.nextLine();
		        
		        String selectDate1 = finalDate+" "+time2;
			 
			 
			 psAdd.setString(4, selectDate1);
			 psAdd.setString(5, "Active");
			 
			 int rows = psAdd.executeUpdate();
			 
			 
			 if(rows > 0) {
				 System.out.println("Added successfully!");
			 }
			 
		 }
	 }
	 
//	2. SHOW APPOINTMENT BY ID AND NAME ----------------------------------------------------------------
		 
	 static void ShowDetails(Connection con) throws SQLException {
		 Scanner sc = new Scanner(System.in);
		 
		 String showQuery = """
	 SELECT
	 a.appt_id AS Token_number, 
     a.patient_name AS Name, 
     a.patient_age AS Age, 
     a.appt_details AS Appointment_At,
     a.appt_status AS Status, 
     d.name AS Doctor_Name, 
     d.specialty AS Specialty, 
     d.fee As Doctor_Fee
      FROM
     
     appointment a
     
     INNER JOIN doctors d
     
     ON a.doc_id = d.doc_id
     
      WHERE a.appt_id = ? && a.patient_name = ?
      """;
			 
		 try(PreparedStatement psShow = 
				 con.prepareStatement(showQuery)){
			 
			 System.out.print("Enter the patient id: ");
			 int id = sc.nextInt();
			 sc.nextLine();
			 
			 System.out.print("Enter valid patient name:");
			 String name = sc.nextLine();
			 
			 
			 psShow.setInt(1, id);
			 psShow.setString(2, name);
			 
			 ResultSet rs = psShow.executeQuery();
			 
			 
			 if(rs.next()) {
				    
				 System.out.println("================================");
				 System.out.println("PUBLIC HOSPITAL ");
				 System.out.println("------------------------------");
				 	System.out.println("Patient Token Number : "
				 			+rs.getInt("Token_number"));
				    System.out.println("Patient Name : "
				            + rs.getString("Name"));

				    System.out.println("Age : "
				            + rs.getInt("Age"));

				    System.out.println("Appointment At : "
				            + rs.getString("Appointment_At"));

				    System.out.println("Doctor Name : "
				            + rs.getString("Doctor_Name"));

				    System.out.println("Specialty : "
				            + rs.getString("Specialty"));

				    System.out.println("Doctor Fee : \u20B9"
				            + rs.getDouble("Doctor_Fee")+" ");
				    
				    System.out.println("Status : "
				            +rs.getString("Status"));

				    System.out.println("=============================");
				}else {
					System.out.println("No Patient found by "+id);
				}
			 
			 rs.close();
			 psShow.close();
			
		 }
	 }
	 
	 
// SHOW ALL APPOINTMNET ----------------------------------------------------------------------------------------------
	 static void ShowAllDetails(Connection con) throws SQLException {
		 Scanner sc = new Scanner(System.in);
		 
		 String showQuery = """
	 SELECT
	 a.appt_id AS Token_number, 
     a.patient_name AS Name, 
     a.patient_age AS Age, 
     a.appt_details AS Appointment_At,
     a.appt_status AS Status, 
     d.name AS Doctor_Name, 
     d.specialty AS Specialty, 
     d.fee As Doctor_Fee
      FROM
     
     appointment a
     
     INNER JOIN doctors d
     
     ON a.doc_id = d.doc_id
      """;
			 
		 PreparedStatement psShow = 
				 con.prepareStatement(showQuery);
			 
			 ResultSet rs = psShow.executeQuery();
			 
			 
			 while(rs.next()) {
				    
				 System.out.println("================================");
				 System.out.println("PUBLIC HOSPITAL ");
				 System.out.println("------------------------------");
				 	System.out.println("Patient Token Number : "
				 			+rs.getInt("Token_number"));
				    System.out.println("Patient Name : "
				            + rs.getString("Name"));

				    System.out.println("Age : "
				            + rs.getInt("Age"));

				    System.out.println("Appointment At : "
				            + rs.getString("Appointment_At"));

				    System.out.println("Doctor Name : "
				            + rs.getString("Doctor_Name"));

				    System.out.println("Specialty : "
				            + rs.getString("Specialty"));

				    System.out.println("Doctor Fee : \u20B9"
				            + rs.getDouble("Doctor_Fee")+" ");
				    
				    System.out.println("Status : "
				            +rs.getString("Status"));

				    System.out.println("=============================");
				}
			 
			 rs.close();
			 psShow.close();
			
		 }
	 
//	 UPDATE BOOKING DATE ------------------------------------------------------------------------------------
	 public static void UpdateDate(Connection con) throws Exception{
		 Scanner sc = new Scanner(System.in);
		 
		 String updateQuery = "UPDATE appointment SET appt_details = ? WHERE appt_id = ?";
		 PreparedStatement psUpdate = con.prepareStatement(updateQuery);
		 
		 System.out.println("Enter the id: ");
		 int id = sc.nextInt();
		 sc.nextLine();
		 
		 
//		 SHOW AVAILABLE DATE FOR APPOINTMENT 
		 LocalDateTime currentDate = LocalDateTime.now();

	        DateTimeFormatter formatter =
	                DateTimeFormatter.ofPattern("dd-MM-yyyy");

	        System.out.println("Available Dates");
	        System.out.println("----------------");

	        for(int i = 1; i <= 3; i++) {

	            LocalDateTime appointmentDate =
	                    currentDate.plusDays(i - 1);

	            String formattedDate =
	                    appointmentDate.format(formatter);

	            System.out.println(i + ". " + formattedDate);
	        }
		 
		 System.out.print("\nSelect Appointment Date: ");

	        int choice = sc.nextInt();
	        sc.nextLine();

	        String finalDate = "";
	        if(choice >= 1 && choice <= 3) {

	            LocalDateTime selectedDate =
	                    currentDate.plusDays(choice - 1);

	            finalDate =
	                    selectedDate.format(formatter);


	        }else {

	            System.out.println("\nInvalid Selection!");
	        }
	        
	        System.out.println("Write the Available time in this formate(hh:mm am/pm):");
	        String time2 = sc.nextLine();
	        
	        String selectDate1 = finalDate+" "+time2;
		 
		 
		 psUpdate.setString(1, selectDate1);
		 psUpdate.setInt(2, id);
		 
		 System.out.println("Updated sucessfully!");
		 
		 		 
	 }
	 
	 static void ShowDetailsById(Connection con, int id) throws SQLException {
		 String showQuery = """
	 SELECT
	 a.appt_id AS Token_number, 
     a.patient_name AS Name, 
     a.patient_age AS Age, 
     a.appt_details AS Appointment_At,
     a.appt_status AS Status, 
     d.name AS Doctor_Name, 
     d.specialty AS Specialty, 
     d.fee As Doctor_Fee
      FROM
     
     appointment a
     
     INNER JOIN doctors d
     
     ON a.doc_id = d.doc_id
     
      WHERE a.appt_id = ?
      
      """;
			 
		 try(PreparedStatement psShow = 
				 con.prepareStatement(showQuery)){
			 
			 psShow.setInt(1, id);
			 
			 ResultSet rs = psShow.executeQuery();
			 
			 
			 if(rs.next()) {
				    
				 System.out.println("================================");
				 System.out.println("PUBLIC HOSPITAL ");
				 System.out.println("------------------------------");
				 	System.out.println("Patient Token Number : "
				 			+rs.getInt("Token_number"));
				    System.out.println("Patient Name : "
				            + rs.getString("Name"));

				    System.out.println("Age : "
				            + rs.getInt("Age"));

				    System.out.println("Appointment At : "
				            + rs.getString("Appointment_At"));

				    System.out.println("Doctor Name : "
				            + rs.getString("Doctor_Name"));

				    System.out.println("Specialty : "
				            + rs.getString("Specialty"));

				    System.out.println("Doctor Fee : \u20B9"
				            + rs.getDouble("Doctor_Fee")+" ");
				    
				    System.out.println("Status : "
				            +rs.getString("Status"));

				    System.out.println("=============================");
				}else {
					System.out.println("No Patient found by "+id);
				}
			 
			 rs.close();
			 psShow.close();
			 
			
		 }
	 }
	
	 
//	 UPDATE PATIENT NAME -------------------------------------------------------------------------------------------------
	 public static void ChangeName(Connection con, int id) throws SQLException{
		 Scanner sc = new Scanner(System.in);
		 
		 String changeQuery = "UPDATE appointment SET patient_name = ? WHERE appt_id = ?";
		 PreparedStatement psUpdate = con.prepareStatement(changeQuery);
		  
		  System.out.println("Enter the name to Update: ");
		  String name = sc.nextLine();
		  
		  psUpdate.setString(1, name);
		  psUpdate.setInt(2, id);
		  
		 int rows = psUpdate.executeUpdate();
		  
		   psUpdate.close();
		   
		   if(rows>0) {
			   System.out.println("Updated successfully1");
		   }else {
			   System.out.println("Something went wrong!");
		   }
	 }
	 
//	UPDATE DOCTOR ID IN APPOINTENT -----------------------------------------------------------------------------------
	 public static void ChangeID(Connection con, int id) throws SQLException{
		 Scanner sc = new Scanner(System.in);
		 
		 String changeQuery = "UPDATE appointment SET doc_id = ? WHERE appt_id = ?";
		 PreparedStatement psUpdate = con.prepareStatement(changeQuery);
		  
		  System.out.println("Enter the Available Doctor id to Update: ");
		  int id1 = sc.nextInt();
		  sc.nextLine();
		  
		  psUpdate.setInt(1, id1);
		  psUpdate.setInt(2, id);
		  
		 int rows = psUpdate.executeUpdate();
		  
		   psUpdate.close();
		   
		   if(rows>0) {
			   System.out.println("Updated successfully1");
		   }else {
			   System.out.println("Something went wrong!");
		   }
	 }
	 
//	 UPDATE THE PATIENT AGE ----------------------------------------------------------------------------------
	 public static void ChangeAge(Connection con, int id) throws SQLException{
		 Scanner sc = new Scanner(System.in);
		 
		 String changeQuery = "UPDATE appointment SET patient_age = ? WHERE appt_id = ?";
		 PreparedStatement psUpdate = con.prepareStatement(changeQuery);
		  
		  System.out.println("Enter the age to Update: ");
		  int id1 = sc.nextInt();
		  sc.nextLine();
		  
		  psUpdate.setInt(1, id1);
		  psUpdate.setInt(2, id);
		  
		 int rows = psUpdate.executeUpdate();
		  
		   psUpdate.close();
		   
		   if(rows>0) {
			   System.out.println("Updated successfully1");
		   }else {
			   System.out.println("Something went wrong!");
		   }
	 }
	 
//	 
//	3. CANCEL BOOKIN BY ID -----------------------------------------------------------------------------------
		 
		 public static void CancelAppat(Connection con) throws SQLException{
			 Scanner sc = new Scanner(System.in);
			 
			 String deleteQuery = "UPDATE appointmnet SET appt_status = ? WHERE appt_id = ?";
			 
			 try(PreparedStatement psDelete = con.prepareStatement(deleteQuery)){
				 
				 System.out.println("------------------------------");
				 System.out.println("Cancel Appointment!");
				 System.out.println("----------------");
				 System.out.print("Enter patient id: ");
				 int id = sc.nextInt();
				 sc.nextLine();
				 
				 psDelete.setString(1, "Cancelled");
				 psDelete.setInt(2, id);
				int runs = psDelete.executeUpdate();
				 
				
				 if(runs > 0) {
					 System.out.println(id+" patient has been removed.");
				 }else {
					 System.out.println("The action was turminated.");
				 }
				 
				 psDelete.close();
				 
			 }
			 
			 
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
}
