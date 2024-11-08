/*
Title: Implement MYSQL/Oracle database connectivity with PHP/ python/Java Implement
Database navigation operations (add, delete, edit,) using ODBC/JDBC.
*/


import java.sql.*;
import java.util.Scanner;

//Commands to execute on command line 
//>javac -cp ".;mysql-connector-j-9.1.0.jar" MySqlTest.java
//>java -cp ".;mysql-connector-j-9.1.0.jar" MySqlTest

public class MySqlTest {

	public static void main(String[] args) {
		Connection connection=null;
		Statement statement=null;
		ResultSet resultset=null;
		try
		{
			//1.loading and registering the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded and registerd sucessfully");
			
			//2.establishing the connection with the database
			String url="jdbc:mysql://localhost:3306/dbmsl";
			//username and password vary from user to user
			String userName="root";
			String passWord="root";
			connection=DriverManager.getConnection(url,userName,passWord);
			System.out.println("connection established sucessfully");
			Scanner sc=new Scanner(System.in);
			int choice,y=1;
			int roll,mark;
			String name;
			while(y==1)
			{
				System.out.println("Enter the choice\n0.Exit\n1.View\n2.Insert\n3.Update\n4.Delete");
				choice=sc.nextInt();
				switch(choice)
				{
				case 0:
					System.out.println("Thank you!");
					y=0;
					break;
				case 1:
					String sqlSelectQuery="select Rollno,Name,Marks from student order by(Rollno)";
					statement=connection.createStatement();
					resultset=statement.executeQuery(sqlSelectQuery);
					System.out.println("Rollno\tName\tMarks");
					while(resultset.next())
					{
						Integer id =resultset.getInt(1);
						String names=resultset.getString(2);
						Integer marks=resultset.getInt(3);
						System.out.println(id+"\t"+names+"\t"+marks);
					}
					break;
				case 2:
					System.out.println("Enter Rollno,Name,Marks to Insert");
					roll=sc.nextInt();
					name=sc.next();
					mark=sc.nextInt();
					String sqlInsertQuery=String.format("insert into student(Rollno,Name,Marks)values('%d','%s','%d')",roll,name,mark);
					statement=connection.createStatement();
					int InsertrowAffected=statement.executeUpdate(sqlInsertQuery);
					System.out.println("Row Inserted :"+InsertrowAffected);
					break;
				case 3:
					System.out.println("Enter Rollno and their Name,Marks for Updating ");
					roll=sc.nextInt();
					name=sc.next();
					mark=sc.nextInt();
					String sqlUpdateQuery=String.format("update student set Name='%s',Marks=%d where Rollno=%d",name,mark,roll);
					statement=connection.createStatement();
					int UpdaterowAffected=statement.executeUpdate(sqlUpdateQuery);
					System.out.println("Row Updated :"+UpdaterowAffected);
					break;
				case 4:
					System.out.println("Enter Rollno to delete");
					roll=sc.nextInt();
					String sqlDeleteQuery = String.format("DELETE FROM student WHERE Rollno = %d", roll);
					statement=connection.createStatement();
					int DeleterowAffected=statement.executeUpdate(sqlDeleteQuery);
					System.out.println("Row Deleted :"+DeleterowAffected);
					break;
				default:
					System.out.println("Enter the correct choice");
					break;
					
					
				}
			}
			
			
			
			
			
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					connection.close();
					System.out.println("Connection closed sucessfully");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
