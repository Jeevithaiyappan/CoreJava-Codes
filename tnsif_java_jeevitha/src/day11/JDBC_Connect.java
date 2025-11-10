rpackage Day11JDBCConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PreparedStatementDemo {

	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/student_portal";
		String username = "postgres";
		String password = "janani@2204";
		
		int studentid = 7;
		String firstName = "viji";
		String lastName = "G";
		int age = 20;
		String dobStr = "2004-04-10";
		java.sql.Date dob = java.sql.Date.valueOf(dobStr);
		String department = "EEE";
		String email = "viji08@gmail.com";
		
		
		String query = "INSERT INTO studentdetails(student_id, first_name, last_name, age, dob, department, email) VALUES(?, ?, ?, ?, ?, ?, ?) ";
		
		
		try {
			
			//Load the driver - 2nd step
			Class.forName("org.postgresql.Driver");
			
			
			//Establishing the connection - 3rd step
			
			Connection con = DriverManager.getConnection(url, username, password);
			
			//Define SQL Query - 4th step
			
			PreparedStatement st = con.prepareStatement(query);
			
			//Execting the query - 5th step
			st.setInt(1, studentid);
			st.setString(2, firstName);
			st.setString(3, lastName);
			st.setInt(4, age);
		
			
			
			//Process the Result - 6th Step
			
			int rs = st.executeUpdate();
			System.out.println(rs +" row/s affected");
			
			
			
			//close the resources - 7th
			
			st.close();
			con.close();
			
			
		}
		

		catch(Exception e)
		{
			e.printStackTrace();
		}
		


	}

}