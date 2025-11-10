package jdbc.connection;

import java.sql.*; //1st step
public class JdbcConnectivity {

	public static void main(String[] args) {
				
				String url = "jdbc:postgresql://localhost:5432/student_portal";
				String username = "postgres";
				String password = "jeevitha";
				
				
				try {
					
					//Load the driver - 2nd step
					Class.forName("org.postgresql.Driver");
					
					
					//Establishing the connection - 3rd step
					
					Connection con = DriverManager.getConnection(url, username, password);
					
					//Define SQL Query - 4th step
					
					Statement st = con.createStatement();
					
					//Execting the query - 5th step
					
					
					String query = "SELECT * FROM student_details_new";
					
					//Process the Result - 6th Step
					
					ResultSet rs = st.executeQuery(query);
					
					
					while(rs.next())
						
					{
						String table = rs.getInt(3) +":" + rs.getString(1) + ":" + rs.getString(2) + ":" + rs.getInt(4)+ rs.getDate(5) + ":" + rs.getString(5) + ":" + rs.getString(5);
						System.out.println(table);
					}

					
					//close the resources - 7th
					
					rs.close();
					st.close();
					con.close();
					
					
				}
				

				catch(Exception e)
				{
					e.printStackTrace();
				}
				

			}

		}

