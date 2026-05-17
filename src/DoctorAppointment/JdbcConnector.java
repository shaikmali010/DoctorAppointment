package DoctorAppointment;
import java.sql.*;

public class JdbcConnector {
      
	static final String url = "jdbc:mysql://localhost:3306/hospital_database";
	static final String root = "root";
	static final String password = "Shaikali@270";
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, root, password);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
