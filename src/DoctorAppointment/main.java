package DoctorAppointment;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class main {
	
	
	public static void main(String[] args) throws Exception{
		
		
		Connection con = JdbcConnector.getConnection();
		
		DoctorService service = new DoctorService();
		AppointmentService appointment = new AppointmentService();
		Users user = new Users();
		Scanner sc = new Scanner(System.in);
	    boolean run = true;
	    
	    while(run) {
	    	System.out.println("-------------------------");
	    	System.out.println("WELCOM TO PUBLIC HOSPITAL");
	    	System.out.println("-------------------------");
	    	System.out.println("1. Patient");
	    	System.out.println("2. Admin");
	    	System.out.println("3. Exit");
	    	System.out.println("-----------");
	    	
	    	System.out.print("Choose an option: ");
	    	int select = sc.nextInt();
	    	sc.nextLine();
	    	
	    	switch(select) {
	    	
	    	case 1: 
	    		
	    		boolean runs = true;
	    		
	    		while(runs) {
	    			
	    		
	    		System.out.println("----------------------");
	    		System.out.println(" DOCTOR APPOINTMENT ");
	    		System.out.println("----------------------");
	    		System.out.println("Welcome to the Public Hospital Appointment System");
	    		System.out.println("Book appointments quickly with available doctors.");
	    		System.out.println("You can choose:");
	    		System.out.println("1. View Doctor details and Schedule an appointment");
	    		System.out.println("2. View appointment details");
	    		System.out.println("3. Cancel appointments");
	    		System.out.println("4. Go to main menu -->");
	    		System.out.println("----------------------");
	    		System.out.println("Enter the option: ");
	    		int choose = sc.nextInt();
	    		sc.nextLine();
	    		
	    		switch(choose) {
	    		
	    		case 1:
	    			service.showDoc(con);
	    			appointment.BookAppointment(con);
	    			break;
	    			
	    		case 2:
	    			appointment.ShowDetails(con);
	    			break;
	    			
	    		case 3:
	    			appointment.CancelAppat(con);
	    			break;
	    			
	    		case 4: 
	    			runs = false;
	    			break;
	    			
	    			default :
	    				System.out.println("Enter valid number!");
	    			
	    		}
	    		}
	    		break;
	    		
	    	case 2: 
	    				    		
	    		boolean runs1 = true;
	    		while(runs1) {
	    			
	    			System.out.println("----------------------");
	    		System.out.println(" ADMIN PORTAL ");
	    		System.out.println("----------------------");

	    		System.out.println("Welcome Admin!");
	    		System.out.println("Manage hospital operations efficiently.");
	    		System.out.println("You can choose:");
	    		System.out.println("1. Add new doctors");
	    		System.out.println("2. View doctor details");
	    		System.out.println("3. Modify Doctor details");
	    		System.out.println("4. Add and manage patient information");
	    		System.out.println("5. Go to main menu -->");
	    		System.out.println("----------------------");
	    		System.out.print("Enter the Option: ");
	    		int choose1 = sc.nextInt();
	    		sc.nextLine();
	    		
	    			switch(choose1) {
	    			
	    			case 1:
	    				service.AddDoctor(con);
	    				
	    				break;
	    			case 2:
	    				service.showDoc(con);
	    				break;
	    				
	    			case 3:
	    				
	    				boolean runs2 = true;
	    				
	    				while(runs2) {
	    				System.out.println("---------------------");
	    				System.out.println("Modify doctor details");
	    				System.out.println("---------------------");
	    				System.out.println("You can choose:");
	    				System.out.println("1. Modify name");
	    				System.out.println("2. Modify specialty");
	    				System.out.println("3. Modify fee");
	    				System.out.println("4. Go to main menu");
	    				System.out.println("---------------------");
	    				System.out.print("Enter the Option: ");
	    				int select2 = sc.nextInt();
	    				sc.nextLine();
	    				
	    				switch(select2) {
	    				
	    				case 1:
	    					service.ModifyName(con);
	    				break;
	    				
	    				case 2:
	    					service.ModifySpecialty(con);
	    					break;
	    					
	    				case 3:
	    					service.ModifyFee(con);
	    					break;
	    					
	    				case 4:
	    					runs2 = false;
	    					break;
	    					
	    					default:
	    						System.out.println("Enter valid number!");
	    					
	    				}
	    				}
	    				break;
	    				
	    			case 4:
	    				
	    				boolean runs3 = true;
	    				
	    				while(runs3) {
	    					
	    				System.out.println("-------------------------------");
	    				System.out.println("APPOINTMENTS");
	    				System.out.println("--------------");
	    				System.out.println("Manage appointment scheduling");
	    				System.out.println("between patients and doctors.");
	    				System.out.println("Choose features:");
	    				System.out.println("1. Schedule appointments");
	    				System.out.println("2. View appointment information");
	    				System.out.println("3. Update appointment details");
	    				System.out.println("4. Cancel appointments");
	    				System.out.println("5. Go to main menu -->");
	    				System.out.println("-------------------------------");
	    				System.out.println("Enter the option: ");
	    				int select3 = sc.nextInt();
	    				sc.nextLine();
	    				
	    					switch(select3) {
	    					case 1:
	    						appointment.BookAppointment(con);
	    						break;
	    						
	    					case 2:
	    						appointment.ShowAllDetails(con);
	    						break;
	    						
	    					case 3:
	    						boolean run1 = true;
	    						while(run1) {
	    							System.out.println("---------------------------");
	    							System.out.println("Update Appointment Details");
	    							System.out.println("---------------------------");
	    							System.out.println("1. Change the patient name.");
	    							System.out.println("2. Change the doctor id.");
	    							System.out.println("3. Change the patient age.");
	    							System.out.println("4. Change the appointment date.");
	    							System.out.println("5. Go to main menu -->");
	    							System.out.println("----------------------------");
	    							System.out.println("Enter the Choice: ");
	    							int choice = sc.nextInt();
	    							sc.nextLine();
	    							
	    							switch(choice) {
	    							
	    							case 1:
	    								System.out.println("Enter the patient id: ");
	    								int id = sc.nextInt();
	    								sc.nextLine();
	    								
	    								appointment.ShowDetailsById(con, id);
	    								appointment.ChangeName(con, id);
	    							break;
	    							
	    							case 2: 
	    								
	    								System.out.println("Enter the patient id: ");
	    								int id1 = sc.nextInt();
	    								sc.nextLine();
	    								
	    								service.showDoc(con);
	    								appointment.ChangeID(con, id1);
	    								
	    							break;
	    							
	    							case 3:
	    								
	    								System.out.println("Enter the patient id: ");
	    								int id2 = sc.nextInt();
	    								sc.nextLine();
	    								
	    								appointment.ChangeAge(con, id2);
	    								
	    							break;
	    							
	    							case 4:
	    							
	    							 appointment.UpdateDate(con);
	    							 
	    							break;
	    							
	    							case 5:
	    								run1 = false;
	    								break;
	    								
	    							default :
	    								System.out.println("Enter the valid number!");
	    								
	    							}
	    						}
	    					case 4: 
	    						appointment.CancelAppat(con);
	    						break;
	    						
	    					case 5:
	    						runs3 = false;
	    						break;
	    						
	    					}
	    				}
	    				
	    			case 5: 
	    				runs1 = false;
	    				break;
	
	    			}
	    		}
	    		break;
	    		
	    	case 3 :	
	    		run = false;
	    		System.out.println("Exiting..................");
	    		break;
	    		
	    	}
	    }
		
		
		
		
//		
//		
//		
//		
		
		
//		user.UserTable(con);
//		user.addUser(con);
//		user.ShowAllUser(con);
//		user.UpdateUser(con);
		
//		service.DocTable(con);

//		
}
}
