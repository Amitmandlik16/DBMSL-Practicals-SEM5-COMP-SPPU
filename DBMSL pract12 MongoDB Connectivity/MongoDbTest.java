/*
Title: Write a program to implement Mongo DB database connectivity with any front end language
to implement Database navigation operations(add, delete, edit etc.)
*/


//command to execute on terminal
//javac -cp ".;mongodb-driver-sync-5.1.4.jar;mongodb-driver-core-5.1.4.jar;bson-5.1.4.jar" MongoDbTest.java
//java -cp ".;mongodb-driver-sync-5.1.4.jar;mongodb-driver-core-5.1.4.jar;bson-5.1.4.jar" MongoDbTest

import java.util.Scanner;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

public class MongoDbTest {

	public static void main(String[] args) {
		String url="mongodb://localhost:27017";
		//connect to the MongoDb server
		try(MongoClient mongoclient=MongoClients.create(url))
		{
			//Acess the Database
			MongoDatabase database =mongoclient.getDatabase("db");
			System.out.println("Connected to MongoDB database :"+database.getName());
			MongoCollection<Document>collection=database.getCollection("student");
			Scanner sc=new Scanner(System.in);
			int roll;
			String name,branch;;
			int choice,y=1;
			while(y==1)
			{
				
				System.out.println("\nEnter the choice\n0.Exit\n1.View\n2.Insert\n3.Update\n4.Delete");
				choice=sc.nextInt();
				switch(choice)
				{
				case 0:
					y=0;
					break;
				case 1:
					System.out.println("student Collection Data displaying");
					MongoCursor<Document>cursor=collection.find().iterator();
					while(cursor.hasNext())
					{
						System.out.println(cursor.next().toJson());
					}
					break;
				case 2:					
					System.out.println("Enter the Roll number ,Name,Branch to insert");
					roll=sc.nextInt();
					name=sc.next();
					branch=sc.next();
					//create new document
					Document stud=new Document("Roll",roll).append("Name",name).append("Branch",branch);
					collection.insertOne(stud);
					System.out.println("Document Inserted sucessfully");
					break;
				case 4:					
					System.out.println("Enter the Roll to delete document");
					roll=sc.nextInt();
					DeleteResult result=collection.deleteOne(Filters.eq("Roll",roll));
					if(result.getDeletedCount()>0)
					{
						System.out.println("Document Deleted sucessfully");
					}
					else
					{
						System.out.println("No Document with ROllno"+roll+" having to delete");
					}
					break;
				case 3:					
					System.out.println("Enter the Roll number for which Name,Branch to update");
					roll=sc.nextInt();
					name=sc.next();
					branch=sc.next();
					collection.updateOne(Filters.eq("Roll",roll),Updates.combine(Updates.set("Name",name),Updates.set("Branch",branch)));
					System.out.println("Document Updated sucessfully");
					break;
				default:
					System.out.println("Invalid Choice");
					break;
				}
			}
			System.out.println("Connection closed sucessfully");
			System.out.println("Thank you");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
