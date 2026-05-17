package DoctorAppointment;

import java.sql.*;
import java.util.*;

public class Users {

	
	 static void UserTable(Connection con) throws SQLException{
		 
		 String query = """
			        CREATE TABLE IF NOT EXISTS users(
			            user_id INT PRIMARY KEY AUTO_INCREMENT,
			            username VARCHAR(30) UNIQUE NOT NULL,
			            password VARCHAR(50) NOT NULL,
			            role ENUM('ADMIN','DOCTOR') NOT NULL
			        )
			        """;
		 
		PreparedStatement st = con.prepareStatement(query);
		st.executeUpdate();
		
		System.out.println("User created successfully!");
                              
	 }
	 
//	 1. ADD ADMIN -----------------------------------------------------------------------------------------------------
	 static void addUser(Connection con) throws SQLException{
		 Scanner sc = new Scanner(System.in);
		 
		 String insertQuery = 
				 "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";
		 
		 PreparedStatement ptInsert = con.prepareStatement(insertQuery);
		 
		 System.out.println("Enter use name: ");
		 String name = sc.nextLine();
		 
		 System.out.print("Enter password: ");
	     String password = sc.nextLine();

	        boolean hasUpper = false;
	        boolean hasLower = false;
	        boolean hasDigit = false;
	        boolean hasSpecial = false;

	        if (password.length() >= 8) {

	            for (int i = 0; i < password.length(); i++) {
	                char ch = password.charAt(i);

	                if (Character.isUpperCase(ch)) {
	                    hasUpper = true;
	                } else if (Character.isLowerCase(ch)) {
	                    hasLower = true;
	                } else if (Character.isDigit(ch)) {
	                    hasDigit = true;
	                } else {
	                    hasSpecial = true;
	                }
	            }

	            if (hasUpper && hasLower && hasDigit && hasSpecial) {
	                System.out.println("Strong Password!");
	                
	            } else {
	                System.out.println("Weak Password!");
	            }

	        } else {
	            System.out.println("Password must be at least 8 characters!");
	        }
	        
	        ptInsert.setString(1, name);
	       ptInsert.setString(2, password);
	       ptInsert.setString(3, "ADMIN");
	        
	       int runs = ptInsert.executeUpdate();
	        
	       if(runs > 0) {
	    	   System.out.println("Admin added Successfully!");
	       }else {
	    	   System.out.println("Not added!");
	       }	
	       
	      
	 }
	 
//	 2. SHOW ALL USER ----------------------------------------------------------------------------------------------
	 public static void ShowAllUser(Connection con) throws Exception{
		 Scanner sc = new Scanner(System.in);
		 
		 String showAllQuery = "SELECT * FROM users";
		 
		 PreparedStatement ptShow = con.prepareStatement(showAllQuery);
		 
		 ResultSet rs = ptShow.executeQuery();
		 
		 while(rs.next()) {
			 System.out.println(rs.getInt("user_id")+" | "+rs.getString("username")+" | "+rs.getString("password")+" | "+rs.getString("role"));
		 }
		 
		 rs.close();
		 ptShow.close();
	 }
       	 
//	 3. SHOW USER DETAILS -------------------------------------------------------------------------------------------
	 public static void ShowUserById(Connection con) throws Exception {
		 Scanner sc = new Scanner(System.in);
		 
		 String showQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
		 
		 PreparedStatement ptShow = con.prepareStatement(showQuery);
		 
		 System.out.print("Enter user name: ");
		 String name = sc.nextLine();
		 
		 System.out.print("Enter the password: ");
		 String password = sc.nextLine();
		 
		 ptShow.setString(1, name);
		 ptShow.setString(2, password);
		 
		 ResultSet rs = ptShow.executeQuery();
		 
		 if(rs.next()) {
			 System.out.println(
					 rs.getInt("user_id")+" | "
			        +rs.getString("username")+" | "
				    +rs.getString("password")+" | "
			        +rs.getString("role")
			        );
		 }else {
			 System.out.println("Invalid username or password!");
		 }
		 
		 rs.close();
		 ptShow.close();
	 
	 }
	 
//	 UPDATE USERS --------------------------------------------------------------------------------
	 public static void UpdateUser(Connection con) throws Exception{
		 Scanner sc  = new Scanner(System.in);
		 
		 String updateQuery = "UPDATE users SET password = ? WHERE user_id = ? AND password = ?";
		 PreparedStatement ptUpdate = con.prepareStatement(updateQuery);
		 
		 System.out.println("Enter user id: ");
		 int id = sc.nextInt();
		 sc.nextLine();
		 
		 System.out.print("Enter old Password: ");
		 String oldPass = sc.nextLine();
		 
		 System.err.print("Enter new Password: ");
		 String newPass = sc.nextLine();
		 
		 ptUpdate.setString(1, newPass);
		 ptUpdate.setInt(2, id);
		 ptUpdate.setString(3, oldPass);
		 
		 int rows = ptUpdate.executeUpdate();
		 
		 if(rows > 0) {
			 System.out.println("Password updated successfully!");
		 }else {
			 System.out.println("Invalid id or password.");
		 }
		 
		 
	 }
	 
	 
}
