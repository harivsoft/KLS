package com.vsoftcorp.kls.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class GetDBPropertiesServlet {

	private static final long serialVersionUID = -2156179601256068005L;

	public GetDBPropertiesServlet() {
	}

	public void init() {
		System.out.println("111111111111111111111111111111111111111111111111111111");
		String url = "jdbc:postgresql://192.168.1.193/";
		String dbName = "kls";
		String driver = "org.postgresql.Driver";
		String userName = "postgres";
		String password = "postgres";

		try {
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url + dbName, userName, password);
			
			System.out.println("conn::::"+conn);
			
			Statement stmt = conn.createStatement();

		      String sql = "SELECT * FROM kls_schema.kls_properties";
		      ResultSet rs = stmt.executeQuery(sql);
		      
		      ResultSetMetaData md = rs.getMetaData();
		      int columns = md.getColumnCount();
		      ArrayList list = new ArrayList(50);
		      while (rs.next()){
		         HashMap row = new HashMap(columns);
		         for(int i=1; i<=columns; ++i){           
		          row.put(md.getColumnName(i),rs.getObject(i));
		         }
		          list.add(row);
		      }
		      
		      System.out.println("list siz---"+list.size());
		      System.out.println("list ele==="+list.get(0));
		      
		     /* while(rs.next()){
		         //Retrieve by column name
		         int id  = rs.getInt("id");
		         String age = rs.getString("key");
		         String first = rs.getString("value");
		         String last = rs.getString("description");

		         //Display values
		         System.out.print("ID: " + id);
		         System.out.print(", Age: " + age);
		         System.out.print(", First: " + first);
		         System.out.println(", Last: " + last);
		      }*/
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}