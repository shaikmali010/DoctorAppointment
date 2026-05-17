package DoctorAppointment;
import java.sql.*;
import java.util.*;

public class DoctorService {
			public static ArrayList<String> docName = new ArrayList<>();
	
       static void DocTable(Connection con ) throws SQLException {
		
		String createTable = """
				 CREATE TABLE IF NOT EXISTS doctors ( 
				 
					 doc_id INT PRIMARY KEY AUTO_INCREMENT, 
					 name VARCHAR(30),
					 
					 specialty ENUM(
					 'General', 
					 'Dental', 
					 'Skin') 
					 NOT NULL,
									 
					 fee DECIMAL(10,2),
					 
					 user_id INT UNIQUE,
					 
					 FOREIGN KEY(user_id)
					 REFERENCES users(user_id)
					 );
				""";
		
		try(PreparedStatement spCreate = con.prepareStatement(createTable)) {
		
			spCreate.executeUpdate();
			System.out.println("The Doctor table created successfully!\n");
		
		}
       }
		
	 
//	 --------------------------------------------------------------------------------
//	 Doctor =========================================================================

	 
//	 TOTAL DOCTOR -------------------------------------------------------------------
	 public static int Count(Connection con) throws Exception {
		 
		 int count = 0;
		 String countQuery = 
				 "SELECT COUNT(*) AS count FROM doctors";
		 
		 try {
			 PreparedStatement psCount = con.prepareStatement(countQuery);
			 
			 ResultSet rs = psCount.executeQuery();
			 
			 if(rs.next()) {
				count = rs.getInt("count");
			 }
			 
		 }catch (Exception e) {
			 e.printStackTrace();
		 }
		 
		 return count;
	 }
	 
//	 ADDING DOCTOR --------------------------------------------------------------------
	public static void AddDoctor(Connection con) throws Exception{
		
		 Scanner sc = new Scanner(System.in);
		 
		 double fee = 0;
		 
		 
		 String createDoc = 
				 "INSERT INTO doctors(name, specialty, user_id,  fee) VALUES(?, ?, ?, ?)";
		 
		 int totalDoctor = Count(con);
		 
		  System.out.println("Number of Doctors: ");
		 int n = sc.nextInt();
		 sc.nextLine();
		 
		 final int MAX_DOCTORS = 3;
		 
		 if(totalDoctor + n > MAX_DOCTORS) {
			 System.out.println("No Vacancies!");
			 return;
		 }
		 
		  PreparedStatement psAdd = 
				 con.prepareStatement(createDoc);
		  
		  System.out.println("Spcialty(General, Dental, Skin)");
		 
		  for(int i = 1; i <= n; i++) {
			
		 System.out.println("\nDoctor "+i);
		  
		 System.out.print("Enter doctor name: ");
		 String name = sc.nextLine();
		 
		 System.out.print("Enter doctor password: ");
		 String password = sc.nextLine();
		 
		    boolean hasUpper = false;
	        boolean hasLower = false;
	        boolean hasDigit = false;
	        boolean hasSpecial = false;

	        if (password.length() >= 8) {

	            for (int j = 0; j < password.length(); j++) {
	                char ch = password.charAt(j);

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
	                return;
	            }

	        } else {
	            System.out.println("Password must be at least 8 characters!");
	            return;
	        }

//		 Create user account
		 String userQuery =
		 """
		 INSERT INTO users
		 (username,password,role)
		 VALUES(?,?,?)
		 """;

		 PreparedStatement psUser =
		 con.prepareStatement(
		         userQuery,
		         Statement.RETURN_GENERATED_KEYS);

		 psUser.setString(1, name);
		 psUser.setString(2, password);
		 psUser.setString(3, "DOCTOR");

		 psUser.executeUpdate();

		 ResultSet generated =
		         psUser.getGeneratedKeys();

		 int userId = 0;

		 if(generated.next()) {
		     userId = generated.getInt(1);
		 }
		 
		 
		  System.out.print("Enter specialty: ");
				 String specialty = sc.nextLine();		
				 
				 specialty = specialty.trim();
				 specialty = specialty.substring(0,1).toUpperCase()
						     + specialty.substring(1).toLowerCase();
				 
				 if(!(specialty.equals("General") 
						 || specialty.equals("Dental")
						 || specialty.equals("Skin"))) {
					 
					 System.out.println("Invalid specialty!");
					 return;
					 
				 }
				 
				 if(specialty.equals("General")) {
					
					fee = 150;
					
				}else if(specialty.equals("Dental")) {
					
					fee = 250;
					
				}else if(specialty.equals("Skin")) {
					
					fee = 200;
				}
				
				psAdd.setString(1, name);
				psAdd.setString(2, specialty);
				psAdd.setInt(3, userId);
				psAdd.setDouble(4,fee);
				
				 psAdd.addBatch();
				 
				 generated.close();
				 psUser.close();
				 
				 
				 }		  
				  
				  
				 psAdd.executeBatch();
				 psAdd.close();
				 
				System.out.println("\nThe doctors data inserted successfully!");
			 }
	
//	MODIFY THE NAME BY ID ------------------------------------------------------------------------
	public static void ModifyName(Connection con) throws Exception {
		Scanner sc = new Scanner(System.in);
		
		String Modify = "UPDATE doctors SET name = ? WHERE doc_id = ?";
		PreparedStatement psUpdate = con.prepareStatement(Modify);
		
		System.out.println("Enter the id: ");
		 int id = sc.nextInt();
		 sc.nextLine();
		 
		 if(id >= 4) {
			 System.out.println("Invalid Doctor id\n");
			 return;
		 }
		
		System.out.println("Enter the name: ");
			String name = sc.nextLine();
			
			psUpdate.setString(1, name);
			psUpdate.setInt(2, id);
			
			int rows = psUpdate.executeUpdate();
			System.out.println(rows + " row updates");
			psUpdate.close();
	}
	
	
//	Modify Doctor SPECIALTY BY ID ------------------------------------------------------------------
	public static void ModifySpecialty(Connection con) {
		Scanner sc = new Scanner(System.in);
		
		String Modify = "UPDATE doctors SET specialty = ? WHERE doc_id = ?";
		
		try{
			PreparedStatement psUpdate = con.prepareStatement(Modify);
			
			System.out.println("Enter the id: ");
			 int id = sc.nextInt();
			 sc.nextLine();
			 
			 if(id >= 4) {
				 System.out.println("Invalid Doctor id\n");
				 return;
			 }
			 
			
			
			System.out.println("Enter new Spetialty: ");
			String specialty = sc.nextLine();
			
			specialty = specialty.substring(0,1).toUpperCase()
					    +specialty.substring(1).toLowerCase();
			
			if(!(specialty.equals("General")
					|| specialty.equals("Dental")
					|| specialty.equals("Skin"))) {
				System.out.println("Invalid specialty! "+"Only General, Dental, Skin allowed.");
				
				return;
			}
			
			
			psUpdate.setString(1, specialty);
			psUpdate.setInt(2, id);
			
			int rows = psUpdate.executeUpdate();
			System.out.println(rows + " row updates");
			psUpdate.close();
			
			
		}catch(Exception e) {
			if(e.getMessage().contains("Data truncates")) {
				System.out.println("Invalid specialty entered!");
			}else {
				System.out.println("Data error occured!");
				e.printStackTrace();
			}
		}
		
	}
	
//	MODIFY THE DOCTOR FEE BY ID -------------------------------------------------------------------------
	public static void ModifyFee(Connection con) throws Exception{
       Scanner sc = new Scanner(System.in);
		
		String Modify = "UPDATE doctors SET fee = ? WHERE doc_id = ?";
		PreparedStatement psUpdate = con.prepareStatement(Modify);
		
		System.out.println("Enter the id: ");
		 int id = sc.nextInt();
		 sc.nextLine();
		 
		 if(id >= 4) {
			 System.out.println("Invalid Doctor id\n");
			 return;
		 }
		
		System.out.println("Enter new Fee: ");
			String fee = sc.nextLine();
			
			psUpdate.setString(1, fee);
			psUpdate.setInt(2, id);
			
			int rows = psUpdate.executeUpdate();
			System.out.println(rows + " row updates");
			psUpdate.close();
	}
	
	 
//	SHOWING DOCTOR RECORDS --------------------------------------------------------------
	 public static void showDoc(Connection con) throws SQLException{
		 
			 
			 String showQuery = "SELECT * FROM doctors";
			 PreparedStatement psShow = con.prepareStatement(showQuery);
			 ResultSet rs = psShow.executeQuery();
			 
			 System.out.println("---------------------------------------");
			 System.out.printf("%-5s %-20s %-15s%n",
			                   "ID", "Doctor Name", "Specialist");
			 System.out.println("---------------------------------------");

			 while(rs.next()) {
			     System.out.printf("%-5d %-20s %-15s%n",
			             rs.getInt("doc_id"),
			             rs.getString("name"),
			             rs.getString("specialty"));
			 }

			 System.out.println("---------------------------------------");
				
				
				rs.close();
				psShow.close();
				
				
	 }
	 
//	 DELETE THE RECORD ----------------------------------------------------------------
	 public static void DeleteDoc(Connection con) {
		 
		 Scanner sc = new Scanner(System.in);
		 
		 String deleteQuery = 
				 "DELETE FROM doctors WHERE doc_id = ?";
		 
		 System.out.println("DELETING THE RECORD\n");
		 System.out.println("Enter the id: ");
		 int id = sc.nextInt();
		 sc.nextLine();
		 
		 try(PreparedStatement psDelete = con.prepareStatement(deleteQuery)){
			 psDelete.setInt(1, id);
			 psDelete.executeUpdate();
			 psDelete.close();
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 
}

